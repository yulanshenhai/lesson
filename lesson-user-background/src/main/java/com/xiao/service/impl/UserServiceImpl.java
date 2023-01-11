package com.xiao.service.impl;

import com.xiao.entity.User;
import com.xiao.entity.VideoOrder;
import com.xiao.mapper.OrderMapper;
import com.xiao.mapper.UserMapper;
import com.xiao.mapper.VideoOrderMapper;
import com.xiao.param.*;
import com.xiao.service.UserService;
import com.xiao.util.*;
import com.xiao.vo.UserLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

/**
 * @author xiao
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int register(UserRegisterParam userRegisterParam) {

        // 检查必填参数
        if (NullUtil.hasNull(
                userRegisterParam.getUsername(),
                userRegisterParam.getPassword(),
                userRegisterParam.getPhone(),
                userRegisterParam.getRealName(),
                userRegisterParam.getIdCard())) {
            throw new RuntimeException("必填参数为空");
        }

        // 组装User实体
        User user = new User();
        BeanUtils.copyProperties(userRegisterParam, user);
        user.setNickName(BuildUtil.buildNickName());
        user.setPassword(EncryptionUtil.encryptPassword(user.getPassword()));
        user.setAvatar("default-user-avatar.jpg");
        user.setInfo("该用户很懒，没留下任何信息..");
        user.setGender(2);
        user.setAge(16);
        user.setCreateTime(new Date());
        user.setLastModify(new Date());

        // 添加用户记录
        return userMapper.insert(user);
    }

    @Override
    public UserLoginVo login(UserLoginParam userLoginParam) {

        String username = userLoginParam.getUsername();
        String password = userLoginParam.getPassword();

        // 检查必填参数
        if (NullUtil.hasNull(username, password)) {
            throw new RuntimeException("必要参数为空");
        }

        // 对密码进行MD5加密
        password = EncryptionUtil.encryptPassword(password);

        // 按账号密码查询User记录
        User user = encryptUser(userMapper.selectByUsernameAndPassword(username, password));

        if (null == user) {
            return null;
        }

        // 组装VO
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUser(user);
        userLoginVo.setToken(JwtUtil.build(
                user.getId(),
                user.getNickName(),
                user.getAvatar()));

        // 返回VO
        return userLoginVo;
    }

    @Override
    public User selectById(Integer userId) {
        return encryptUser(userMapper.selectByUserId(userId));
    }

    @Override
    public User selectByPhone(String phone) {
        return encryptUser(userMapper.selectByPhone(phone));
    }

    @Override
    public int updateByUserId(UserUpdateParam userUpdateParam) {
        if (null == userUpdateParam.getId()) {
            throw new RuntimeException("必要参数为空");
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateParam, user);
        return userMapper.updateByUserId(user);
    }

    @Override
    public int updatePasswordById(UserUpdatePasswordParam userUpdatePasswordParam) {

        Integer id = userUpdatePasswordParam.getId();
        String oldPassword = userUpdatePasswordParam.getOldPassword();
        String newPassword = userUpdatePasswordParam.getNewPassword();

        if (NullUtil.hasNull(id, oldPassword, newPassword)) {
            throw new RuntimeException("必要参数为空");
        }

        User user = this.selectUser(id);
        this.checkOldPassword(oldPassword, user);
        this.updateNewPassword(newPassword, user);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByUserId(UserDeleteParam userDeleteParam) {
        Integer userId = userDeleteParam.getUserId();
        if (null == userId) {
            throw new RuntimeException("必要参数为空");
        }
        this.checkUserExists(userId);
        this.delUserOrder(userId);
        this.delUser(userId);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateAvatarById(UserUpdateAvatarParam userUpdateAvatarParam) {
        Integer id = userUpdateAvatarParam.getId();
        MultipartFile avatarFile = userUpdateAvatarParam.getAvatarFile();
        if (NullUtil.hasNull(id, avatarFile)) {
            throw new RuntimeException("必要参数为空");
        }
        User user = userMapper.selectByUserId(id);
        if (null == user) {
            throw new RuntimeException("用户不存在");
        }
        FileUtil.deleteAvatarFromOss(user.getAvatar());
        String avatar = FileUtil.uploadAvatarToOss(avatarFile);
        return this.updateUserAvatar(avatar, id);
    }

    @Override
    public String getVerificationCode(String phone) {

        // 检查手机号是否存在
        User user = userMapper.selectByPhone(phone);
        if (null == user) {
            throw new RuntimeException("手机号不存在");
        }

        // 生成4位随机的验证码
        String verificationCode = BuildUtil.buildVerificationCode(4);

        // 获取一个单机的Jedis连接实例
        Jedis jedis = JedisStandaloneUtil.getJedis();

        // 将验证码存入Redis，时效5分钟
        jedis.setex(phone, 60 * 5, verificationCode);

        // 关闭Jedis连接以节省资源
        JedisStandaloneUtil.closeJedis(jedis);

        // 向指定手机号码发送验证码
        SmsUtil.sendSms(phone, verificationCode);

        // 将验证码响应给客户端
        return verificationCode;
    }

    @Override
    public UserLoginVo loginByPhone(UserLoginByPhoneParam userLoginByPhoneParam) {
        String phone = userLoginByPhoneParam.getPhone();
        String verificationCode = userLoginByPhoneParam.getVerificationCode();

        // 检查必要参数，若必要参数为空则直接抛出异常
        if (NullUtil.hasNull(phone, verificationCode)) {
            throw new RuntimeException("必要参数为空");
        }

        // 获取一个单机的Jedis连接实例
        Jedis jedis = JedisStandaloneUtil.getJedis();

        // 调用 `jedis.get(key)` 判断验证码是否正确，错误直接抛出异常
        String value = jedis.get(phone);
        if (!verificationCode.equals(value)) {
            throw new RuntimeException("验证码错误或已失效");
        }

        // 调用Mapper接口返回手机号对应的用户信息
        User user = userMapper.selectByPhone(phone);

        // 关闭Jedis连接以节省资源
        JedisStandaloneUtil.closeJedis(jedis);

        if (null == user) {
            return null;
        }
        // 组装VO
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUser(user);
        userLoginVo.setToken(JwtUtil.build(
                user.getId(),
                user.getNickName(),
                user.getAvatar()));
        // 登录成功返回对应的User信息
        return userLoginVo;
    }

    @Override
    public String selectPointsByUserId(Integer userId) {

        // 获取一个单机的Jedis连接实例
        Jedis jedis = JedisStandaloneUtil.getJedis();

        // 获取用户积分，错误直接抛出异常
        String points = jedis.get("points:user-" + userId);

        // 关闭Jedis连接以节省资源
        JedisStandaloneUtil.closeJedis(jedis);

        // 返回用户积分
        return points;
    }

    /**
     * 调用数据接口按主键查询用户记录，若用户不存在则抛异常
     *
     * @param userId User表主键
     */
    private void checkUserExists(Integer userId) {
        if (null == userMapper.selectByUserId(userId)) {
            throw new RuntimeException("用户不存在");
        }
    }

    /**
     * 按主键修改User表的头像地址
     *
     * @param avatar 头像地址
     * @param userId User表主键
     * @return 影响条目数
     */
    private int updateUserAvatar(String avatar, Integer userId) {
        User user = new User();
        user.setId(userId);
        user.setAvatar(avatar);
        return userMapper.updateByUserId(user);
    }

    /**
     * 将User实体数据中的手机号码和身份证号码脱敏后返回
     *
     * @param user 包含手机号码和身份证号码的User实体
     * @return 将User实体数据中的手机号码和身份证号码脱敏后的User实体
     */
    private User encryptUser(User user) {
        if (null == user) {
            return null;
        }
        String phone = user.getPhone();
        String idCard = user.getIdCard();
        if (!NullUtil.hasNull(phone, idCard)) {
            user.setPhone(EncryptionUtil.encryptPhone(phone));
            user.setIdCard(EncryptionUtil.encryptIdCard(idCard));
        }
        return user;
    }

    /**
     * 删除该用户的User表记录
     *
     * @param userId User主键
     */
    private void delUser(Integer userId) {
        if (userMapper.deleteByUserId(userId) <= 0) {
            throw new RuntimeException("用户删除操作异常");
        }
    }

    /**
     * 删除该用户关联的全部VideoOrder表记录和Order表记录
     *
     * @param userId User主键
     */
    private void delUserOrder(Integer userId) {

        // 查询该用户关联的全部VideoOrder表记录
        List<VideoOrder> videoOrders = videoOrderMapper.selectByUserId(userId);
        if (null != videoOrders && !videoOrders.isEmpty()) {

            // 删除该用户关联的VideoOrder表记录，若不存在则略过
            videoOrderMapper.deleteByUserId(userId);

            // 删除该用户关联的Order表记录，若不存在则略过
            for (VideoOrder videoOrder : videoOrders) {
                orderMapper.deleteByOrderId(videoOrder.getOrderId());
            }
        }
    }

    /**
     * 查询用户记录
     *
     * @param userId User主键
     * @return 用户记录，若不存在则抛出异常
     */
    private User selectUser(Integer userId) {
        User user = userMapper.selectByUserId(userId);
        if (null == user) {
            throw new RuntimeException("用户不存在");
        }
        return user;
    }

    /**
     * 修改该用户的密码为新密码，新密码需要加密和入库
     *
     * @param newPassword 新密码
     * @param user        User实体
     */
    private void updateNewPassword(String newPassword, User user) {
        newPassword = EncryptionUtil.encryptPassword(newPassword);
        user.setPassword(newPassword);
        if (userMapper.updateByUserId(user) <= 0) {
            throw new RuntimeException("修改密码异常");
        }
    }

    /**
     * 检查该用户的原密码是否正确，密码需要加密后比对
     *
     * @param oldPassword 用户输入的原密码
     * @param user        用户实体
     */
    private void checkOldPassword(String oldPassword, User user) {
        oldPassword = EncryptionUtil.encryptPassword(oldPassword);
        if (!oldPassword.equals(user.getPassword())) {
            throw new RuntimeException("原密码有误");
        }
    }
}
