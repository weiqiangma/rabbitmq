package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.dao.ProductDao;
import com.example.rabbitmq.dao.ProductRobbingRecordDao;
import com.example.rabbitmq.entity.Product;
import com.example.rabbitmq.entity.ProductRobbingRecord;
import com.example.rabbitmq.entity.UserLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/25.
 */
@Service
public class ConcurrencyService {

    private static final Logger log= LoggerFactory.getLogger(ConcurrencyService.class);

    private static final String ProductNo="product_10010";

    @Resource
    private ProductDao productDao;

    @Resource
    private ProductRobbingRecordDao productRobbingRecordDao;

    @Resource
    private CommonMqService commonMqService;

    /**
     * 处理抢单
     * @param mobile
     */
    public int manageRobbing(String mobile){
        int result = -1;
        try {
            Product product=productDao.selectByProductNo(ProductNo);
            if (product != null && product.getTotal() > 0){
                result=productDao.updateTotal(product);
                if (result>0) {
                    ProductRobbingRecord entity=new ProductRobbingRecord();
                    entity.setMobile(mobile);
                    entity.setProductId(product.getId());
                    entity.setRobbingTime(new Date());
                    entity.setUpdateTime(new Date());
                    productRobbingRecordDao.insert(entity);
                    UserLog userLog = new UserLog("mobile", "商品秒杀", "抢商品","用户" + mobile + "抢单成功", new Date());
                    commonMqService.sendUserLog(userLog);
                } else{
                    log.info("抢单失败！！！");
                }
            }
        }catch (Exception e){
            log.error("处理抢单发生异常：mobile={} ",mobile);
        }
        return result;
    }
}