package com.xiao.service;

import com.xiao.entity.Banner;

import java.util.List;

/**
 * @author xiao
 */
public interface BannerService {

    /**
     * 查询最多5条Banner表记录，结果集按权重weight升序
     *
     * @return 最多5条Banner表记录，若结果集为空，则返回空List对象
     */
    List<Banner> list();
}
