package com.xiao.controller;

import com.xiao.entity.Video;
import com.xiao.param.VideoSearchParam;
import com.xiao.service.VideoService;
import com.xiao.util.BindingResultUtil;
import com.xiao.util.Result;
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

    @ResponseBody
    @Operation(summary = "按视频主键单查视频记录详情", description = "无需token验证，查询的结果中包含视频中的章节记录")
    @GetMapping("/select-detail-by-id")
    public Result selectDetailById(@Parameter(description = "视频表主键")
                                   @RequestParam("video-id") Integer videoId) {
        Video video = videoService.selectDetailById(videoId);
        return null != video ?
                Result.ok(video) :
                Result.fail(0, "视频不存在");
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
}
