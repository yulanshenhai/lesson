package com.yulanshenhai.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.yulanshenhai.fallback.SentinelFallback;
import com.yulanshenhai.feign.ProductFeign;
import com.yulanshenhai.util.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Lenovo
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/sentinel")
public class SentinelController {

    @SentinelResource(value = "qps-direct-fail", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @GetMapping("/qps-direct-fail")
    public Result qpsDirectFail() {
        log.info("资源qps-direct-fail调用成功");
        return Result.ok("资源qps-direct-fail调用成功");
    }

    @SentinelResource(value = "qps-direct-warm-up", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @GetMapping("/qps-direct-warm-up")
    public Result qpsDirectWarmUp() {
        log.info("资源qps-direct-warm-up调用成功");
        return Result.ok("资源qps-direct-warm-up调用成功");
    }

    @SentinelResource(value = "qps-direct-line-up", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @GetMapping("/qps-direct-line-up")
    public Result qpsDirectLineUp() {
        log.info("资源qps-direct-line-up调用成功");
        return Result.ok("资源qps-direct-line-up调用成功");
    }

    @SentinelResource(value = "qps-relation-01", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @GetMapping("/qps-relation-01")
    public Result qpsRelation01() {
        return Result.ok("资源qps-relation-01用成功");
    }

    @SentinelResource(value = "qps-relation-02", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @RequestMapping("qps-relation-02")
    public Result qpsRelation02() {
        return Result.ok("资源qps-relation-02用成功");
    }

    @SentinelResource(value = "thread-direct", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @RequestMapping("thread-direct")
    public Result threadDirect() {
        return Result.ok("资源thread-direct用成功");
    }

    @SneakyThrows
    @SentinelResource(value = "rt", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @RequestMapping("rt")
    public Result rt() {
        TimeUnit.SECONDS.sleep(1L);
        return Result.ok("资源rt调用成功");
    }

    private int exRatioNum = 0;

    @SentinelResource(value = "ex-ratio", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @RequestMapping("ex-ratio")
    public Result exRatio() {
        if (++exRatioNum % 3 == 0) {
            throw new RuntimeException("资源ex-ratio人为异常");
        }
        return Result.ok("资源ex-ratio调用成功");
    }

    private int exCountNum = 0;

    @SentinelResource(value = "ex-count", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @RequestMapping("ex-count")
    public Result exCount() {
        if (++exCountNum % 3 == 0) {
            throw new RuntimeException("资源ex-count人为异常");
        }
        return Result.ok("资源ex-count调用成功");
    }

    @SentinelResource(value = "white-auth", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @RequestMapping("white-auth")
    public Result whiteAuth() {
        log.info("资源white-auth调用成功");
        return Result.ok("资源white-auth调用成功");
    }

    @SentinelResource(value = "black-auth", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @RequestMapping("black-auth")
    public Result blackAuth() {
        log.info("资源black-auth调用成功");
        return Result.ok("资源black-auth调用成功");
    }

    @SentinelResource(value = "param", fallbackClass = SentinelFallback.class, fallback = "fallback")
    @RequestMapping("param")
    public Result param(@RequestParam(required = false) String name,
                        @RequestParam(required = false) Integer age) {
        log.info("资源param调用成功");
        return Result.ok("资源param调用成功");
    }

    @Autowired
    @Qualifier("productFeign")
    private ProductFeign productFeign;

    @SentinelResource(value = "open-feign")
    @RequestMapping("open-feign")
    public Result openFeign(@RequestParam("product-id") Integer productId) {
        log.info("接收到参数: {}", productId);
        return productFeign.selectById(productId);
    }

}
