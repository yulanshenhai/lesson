package com.xiao.controller;

import com.xiao.annotation.Token;
import com.xiao.param.OrderDeleteParam;
import com.xiao.param.OrderInsertParam;
import com.xiao.param.OrderPageParam;
import com.xiao.service.OrderService;
import com.xiao.util.BindingResultUtil;
import com.xiao.util.Result;
import com.xiao.vo.OrderPageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiao
 */
@Tag(name = "OrderController", description = "订单模块接口")
@Controller
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @Operation(summary = "单增订单记录", description = "需要token验证")
    @Token
    @PostMapping("/insert")
    public Result insert(@RequestBody @Validated OrderInsertParam orderInsertParam,
                         BindingResult bindingResult) {
        BindingResultUtil.check(bindingResult);
        return orderService.insert(orderInsertParam) > 0 ?
                Result.ok() :
                Result.fail(0, "下单失败");
    }

    @ResponseBody
    @Operation(summary = "按用户主键批查订单记录", description = "需要token验证")
    @Token
    @GetMapping("/page-detail-by-user-id")
    public Result pageDetailByUserId(@Parameter(description = "用户表主键")
                                     @RequestParam("user-id") Integer userId,
                                     @Parameter(description = "当前显示第几页")
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @Parameter(description = "每页显示多少条")
                                     @RequestParam(value = "size", defaultValue = "6") Integer size) {
        OrderPageVo orderPageVo = orderService.pageDetailByUserId(userId, page, size);
        return orderPageVo.getTotal() > 0 ?
                Result.ok(orderPageVo) :
                Result.fail(0, "该用户暂无订单记录");
    }

    @Operation(summary = "按订单主键单删订单记录", description = "需要token验证")
    @Token
    @PostMapping("/delete-by-order-id")
    public Result deleteByOrderId(@RequestBody OrderDeleteParam orderDeleteParam) {
        return orderService.deleteByOrderId(orderDeleteParam) > 0 ?
                Result.ok() :
                Result.fail(0, "删除失败");
    }
}
