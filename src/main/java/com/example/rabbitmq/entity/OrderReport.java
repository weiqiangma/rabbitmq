package com.example.rabbitmq.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 订单报表表-分析级别(OrderReport)实体类
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
public class OrderReport implements Serializable {
    private static final long serialVersionUID = -46313467952382673L;
    
    private Integer id;
    /**
    * 订单编号
    */
    private String orderNo;
    /**
    * 订单类型
    */
    private String orderType;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}