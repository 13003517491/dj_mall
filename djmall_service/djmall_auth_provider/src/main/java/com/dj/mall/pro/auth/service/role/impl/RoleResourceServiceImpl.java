package com.dj.mall.pro.auth.service.role.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.entity.auth.role.RoleResourceEntity;
import com.dj.mall.mapper.auth.role.RoleResourceMapper;
import com.dj.mall.pro.auth.service.role.RoleResourceService;
import org.springframework.stereotype.Service;

/**
 * @描述 角色资源接口实现类
 * @创建人 zhangjq
 * @创建时间 2020/3/29
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResourceEntity> implements RoleResourceService {
}
