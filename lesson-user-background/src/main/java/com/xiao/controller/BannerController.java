package com.xiao.controller;

import com.xiao.entity.Banner;
import com.xiao.service.BannerService;
import com.xiao.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xiao
 */
@Slf4j
@Tag(name = "BannerController", description = "轮播图模块接口")
@Controller
@RequestMapping("/api/v1/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @Operation(summary = "批查轮播图记录",
            description = "无需token验证，查询最多5条Banner表记录，结果集按权重weight升序")
    @ResponseBody
    @GetMapping("/list")
    public Result list() {
        List<Banner> banners = bannerService.list();
        if (banners.isEmpty()) {
            String errorMsg = "暂无轮播图记录，请联系运维人员";
            log.warn(errorMsg);
            return Result.fail(0, errorMsg);
        }
        return Result.ok(banners);
    }
}
