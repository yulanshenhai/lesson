package com.yulanshenhai.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JoeZhou
 * @since 2022-10-25
 */
@Data
public class UserUpdateParam implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String password;
    private String phone;
}

