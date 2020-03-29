package com.dj.mall.api.auth.user;

import com.dj.mall.entity.auth.role.RoleEntity;
import com.dj.mall.entity.auth.user.UserRoleEntity;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;

import java.util.List;

/**
 * @描述 用户接口
 * @创建人 zhangjq
 * @创建时间 2020/3/24
 */
public interface UserApi {

    /**
     * 根据用户名和密码获取用户信息
     * @param userDTOReq 用户对象
     * @return
     * @throws Exception
     */
    UserDTOResp getUser(UserDTOReq userDTOReq) throws Exception, BusinessException;

    /**
     * 根据用户名查盐
     * @param username
     * @return
     */
    UserDTOResp getUserByUserName(String username) throws Exception;

    /**
     * 用户注册去重
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    Boolean distinct(UserDTOReq userDTOReq) throws Exception;

    /**
     * 用户注册
     * @throws Exception
     */
    void saveUser(UserDTOReq userDTOReq) throws Exception;

    /**
     * 邮箱激活
     * @param username
     */
    void updateStatusByUsername(String username) throws Exception;

    /**
     * 根据手机查找用户
     * @param phone
     */
    UserDTOResp getUserByPhone(String phone) throws Exception;

    /**
     *根据手机号修改验证码与失效时间
     * @param phone 手机号
     * @param newcode 验证码
     */
    void updateCodeAndCodeTimeByPhone(String phone, Integer newcode) throws Exception;

    /**
     * 根据手机号和验证码查询用户
     * @param phone 手机号
     * @param code 验证码
     * @return
     * @throws Exception
     */
    UserDTOResp getUserByPhoneAndCode(String phone, String code) throws Exception;

    /**
     * 根据手机号修改盐和密码
     * @param userDTOReq 接收对象
     */
    void updateSaltAndPwdByPhone(UserDTOReq userDTOReq) throws Exception;

    /**
     * 查询所有用户
     * @param userDTOReq 接收对象
     * @return
     * @throws Exception
     */
    List<UserDTOResp> findUserAll(UserDTOReq userDTOReq) throws Exception;

    /**
     * 根据id获取用户
     * @param id 用户id
     * @return
     * @throws Exception
     */
    UserDTOResp getUserById(Integer id) throws Exception;

    /**
     * 根据id修改用户
     * @param userDTOReq 用户接收对象
     */
    void updateUserById(UserDTOReq userDTOReq) throws Exception;

    /**
     * 修改用户角色
     * @param userId 用户id
     * @param roleId 角色id
     * @throws Exception
     */
    void updateUserRole(Integer userId, Integer roleId) throws Exception;

    /**
     * 根据id获取用户角色
     * @param id 用户id
     * @return
     * @throws Exception
     */
    UserRoleEntity getUserRole(Integer id) throws Exception;

    /**
     * 用户激活
     * @param id 用户id
     * @throws Exception
     */
    void updateUser(Integer id) throws Exception;
}
