package com.dj.mall.admin.web.auth.base;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.auth.base.BaseDataApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZhangJQ
 * @描述  基础数据控制层
 * @创建人 zhangjq
 * @创建时间 2020/4/1
 */
@RestController
@RequestMapping("/auth/base/")
public class BaseDataController {

    @Reference
    private BaseDataApi baseDataApi;

    /**
     * 基础数据展示
     * @return
     */
    @GetMapping
    public ResultModel<Object> list() throws Exception {
        List<BaseDataDTOResp> baseDataList = baseDataApi.getBaseData();
        return new ResultModel<>().success(baseDataList);
    }

}
