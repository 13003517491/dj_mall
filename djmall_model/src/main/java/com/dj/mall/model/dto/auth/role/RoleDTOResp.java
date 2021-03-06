package com.dj.mall.model.dto.auth.role;

import lombok.Data;

import java.io.Serializable;

/**
 * @描述  角色DTO resq对象
 * @创建人 zhangjq
 * @创建时间 2020/3/27
 */
@Data
public class RoleDTOResp implements Serializable {

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 1正常展示 2伪删除
     */
    private Integer isDel;
}
