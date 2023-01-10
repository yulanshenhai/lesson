package com.xiao.service.impl;

import com.xiao.document.VideoDoc;
import com.xiao.entity.Chapter;
import com.xiao.entity.Episode;
import com.xiao.entity.Video;
import com.xiao.mapper.ChapterMapper;
import com.xiao.mapper.EpisodeMapper;
import com.xiao.mapper.VideoMapper;
import com.xiao.param.VideoSearchParam;
import com.xiao.service.VideoService;
import com.xiao.util.NullUtil;
import com.xiao.vo.VideoSearchVo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xiao
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private EpisodeMapper episodeMapper;

    @Override
    public Video selectDetailById(Integer videoId) {
        return videoMapper.selectDetailById(videoId);
    }

    @Override
    public VideoSearchVo searchByTitle(VideoSearchParam videoSearchParam) {

        String title = videoSearchParam.getTitle();
        Integer page = videoSearchParam.getPage();
        Integer size = videoSearchParam.getSize();

        if (NullUtil.hasNull(title, page, size)) {
            throw new RuntimeException("必要参数为空");
        }

        // ES的分页是从0开始的
        page = page - 1;

        // 最小边界保护，规避ES报错。
        if (page < 0) {
            page = 0;
        }

        List<Video> esVideos = this.searchByTitleFromEs(title, page, size);

        // 如果ES中未搜索到任何数据，则尝试从数据库中获取
        if (esVideos.isEmpty()) {
            return null;
        }

        // 组装VO
        VideoSearchVo result = new VideoSearchVo();
        result.setPageNum(page + 1);
        result.setPageSize(size);
        result.setVideos(esVideos);
        result.setTotal(this.countByTitleFromEs(title));
        return result;
    }

    /**
     * 从ES中，按标题分页搜索视频
     *
     * @param title 视频标题
     * @param page  当前第几页
     * @param size  每页多少条
     * @return 符合条件的ES数据
     */
    private List<Video> searchByTitleFromEs(String title, Integer page, Integer size) {

        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", title);
        Query query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(PageRequest.of(page, size))
                .withSort(SortBuilders.fieldSort("_score").order(SortOrder.DESC))
                .withHighlightFields(new HighlightBuilder.Field("title"))
                .build();
        SearchHits<VideoDoc> searchHits = elasticsearchRestTemplate.search(query, VideoDoc.class);
        if (!searchHits.isEmpty()) {
            List<Video> result = new ArrayList<>();
            for (SearchHit<VideoDoc> searchHit : searchHits) {
                VideoDoc videoDoc = searchHit.getContent();
                Video video = new Video();
                BeanUtils.copyProperties(videoDoc, video);
                result.add(video);
            }
            return result;
        }
        return Collections.emptyList();
    }

    /**
     * 从ES中，按标题分页搜索视频，返回符合条件的的视频个数
     *
     * @param title 视频标题
     * @return 返回符合条件的的视频个数
     */
    private Long countByTitleFromEs(String title) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", title);
        Query query = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        return elasticsearchRestTemplate.count(query, VideoDoc.class);
    }


    @Override
    public Episode selectFirstByVideoId(Integer videoId) {
        Chapter firstChapter = chapterMapper.selectFirstByVideoId(videoId);
        if (null != firstChapter) {
            Integer firstChapterId = firstChapter.getId();
            return episodeMapper.selectFirstByChapterId(firstChapterId);
        }
        return null;
    }
}
