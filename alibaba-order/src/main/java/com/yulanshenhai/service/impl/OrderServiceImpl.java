package com.yulanshenhai.service.impl;

import com.yulanshenhai.feign.ProductFeign;
import com.yulanshenhai.mapper.OrderMapper;
import com.yulanshenhai.service.OrderService;
import com.yulanshenhai.entity.Order;
import com.yulanshenhai.entity.Product;
import com.yulanshenhai.util.JacksonUtil;
import com.yulanshenhai.util.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JoeZhou
 * @since 2022-10-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductFeign productFeign;

    @Override
    public int insert(Order order) {

        // 获取商品主键
        Integer productId = order.getProductId();

        // 调用商品微服务的的 `按主键查询商品信息` 方法
        Result result = productFeign.selectById(productId);
        if (result.getCode() <= 0) {
            throw new RuntimeException("商品信息查询失败");
        }

        // 将结果中的Product实体数据解析出来
        Product product = JacksonUtil.parseData(result.getData(), Product.class);

        // 填充实体类
        order.setProductName(product.getProductName());

        // 调用添加订单的数据方法
        int insertResult = orderMapper.insert(order);
        if (insertResult < 0) {
            throw new RuntimeException("订单添加失败");
        }

        // 添加成功后，进行扣减库存
        Result reduceResult = productFeign.reduceInventory(productId, order.getNumber());
        if (reduceResult.getCode() < 0) {
            throw new RuntimeException("扣减库存失败");
        }

        // 添加一条订单记录
        return insertResult;
    }
}
