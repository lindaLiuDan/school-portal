package com.info.redis;

/**
 * 功能描述: Redis所有Keys
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/6/10 13:41
 * @Return:
 */
public class RedisKeyUtils {

    public static String getSysConfigKey(String key) {
        return "sys:config:" + key;
    }

    public static String getShiroSessionKey(String key) {
        return "sessionid:" + key;
    }

    /**
     * 功能描述: 测试redis的内部类keys
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/14 10:48
     * @Return:
     */
    public class TestRedisKeys {

        // 这个是作为会员签到时候真能送积分(金币)的keys
        public static final String TEST_INFO = "test:";

    }

    /**
     * 功能描述: 商品信息的keys
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 17:00
     * @Return:
     */
    public class ProductKeys {

        // 商品单位信息的keys
        public static final String UNIT = "unit:";
        //商品单位信息hset，hget存储方式：UNIT_INFO,info,id
        public static final String UNIT_INFO = UNIT + "info";
        //存储所有的商品单位信息String存储方式：UNIT_ALL,list,id
        public static final String UNIT_ALL = UNIT + "all";
        //存储只是单单一个名字的--string
        public static final String UNIT_INFO_NAME = UNIT + "infoName";

        //商品类别信息的keys
        public static final String CATE = "cate:";
        //商品类别信息详情的keys，hset，hget存储方式：CATE_INFO,catteInfo,ID
        public static final String CATE_INFO = CATE + "info";
        //商品类别信息详情的keys，hset，hget存储方式：CATE_INFO,catteInfo,ID--string
        public static final String CATE_INFO_NAME = CATE + "name";
        //商品类根据信息详情的keys，hset，hget存储方式：CATE_PARENT,catteInfo,parentID
        public static final String CATE_PARENT = CATE + "parent";
        //所有商品类根据信息详情的keys，String存储方式：CATE_PARENT,list
        public static final String CATE_ALL = CATE + "all";

        //所有商品信息详情的keys，hset,hget存储方式
        public static final String PRODUCT = "product:";
        //所有商品信息详情的keys，hset,hget存储方式：PRODUCT_INFO,info,ID
        public static final String PRODUCT_INFO = PRODUCT + "info";


    }

    /**
     * 功能描述: 数据库公共字典类型集合
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/14 10:50
     * @Return:
     */
    public class AdvertisKeys {
        //存放广告信息的redis集合
        public static final String ADVERTIS = "advertis:";
        //存放字典详情的值hset，het存放方式：ADVERTIS_INFO，info,Id
        public static final String ADVERTIS_INFO = ADVERTIS + "info";
        //存放同一个key字典集合hset，het存放方式：ADVERTIS_LIST，list,infoId
        public static final String ADVERTIS_LIST = ADVERTIS + "list";
    }

    /**
     * 功能描述: 数据库公共字典类型集合
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/14 10:50
     * @Return:
     */
    public class DictKeys {

        public static final String DICT = "dict:";
        //存放字典详情的值hset，het存放方式：DICT_INFO，info,infoId
        public static final String DICT_INFO = DICT + "info";
        //存放同一个key字典集合hset，het存放方式：DICT_LIST，infoList,key
        public static final String DICT_LIST = DICT + "list";
    }

    /**
     * 功能描述: 商家信息的keys
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/23 11:44
     * @Return:
     */
    public class ProviderKeys {

        //商家信息keys
        public static final String PROVIDER = "provider:";
        //商家信息--存储店铺详情信息hset，hget存储方式：PROVIDER_INFO,providerId,providerInfo
        public static final String PROVIDER_INFO = PROVIDER + "info";

        //店铺等级的集合
        public static final String GRADE = "grade:";
        //店铺等级的集合所有string存储
        public static final String GRADE_LIST = GRADE + "list";
        //单个店铺等级的hset，hget存储方式：GRADE_INFO,gradeId,gradeInfo
        public static final String GRADE_INFO = GRADE + "info";

        //存储店铺类型详情信息hset，hget存储方式：PROVIDER_TYPE,typeId,typeInfo
        public static final String PROVIDER_TYPE_INFO = PROVIDER + "typeInfo";
        //存储店铺类型parentId下的所有的集合hset，hget存储方式：PROVIDER_TYPE,typeId,typeInfo
        public static final String PROVIDER_TYPE_list = PROVIDER + "typeList";
        //存储店铺所有的类型String存储方式：PROVIDER_TYPE_ALL,typeInfoList
        public static final String PROVIDER_TYPE_ALL = PROVIDER + "typeALL";

