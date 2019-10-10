package com.info.manager;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Set;

/**
 * 功能描述: 基本的redis CRUD操作接口
 *
 * @Params: * @param null
 * @Author:960889426@qq.com By User
 * @Date: 2019/6/14 18:42
 * @Return:
 */
public interface ICrudRedisManager<E> {


    /**
     * 功能描述: hash的缓存操作
     *
     * @param tabKey key
     * @param rowId  field
     * @param value  值的大小 可能是钻石 魅力值 关注数 等等
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:29
     * @Return:
     */
    void hset(String tabKey, String rowId, String value, String message);

    /**
     * 功能描述: hash的缓存操作----带过期时间的方法
     *
     * @param tabKey key
     * @param rowId  field
     * @param value  值的大小 可能是钻石 魅力值 关注数 等等
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:29
     * @Return:
     */
    void hset(String tabKey, String rowId, String value, String message,Integer expire);

    /**
     * 功能描述: 判断 是否存进去了 不能使用redisTemplate.hasKey(tabKey)方法
     * 因为table也就是KEY 所有用户的关注数 粉丝数的KEY都是一样的
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:31
     * @Return:
     */
    String hget(String key, String field, String message);

    /**
     * 功能描述: hget返回一个实体信息--和上面的方法对应的
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:33
     * @Return:
     */
    <T> T hget(String key, String field, Class<T> clazz, String message);

    /**
     * 功能描述: 递增-原子性递增
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:35
     * @Return:
     */
    long incr(String key, Integer delta, String message);

    /**
     * 功能描述: 递减做原子性递减上面成对的
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:37
     * @Return:
     */
    long decr(String key, Integer delta, String message);

    /**
     * 功能描述: 删除hash中指定key的字段field的值
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:38
     * @Return:
     */
    Long hdel(String key, String message, String... field);

    /**
     * 功能描述: set集合操作, 添加操作
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:39
     * @Return:
     */
    Long sadd(String key, String message, String... value);

    /**
     * 功能描述: 查询set集合中的所有数据
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:40
     * @Return:
     */
    Set<Object> smembers(String keys, String message);

    /**
     * 功能描述: 判断set集合中是否包含指定元素
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:42
     * @Return:
     */
    Boolean sismember(String key, String value, String message);

    /**
     * 功能描述: 删除set集合中的一个或者多个元素
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:43
     * @Return:
     */
    Long srem(String key, String message, String... values);

    /**
     * 功能描述: Zset添加操作
     *
     * @param: key   键
     * @param: start 查询起始值
     * @param: end   查询结束值
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:45
     * @Return:
     */
    Boolean zadd(String key, String value, double score, String message);

    /**
     * 功能描述:  Zset操作, 按照索引从大到小的顺序返回指定索引start到stop之间的元素,参数WITHSCORES指定显示分数
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:47
     * @Return:
     */
    Set<Object> zrevrange(String key, long start, long end, String message);

    /**
     * 功能描述: Zset获得集合中元素的数量
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:48
     * @Return:
     */
    Long zcard(String key, String message);

    /**
     * 功能描述: Zset操作, 删除一个或者多个元素，返回删除元素的个数
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:50
     * @Return:
     */
    Long zrem(String key, String message, String... value);

    /**
     * 功能描述: Zset操作, 删除指定区间的值
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:52
     * @Return:
     */
    Long zremrangebyLex(String key, Integer min, Integer max, String message);

    /**
     * 功能描述: zset操作, 根据元素值返回元素对应的索引位置
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:53
     * @Return:
     */
    Long zrank(String key, String value, String message);

    /**
     * 功能描述: zset操作, 返回有序集合中分值介于min和max之间的所有成员，包括min和max在内，并按照分值从小到大的排序来返回他们
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:54
     * @Return:
     */
    Set<Object> zrangebyscore(String key, double min, double max, String message);


    /**
     * 功能描述: string中的Set方法  自定义设置有效期
     *
     * @Params: * @param null
     * @Author:960889426@qq.com By User
     * @Date: 2019/6/15 10:27
     * @Return:
     */
    void set(String key, Object object, long expire, String message);

