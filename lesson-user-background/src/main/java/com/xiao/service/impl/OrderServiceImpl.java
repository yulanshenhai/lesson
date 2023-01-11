package com.xiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiao.entity.Order;
import com.xiao.entity.VideoOrder;
import com.xiao.mapper.OrderMapper;
import com.xiao.mapper.UserMapper;
import com.xiao.mapper.VideoMapper;
import com.xiao.mapper.VideoOrderMapper;
import com.xiao.param.OrderDeleteParam;
import com.xiao.param.OrderInsertParam;
import com.xiao.param.OrderPageParam;
import com.xiao.server.PointsServer;
import com.xiao.service.OrderService;
import com.xiao.util.BuildUtil;
import com.xiao.util.NullUtil;
import com.xiao.vo.OrderPageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.Future;

/**
 * @author xiao
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private PointsServer pointsServer;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(OrderInsertParam orderInsertParam) {

        Integer[] videoIds = orderInsertParam.getVideoIds();
        Integer userId = orderInsertParam.getUserId();
        Double totalFee = orderInsertParam.getTotalFee();
        String info = orderInsertParam.getInfo();

        if (NullUtil.hasNull(videoIds, userId, totalFee)) {
            throw new RuntimeException("必要参数为空");
        }

        this.checkUserExists(userId);
        this.checkVideoExists(videoIds);
        this.checkRepeatBuying(userId, videoIds);
        Integer orderId = this.addOrder(totalFee, info);
        this.addVideoOrder(userId, orderId, videoIds);
        this.incrByPoints(userId, totalFee);
        return 1;
    }

    @Override
    public OrderPageVo pageDetailByUserId(Integer userId, Integer page, Integer size){

        this.checkUserExists(userId);
        // 分页查询VideoOrder记录
        PageHelper.startPage(page, size);
        PageInfo<VideoOrder> videoPageInfo = new PageInfo<>(videoOrderMapper.selectDetailByUserId(userId));

        // 组装VO
        OrderPageVo orderPageVo = new OrderPageVo();
        orderPageVo.setTotal(videoPageInfo.getTotal());
        orderPageVo.setPageNum(videoPageInfo.getPageNum());
        orderPageVo.setPageSize(videoPageInfo.getPageSize());
        orderPageVo.setPages(videoPageInfo.getPages());
        orderPageVo.setVideoOrders(videoPageInfo.getList());

        // 返回VO
        return orderPageVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteById(OrderDeleteParam orderDeleteParam) {
        int orderId = orderDeleteParam.getOrderId();
        this.checkOrderExists(orderId);
        this.deleteVideoOrder(orderId);
        this.deleteOrder(orderId);
        return 1;
    }

    /**
     * 检查用户是否存在，不存在直接抛出异常
     *
     * @param userId User主键
     */
    private void checkUserExists(Integer userId) {
        if (null == userMapper.selectByUserId(userId)) {
            throw new RuntimeException("用户不存在");
        }
    }

    /**
     * 检查要购买的视频是否全部存在，有一个不存在就抛出异常
     *
     * @param videoIds 视频主键数组
     */
    private void checkVideoExists(Integer[] videoIds) {
        if (videoMapper.selectByIds(videoIds).size() < videoIds.length) {
            throw new RuntimeException("至少有一个视频不存在");
        }
    }

    /**
     * 检查用户是否重复购买视频，重复购买直接抛异常
     *
     * @param userId   User主键
     * @param videoIds Video主键数组
     */
    private void checkRepeatBuying(Integer userId, Integer[] videoIds) {
        if (!videoOrderMapper.selectByUserIdAndVideoIds(userId, videoIds).isEmpty()) {
            throw new RuntimeException("至少有一个视频已购买");
        }
    }

    /**
     * 添加Order表记录，若添加失败则抛出异常触发回滚
     *
     * @param totalFee 订单总价
     * @param info     订单信息
     * @return Order主键
     */
    private Integer addOrder(Double totalFee, String info) {

        Order order = new Order();
        order.setNumber(BuildUtil.buildUuid());
        order.setState(1);
        order.setTotalFee(totalFee);
        order.setInfo(StringUtils.isBlank(info) ? "该订单暂无描述信息" : info);
        order.setCreateTime(new Date());
        order.setLastModify(new Date());

        // 添加Order表记录，若添加失败则抛出异常触发回滚
        if (orderMapper.insert(order) <= 0) {
            throw new RuntimeException("Order表添加异常");
        }

        Integer orderId = order.getId();
        if (null == orderId) {
            throw new RuntimeException("Order表主键为空：可能是回注异常");
        }

        return orderId;
    }

    /**
     * 循环添加VideoOrder中间表数据
     *
     * @param userId   User主键
     * @param orderId  Order主键
     * @param videoIds Video主键数组
     */
    private void addVideoOrder(Integer userId, Integer orderId, Integer[] videoIds) {

        // 循环添加VideoOrder中间表数据
        for (Integer videoId : videoIds) {

            // 准备VideoOrder数据
            VideoOrder videoOrder = new VideoOrder();
            videoOrder.setVideoId(videoId);
            videoOrder.setOrderId(orderId);
            videoOrder.setUserId(userId);
            videoOrder.setInfo(userId + "号用户在" + orderId + "号订单中购买了" + videoId + "号视频");
            videoOrder.setCreateTime(new Date());
            videoOrder.setLastModify(new Date());

            // 调用数据接口循环添加VideoOrder表记录，若添加失败则抛出异常触发回滚
            if (videoOrderMapper.insert(videoOrder) <= 0) {
                throw new RuntimeException("VideoOrder表添加异常");
            }
        }
    }

    /**
     * 调用数据接口按主键查询订单记录，若订单不存在则抛异常
     *
     * @param orderId Order表主键
     */
    private void checkOrderExists(Integer orderId) {
        if (null == orderMapper.selectById(orderId)) {
            throw new RuntimeException("订单不存在");
        }
    }

    /**
     * 调用数据接口删除该订单的Order记录
     *
     * @param orderId Order表主键
     */
    private void deleteOrder(Integer orderId) {
        if (orderMapper.deleteByOrderId(orderId) <= 0) {
            throw new RuntimeException("Order删除失败");
        }
    }

    /**
     * 调用数据接口删除该订单的所有VideoOrder表记录
     *
     * @param orderId Order表主键
     */
    private void deleteVideoOrder(Integer orderId) {
        if (videoOrderMapper.deleteByOrderId(orderId) <= 0) {
            throw new RuntimeException("VideoOrder删除失败");
        }
    }

    /**
     * 下单成功后为用户异步添加积分
     *
     * @param userId   User主键
     * @param totalFee 订单总金额
     */
    private void incrByPoints(Integer userId, double totalFee) {
        Future<String> future = pointsServer.incrByPoints(userId, totalFee);
        while (true) {
            try {
                if (future.isDone()) {
                    log.info(future.get());
                    break;
                }
            } catch (Exception e) {
                throw new RuntimeException("异步添加积分异常");
            }
        }
    }
}
