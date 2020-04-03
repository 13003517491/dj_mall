package com.dj.mall.admin.web.auth.base;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.base.BaseDataVOReq;
import com.dj.mall.api.auth.base.BaseDataApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.dto.auth.base.BaseDataDTOReq;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据id修改数据名
     * @param name 数据名
     * @param baseDataId 数据id
     * @return
     * @throws Exception
     */
    @PutMapping
    public ResultModel<Object> update(String name, Integer baseDataId) throws Exception {
        baseDataApi.updateNameById(name, baseDataId);
        return new ResultModel<>().success();
    }

    /**
     * 新增数据
     * @param baseDataVOReq 接收对象
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResultModel<Object> add(BaseDataVOReq baseDataVOReq) throws Exception {
        baseDataApi.saveBaseData(DozerUtil.map(baseDataVOReq, BaseDataDTOReq.class));
        return new ResultModel<>().success();
    }




}
