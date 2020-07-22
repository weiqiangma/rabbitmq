package com.example.rabbitmq.service;

import com.example.rabbitmq.entity.OrderRecord;
import java.util.List;

/**
 * 订单记录表-业务级别(OrderRecord)表服务接口
 *
 * @author makejava
 * @since 2020-07-16 13:39:42
 */
public interface OrderRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderRecord queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<OrderRecord> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param orderRecord 实例对象
     * @return 实例对象
     */
    OrderRecord insert(OrderRecord orderRecord);

    /**
     * 修改数据
     *
     * @param orderRecord 实例对象
     * @return 实例对象
     */
    OrderRecord update(OrderRecord orderRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}