    /**
     * 功能描述: string中的Set方法  默认设置有效期
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 10:36
     * @Return:
     */
    void set(String key, Object object, String message);

    /**
     * 功能描述: string中的get方法 然后对应的转成对应的实体对象的方法
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 10:39
     * @Return:
     */
    <T> T get(String key, Class<T> clazz, long expire, String message);

    /**
     * 功能描述: string中的get方法 然后对应的转成对应的实体对象的方法
     *
     * @Params: * @param null
     * @Author:960889426@qq.com By User
     * @Date: 2019/6/15 10:39
     * @Return:
     */
    <T> T get(String key, Class<T> clazz, String message);

    /**
     * 功能描述: string中的get方法 然后对应返回一个List集合的方法
     *
     * @Params: * @param null
     * @Author:960889426@qq.com By User
     * @Date: 2019/6/15 10:39
     * @Return:
     */
//    List<E> getList(String key, E clazz, String message);

    /**
     * 功能描述: string中的get方法
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 10:47
     * @Return:
     */
    String get(String key, String message);

    /**
     * 功能描述: string中的delete方法
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 10:48
     * @Return:
     */
    void delete(String key, String message);

    /**
     * 功能描述: 批量删除key
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 10:50
     * @Return:
     */
    void delete(Set<String> keys, String message);

    /**
     * 功能描述: 简单的往数组里面添加元素
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 10:53
     * @Return:
     */
    void listSet(String key, String value, long expire, String message);

    /**
     * 功能描述: 简单的往数组里面添加元素
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 10:53
     * @Return:
     */
    void listSet(String key, List<E> E, long expire, String message);

    /**
     * 功能描述: 获取指定下标的数组元素  @param key    list键 @param offset 索引位置
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 10:55
     * @Return:
     */
    String listGet(String key, int offset, String message);

    /**
     * 功能描述: 获取list集合长度
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/16 18:02
     * @Return:
     */
    Long llen(String key, String message);

    /**
     * @param key   list键
     * @param start 开始索引
     * @param end   结束索引
     * @description 查询区间范围内的元素
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/16 18:08
     * @Return:
     */
    List<Object> lrange(String key, Long start, Long end, Class<T> clazz, String message);

    /**
     * 功能描述:移除List中的元素
     * *
     *
     * @param key   被操作的List
     * @param count 要删除的元素个数【为负数表示从尾部，向头部删除;正数则相反】
     * @param value 要删除的元素值
     * @return 移除完毕之后，当前List的长度
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 10:57
     * @Return:
     */
    void remove(String key, long count, String value, String message);

    /**
     * 功能描述: 在redis中做原子加1
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @return 原子加之后的结果
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:18
     */
    int atomAddOne(final String tabKey, final Integer rowId, String message);

    /**
     * 功能描述: 在redis中做原子减1
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @return 原子减之后的结果
     */
    int atomMinusOne(final String tabKey, final Integer rowId, String message);

    /**
     * 在redis中做原子加操作
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @return 原子加之后的结果
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:23
     */
    int atomAdd(final String tabKey, final Integer rowId, final Integer value, String message);

    /**
     * 功能描述: 在redis中做原子加操作
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @return 原子加之后的结果
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:25
     */
    int atomMinus(final String tabKey, final Integer rowId, final Integer value, String message);

    /**
     * 功能描述: 在redis中做原子加或者减操作
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @return 原子加之后的结果
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 11:27
     */
    int atomAddOrMinus(final String tabKey, final Integer rowId, final Integer value, String message);


    /**
     * 通过ID, 将实体类从redis中删除
     *
     * @param id
     */
    void removeEntityById(Integer id);

    /**
     * 删除所有缓存的实体类
     */
    void removeAllEntity(String key, String message);

    /**
     * 更新redis中的实体类
     *
     * @param entity
     */
    void updateEntity(E entity);

    /**
     * 通过ID获取缓存的实体类
     *
     * @param id
     * @return
     */
    E getEntityById(Integer id);

    /**
     * 获取缓存中所有的实体类
     *
     * @return
     */
    List<E> getAllEntity(String key, String message);

    /**
     * 获取缓存中所有zifuchuan
     *
     * @return
     */
    String getAllString(String key, String message);


}
