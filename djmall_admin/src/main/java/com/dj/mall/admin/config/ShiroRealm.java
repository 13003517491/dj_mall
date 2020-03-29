package com.dj.mall.admin.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.entity.auth.resource.ResourceEntity;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOReq;

import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShiroRealm extends AuthorizingRealm {

    @Reference
    private UserApi userApi;

    @Reference
    private ResourceApi resourceApi;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取当前登录用户权限信息
        Session session = SecurityUtils.getSubject().getSession();
        UserDTOResp userDTOResp = (UserDTOResp) session.getAttribute("userEntity");
        for (ResourceDTOResp resourceEntity : userDTOResp.getPermissionList()) {
            simpleAuthorizationInfo.addStringPermission(resourceEntity.getUrl());
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证-登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException, BusinessException {
        // 得到用户名
        String username = (String) authenticationToken.getPrincipal();
        // 得到密码
        String password = new String((char[]) authenticationToken.getCredentials());
        UserDTOReq userDTOReq = new UserDTOReq();
        userDTOReq.setPassword(password);
        userDTOReq.setUsername(username);
        UserDTOResp userDTOResp = userApi.getUser(userDTOReq);
//            if (null == userDTOResp) {
//                throw new AccountException(SystemConstant.LOGIN_ERROR);
//            }
//            if (!userDTOResp.getIsDel().equals(SystemConstant.NOT_DEL)) {
//                throw new UnknownAccountException(SystemConstant.DEL);
//            }
//            if (userDTOResp.getStatus() != 1) {
//                throw new LockedAccountException(SystemConstant.NOT_ACTIVE);
//            }
        userDTOResp.setPermissionList(resourceApi.getUserResourceList(userDTOResp.getUserId()));
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userEntity", userDTOResp);
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
