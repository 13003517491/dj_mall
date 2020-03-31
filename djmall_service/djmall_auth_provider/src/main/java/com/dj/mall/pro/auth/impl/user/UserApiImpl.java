package com.dj.mall.pro.auth.impl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.entity.auth.role.RoleEntity;
import com.dj.mall.entity.auth.user.UserEntity;
import com.dj.mall.entity.auth.user.UserRoleEntity;
import com.dj.mall.mapper.auth.role.RoleMapper;
import com.dj.mall.mapper.auth.user.UserMapper;
import com.dj.mall.mapper.bo.user.UserBOReq;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.constant.SystemConstant;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.JavaEmailUtils;
import com.dj.mall.model.util.PasswordSecurityUtil;
import com.dj.mall.pro.auth.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @描述
 * @创建人 zhangjq
 * @创建时间 2020/3/24
 */
@Service
public class UserApiImpl extends ServiceImpl<UserMapper, UserEntity> implements UserApi {


    @Autowired
    private UserRoleService userRoleService;


    /**
     * 根据用户名和密码获取用户信息
     *
     * @param userDTOReq 用户对象
     * @return
     * @throws Exception
     */
    @Override
    public UserDTOResp getUser(UserDTOReq userDTOReq) throws Exception, BusinessException {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.or(i -> i.eq("username", userDTOReq.getUsername())
                    .or().eq("email", userDTOReq.getUsername())
                    .or().eq("phone", userDTOReq.getUsername()));
           queryWrapper.eq("password", userDTOReq.getPassword());

        UserEntity userEntity = this.getOne(queryWrapper);

        if (null == userEntity) {
            throw new BusinessException(SystemConstant.LOGIN_ERROR);
        }
        if (!StringUtils.isEmpty(userEntity.getResetPassword()) &&
                userDTOReq.getPassword().equals(userEntity.getResetPassword())) {
            throw new BusinessException((SystemConstant.RESET_PWD));
        }
        if (!userEntity.getIsDel().equals(SystemConstant.NOT_DEL)) {
            throw new BusinessException(SystemConstant.DEL);
        }
        if (userEntity.getStatus() != 1) {
            throw new BusinessException(SystemConstant.NOT_ACTIVE);
        }
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        if (null != userEntity) {
            updateWrapper.set("last_login_time", new Date());
            updateWrapper.eq("id", userEntity.getId());
            this.update(updateWrapper);
        }
        return DozerUtil.map(userEntity, UserDTOResp.class);
    }

