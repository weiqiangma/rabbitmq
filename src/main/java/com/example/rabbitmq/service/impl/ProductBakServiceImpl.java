package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.entity.ProductBak;
import com.example.rabbitmq.dao.ProductBakDao;
import com.example.rabbitmq.service.ProductBakService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 产品信息表(ProductBak)表服务实现类
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@Service("productBakService")
public class ProductBakServiceImpl implements ProductBakService {
    @Resource
    private ProductBakDao productBakDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProductBak queryById(Integer id) {
        return this.productBakDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ProductBak> queryAllByLimit(int offset, int limit) {
        return this.productBakDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param productBak 实例对象
     * @return 实例对象
     */
    @Override
    public ProductBak insert(ProductBak productBak) {
        this.productBakDao.insert(productBak);
        return productBak;
    }

    /**
     * 修改数据
     *
     * @param productBak 实例对象
     * @return 实例对象
     */
    @Override
    public ProductBak update(ProductBak productBak) {
        this.productBakDao.update(productBak);
        return this.queryById(productBak.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.productBakDao.deleteById(id) > 0;
    }
}