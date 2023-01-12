package com.xiao.mapper;

import com.xiao.entity.Video;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiao
 */
@Repository
public interface VideoMapper {

    String SELECT_ALL = "SELECT v.id, v.title, v.author, v.info, v.summary_image, v.cover_image, " +
            "v.price, v.star, v.create_time, v.last_modify " +
            "FROM lesson.video v ";

    /**
     * 全查Video记录
     *
     * @return 全部Video记录
     */
    @Select(SELECT_ALL)
    List<Video> list();

    /**
     * 按视频主键单查Video记录
     *
     * @param videoId Video表主键
     * @return 单条Video记录
     */
    @Select("<script>" + SELECT_ALL +
            "<where>" +
            "<if test='_parameter != null'> v.id = #{param1} </if> " +
            "OR 1 = 2" +
            "</where>" +
            "</script>")
    Video selectById(Integer videoId);

    /**
     * 按视频主键数组批查Video记录
     *
     * @param videoIds Video主键数组
     * @return 批量Video记录
     */
    @Select("<script>" + SELECT_ALL +
            "<where>" +
            "<if test='_parameter != null'>" +
            "<foreach collection='array' item='e' open='v.id in (' close=')' separator=',' > #{e} </foreach>" +
            "</if>" +
            "OR 1 = 2" +
            "</where>" +
            "</script>")
    List<Video> selectByVideoIds(Integer[] videoIds);

    /**
     * 按视频主键单查Video完整记录：分步查询视频中章的信息和每一张下集的信息
     *
     * @param videoId Video表主键
     * @return 单条Video完整记录
     */
    @Results({
            @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "id", property = "chapters", jdbcType = JdbcType.INTEGER, javaType = List.class,
                    many = @Many(select = "com.xiao.mapper.ChapterMapper.selectDetailByVideoId")
            )
    })
    @Select("<script>" + SELECT_ALL +
            "<where>" +
            "<if test='_parameter != null'> v.id = #{param1} </if> " +
            "OR 1 = 2" +
            "</where>" +
            "</script>")
    Video selectDetailByVideoId(Integer videoId);
}
