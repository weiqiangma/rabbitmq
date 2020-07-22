package com.example.rabbitmq.service;

import com.example.rabbitmq.entity.ProductRobbingRecord;
import java.util.List;

/**
 * 抢单记录表(ProductRobbingRecord)表服务接口
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
public interface ProductRobbingRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProductRobbingRecord queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ProductRobbingRecord> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param productRobbingRecord 实例对象
     * @return 实例对象
     */
    ProductRobbingRecord insert(ProductRobbingRecord productRobbingRecord);

    /**
     * 修改数据
     *
     * @param productRobbingRecord 实例对象
     * @return 实例对象
     */
    ProductRobbingRecord update(ProductRobbingRecord productRobbingRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}