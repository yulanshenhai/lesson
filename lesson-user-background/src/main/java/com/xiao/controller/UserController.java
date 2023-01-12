package com.xiao.controller;

import com.xiao.annotation.Token;
import com.xiao.entity.User;
import com.xiao.param.*;
import com.xiao.service.UserService;
import com.xiao.util.BindingResultUtil;
import com.xiao.util.JwtUtil;
import com.xiao.util.Result;
import com.xiao.vo.UserLoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiao
 */
@Tag(name = "UserController", description = "用户模块接口")
@Slf4j
@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @Operation(summary = "用户注册", description = "无需token验证")
    @PostMapping("/register")
    public Result register(@RequestBody @Validated UserRegisterParam userRegisterParam,
                           BindingResult bindingResult) {
        BindingResultUtil.check(bindingResult);
        return userService.register(userRegisterParam) > 0 ?
                Result.ok() :
                Result.fail(0, "注册失败");
    }

    @ResponseBody
    @Operation(summary = "用户登录", description = "无需token验证")
    @PostMapping("/login")
    public Result login(@RequestBody @Validated UserLoginParam userLoginParam,
                        BindingResult bindingResult) {
        BindingResultUtil.check(bindingResult);

        User user = userService.login(userLoginParam);
        if (null == user) {
            return Result.fail(0, "账号/密码有误");
        }

        // 组装VO
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUser(user);
        userLoginVo.setToken(JwtUtil.build(
                user.getId(),
                user.getNickName(),
                user.getAvatar()));
        return Result.ok(userLoginVo);
    }

    @ResponseBody
    @Operation(summary = "按用户主键查询用户信息", description = "需要token验证")
    @Token
    @GetMapping("/select-by-id")
    public Result selectByUserId(@Parameter(description = "用户表主键")
                                 @RequestParam("user-id") Integer userId) {
        User user = userService.selectById(userId);
        return user != null ?
                Result.ok(user) :
                Result.fail(0, "账号/用户不存在");
    }



    @ResponseBody
    @Operation(summary = "按用户主键修改用户密码", description = "需要token验证")
    @Token
    @PostMapping("/update-password-by-id")
    public Result updatePasswordById(@RequestBody @Validated UserUpdatePasswordParam userUpdatePasswordParam,
                                     BindingResult bindingResult) {
        BindingResultUtil.check(bindingResult);
        return userService.updatePasswordById(userUpdatePasswordParam) > 0 ?
                Result.ok() :
                Result.fail(0, "修改失败");
    }

    @ResponseBody
    @Operation(summary = "按用户主键删除用户信息", description = "需要token验证")
    @Token
    @PostMapping("/delete-by-id")
    public Result deleteById(@RequestBody UserDeleteParam userDeleteParam) {
        return userService.deleteById(userDeleteParam) > 0 ?
                Result.ok() :
                Result.fail(0, "删除失败");
    }

    /**
     * 这里不要使用@RequestBody 因为文件类型不是application/json
     */
    @Operation(summary = "按用户主键修改用户头像", description = "需要token验证")
    @Token
    @PostMapping(value = "/update-avatar-by-id")
    public Result updateAvatarById(@Validated UserUpdateAvatarParam userUpdateAvatarDTO,
                                   BindingResult bindingResult) {
        BindingResultUtil.check(bindingResult);
        return userService.updateAvatarById(userUpdateAvatarDTO) > 0 ?
                Result.ok() :
                Result.fail(0, "修改失败");
    }

    @ResponseBody
    @Operation(summary = "获取验证码", description = "无需token验证")
    @GetMapping("/get-verification-code")
    public Result getVerificationCode(@RequestParam @Parameter(description = "用户电话") String phone) {
        String verificationCode = userService.getVerificationCode(phone);
        return !StringUtils.isBlank(verificationCode) ?
                Result.ok(verificationCode) :
                Result.fail(0, "获取验证码失败");
    }

    @ResponseBody
    @Operation(summary = "根据手机号和验证码进行用户登录", description = "无需token验证")
    @PostMapping("/login-by-phone")
    public Result loginByPhone(@RequestBody @Validated UserLoginByPhoneParam userLoginByPhoneParam,
                               BindingResult bindingResult) {
        BindingResultUtil.check(bindingResult);

        User user = userService.loginByPhone(userLoginByPhoneParam);
        if (null == user) {
            return Result.fail(0, "手机号/验证码有误");
        }

        // 组装VO
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUser(user);
        userLoginVo.setToken(JwtUtil.build(
                user.getId(),
                user.getNickName(),
                user.getAvatar()));
        return Result.ok(userLoginVo);
    }

    @ResponseBody
    @Operation(summary = "按主键单改用户记录", description = "需要token验证")
    @Token
    @PostMapping("/update-by-user-id")
    public Result updateByUserId(@RequestBody @Validated UserUpdateParam userUpdateParam,
                                 BindingResult bindingResult) {
        BindingResultUtil.check(bindingResult);
        return userService.updateByUserId(userUpdateParam) > 0 ?
                Result.ok() :
                Result.fail(0, "修改失败");
    }


    @Operation(summary = "按主键单删用户记录", description = "需要token验证")
    @Token
    @PostMapping("/delete-by-user-id")
    public Result deleteByUserId(@RequestBody UserDeleteParam userDeleteParam) {
        return userService.deleteByUserId(userDeleteParam) > 0 ?
                Result.ok() :
                Result.fail(0, "删除失败");
    }


}
