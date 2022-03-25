package org.karl.sh.purchase.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.karl.sh.core.templates.API_RESULT_CODE;
import org.karl.sh.core.templates.ApiResult;
import org.karl.sh.core.utils.SnowFlake;
import org.karl.sh.purchase.client.SysService;
import org.karl.sh.purchase.mapper.PurchaseOrderMapper;
import org.karl.sh.purchase.model.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
public class OrderService extends ServiceImpl<PurchaseOrderMapper, PurchaseOrder> implements IService<PurchaseOrder> {

    private static final Logger logger = LoggerFactory.getLogger("订单service");

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private SysService sysService;
    @Autowired
    private SnowFlake snowFlake;

    private static final ReentrantLock LOCK = new ReentrantLock();

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class}, transactionManager = "transactionManager")
    public void createOrder(Integer userId, Integer goodsId, Integer num) throws InterruptedException {
        if (LOCK.tryLock(5000, TimeUnit.SECONDS)) {
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
                LOCK.unlock();
            }
        } else {
            logger.error("lock 失败");
        }
    }

    @Async
    public void createOrder(String name) {
        try {
            Thread.sleep(5000);
            logger.info("=======产品:{}订单ID:{}创建完成=======", name, snowFlake.nextId());
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    public PurchaseOrder query(String orderId) {
        LambdaQueryWrapper<PurchaseOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PurchaseOrder::getOrderId, orderId);
        return baseMapper.selectOne(wrapper);
    }


}