    /**
     * 根据用户名查盐
     *
     * @param username
     * @return
     */
    @Override
    public UserDTOResp getUserByUserName(String username) throws Exception{
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username)
                    .or().eq("phone", username)
                    .or().eq("email", username);
        UserEntity userEntity = this.getOne(queryWrapper);
        return DozerUtil.map(userEntity, UserDTOResp.class);
    }

    /**
     * 用户注册去重
     *
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public Boolean distinct(UserDTOReq userDTOReq) throws Exception {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(userDTOReq.getUsername())) {
            queryWrapper.eq("username", userDTOReq.getUsername());
        }
        if (!StringUtils.isEmpty(userDTOReq.getPhone())) {
            queryWrapper.eq("phone", userDTOReq.getPhone());
        }
        if (!StringUtils.isEmpty(userDTOReq.getEmail())) {
            queryWrapper.eq("email", userDTOReq.getEmail());
        }
        queryWrapper.eq("is_del", SystemConstant.NOT_DEL);
        UserEntity userEntity = this.getOne(queryWrapper);
        if (userEntity == null) {
            return true;
        }
        return false;
    }

    /**
     * 用户注册
     *
     * @param userDTOReq
     * @throws Exception
     */
    @Override
    public void saveUser(UserDTOReq userDTOReq) throws Exception {
        UserEntity user = DozerUtil.map(userDTOReq, UserEntity.class);
        this.save(user);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRoleId(userDTOReq.getRoleId());
        userRoleEntity.setUserId(user.getId());
        userRoleService.save(userRoleEntity);
    }

    /**
     * 邮箱激活
     *
     * @param username
     */
    @Override
    public void updateStatusByUsername(String username) throws Exception {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper();
        updateWrapper.set("status", SystemConstant.ACTIVE_SUCCESS);
        updateWrapper.eq("username", username);
        this.update(updateWrapper);
    }

    /**
     * 根据手机查找用户
     *
     * @param phone
     */
    @Override
    public UserDTOResp getUserByPhone(String phone) throws Exception {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        UserEntity userEntity = this.getOne(queryWrapper);
        return DozerUtil.map(userEntity, UserDTOResp.class);
    }

    /**
     * 根据手机号修改验证码与失效时间
     *
     * @param phone
     * @param newcode
     */
    @Override
    public void updateCodeAndCodeTimeByPhone(String phone, Integer newcode) throws Exception {
        //根据手机号修改验证码
        //时间加减
        Calendar cal = Calendar.getInstance();
        //设置起时间
        cal.setTime(new Date());
        //增加一分钟
        cal.add(Calendar.MINUTE, 2);
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("code_time", cal.getTime()).set("code", newcode);
        updateWrapper.eq("phone", phone);
        this.update(updateWrapper);
    }

    /**
     * 根据手机号和验证码查询用户
     *
     * @param phone
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public UserDTOResp getUserByPhoneAndCode(String phone, String code) throws Exception {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code)
                .eq("phone", phone);
        return DozerUtil.map(this.getOne(queryWrapper), UserDTOResp.class);
    }

    /**
     * 根据手机号修改盐和密码
     * @param userDTOReq
     */
    @Override
    public void updateSaltAndPwdByPhone(UserDTOReq userDTOReq) throws Exception {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("salt", userDTOReq.getSalt())
                .set("password", userDTOReq.getPassword());
        updateWrapper.eq("phone", userDTOReq.getPhone());
        this.update(updateWrapper);
    }

    /**
     * 查询所有用户
     *
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public List<UserDTOResp> findUserAll(UserDTOReq userDTOReq) throws Exception {
        return DozerUtil.mapList(getBaseMapper().findUserAll(DozerUtil.map(userDTOReq, UserBOReq.class)), UserDTOResp.class);
    }

    /**
     * 根据id获取用户
     *
     * @param id 用户id
     * @return
     * @throws Exception
     */
    @Override
    public UserDTOResp getUserById(Integer id) throws Exception {
        return DozerUtil.map(this.getById(id), UserDTOResp.class);
    }

    /**
     * 根据id修改用户
     *
     * @param userDTOReq
     */
    @Override
    public void updateUserById(UserDTOReq userDTOReq) throws Exception {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("username", userDTOReq.getUsername())
                .set("phone", userDTOReq.getPhone())
                .set("email", userDTOReq.getEmail())
                .set("sex", userDTOReq.getSex());
        updateWrapper.eq("id", userDTOReq.getUserId());
        this.update(updateWrapper);
    }

    /**
     * 修改用户角色
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @throws Exception
     */
    @Override
    public void updateUserRole(Integer userId, Integer roleId) throws Exception {
        //用户角色表修改
        UpdateWrapper<UserRoleEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("role_id", roleId);
        updateWrapper.eq("user_id", userId);
        userRoleService.update(updateWrapper);
    }

    /**
     * 根据id获取用户角色
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public UserRoleEntity getUserRole(Integer id) throws Exception {
        QueryWrapper<UserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        return userRoleService.getOne(queryWrapper);
    }


    /**
     * 修改用户
     *
     * @param id 用户id
     * @throws Exception
     */
    @Override
    public void updateUser(Integer id) throws Exception {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", SystemConstant.ACTIVE_SUCCESS);
        updateWrapper.eq("id", id);
        this.update(updateWrapper);
    }

    /**
     * 批量删除用户
     *
     * @param ids 用户id
     */
    @Override
    public void delUserAndUserRoleByIds(Integer[] ids) throws Exception {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del", SystemConstant.NOT_SHOW);
        updateWrapper.in("id", ids);
        this.update(updateWrapper);
        UpdateWrapper<UserRoleEntity> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.set("is_del", SystemConstant.NOT_SHOW);
        updateWrapper1.in("user_id", ids);
        userRoleService.update(updateWrapper1);
    }

    /**
     * 重置密码
     *
     * @param id 用户id
     */
    @Override
    public void updatePasswordById(Integer id) throws Exception {
        String resetPassword = PasswordSecurityUtil.generateRandom(6);
        String salt = PasswordSecurityUtil.generateSalt();
        String md5Pwd = PasswordSecurityUtil.enCode32(resetPassword);
        String md5ResetPwd = PasswordSecurityUtil.enCode32(md5Pwd + salt);
        System.out.println("初始密码 = " + resetPassword);
        System.out.println("盐 = " + salt);
        System.out.println("初始密码mds加密 = " + md5Pwd);
        System.out.println("md5加密(初始密码mds加密+盐) = " + md5ResetPwd);
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper();
        updateWrapper.set("password", md5ResetPwd)
                     .set("reset_password", md5ResetPwd)
                     .set("salt", salt);
        updateWrapper.eq("id", id);
        this.update(updateWrapper);
        UserEntity user = this.getById(id);
        //发送邮件
        DateFormat df = DateFormat.getDateTimeInstance();
        JavaEmailUtils.sendEmail(user.getEmail(), "重置密码",
                "您的密码已被管理员于"+df.format(new Date())+"时重置为"+resetPassword+".为了您的账户安全，请及时修改。</br>" +
                        "<a href='http://localhost:8081/admin/auth/user/toLogin'>点我去登陆</a><br>"
                        );
    }

    
}
