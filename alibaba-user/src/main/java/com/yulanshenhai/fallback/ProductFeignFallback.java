package com.yulanshenhai.fallback;

import com.yulanshenhai.feign.ProductFeign;
import com.yulanshenhai.util.Result;
import org.springframework.stereotype.Component;

/**
 * @author JoeZhou
 */

@Component
public class ProductFeignFallback implements ProductFeign {

    @Override
    public Result selectById(Integer id) {
        return Result.fail(0, "远程调用失败");
    }
}
