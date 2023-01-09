package com.xiao.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xiao.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiao
 */
@Schema(name = "UserLoginVo", description = "用户VO实体")
@Data
@JsonIgnoreProperties("handler")
public class UserLoginVo implements Serializable {

    private User user;

    private String token;
}
