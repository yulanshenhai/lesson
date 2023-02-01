package com.yulanshenhai.server;

import com.yulanshenhai.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-10-23
 */
public interface ProductService extends IService<Product> {


    /**
     * 扣减商品库存
     *
     * @param productId 商品主键
     * @param number    扣减的数量
     * @return 影响条目数
     */
    int reduceInventory(Integer productId, Integer number);

}
