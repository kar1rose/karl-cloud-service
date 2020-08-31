package org.karl.sh.consumer.service;

import org.karl.sh.consumer.mapper.PurchaseOrderMapper;
import org.karl.sh.consumer.model.PurchaseOrder;
import org.karl.sh.core.templates.API_RESULT_CODE;
import org.karl.sh.core.templates.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:49
 **/
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger("订单service");

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private SysService sysService;

    private static final ReentrantLock lock = new ReentrantLock();

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class}, transactionManager = "transactionManager")
    public void createOrder(Integer userId, Integer goodsId, Integer num) throws InterruptedException {
        if (lock.tryLock(5000, TimeUnit.SECONDS)) {
            try {
                PurchaseOrder order = new PurchaseOrder(UUID.randomUUID().toString().replaceAll("-", ""), new Date(), true, userId);
                if (purchaseOrderMapper.createOrder(order) != 1) {
                    throw new RuntimeException("订单创建失败");
                }
                ApiResult<String> result = sysService.decrement(goodsId, num);
                if (result != null && result.getCode().equals(API_RESULT_CODE.SUCCESS.getCode())) {
                    //处理正常
                    logger.info("order create success");
                } else if (result != null) {
                    throw new RuntimeException(result.getMsg());
                } else {
                    throw new RuntimeException("系统异常");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            } finally {
                lock.unlock();
            }
        } else {
            logger.error("lock 失败");
        }

    }
}
