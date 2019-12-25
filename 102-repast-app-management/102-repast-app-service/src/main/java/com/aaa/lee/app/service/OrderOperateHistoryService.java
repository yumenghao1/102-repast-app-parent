package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.mapper.OrderOperateHistoryMapper;
import com.aaa.lee.app.model.OrderOperateHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class OrderOperateHistoryService extends BaseService<OrderOperateHistory> {
    @Autowired
    private OrderOperateHistoryMapper orderOperateHistoryMapper;

    @Override
    public Mapper<OrderOperateHistory> getMapper() {
        return orderOperateHistoryMapper;
    }
}
