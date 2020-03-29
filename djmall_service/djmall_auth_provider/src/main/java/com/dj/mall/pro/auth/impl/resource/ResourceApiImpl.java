package com.dj.mall.pro.auth.impl.resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.entity.auth.resource.ResourceEntity;
import com.dj.mall.entity.auth.role.RoleResourceEntity;
import com.dj.mall.mapper.auth.resource.ResourceMapper;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOReq;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.pro.auth.service.role.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述  资源接口实现类
 * @创建人 zhangjq
 * @创建时间 2020/3/28
 */
@Service
public class ResourceApiImpl extends ServiceImpl<ResourceMapper, ResourceEntity> implements ResourceApi {

    @Autowired
    private RoleResourceService roleResourceService;

    /**
     * 获取用户资源
     *
     * @param userId
     * @return
     */
    @Override
    public List<ResourceDTOResp> getUserResourceList(Integer userId) throws Exception {
        return DozerUtil.mapList(this.getUserResourceList(userId), ResourceDTOResp.class);
    }

    /**
     * 获取所有资源
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<ResourceDTOResp> getResource() throws Exception {
        QueryWrapper<ResourceEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_del", SystemConstant.NOT_DEL);
        return DozerUtil.mapList(this.list(queryWrapper), ResourceDTOResp.class);
    }

    /**
     * 资源名去重
     *
     * @param resourceName
     * @return
     * @throws Exception
     */
    @Override
    public Boolean distinct(String resourceName) throws Exception {
        QueryWrapper<ResourceEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("resource_name", resourceName);
        this.getOne(queryWrapper);
        if (this.getOne(queryWrapper) != null) {
            return false;
        }
        return true;
    }

    /**
     * 根据id修改资源
     *
     * @param resourceDTOReq 接收参数
     */
    @Override
    public void updateResourceById(ResourceDTOReq resourceDTOReq) throws Exception {
        UpdateWrapper<ResourceEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("resource_name", resourceDTOReq.getResourceName()).set("url", resourceDTOReq.getUrl());
        updateWrapper.eq("id", resourceDTOReq.getResourceId());
        this.update(updateWrapper);
    }

    /**
     * @param resourceId 资源id
     * @return
     * @throws Exception
     */
    @Override
    public ResourceDTOResp getResourceById(Integer resourceId) throws Exception {
        return DozerUtil.map(this.getById(resourceId), ResourceDTOResp.class);
    }

    /**
     * 删除资源
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delResAndRoleResByIds(Integer id) throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(id);
        getIds(id, list);
        //资源删除
        UpdateWrapper<ResourceEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del", SystemConstant.NOT_SHOW);
        updateWrapper.in("id", id);
        this.update(updateWrapper);
        //角色资源删除
        UpdateWrapper<RoleResourceEntity> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.set("is_del", SystemConstant.NOT_SHOW);
        updateWrapper1.in("resource_id", id);
        roleResourceService.update(updateWrapper1);
    }

    public void getIds(Integer id, List<Integer> list) throws Exception {
        QueryWrapper<ResourceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", SystemConstant.NOT_DEL)
                .eq("p_id", id);
        List<ResourceEntity> resourceList = this.list(queryWrapper);
        if (null != resourceList && resourceList.size() > 0) {
            for (ResourceEntity resource : resourceList) {
                list.add(resource.getId());
                getIds(id, list);
            }
        }
    }
}
