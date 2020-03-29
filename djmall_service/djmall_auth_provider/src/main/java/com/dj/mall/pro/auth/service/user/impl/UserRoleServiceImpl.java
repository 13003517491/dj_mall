package com.dj.mall.pro.auth.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.entity.auth.user.UserRoleEntity;
import com.dj.mall.mapper.auth.user.UserRoleMapper;
import com.dj.mall.pro.auth.service.user.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @描述  用户角色接口实现类
 * @创建人 zhangjq
 * @创建时间 2020/3/29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {
}
