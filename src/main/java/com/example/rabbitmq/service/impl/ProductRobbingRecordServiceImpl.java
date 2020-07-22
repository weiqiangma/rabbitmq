package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.entity.ProductRobbingRecord;
import com.example.rabbitmq.dao.ProductRobbingRecordDao;
import com.example.rabbitmq.service.ProductRobbingRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 抢单记录表(ProductRobbingRecord)表服务实现类
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@Service("productRobbingRecordService")
public class ProductRobbingRecordServiceImpl implements ProductRobbingRecordService {
    @Resource
    private ProductRobbingRecordDao productRobbingRecordDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProductRobbingRecord queryById(Integer id) {
        return this.productRobbingRecordDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ProductRobbingRecord> queryAllByLimit(int offset, int limit) {
        return this.productRobbingRecordDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param productRobbingRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ProductRobbingRecord insert(ProductRobbingRecord productRobbingRecord) {
        this.productRobbingRecordDao.insert(productRobbingRecord);
        return productRobbingRecord;
    }

    /**
     * 修改数据
     *
     * @param productRobbingRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ProductRobbingRecord update(ProductRobbingRecord productRobbingRecord) {
        this.productRobbingRecordDao.update(productRobbingRecord);
        return this.queryById(productRobbingRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.productRobbingRecordDao.deleteById(id) > 0;
    }
}