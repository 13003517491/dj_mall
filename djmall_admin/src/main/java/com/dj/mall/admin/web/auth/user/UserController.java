package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.PermissionCode;
import com.dj.mall.admin.vo.auth.user.UserVOReq;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.MessageVerifyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @描述
 * @创建人 zhangjq
 * @创建时间 2020/3/24
 */
@RestController
@RequestMapping("/auth/user/")
public class UserController {

    @Reference
    private UserApi userApi;

    @Reference
    private ResourceApi resourceApi;

    /**
     * 登录
     * @param userVOReq 用户接收对象
     * @return
     * @throws Exception
     */
    @RequestMapping("login")
    public ResultModel<Object> login(UserVOReq userVOReq) throws Exception {
            Assert.hasText(userVOReq.getUsername(), SystemConstant.LOGIN_VERIFY);
            Assert.hasText(userVOReq.getPassword(), SystemConstant.LOGIN_VERIFY);
            UserDTOResp userDTOResp = userApi.getUser(DozerUtil.map(userVOReq, UserDTOReq.class));
            userDTOResp.setPermissionList(resourceApi.getUserResourceList(userDTOResp.getUserId()));
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute(SystemConstant.USER_SESSION, userDTOResp);
            //shiro 登录方式
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userVOReq.getUsername(), userVOReq.getPassword());
            subject.login(token);
            return new ResultModel<>().success();
    }

    /**
     *  获取盐
     * @param userVOReq 用户接收对象
     * @return
     * @throws Exception
     */
    @RequestMapping("getSalt")
    public ResultModel<Object> findSalt(UserVOReq userVOReq) throws Exception {
        Assert.hasText(userVOReq.getUsername(), SystemConstant.USSERNAME_EMPTY);
        UserDTOResp userDTOResp = userApi.getUserByUserName(userVOReq.getUsername());
        UserVOResp userVOResp = DozerUtil.map(userDTOResp, UserVOResp.class);
        return new ResultModel<>().success(userVOResp.getSalt());
    }

    /**
     * 用户注册去重
     * @param userVOReq 用户接收对象
     * @return
     */
    @RequestMapping("distinct")
    public Boolean findByUsername (UserVOReq userVOReq) throws Exception{
        Boolean distinct = userApi.distinct(DozerUtil.map(userVOReq, UserDTOReq.class));
        return distinct;
    }

    /**
     * 用户注册
     * @param userVOReq 用户接收对象
     * @return
     */
    @RequestMapping("add")
    public ResultModel<Object> add (UserVOReq userVOReq) throws Exception{
        userVOReq.setCreateTime(LocalDateTime.now());
        userApi.saveUser(DozerUtil.map(userVOReq, UserDTOReq.class));
        return new ResultModel<Object>().success();
    }

    /**
     * 真正实现短信呢验证码
     * @param phone 手机号
     * @return
     */
    @RequestMapping("sendMessage")
    public ResultModel<Object> send(String phone) throws Exception{
        UserDTOResp userDTOResp = userApi.getUserByPhone(phone);
        //判断手机号数据库中是否存在
        Assert.notNull(userDTOResp, SystemConstant.NOT_PHONE);
        Assert.isTrue(userDTOResp.getIsDel().equals(SystemConstant.NOT_DEL), SystemConstant.DEL);
        int newcode = MessageVerifyUtils.getNewcode();
        MessageVerifyUtils.sendSms(phone, String.valueOf(newcode));
        userApi.updateCodeAndCodeTimeByPhone(phone, newcode);
        return new ResultModel<>().success();
    }

    /**
     * 忘记密码
     * @param userVOReq 用户接收对象
     * @return
     */
    @RequestMapping("updatePwd")
    public ResultModel<Object> loginPhone(UserVOReq userVOReq) throws Exception{
        //获取查到的数据
        UserDTOResp userDTOResp = userApi.getUserByPhoneAndCode(userVOReq.getPhone(), userVOReq.getCode());
        //判断数据库中是否存在此条数据
        Assert.notNull(userDTOResp, SystemConstant.NOT_CODE);
        //验证码已失效
        Assert.state(userDTOResp.getCodeTime().isAfter(LocalDateTime.now()), SystemConstant.FALSE_CODE);
        //删除过的用户提示用户已删除
        Assert.state(userDTOResp.getIsDel().equals(SystemConstant.NOT_DEL), SystemConstant.DEL);
        //存进新的密码和盐
        userVOReq.setEmail(userDTOResp.getEmail());
        userVOReq.setNickname(userDTOResp.getNickname());
        userApi.updateSaltAndPwdByPhone(DozerUtil.map(userVOReq, UserDTOReq.class));
        return new ResultModel<>().success();
    }

    /**
     * 修改密码
     * @param userVOReq 接收参数
     * @return
     * @throws Exception
     */
    @RequestMapping("updatePasswordByUsername")
    public ResultModel<Object> updatePasswordByUsername(UserVOReq userVOReq) throws Exception{
        userApi.updatePasswordByUsername(DozerUtil.map(userVOReq, UserDTOReq.class));
        return new ResultModel<>().success();
    }

    /**
     * 用户展示
     * @param userVOReq 用户接收对象
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions(value = PermissionCode.USER_MANAGER)
    public ResultModel<Object> list(UserVOReq userVOReq) throws Exception{
        PageResult pageResult = userApi.findUserAll(DozerUtil.map(userVOReq, UserDTOReq.class));
        pageResult.setList(DozerUtil.mapList(pageResult.getList(), UserVOResp.class));
        return new ResultModel<>().success(pageResult);

    }

    /**
     * 根据id修改
     * @param userVOReq 用户接收对象
     * @return
     */
    @RequestMapping("update")
    @RequiresPermissions(value = PermissionCode.USER_UPDATE_BTN)
    public ResultModel<Object> update(UserVOReq userVOReq) throws Exception {
        userApi.updateUserById(DozerUtil.map(userVOReq, UserDTOReq.class));
        return new ResultModel<>().success();
    }

    /**
     * 修改用户角色
     * @param userId 用户id
     * @param roleId 角色id
     * @return
     */
    @RequestMapping("updateUserRole")
    @RequiresPermissions(value = PermissionCode.USER_AUTH_BTN)
    public ResultModel<Object> updateUserRole(Integer userId, Integer roleId) throws Exception {
        userApi.updateUserRole(userId, roleId);
        return new ResultModel<>().success();
    }

    /**
     * 用户激活
     * @param id 用户id
     * @return
     */
    @RequestMapping("updateStatusById")
    @RequiresPermissions(value = PermissionCode.USER_ACTIVE_BTN)
    public ResultModel<Object> updateStatusById(Integer id) throws Exception {
        UserDTOResp userDTOResp = userApi.getUserById(id);
        if (userDTOResp.getStatus().equals(SystemConstant.ACTIVE_SUCCESS)) {
            return new ResultModel<>().error(SystemConstant.YES_ACTIVE);
        }
        userApi.updateUser(id);
        return new ResultModel<>().success();
    }

    /**
     * 批量伪删除
     * @param ids 用户id数组
     * @return
     */
    @RequestMapping("delByIds")
    @RequiresPermissions(value = PermissionCode.USER_DEL_BTN)
    public ResultModel<Object> delByIds(Integer[] ids) throws Exception {
        userApi.delUserAndUserRoleByIds(ids);
        return new ResultModel<>().success();
    }

    /**
     * 重置密码
     * @param id 用户id
     * @return
     */
    @RequestMapping("updatePasswordById")
    @RequiresPermissions(value = PermissionCode.USER_RESETPWD_BTN)
    public ResultModel<Object> updatePasswordById(Integer id) throws Exception {
        userApi.updatePasswordById(id);
        return new ResultModel<>().success();
    }

}
