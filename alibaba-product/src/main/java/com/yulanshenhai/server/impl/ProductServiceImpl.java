package com.yulanshenhai.server.impl;

import com.yulanshenhai.mapper.ProductMapper;
import com.yulanshenhai.server.ProductService;
import com.yulanshenhai.entity.Product;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author JoeZhou
 * @since 2022-10-25
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int reduceInventory(Integer productId, Integer number) {

        // 通过商品主键查询该商品记录
        Product product = productMapper.selectById(productId);

        // 查询当前产品的库存量
        Integer currentProductStock = product.getProductStock();

        // 在内存中扣减库存
        int reduceResult = currentProductStock - number;
        if (reduceResult < 0) {
            throw new RuntimeException("当前商品库存不足");
        }

        // 重新设置商品库存
        product.setProductStock(reduceResult);

        // 修改商品信息
        int updateResult = productMapper.updateById(product);

        return updateResult;
    }
}

