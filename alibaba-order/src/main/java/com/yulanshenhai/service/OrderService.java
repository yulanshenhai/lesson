package com.yulanshenhai.service;

import com.yulanshenhai.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author JoeZhou
 * @since 2022-10-25
 */
public interface OrderService extends IService<Order> {

    /**
     * 添加一条订单记录
     *
     * @param order 订单实体
     * @return 影响条目数
     */
    int insert(Order order);

}

