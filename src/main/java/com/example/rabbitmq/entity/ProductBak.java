package com.example.rabbitmq.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 产品信息表(ProductBak)实体类
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
public class ProductBak implements Serializable {
    private static final long serialVersionUID = 320613356137368598L;
    
    private Integer id;
    
    private String name;
    /**
    * 库存量
    */
    private Integer stock;
    /**
    * 采购日期
    */
    private Object purchaseDate;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 是否有效
    */
    private Integer isActive;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Object getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Object purchaseDate) {
        this.purchaseDate = purchaseDate;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

}