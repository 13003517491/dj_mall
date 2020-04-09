package com.dj.mall.mapper.auth.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.entity.auth.user.UserEntity;
import com.dj.mall.mapper.bo.user.UserBOReq;
import com.dj.mall.mapper.bo.user.UserBOResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @描述 用户mapper
 * @创建人 zhangjq
 * @创建时间 2020/3/24
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 查询所有用户
     * @param query
     * @return
     * @throws Exception
     */
    IPage<UserBOResp> findUserAll(IPage page, @Param("query") UserBOReq query) throws DataAccessException;
}
