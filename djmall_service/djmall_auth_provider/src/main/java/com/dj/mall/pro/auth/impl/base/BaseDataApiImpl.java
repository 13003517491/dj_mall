package com.dj.mall.pro.auth.impl.base;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.base.BaseDataApi;
import com.dj.mall.entity.auth.base.BaseDataEntity;
import com.dj.mall.mapper.auth.base.BaseDataMapper;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.base.BaseDataDTOReq;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.util.DozerUtil;

import java.util.List;

/**
 * @author ZhangJQ
 * @描述 基础数据接口实现类
 * @创建人 zhangjq
 * @创建时间 2020/4/1
 */
@Service
public class BaseDataApiImpl extends ServiceImpl<BaseDataMapper, BaseDataEntity> implements BaseDataApi {

    /**
     * 获取基础数据
     *
     * @return
     */
    @Override
    public List<BaseDataDTOResp> getBaseData() throws Exception {
        List<BaseDataEntity> baseDataList = this.list();
        return DozerUtil.mapList(baseDataList, BaseDataDTOResp.class);
    }

    /**
     * 获取所有编码
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<BaseDataDTOResp> getBaseDataList() throws Exception {
        QueryWrapper<BaseDataEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("p_code", SystemConstant.SYSTEM);
        return DozerUtil.mapList(this.list(queryWrapper), BaseDataDTOResp.class);
    }

    /**
     * 根据id获取基础数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public BaseDataDTOResp getBaseDataById(Integer id) throws Exception {
        return DozerUtil.map(this.getById(id), BaseDataDTOResp.class);
    }

    /**
     * 根据id修改数据名
     *
     * @param name       数据名
     * @param baseDataId 数据id
     * @throws Exception
     */
    @Override
    public void updateNameById(String name, Integer baseDataId) throws Exception {
        UpdateWrapper<BaseDataEntity> updateWrapper = new UpdateWrapper();
        updateWrapper.set("name", name);
        updateWrapper.eq("id", baseDataId);
        this.update(updateWrapper);
    }

    /**
     * 新增数据
     *
     * @param baseDataDTOReq
     * @throws Exception
     */
    @Override
    public void saveBaseData(BaseDataDTOReq baseDataDTOReq) throws Exception {
        baseDataDTOReq.setCode(baseDataDTOReq.getCode().toUpperCase());
        this.save(DozerUtil.map(baseDataDTOReq, BaseDataEntity.class));
    }
}
