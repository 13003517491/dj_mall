package com.dj.mall.mapper.bo.resource;

import lombok.Data;

import java.io.Serializable;

/**
 * @描述  资源 BO Req对象
 * @创建人 zhangjq
 * @创建时间 2020/3/28
 */
@Data
public class ResourceBOReq implements Serializable {

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
}
