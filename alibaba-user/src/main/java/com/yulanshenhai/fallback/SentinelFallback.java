package com.yulanshenhai.fallback;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.yulanshenhai.util.Result;

/**
 * @author Lenovo
 */
public class SentinelFallback {

    public static Result fallback(Throwable e) {
        String exceptionMessage = "未知异常：服务器未知异常，请稍后重试..";
        if (e instanceof FlowException) {
            exceptionMessage = "流控异常：您访问的太快或当前访问人数过多，请稍后重试..";
        } else if (e instanceof DegradeException) {
            exceptionMessage = "熔断异常：服务器响应超时或失败次数过多，请稍后重试..";
        } else if (e instanceof AuthorityException) {
            exceptionMessage = "授权异常：您无权访问该资源，请联系管理员..";
        } else if (e instanceof SystemBlockException) {
            exceptionMessage = "系统异常：系统限制，sentinel熔断降级..";
        }
        return Result.fail(-1, exceptionMessage);
    }

    public static Result fallback(String name, Integer age, Throwable e) {
        String exceptionMessage = "未知异常：服务器未知异常，请稍后重试..";
        if (e instanceof ParamFlowException) {
            exceptionMessage = "热点异常：访问受限";
        }
        return Result.fail(-1, exceptionMessage);
    }

}