        //商户token  set get 方式
        public static final String TOKEN = PROVIDER + "token_";

        //商户信息  provider：info    hset  hget 方式
        public final static String USER_INFO_MOBILE = PROVIDER + "mobile";


    }

    /**
     * 功能描述: 关于订单的keys
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 11:44
     * @Return:
     */
    public class OrderKeys {

        //用户订单的集合
        public static final String ORDER = "order:";
        //存放订单流程的集合使用string存储获取，存储格式：ORDER_FLOW，flowList
        public static final String ORDER_FLOW_LIST = "flow:list";
        //单个订单节点的流程信息hset，hget存储方式：ORDER_FLOW_INFO,flow,flowId
        public static final String ORDER_FLOW_INFO = "flow:info";

        //存放用户订单日志存放格式hset，hget存储，ORDER_LOG，orderId,logInfo
        public static final String ORDER_LOG = ORDER + "log";

        //存放用户订单详情存放格式hset，hget存储，ORDER_INFO，orderId,orderInfo--注意仅仅存储订单信息不存储订单详情信息
        public static final String ORDER_INFO = ORDER + "info";

        //订单详情的keys，hset，hget存储：ORDER_ITEM,info,orderId,这里之存放商品信息不存放整个订单详情信息注意了 TODO 注意
        //由于商品频繁更新原因，也因为查询的比较频繁
        public static final String ORDER_ITEM = ORDER + "item";


    }

    /**
     * 功能描述: 业主端的redis key
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/13 18:17
     * @Return:
     */
    public class OwnerKeys {

        // 业主认证key
        public static final String AUTH = "auth:";
        //业主认证key
        public static final String AUTH_INFO = AUTH + "info";


        //业主报修key
        public static final String REPAIR = "repair:";
        //业主报修详情key
        public static final String REPAIR_INFO = REPAIR + "info";
        //业主报修类型
        public static final String REPAIR_TYPE = REPAIR + "type";
        //业主报修类型List
        public static final String REPAIR_LIST = REPAIR + "list";


        //社区小区名称
        public static final String COMMUNITY = "community:";
        //单个社区名字
        public static final String COMMUNITY_INFO = COMMUNITY + "info";
        //社区集合set，get，String存储方式：
        public static final String COMMUNITY_LIST = COMMUNITY + "list";
        //社区消息通告详情
        public static final String COMMUNITY_MESSAGE_INFO = COMMUNITY + "message:info";
        //社区消息通告集合
        public static final String COMMUNITY_MESSAGE_LIST = COMMUNITY + "message:list";


        //社区楼号信息前缀
        public static final String FLOOR = "floor:";
        //社区楼号详细信息存储方式是根据hset，hget：FLOOR_INFO,社区ID主键，INFO
        public static final String FLOOR_INFO = FLOOR + "info";
        //社区楼号信息集合存储方式是根据hset，hget：FLOOR_IIST,社区ID主键，list集合
        public static final String FLOOR_LIST = FLOOR + "list";


        //社区单元信息
        public static final String UNIT = "unit:";
        //反向的根据单元ID查询存储hset，hget存储都是汉字:ROOM_INFO,info,buildId
        public static final String UNIT_INFO = UNIT + "info";
        //反向的根据楼号ID查询对应的 楼号 单元号 楼层--这是一个反向查询的方法,存储hset，hget存储都是汉字
        public static final String UNIT_LIST = UNIT + "list";


        //社区楼层信息
        public static final String LEVEL = "level:";
        //社区楼层信息，查询楼层信息hset，hget存储：LEVEL_INFO，info，id主键
        public static final String LEVEL_INFO = LEVEL + "info";
        //社区楼层信息根据单元ID信息查询对应hset，hget存储：LEVEL_LIST，list，单元ID主键
        public static final String LEVEL_LIST = LEVEL + "list";


        //社区房号信息
        public static final String ROOM = "room:";
        //反向的根据房号查询存储hset，hget存储都是汉字:ROOM_INFO,info,buildId
        public static final String ROOM_INFO = ROOM + "info";
        //反向的根据楼层ID查询对应的房号集合存储hset，hget存储都是汉字
        public static final String ROOM_LIST = ROOM + "list";


        //业主租赁信息key
        public static final String LEASE = "lease:";
        //租赁信息详情key
        public static final String LEASE_INFO = "info";
        //个人租赁信息集合带分页key
        public static final String LEASE_LIST = "list";


