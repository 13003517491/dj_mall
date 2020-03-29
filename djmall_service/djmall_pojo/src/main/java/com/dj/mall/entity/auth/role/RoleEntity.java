package com.dj.mall.entity.auth.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.dozer.Mapping;

/**
 * @描述  角色实体
 * @创建人 zhangjq
 * @创建时间 2020/3/26
 */
@Data
@TableName("djmall_auth_role")
public class RoleEntity {

    /**
     * 角色id
     */
    @TableId(type = IdType.AUTO)
    @Mapping("roleId")
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 1展示 2伪删除
     */
    private Integer isDel;
}
