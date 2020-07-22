package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.entity.UserOrder;
import com.example.rabbitmq.dao.UserOrderDao;
import com.example.rabbitmq.service.UserOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户订单表(UserOrder)表服务实现类
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@Service("userOrderService")
public class UserOrderServiceImpl implements UserOrderService {
    @Resource
    private UserOrderDao userOrderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserOrder queryById(Integer id) {
        return this.userOrderDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserOrder> queryAllByLimit(int offset, int limit) {
        return this.userOrderDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userOrder 实例对象
     * @return 实例对象
     */
    @Override
    public UserOrder insert(UserOrder userOrder) {
        this.userOrderDao.insert(userOrder);
        return userOrder;
    }

    /**
     * 修改数据
     *
     * @param userOrder 实例对象
     * @return 实例对象
     */
    @Override
    public UserOrder update(UserOrder userOrder) {
        this.userOrderDao.update(userOrder);
        return this.queryById(userOrder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userOrderDao.deleteById(id) > 0;
    }
}