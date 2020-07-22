package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.entity.OrderRecord;
import com.example.rabbitmq.dao.OrderRecordDao;
import com.example.rabbitmq.service.OrderRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单记录表-业务级别(OrderRecord)表服务实现类
 *
 * @author makejava
 * @since 2020-07-16 13:39:42
 */
@Service("orderRecordService")
public class OrderRecordServiceImpl implements OrderRecordService {
    @Resource
    private OrderRecordDao orderRecordDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderRecord queryById(Integer id) {
        return this.orderRecordDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<OrderRecord> queryAllByLimit(int offset, int limit) {
        return this.orderRecordDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param orderRecord 实例对象
     * @return 实例对象
     */
    @Override
    public OrderRecord insert(OrderRecord orderRecord) {
        this.orderRecordDao.insert(orderRecord);
        return orderRecord;
    }

    /**
     * 修改数据
     *
     * @param orderRecord 实例对象
     * @return 实例对象
     */
    @Override
    public OrderRecord update(OrderRecord orderRecord) {
        this.orderRecordDao.update(orderRecord);
        return this.queryById(orderRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.orderRecordDao.deleteById(id) > 0;
    }
}