package com.xiao.service;

import com.xiao.entity.Episode;
import com.xiao.entity.Video;
import com.xiao.param.VideoSearchParam;
import com.xiao.vo.VideoSearchVo;

/**
 * @author xiao
 */
public interface VideoService {

    /**
     * <h2>按视频主键查询视频详情</h2>
     *
     * <p> 01. 调用Mapper接口按视频主键查询Video详情，包括章集信息。
     *
     * @param videoId Video表主键
     * @return 查询成功返回该条Video记录，查询失败直接返回null值
     */
    Video selectDetailById(Integer videoId);

    /**
     * 按视频标题从ES中搜索符合条件的VideoDoc记录
     *
     * @param videoSearchParam 视频分页搜索实体参数
     * @return 搜索视频的VO实体
     */
    VideoSearchVo searchByTitle(VideoSearchParam videoSearchParam);


    /**
     *
     * @param id
     * @return
     */
    Episode selectFirstByVideoId(Integer id);
}
