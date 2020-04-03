package com.dj.mall.pro.auth.service.record.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.entity.auth.record.TimeRecordEntity;
import com.dj.mall.mapper.auth.record.TimeRecordMapper;
import com.dj.mall.pro.auth.service.record.TimeRecordService;
import org.springframework.stereotype.Service;

/**
 * @描述 时间记录接口实现类
 * @创建人 zhangjq
 * @创建时间 2020/4/2
 */
@Service
public class TimeRecordServiceImpl extends ServiceImpl<TimeRecordMapper, TimeRecordEntity> implements TimeRecordService {
}
