package com.yulanshenhai.controller;

import com.yulanshenhai.dto.OrderInsertDto;
import com.yulanshenhai.service.OrderService;
import com.yulanshenhai.entity.Order;
import com.yulanshenhai.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JoeZhou
 * @since 2022-10-25
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/insert")
    public Result insert(@RequestBody OrderInsertDto orderInsertDto) {
        log.info("请求：添加一条订单记录");
        Order order = new Order();
        BeanUtils.copyProperties(orderInsertDto, order);
        log.info("参数：{}", order);
        int insertResult = orderService.insert(order);
        log.info("响应：{}", insertResult > 0 ? "插入成功" : "插入失败");
        return insertResult > 0 ? Result.ok(order) : Result.fail(-1, "插入失败");
    }

}

