package com.yulanshenhai.feign;

import com.yulanshenhai.fallback.ProductFeignFallback;
import com.yulanshenhai.util.Result;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author JoeZhou
 * <p>
 * <p>远程接口，对应商品微服务的控制器
 * <p>FeignClient：标记该类为远程接口类，value值为对应的远程微服务的名字
 */
@Qualifier("productFeign")
@FeignClient(value = "alibaba-product", fallback = ProductFeignFallback.class)
public interface ProductFeign {

    /**
     * 远程方法：通过主键查询一条商品记录
     *
     * @param id 商品主键
     * @return 对应主键的商品信息
     */
    @GetMapping("/api/v1/product/select-by-id")
    Result selectById(@RequestParam Integer id);

}
