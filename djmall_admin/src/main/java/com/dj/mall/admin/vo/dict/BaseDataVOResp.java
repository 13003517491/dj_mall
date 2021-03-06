package com.dj.mall.admin.vo.dict;

import lombok.Data;

import java.io.Serializable;

/**
 * @描述 基础数据VO Resp
 * @创建人 zhangjq
 * @创建时间 2020/4/1
 */
@Data
public class BaseDataVOResp implements Serializable {
    /**
     * 基础数据ID
     */
    private Integer baseDataId;

    /**
     * 基础数据编码
     */
    private String code;

    /**
     * 基础数据名
     */
    private String name;

    /**
     * 基础数据父级编码
     */
    private String pCode;
}