        //业主闲置交易信息keys集合
        public static final String IDLE = "idle:";
        //闲置交易信息详情hset，hget存储样例：IDLE_INFO，idleID,json
        public static final String IDLE_INFO = IDLE + "info";
        //闲置交易信息详情图片集合,hset，hget存储样例：IDLE_IMG，idleID,json
        public static final String IDLE_IMG = IDLE + "img";
        //闲置交易信息评论集合,hset，hget存储样例：IDLE_IMG，idleID,json
        public static final String IDLE_COMMENT = IDLE + "comment";

        //物业信息
        public static final String OWNER = "ower:";

        //物业的token  set get 方式
        public static final String TOKEN = OWNER + "token_";

        //物业的信息  provider：info    hset  hget 方式
        public final static String USER_INFO_MOBILE = OWNER + "mobile";

    }

    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/13 18:17
     * @Return:
     */
    public class UserInfoKyes {

        //所有涉及到用户基本信息的keys user_info_{userId}
        public final static String USER = "user:";
        public final static String MOBILE = "mobile:";
        public final static String COLLECT = "collect:";

        //根据手机号码查询用户信息的Keys mobile_{mobile}
        public final static String USER_GET_MOBILE = MOBILE + "user";
        //用户金币(积分)数
        public final static String USER_BALANCE = USER + "balance";
        //所有涉及到用户基本信息的keys user_info_{userId}
        public final static String USER_INFO_MOBILE = USER + "mobile";
        //验证码 get set方式
        public final static String USER_INFO_CHECKCODE = MOBILE + "code_";

        //根据Token值查询用户ID返回用户TK失效时间以及修改时间UserID等
        public final static String TOKEN = USER + "token";
        //用户所在的小区hset，hget存储方式：USER_COMMUNITY,info,id
        public final static String USER_COMMUNITY = USER + "community";

        //业主签到的keys集合
        public final static String SIGN = "sign:";
        //业主签到信息详情hset，hget存储方式：SIGN_INFO,signInfo,id
        public final static String SIGN_INFO = SIGN + "info";
        //业主签到列表使用redis分页的方式获取存储。
        public final static String SIGN_USER_LIST = SIGN + "user";


    }


    /**
     * 功能描述:
     *
     * @param: 区域基础信息
     * @return:
     * @auther: 高山溪  By User
     * @date: 2018/11/12 15:54
     */
    public class AreaKeys {
        //区域基础信息
        public static final String AREA = "area:";
        //区域基础信息详情hget hset存储获取的方式：AREA_INFO，area_code
        public static final String AREA_INFO = AREA + "info";
        //国家信息详情存储方式：AREA_COUNTRY，area_code(自己的code)
        public static final String AREA_COUNTRY = AREA + "country";
        //所有省份直辖市自治区集合存储方式：AREA_COUNTRY，area_code
        public static final String AREA_PROVINCE = AREA + "province";
        //是有城市一级的城市集合存储方式：AREA_CITY，area_code（省份直辖市自治区code）
        public static final String AREA_CITY = AREA + "city";
        //县区集合y一级集合存储方式：AREA_AREA，area_code(城市一级code)
        public static final String AREA_AREA = AREA + "area";
        //省市县区所有的返回数据3600多条数据存储
        public static final String AREA_ALL = AREA + "all";

        //用户收货地址的keys
        public static final String ADDRESS = "address";
        //用户收获地址详情hget hset存储获取的方式：ADDRESS_INFO，addressInfo,addressId
        public static final String ADDRESS_INFO = ADDRESS + "info";
        //用户收获地址列表hget hset存储获取的方式：ADDRESS_LIST，addressList,userId
        public static final String ADDRESS_LIST = ADDRESS + "list";

    }

    /**
     * 功能描述: 系统管理模块相关redis key
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/10 13:48
     * @Return:
     */
    public class SystemModuleKyes {
        // 管理员账号详情信息的keys
        public static final String ADMIN_INFO = "user:";
        //超级管理员表
        public static final String ALL_ADMIN = "admin";
        //
        private static final String MODULE_BASE = "sys:";
        //管理员token前缀, 后边接token值组成redis的key, value为SysUserTokenEntity的json字符串
        public static final String TOKEN_PREFIX = MODULE_BASE + "token:";
        //         * 管理员权限列表前缀, 后边接user_id组成redis的key, value为用户拥有的权限字符串列表
        public static final String PERMS_PREFIX = MODULE_BASE + "perms:";
        //管理员HASH表的key
        public static final String USERS_HASH_TABLE = MODULE_BASE + "users_hash_table";
        //用户ID<->菜单列表
        public static final String USER_MENUS_HASH_TABLE = MODULE_BASE + "user_menus_hash_table";
        //国家列表 * value = 所有国家列表的list集合
        public static final String SYS_COUNTRY = MODULE_BASE + "country_list";

