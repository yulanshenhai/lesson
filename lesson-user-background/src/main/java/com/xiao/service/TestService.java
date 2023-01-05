package com.xiao.service;

import com.xiao.entity.User;
import com.xiao.entity.Video;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author xiao
 */
public interface TestService {
    /**
     * 仅用于测试AOP切面
     *
     * @param param AOP切面的参数
     * @return AOP切面返回值
     */
    List<User> testAop(String param);


    /**
     * 仅用于测试Guava缓存
     *
     * @return 返回Guava缓存中的value值
     */
    String testGuava();


    /**
     * 仅用于测试PageHelper分页结果
     *
     * @param page 当前第几页
     * @param size 每页多少条
     * @return 分页结果
     */
    PageInfo<Video> testPage(Integer page, Integer size);


}
