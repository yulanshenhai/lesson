package com.xiao.controller;

import com.xiao.annotation.Token;
import com.xiao.entity.Episode;
import com.xiao.entity.Video;
import com.xiao.param.VideoSearchParam;
import com.xiao.service.VideoOrderService;
import com.xiao.service.VideoService;
import com.xiao.util.BindingResultUtil;
import com.xiao.util.Result;
import com.xiao.vo.OrderPageVo;
import com.xiao.vo.VideoSearchVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiao
 */
@Tag(name = "VideoController", description = "视频模块接口")
@Controller
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Operation(summary = "按视频主键单查视频记录详情", description = "无需token验证，查询的结果中包含视频中的章节记录")
    @GetMapping("/select-detail-by-video-id")
    public Result selectDetailByVideoId(@Parameter(description = "视频表主键")
                                        @RequestParam("video-id") Integer videoId) {
        Video video = videoService.selectDetailByVideoId(videoId);
        return null != video ?
                Result.ok(video) :
                Result.fail(0, "视频不存在");
    }

    @Autowired
    private VideoOrderService videoOrderService;

    @Operation(summary = "按用户主键批查订单记录", description = "需要token验证")
    @Token
    @GetMapping("/page-detail-by-user-id")
    public Result pageDetailByUserId(@Parameter(description = "用户表主键")
                                     @RequestParam("user-id") Integer userId,
                                     @Parameter(description = "当前显示第几页")
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @Parameter(description = "每页显示多少条")
                                     @RequestParam(value = "size", defaultValue = "6") Integer size) {
        OrderPageVo orderPageVo = videoOrderService.pageDetailByUserId(userId, page, size);
        return orderPageVo.getTotal() > 0 ?
                Result.ok(orderPageVo) :
                Result.fail(0, "该用户暂无订单记录");
    }

    @Operation(summary = "按视频标题搜索视频", description = "无需token验证")
    @GetMapping("/search-by-title")
    @ResponseBody
    public Result searchByTitle(@Validated VideoSearchParam videoSearchParam, BindingResult bindingResult) {
        BindingResultUtil.check(bindingResult);
        VideoSearchVo result = videoService.searchByTitle(videoSearchParam);
        return null != result ?
                Result.ok(result) :
                Result.fail(0, "无符合要求的视频");
    }

    @Operation(summary = "按视频主键查询该视频下的第一集", description = "无需token验证")
    @GetMapping("/select-first-episode-by-id")
    @ResponseBody
    public Result selectFirstEpisodeById(@Validated @RequestParam Integer id) {
        Episode episode = videoService.selectFirstByVideoId(id);
        return null != episode ?
                Result.ok(episode) :
                Result.fail(0, "该视频暂无章集信息，请联系运维人员");
    }
}
