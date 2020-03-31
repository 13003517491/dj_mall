package com.dj.mall.model.dto.auth.user;

import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述 用户DTO-Resp对象
 * @创建人 zhangjq
 * @创建时间 2020/3/24
 */
@Data
public class UserDTOResp implements Serializable {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别 1男 2女
     */
    private Integer sex;

    /**
     * 激活状态 -1未激活, 1激活
     */
    private Integer status;

    /**
     * 显示状态 -1已删除, 1正常
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 盐
     */
    private String salt;

    /**
     * 验证码
     */
    private String code;

    /**
     * 验证时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date codeTime;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色展示
     */
    private String roleShow;

    /**
     * 用户权限集合
     */
    private List<ResourceDTOResp> permissionList;

    /**
     * 重置密码
     */
    private String resetPassword;

}
