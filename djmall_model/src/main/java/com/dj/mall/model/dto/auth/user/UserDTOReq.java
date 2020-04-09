package com.dj.mall.model.dto.auth.user;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @描述 用户DTO-Req对象
 * @创建人 zhangjq
 * @创建时间 2020/3/24
 */
@Data
public class UserDTOReq  implements Serializable {

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
    private LocalDateTime createTime;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

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
    private LocalDateTime codeTime;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色展示
     */
    private String roleShow;

    /**
     * 重置密码
     */
    private String resetPassword;

    /**
     * 当前页
     */
    private Integer pageNo;
}
