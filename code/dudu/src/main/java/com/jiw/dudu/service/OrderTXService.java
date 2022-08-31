package com.jiw.dudu.service;

import com.jiw.dudu.entities.Order;
import com.jiw.dudu.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description OrderTXService
 * @Author pangh
 * @Date 2022年08月31日
 * @Version v1.0.0
 */
@Service
@Slf4j
public class OrderTXService {

    @Autowired
    private OrderMapper orderMapper;


    /**
     * 子方法有事务注解+异常
     *
     * @param order
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addOrder61(Order order) throws Exception {
        try {
            orderMapper.insertSelective(order);
            int age = 10 / 0; //V6.1
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addOrder61报告异常:{}", e);
            throw new Exception("addOrder61报告异常:" + e.getMessage());
        }
    }


    /**
     * 子方法有事务注解+正常
     *
     * @param order
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addOrder62(Order order) throws Exception {
        try {
            orderMapper.insertSelective(order);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addOrder62报告异常:{}", e);
            throw new Exception("addOrder62报告异常:" + e.getMessage());
        }
    }

    /**
     * 子方法无事务注解+异常
     *
     * @param order
     * @throws Exception
     */
    public void addOrder63(Order order) throws Exception {
        try {
            orderMapper.insertSelective(order);
            int age = 10 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addOrder63报告异常:{}", e);
            throw new Exception("addOrder63报告异常:" + e.getMessage());
        }
    }

    /**
     * 子方法无事务注解+正常
     *
     * @param order
     * @throws Exception
     */
    public void addOrder64(Order order) throws Exception {
        try {
            orderMapper.insertSelective(order);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addOrder64报告异常:{}", e);
            throw new Exception("addOrder64报告异常:" + e.getMessage());
        }
    }

    /**
     * 只有子方法有事务注解+异常
     *
     * @param order
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addOrder65(Order order) throws Exception {
        try {
            orderMapper.insertSelective(order);
            int age = 10 / 0;//异常在子方法出现
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addOrder65报告异常:{}", e);
            throw new Exception("addOrder65报告异常:" + e.getMessage());
        }
    }

}