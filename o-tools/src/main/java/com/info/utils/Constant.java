package com.info.utils;

/**
 * 常量
 *
 * @author Gaosx
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;
    /**
     * 数据权限过滤
     */
    public static final String SQL_FILTER = "sql_filter";
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String PAGESIZE = "pageSize";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";

    /**
     * 标记为已读信息
     */
    public enum IsRead {
        /**
         * 已读信息
         */
        IS_READ(1),
        /**
         * 未读信息
         */
        NOT_READ(0);

        private int value;

        IsRead(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * redis键值对的过期时间设置枚举类
     */
    public enum RedisKeyExpire {

        SIGN_INFO(90000);

        private int value;

        RedisKeyExpire(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 支付类型 1 微信 2 支付宝 3 银联 4 苹果内购 5 谷歌支付
     */
    public enum PayType {
        /**
         * 微信支付
         */
        PAY_WEIXIN(1),
        /**
         * 支付宝支付
         */
        PAY_ALI(2),
        /**
         * 银联
         */
        PAY_UNION(3),
        /**
         * 苹果内购
         */
        PAY_IOS(4),
        /**
         * 谷歌支付
         */
        PAY_GOOGLE(5),
        /**
         * 苹果内购
         */
        PAY_BEST(6);

        private int value;

        PayType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 订单详情中商品购买方式 1 表示正常 2 表示团购 3 表示换购商品 4 表示抢购 5 表示赠品
     */
    public enum BuyProductType {
        /**
         * 表示正常
         */
        BUY_TYPE_1(1),
        /**
         * 表示团购
         */
        BUY_TYPE_2(2),
        /**
         * 表示换购商品
         */
        BUY_TYPE_3(3),
        /**
         * 表示抢购
         */
        BUY_TYPE_4(4),
        /**
         * 表示赠品
         */
        BUY_TYPE_5(5),
        /**
         * ----------------
         */
        BUY_TYPE_6(6);

        private int value;

        BuyProductType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    /**
     * 订单状态的枚举类 对应数据库的几个状态
     */
    public enum OrderInfoFlow {
        /**
         * 待支付
         */
        ORDER_FLOW_1(1),
        /**
         * 已支付
         */
        ORDER_FLOW_2(2),
        /**
         * 待发货
         */
        ORDER_FLOW_3(3),
        /**
         * 确认收货
         */
        ORDER_FLOW_4(4),
        /**
         * 完成订单
         */
        ORDER_FLOW_5(5),
        /**
         * 等待接单订单
         */
        ORDER_FLOW_6(6);

        private int value;

        OrderInfoFlow(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 商品状态的枚举类 商品状态  1 下架的 2 未销售  3 在售
     */
    public enum ProductInfoStatus {
        /**
         * 下架的
         */
        STATUS_ONE(1),
        /**
         * 未销售
         */
        STATUS_TWO(2),
        /**
         * 在售
         */
        STATUS_THREE(3);

        private int value;

        ProductInfoStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 数组长度默认初始值0
     */
    public enum initListSize {
        /**
         * 目录
         */
        LIST_SIZE(0);

        private int value;

        initListSize(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
