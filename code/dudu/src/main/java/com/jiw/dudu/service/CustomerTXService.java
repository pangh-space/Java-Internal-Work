package com.jiw.dudu.service;

import cn.hutool.core.util.IdUtil;
import com.jiw.dudu.entities.Customer;
import com.jiw.dudu.entities.Order;
import com.jiw.dudu.mapper.CustomerMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description CustomerTXService
 * @Author pangh
 * @Date 2022年08月31日
 * @Version v1.0.0
 */
@Service
@Slf4j
public class CustomerTXService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private OrderTXService orderTXService;

    /**
     * 标准插入数据
     *
     * @param customer
     */
    public void addCustomer(Customer customer) {
        try {
            int i = customerMapper.insertSelective(customer);
            log.info("CustomerTXSerivce插入方法结果：" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Case1，捕获异常时，要想使事务生效，需要手动抛出RuntimeException异常,最好在设定rollbackFor = Exception.class
     * 如果事务被catch掉，则事务不会回滚
     *
     * @param customer
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCustomer1(Customer customer) {
        try {
            int i = customerMapper.insertSelective(customer);
            log.info("CustomerTXSerivce插入方法结果：" + i);
            int age = 10 / 0;//请判断当下配置，出错事务是否会回滚？
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addCustomer1报告异常:{}", e);
            throw new RuntimeException("addCustomer1报告异常:" + e.getMessage());
        }
    }

    /**
     * Case2，应用在非 public 修饰的方法上,private私有方法和@Transactional注解不能共存，也不会生效：
     * IDEA 会有提醒，文案为: Methods annotated with '@Transactional' must be overridable
     *
     * @param customer
     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    private void addCustomer2(Customer customer) {
//        try {
//            int i = customerMapper.insertSelective(customer);
//            System.out.println("CustomerTXSerivce插入方法结果：" + i);
//            int age = 10 / 0;//请判断当下配置，出错事务是否会回滚？
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("addCustomer2报告异常:{}", e);
//            throw new RuntimeException("addCustomer2报告异常:" + e.getMessage());
//        }
//    }

    /**
     * Case3，异常不匹配，也即@Transactional没有设置rollbackFor = Exception.class属性,对检查性异常不敏感
     * 3.1 只有@Transactional，事务不会回滚，默认rollbackFor 异常为空
     * 3.2 @Transactional + 手动抛出throw new Exception(),事务不回滚，手动抛出异常，未被接收
     * 3.3 默认@Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)事务不回滚，异常不匹配
     * <p>
     * 3.4 OK版，事务被回滚
     *
     * @param customer
     * @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
     * +
     * throw new Exception("addCustomer3报告异常:"+e.getMessage());
     * rollbackFor必须要和抛出的异常类型匹配
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCustomer3(Customer customer) throws Exception {
        try {
            int i = customerMapper.insertSelective(customer);
            int read = new FileInputStream(new File("/Users/pangh/Documents/TempWord/v1.txt")).read();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addCustomer3报告异常:{}", e);
            throw new Exception("addCustomer3报告异常:" + e.getMessage());
        }
    }

    /**
     * Case4
     * 4.1 父线程异常，子线程正常：
     * 父线程抛出异常，主线程事务回滚，
     * 由于子线程是独立存在和父线程不在同一个事务中(国中国，局中局)，
     * 所以子线程的修改并不会被回滚，会插入数据库
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCustomer41(Customer customer) throws Exception {
        customer.setCname("V4.1父线程异常，子线程正常插入事务不回滚");
        new Thread(() -> {
            log.info(Thread.currentThread().getName() + "\t" + "操作开始");
            customerMapper.insertSelective(customer);
            log.info(Thread.currentThread().getName() + "\t" + "操作完成");
        }, "sonThread").start();
        //暂停毫秒
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new Exception(Thread.currentThread().getName() + "-主线程抛出异常，子线程的修改并不会被回滚");
    }

    /**
     * Case4
     * 4.2 子线程的异常不会被外部的线程捕获，所以父线程不抛异常，事务回滚没有生效。
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCustomer42(Customer customer) throws Exception {
        customer.setCname("V4.2父线程正常，子线程异常但是插入成功，事务不回滚");
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                customerMapper.insertSelective(customer);
                throw new Exception("sonThread抛出异常，子线程事务回滚失败");
            }
        }, "sonThread").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info(Thread.currentThread().getName() + "\t" + "----主线程未报异常，到此一游，^_^");
    }

    /**
     * Case5,异常被内部catch,记得将异常抛出，让最外层的事务感知到,catch块添加。添加主动抛出异常，事务回滚成功。
     * throw new Exception("addCustomer5报告异常:"+e.getMessage());
     *
     * @param customer
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCustomer5(Customer customer) throws Exception {
        customer.setCname("V5异常被内部catch,记得将异常抛出");
        try {
            int i = customerMapper.insertSelective(customer);
            int age = 10 / 0;
        } catch (Exception e) {
            e.printStackTrace();
//            throw new Exception("出现异常，主动抛出：" + e.getMessage());
        }
    }


    /**
     * Case6
     * 6.1 主子方法都有事务注解，异常在子方法，事务回滚成功。
     *
     * @param customer
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCustomerAndOrder61(Customer customer) throws Exception {
        try {
            customerMapper.insertSelective(customer);

            orderTXService.addOrder61(new Order(IdUtil.simpleUUID(), Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("addCustomerAndOrder61报告异常:{}", e);
            throw new Exception("addCustomerAndOrder61报告异常:" + e.getMessage());
        }
    }


    /**
     * Case6
     * 6.2 都有事务注解，主方法异常，子方法正常。事务回滚成功。
     *
     * @param customer
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCustomerAndOrder62(Customer customer) throws Exception {
        try {
            customerMapper.insertSelective(customer);

            orderTXService.addOrder62(new Order(IdUtil.simpleUUID(), Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
            int age = 10 / 0;//异常在主方法出现
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addCustomerAndOrder62报告异常:{}", e);
            throw new Exception("addCustomerAndOrder62报告异常:" + e.getMessage());
        }
    }

    /**
     * Case6
     * 6.3 只有主方法有事务注解，主方法正常，子方法异常。事务回滚成功。
     *
     * @param customer
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCustomerAndOrder63(Customer customer) throws Exception {
        try {
            customerMapper.insertSelective(customer);

            orderTXService.addOrder63(new Order(IdUtil.simpleUUID(), Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addCustomerAndOrder63报告异常:{}", e);
            throw new Exception("addCustomerAndOrder63报告异常:" + e.getMessage());
        }
    }

    /**
     * Case6
     * 6.4 只有主方法有事务注解，主方法异常，子方法正常。事务回滚成功。
     *
     * @param customer
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCustomerAndOrder64(Customer customer) throws Exception {
        try {
            customerMapper.insertSelective(customer);

            orderTXService.addOrder64(new Order(IdUtil.simpleUUID(), Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
            int age = 10 / 0;//主方法异常
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addCustomerAndOrder64报告异常:{}", e);
            throw new Exception("addCustomerAndOrder64报告异常:" + e.getMessage());
        }
    }

    /**
     * Case6
     * 6.5只有子方法有事务注解，主方法正常，子方法异常。子方法事务回滚成功，主方法回滚失败。
     *
     * @param customer
     * @throws Exception
     */
    public void addCustomerAndOrder65(Customer customer) throws Exception {
        try {
            customerMapper.insertSelective(customer);

            orderTXService.addOrder65(new Order(IdUtil.simpleUUID(), Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addCustomerAndOrder65报告异常:{}", e);
            throw new Exception("addCustomerAndOrder65报告异常:" + e.getMessage());
        }
    }


    /**6.5补充-----类内部访问
     * CustomerTXSerivce类的外部addNestingCustomer方法没有标注@Transactional，
     *     但是
     *     内部addCustomerInnerCall方法标注@Transactional，在addNestingCustomer里面调用addCustomerInnerCall；
     * @param customer
     * @throws RuntimeException
     *
     * 原因：
     *@Transactional 的工作机制是基于 AOP 实现，AOP 是使用动态代理实现的，
     * 如果通过代理直接调用addNestingCustomer()，通过 AOP 会前后进行增强
     * 现在是通过addNestingCustomer()去调用addCustomerInnerCall()，addCustomerInnerCall
     * 前后不会进行任何增强操作，也就是类内部调用，不会通过代理方式访问。
     *
     * 解决：嵌套调用，@Transactional加外不加内
     *
     */
    public void addNestingCustomer(Customer customer) throws Exception
    {
        addCustomerInnerCall(customer);
    }
    @Transactional(rollbackFor = Exception.class)//子方法加了没用，主方法失效
    public void addCustomerInnerCall(Customer customer) throws Exception
    {
        try {
            int i = customerMapper.insertSelective(customer);
            System.out.println("CustomerTXSerivce插入方法结果："+i);
            int result = 10/0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("事务并没有回滚，控制失败，类内部访问");
            throw new Exception("事务不生效：类内部访问，异常原因"+e.getMessage());
        }
    }

}