package com.dj.mall.entity.auth.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述 用户角色表
 * @创建人 zhangjq
 * @创建时间 2020/3/29
 */
@Data
@TableName("djmall_auth_user_role")
public class UserRoleEntity implements Serializable {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 伪删除 -1已删除 1正常
     */
    private Integer isDel;
}
