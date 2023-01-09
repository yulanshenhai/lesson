package com.xiao.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xiao
 */
@Schema(name = "OrderPageParam", description = "用于分页查询个人订单的Param实体参数")
@Data
public class OrderPageParam implements Serializable {

    @NotNull(message = "分页查询订单业务中用户主键不能为空")
    @Schema(description = "用户主键", required = true, example = "1")
    private Integer userId;

    @Min(value = 0, message = "分页查询订单业务中当前第几页不能小于0")
    @NotNull(message = "分页查询订单业务中当前第几页不能为空")
    @Schema(description = "当前第几页", required = true, example = "1")
    private Integer page;

    @Min(value = 0, message = "分页查询订单业务中每页多少条不能小于0")
    @NotNull(message = "分页查询订单业务中每页多少条不能为空")
    @Schema(description = "每页多少条", required = true, example = "5")
    private Integer size;

}
