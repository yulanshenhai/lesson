package com.xiao.controller;

import com.xiao.annotation.Token;
import com.xiao.param.VideoOrderDeleteParam;
import com.xiao.service.VideoOrderService;
import com.xiao.util.Result;
import com.xiao.vo.OrderPageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiao
 */
@Tag(name = "VideoOrderController", description = "视频订单中间表模块接口")
@RestController
@RequestMapping("/api/v1/video-order")
public class VideoOrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    @Operation(summary = "按视频订单中间表主键单删记录", description = "需要token验证")
    @Token
    @PostMapping("/delete-by-video-order-id")
    public Result deleteByVideoOrderId(@RequestBody VideoOrderDeleteParam videoOrderDeleteParam) {
        return videoOrderService.deleteByVideoOrderId(videoOrderDeleteParam) > 0 ?
                Result.ok() :
                Result.fail(0, "删除失败");
    }
}
