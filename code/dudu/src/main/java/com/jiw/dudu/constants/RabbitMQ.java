package com.jiw.dudu.constants;

/**
 * @Description RabbitMQ队列及交换机相关定义
 * @Author pangh
 * @Date 2022年09月27日
 * @Version v1.0.0
 */
public class RabbitMQ {

    /**
     * 用户队列
     */
    public static final String USER_QUEUE = "msg.user";

    /**
     * 用户死信队列
     */
    public static final String DLX_USER_QUEUE = "dlx.msg.user";

    /**
     * 组织队列
     */
    public static final String ORG_QUEUE = "msg.org";

    /**
     * 组织死信队列
     */
    public static final String DLX_ORG_QUEUE = "dlx.msg.org";

    /**
     * 组织架构备份队列
     */
    public static final String BAK_ORG_QUEUE = "bak.msg.org";

    /**
     * 交换机名称
     */
    public static final String ORG_USER_EXCHANGE = "dudu.direct.org.user";

    /**
     * 死信交换机名称
     */
    public static final String DLX_ORG_USER_EXCHANGE = "dlx.dudu.direct.org.user";

    /**
     * 备份交换机名称
     */
    public static final String BAK_ORG_USER_EXCHANGE = "bak.dudu.fanout.org.user";

    /**
     * 用户路由KEY
     */
    public static final String USER_ROUTING_KEY = "userRouting";

    /**
     * 用户死信路由KEY
     */
    public static final String DLX_USER_ROUTING_KEY = "dlxUserRouting";

    /**
     * 组织机构路由KEY
     */
    public static final String ORG_ROUTING_KEY = "orgRouting";

    /**
     * 组织机构死信路由KEY
     */
    public static final String DLX_ORG_ROUTING_KEY = "dlxOrgRouting";

}