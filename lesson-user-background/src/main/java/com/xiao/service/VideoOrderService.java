package com.xiao.service;

import com.xiao.param.VideoOrderDeleteParam;
import com.xiao.vo.OrderPageVo;

/**
 * @author xiao
 */
public interface VideoOrderService {

    /**
     * <h2>分页查询个人订单详情</h2>
     * <p> 01. 调用Mapper接口检查用户是否存在，若不存在抛出异常。
     * <p> 02. 调用Mapper接口按用户主键查询该用户的全部完整VideoOrder记录。
     * <p> 03. 查询成功：返回该用户的全部完整VideoOrder记录。
     * <p> 04. 查询失败：直接返回null值。
     *
     * @param userId User主键
     * @param page   当前显示第几页
     * @param size   每页显示多少条
     * @return 指定用户的部分订单数据的VO实体
     */
    OrderPageVo pageDetailByUserId(Integer userId, Integer page, Integer size);

    /**
     * <h2>删除指定的视频订单中间表记录</h2>
     * <p> 01. 添加 `@Transactional(rollbackFor = Exception.class)` 本地事务保护，抛出异常时触发回滚。
     * <p> 02. 检查必填参数：若包含null值则直接抛出参数异常。
     * <p> 03. 调用Mapper接口按 [视频订单中间表] 主键删除记录，删除失败则抛出异常。
     * <p> 04. 调用Mapper接口通过 [订单表] 主键查询 [视频订单中间表] 记录
     * <p> 05. 若该订单下已经不存在任何 [视频订单中间表] 记录，则调用Mapper接口删除该订单
     *
     * @param videoOrderDeleteParam 删除订单的Param实体
     * @return 影响条目数
     */
    int deleteByVideoOrderId(VideoOrderDeleteParam videoOrderDeleteParam);

}
