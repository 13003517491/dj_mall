package com.dj.mall.model.dto.auth.resource;

import lombok.Data;

import java.io.Serializable;

/**
 * @描述  资源 DTO Resp对象
 * @创建人 zhangjq
 * @创建时间 2020/3/28
 */
@Data
public class ResourceDTOResp implements Serializable {

    /**
     * 资源id
     */
    private Integer resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 状态:-1伪删除,1删除
     */
    private Integer isDel;

    /**
     * 父级id
     */
    private Integer pId;

    /**
     * 菜单展示 1
     */
    private Integer resourceType;

    /**
     * 是否开启复选框
     */
    private Boolean checked = false;


}
