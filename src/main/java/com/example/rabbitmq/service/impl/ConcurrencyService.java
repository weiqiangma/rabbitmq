package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.dao.ProductDao;
import com.example.rabbitmq.dao.ProductRobbingRecordDao;
import com.example.rabbitmq.entity.Product;
import com.example.rabbitmq.entity.ProductRobbingRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/8/25.
 */
@Service
public class ConcurrencyService {

    private static final Logger log= LoggerFactory.getLogger(ConcurrencyService.class);

    private static final String ProductNo="product_10010";

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductRobbingRecordDao productRobbingRecordDao;

    /**
     * 处理抢单
     * @param mobile
     */
    public void manageRobbing(String mobile){
        /*try {
            Product product=productMapper.selectByProductNo(ProductNo);
            if (product!=null && product.getTotal()>0){
                log.info("当前手机号：{} 恭喜您抢到单了!",mobile);
                productMapper.updateTotal(product);
            }else{
                log.error("当前手机号：{} 抢不到单!",mobile);

            }
        }catch (Exception e){
            log.error("处理抢单发生异常：mobile={} ",mobile);
        }*/ //--v1.0


        //+v2.0
        try {
            Product product=productDao.selectByProductNo(ProductNo);
            if (product != null && product.getTotal() > 0){
                int result=productDao.updateTotal(product);
                if (result>0) {
                    ProductRobbingRecord entity=new ProductRobbingRecord();
                    entity.setMobile(mobile);
                    entity.setProductId(product.getId());
                    productRobbingRecordDao.insert(entity);
                }
            }
        }catch (Exception e){
            log.error("处理抢单发生异常：mobile={} ",mobile);
        }
    }
}

