        //日志的Bug
        private static final String LOG = "log:";
        //这是解决日志分页问题的keys
        public static final String SYS_LOG_PAGE = LOG + "log";

        //物业管理后台的消息键值集合
        private static final String MESSAGE = "message:";
        //物业管理后台的消息详细集合hset，hget存储：MESSAGE_INFO，info,id，过期时间
        public static final String MESSAGE_INFO = MESSAGE + "info";


    }

    /**
     * 功能描述: 社区这边便民电话
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/14 9:31
     * @Return:
     */
    public class MobileKeys {
        private static final String MOBILE = "mobile:";
        //社区周边便民电话的详情，hget，hset存储方式：MOBILE_INFO，info,mobileId
        public static final String MOBILE_INFO = MOBILE + "info";
        //社区周边便民电话的集合，string，get，set方式
        public static final String MOBILE_LIST = MOBILE + "list";
    }

    /**
     * 功能描述: 快云图片上传
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/14 9:31
     * @Return:
     */
    public class Img {
        private static final String IMG = "img:";
        //图片上传token
        public static final String IMG_TOKEN = IMG + "token";
    }

    /**
     * 功能描述: Message类型信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/14 9:31
     * @Return:
     */
    public class MessageKeys {
        private static final String MESSAGE = "message:";
        //Message类型信息
        public static final String MESSAGE_TYPE = MESSAGE + "type";
        //用户未读消息
        public static final String MESSAGE_NUM = MESSAGE + "num";
        //消息详情
        public static final String MESSAGE_INFO = MESSAGE + "info";
        //用户是否已读USER_LISTistUSER
        public static final String MESSAGE_READ = MESSAGE + "read";
        //用户消息集合key
        public static final String MESSAGE_USER = MESSAGE + "user";
        //系统消息集合key
        public static final String MESSAGE_LIST = MESSAGE + "list";
    }

    /**
     * 功能描述: 支付redis
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/14 9:31
     * @Return:
     */
    public class PayKeys {
        private static final String PAY = "pay:";
        //支付信息hset，hget存储方式：
        public static final String PAY_STATUS = PAY + "status";
        //支付方式信息hset，hget存储方式：PAY_TYPE,info,id(数据字典的ID主键)
        public static final String PAY_TYPE = PAY + "type";
        //支付方式信息集合String的存储方式：PAY_ALL,list
        public static final String PAY_ALL = PAY + "all";
    }

    /**
     * @Description 社区朋友圈点赞
     * @Author LiuDan
     * @Date 2019/6/22 14:17
     * @Param
     * @Return
     * @Exception
     */

    public class Friends {
        //朋友圈rediskey的集合
        private static final String FRIEND = "friend:";

        //社区朋友圈点赞
        public static final String LIKE = "like";
        //社区朋友圈详情
        public static final String DETAIL = "info";

        //朋友圈信息图片keys,hset,hget存储方式：FRIEND_IMG，infoList,friendId;
        public static final String FRIEND_IMG = FRIEND + "img";

        //朋友圈评论详情的rediskey
        public static final String FRIEND_COMMENT = FRIEND + "comment";

    }

    /**
     * @Description 基础配置 redisKeys
     * @Author LiuDan
     * @Date 2019/6/24 15:21
     * @Param
     * @Return
     * @Exception
     */
    public class sysConfig {
        //配置文件 app基础配置的keys
        public static final String SYS = "sys:";
        //系统配置信息hset，hget存储方式：SYS_CONFIG，key
        public static final String SYS_CONFIG = SYS + "config";
    }

    /**
     * @Description 社区活动 redisKeys
     * @Author LiuDan
     * @Date 2019/6/24 15:21
     * @Param
     * @Return
     * @Exception
     */
    public class move {
        public static final String MOVE = "move:";
        //活动详情
        public static final String DETAIL = MOVE + "info";

    }

    /**
     * @Description 电子放行单
     * @Author LiuDan
     * @Date 2019/7/8 16:12
     * @Param
     * @Return
     * @Exception
     */
    public class CommunityReleaseSlip {

        public static final String SLIP = "slip:";
        //电子放行单活动详情hget，hset存储方式：SLIP_DETAIL，info,ID
        public static final String SLIP_DETAIL = SLIP + "info";
        //访客通行码
        public final static String MOBILE = SLIP + "code_";

    }
}
