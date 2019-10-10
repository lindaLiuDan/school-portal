package com.info.redis;

import com.alibaba.fastjson.JSON;
import com.info.exception.LiftException;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis工具类
 *
 * @author Gaosx
 */
@Component
public class RedisUtils {


    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private ValueOperations<String, String> valueOperations;
    @Resource
    private HashOperations<String, String, Object> hashOperations;
    @Resource
    private ListOperations<String, Object> listOperations;
    @Resource
    private SetOperations<String, Object> setOperations;
    @Resource
    private ZSetOperations<String, Object> zSetOperations;

    /**
     * 默认过期时长，单位：秒 99天有效期
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24 * 99;

    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;

    /**
     * 功能描述: string中的Set方法  自定义设置有效期
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/9 12:38
     * @Return:
     */
    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 功能描述: string中的Set方法  默认设置有效期
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/9 12:38
     * @Return:
     */
    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 功能描述: string中的get方法 然后对应的转成对应的实体对象的方法
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/9 12:40
     * @Return:
     */
    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    /**
     * 功能描述: string中的get方法 然后对应的转成对应的实体对象的方法
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/9 12:40
     * @Return:
     */
    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    /**
     * 功能描述: string中的get方法 然后对应的转成对应的实体对象的方法
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/9 12:40
     * @Return:
     */
    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    /**
     * 功能描述: string中的get方法
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/9 12:40
     * @Return:
     */
    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    /**
     * 功能描述: string中的delete方法
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/9 12:40
     * @Return:
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 获取所有的key
     *
     * @param key
     * @return
     */
    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

    /**
     * 批量删除key
     *
     * @param keys
     */
    public void delete(Set<String> keys) {
        if (keys != null && keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 功能描述: 
     *
     * @Params:  * @param null 
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/16 19:35
     * @Return:
     */

    /**
     * @param key
     * @param value
     * @description 简单的往数组里面添加元素
     */
    public void listSet(String key, String value, long expire) {
        listOperations.leftPush(key, value);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 无过期时间设置集合-高山溪
     *
     * @param key
     * @param value
     * @description 简单的往数组里面添加元素
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/16 18:57
     * @Return:
     */
    public void listSet(String key, String value) {
        listOperations.leftPush(key, value);
    }

    /**
     * 无过期时间设置集合-高山溪
     *
     * @param key
     * @param value
     * @description 简单的往数组里面添加元素
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/16 18:57
     * @Return:
     */
    public void rightPush(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * @param key    list键
     * @param offset 索引位置
     * @description 获取指定下标的数组元素
     */
    public String listGet(String key, int offset) {
        if (listOperations.index(key, offset) == null) {//如果没有返回NULL
            return "";
        }
        String index = listOperations.index(key, offset).toString();
        return index;
    }

    /**
     * @param key   list键
     * @param start 开始索引
     * @param end   结束索引
     * @description 查询区间范围内的元素
     */
    public List<Object> lrange(String key, Long start, Long end) {
        List<Object> range = listOperations.range(key, start, end);
        return range;
    }

    /**
     * @param key   list键
     * @param start 开始索引
     * @param end   结束索引
     * @description 查询区间范围内的元素  新增转换方法---Gaosx
     */
    public List<Object> lrange(String key, Long start, Long end, Class<T> clazz) {
        List<Object> range = listOperations.range(key, start, end);
        return range;
    }

    /**
     * 获取list集合长度
     *
     * @param key list键
     * @return
     */
    public Long llen(String key) {
        Long size = listOperations.size(key);
        if (size <= 0) {
            return 0L;
        }
        return size;
    }

    /**
     * 移除List中的元素
     *
     * @param key   被操作的List
     * @param count 要删除的元素个数【为负数表示从尾部，向头部删除;正数则相反】
     * @param value 要删除的元素值
     * @return 移除完毕之后，当前List的长度
     */
    public void remove(String key, long count, String value) {
        int backValue = listOperations.remove(key, count, value).intValue();
        logger.info(key + " 数组长度为 : " + backValue);
    }

    /**
     * 功能描述: list集合从左到右，删除第一个元素
     *
     * @auther: Gaosx  By User
     * @param:
     * @date: 2019/3/28 15:50
     */
    public void listPop(String key) {
        Object s = listOperations.leftPop(key);
        logger.info(key + " 删除为 : " + s);
    }

    /**
     * 在redis中做原子加1
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @return 原子加之后的结果
     */
    public int atomAddOne(final String tabKey, final Integer rowId) {
        return atomAdd(tabKey, rowId, 1);
    }

    /**
     * 在redis中做原子减1
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @return 原子减之后的结果
     */
    public int atomMinusOne(final String tabKey, final Integer rowId) {
        return atomMinus(tabKey, rowId, 1);
    }

    /**
     * 在redis中做原子加操作
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @return 原子加之后的结果
     */
    public int atomAdd(final String tabKey, final Integer rowId, final Integer value) {
        Objects.requireNonNull(tabKey, "hash table redis key cannot be null");
        Objects.requireNonNull(rowId, "row id in hash table cannot be null");
        Objects.requireNonNull(value, "value cannot be null");

        if (!redisTemplate.hasKey(tabKey)) {
            redisTemplate.opsForHash().put(tabKey, rowId.toString(), "0");
        }

        int add = 1;
        if (value > 0) {
            add = value;
        }

        final Long result = redisTemplate.opsForHash().increment(tabKey, rowId.toString(), add);

        return result.intValue();
    }

    /**
     * 在redis中做原子加操作
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @return 原子加之后的结果
     */

    public int atomMinus(final String tabKey, final Integer rowId, final Integer value) {
        Objects.requireNonNull(tabKey, "hash table redis key cannot be null");
        Objects.requireNonNull(rowId, "row id in hash table cannot be null");
        Objects.requireNonNull(value, "value cannot be null");

        if (!redisTemplate.hasKey(tabKey)) {
            redisTemplate.opsForHash().put(tabKey, rowId.toString(), "0");
            return 0;
        }

        final Object strValue = redisTemplate.opsForHash().get(tabKey, rowId.toString());

        if (Integer.valueOf(strValue.toString()) == 0) {
            return 0;
        }

        int minus = -1;

        if (value > 0) {
            minus = -value;
        }

        final Long result = redisTemplate.opsForHash().increment(tabKey, rowId.toString(), minus);

        return result.intValue();
    }

    /**
     * 在redis中做原子加或者减操作
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @return 原子加之后的结果
     */
    public int atomAddOrMinus(final String tabKey, final Integer rowId, final Integer value) {

        // 如果是减操作, 保证数据不为负数
        if (value == -1) {
            final Object strValue = redisTemplate.opsForHash().get(tabKey, rowId.toString());

            if (Integer.valueOf(strValue.toString()) == 0) {
                return 0;
            }
        }

        final Long result = redisTemplate.opsForHash().increment(tabKey, rowId.toString(), value);

        return result.intValue();
    }

    public int atomAddOrMinus(final String tabKey, final String rowId, final Integer value) {

        // 如果是减操作, 保证数据不为负数
        if (value == -1) {
            final Object strValue = redisTemplate.opsForHash().get(tabKey, rowId);

            if (Integer.valueOf(strValue.toString()) == 0) {
                return 0;
            }
        }
        final Long result = redisTemplate.opsForHash().increment(tabKey, rowId, value);

        return result.intValue();
    }

    /**
     * @param tabKey
     * @param rowId
     * @param reduce 减少的数量
     * @return
     */
    public int reduce(String tabKey, String rowId, Integer reduce) {
        Objects.requireNonNull(tabKey, "hash table redis key cannot be null");
        Objects.requireNonNull(rowId, "row id in hash table cannot be null");
        Objects.requireNonNull(reduce, "row id in hash reduce cannot be null");
        final Long currVal = redisTemplate.opsForHash().increment(tabKey, rowId, 0);
        if (currVal == 0) {
            return 0;
        }
        Long result = redisTemplate.opsForHash().increment(tabKey, rowId, -reduce);
        return result.intValue();
    }

    /**
     * @param tabKey
     * @param rowId
     * @param increment 自增数量
     * @return
     */
    public int increment(String tabKey, String rowId, Integer increment) {
        Objects.requireNonNull(tabKey, "hash table redis key cannot be null");
        Objects.requireNonNull(rowId, "row id in hash table cannot be null");
        Objects.requireNonNull(increment, "increment id in hash table cannot be null");
        final Long result = redisTemplate.opsForHash().increment(tabKey, rowId, increment);
        return result.intValue();
    }

    /**
     * @param key
     * @param increment
     * @return
     */
    public int increment(String key, Integer increment) {
        Objects.requireNonNull(key, " key cannot be null");
        Objects.requireNonNull(increment, " increment cannot be null");
        final Long result = valueOperations.increment(key, increment);
        return result.intValue();
    }

    /**
     * hash的缓存操作
     *
     * @param tabKey key
     * @param rowId  field
     * @param value  值的大小 可能是钻石 魅力值 关注数 等等
     */
    public void hset(String tabKey, String rowId, String value) {
        Objects.requireNonNull(tabKey, "hash table redis key cannot be null");
        Objects.requireNonNull(rowId, "row id in hash table cannot be null");
        Objects.requireNonNull(value, "value cannot be null");
        redisTemplate.opsForHash().put(tabKey, rowId, value);
    }

    /**
     * hash的缓存操作
     *
     * @param tabKey key
     * @param rowId  field
     * @param value  值的大小 可能是钻石 魅力值 关注数 等等
     */
    public void hset(String tabKey, String rowId, String value, Integer expire) {
        Objects.requireNonNull(tabKey, "hash table redis key cannot be null");
        Objects.requireNonNull(rowId, "row id in hash table cannot be null");
        Objects.requireNonNull(value, "value cannot be null");
        redisTemplate.opsForHash().put(tabKey, rowId, value);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(tabKey, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * @author: YS
     * @Date:2018/5/9 10:53
     * @param:key
     * @explain： 判断 是否存进去了 不能使用redisTemplate.hasKey(tabKey)方法
     * 因为table也就是KEY 所有用户的关注数 粉丝数的KEY都是一样的
     * @return:
     */
    public String hget(String key, String field) {
        Object object = redisTemplate.opsForHash().get(key, field);
        if (object == null) {
            return null;
        }
        return object.toString();
    }

    /**
     * 功能描述: hget返回一个实体信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/13 18:35
     * @Return:
     */
    public <T> T hget(String key, String field, Class<T> clazz) {
        Object object = redisTemplate.opsForHash().get(key, field);
        if (object == null) {
            return null;
        }
        return object.toString() == null ? null : fromJson(object.toString(), clazz);
    }


    /**
     * 判断变量中是否有指定的map键
     *
     * @return true 存在 false不存在
     * @param: key 键 field 参数
     */
    public Boolean hasKey(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 判断key是否存在
     *
     * @return true 存在 false不存在
     * @param: key 键
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.info("判断key是否存在,key={},ERROR={}", key, e);
            return false;
        }
    }

    /**
     * 递增
     *
     * @return
     * @param： key 键
     * @param： by  要增加几(大于0)
     */
    public long incr(String key, Integer delta) {
        if (delta < 0) {
            throw new LiftException("递增因子数字必须大于0");
        }
        return valueOperations.increment(key, delta);
    }

    /**
     * 递减
     *
     * @return
     * @param： key 键
     * @param： by  要减少几(小于0)
     */
    public long decr(String key, Integer delta) {
        if (delta < 0) {
            throw new LiftException("递增因子数字必须大于0");
        }
        return valueOperations.increment(key, -delta);
    }


    /**
     * 删除hash中指定key的字段field的值
     *
     * @return
     * @param： key   键
     * @param： field 字段
     */
    public Long hdel(String key, String... field) {
        return redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * set集合操作, 添加操作
     *
     * @return
     * @param： key   键
     * @param： value 值
     */
    public Long sadd(String key, String... value) {
        return setOperations.add(key, value);
    }

    /**
     * 查询set集合中的所有数据
     *
     * @return
     * @param： keys 键
     */
    public Set<Object> smembers(String keys) {
        return setOperations.members(keys);
    }

    /**
     * 判断set集合中是否包含指定元素
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Boolean sismember(String key, String value) {
        return setOperations.isMember(key, value);
    }

    /**
     * 删除set集合中的一个或者多个元素
     *
     * @param key    键
     * @param values 值
     * @return
     */
    public Long srem(String key, String... values) {
        return setOperations.remove(key, values);
    }

    /**
     * 返回KEY的失效时间
     *
     * @param key
     * @return
     */
    public long getExpireTimeType(String key) {
        long time = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return time;
    }


    /**
     * 如果key存在, 则返回false, 如果不存在, 则将key=value放入redis中, 并返回true
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * Zset添加操作
     *
     * @param key   键
     * @param value 值
     * @param score 排序值(如：数据库ID主键，时间，UUID等)
     * @return
     */
    public Boolean zadd(String key, String value, double score) {
        return zSetOperations.add(key, value, score);
    }

    /**
     * Zset操作, 按照索引从小到大的顺序返回指定索引start到stop之间的元素,参数WITHSCORES指定显示分数
     *
     * @param key   键
     * @param start 查询起始值
     * @param end   查询结束值
     * @return
     */
    public Set<Object> zrange(String key, long start, long end) {
        return zSetOperations.range(key, start, end);
    }

    /**
     * Zset操作, 按照索引从大到小的顺序返回指定索引start到stop之间的元素,参数WITHSCORES指定显示分数
     *
     * @param key   键
     * @param start 查询起始值
     * @param end   查询结束值
     * @return
     */
    public Set<Object> zrevrange(String key, long start, long end) {
        return zSetOperations.reverseRange(key, start, end);
    }

    /**
     * Zset获得集合中元素的数量
     *
     * @param key 键
     * @return
     */
    public Long zsize(String key) {
        return zSetOperations.size(key);
    }

    /**
     * Zset获得集合中元素的数量
     *
     * @param key 键
     * @return
     */
    public Long zcard(String key) {
        return zSetOperations.zCard(key);
    }

    /**
     * Zset操作, 删除一个或者多个元素，返回删除元素的个数
     *
     * @param key   键
     * @param value 要删除的元素的值
     * @return
     */
    public Long zrem(String key, String... value) {
        return zSetOperations.remove(key, value);
    }

    /**
     * Zset操作, 删除指定区间的值
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public Long zremrangebyLex(String key, Integer min, Integer max) {
        return zSetOperations.removeRangeByScore(key, min, max);
    }

    /**
     * zset操作, 根据元素值返回元素对应的索引位置
     *
     * @param: key   键
     * @param: value 值
     * @return: 索引下标
     */
    public Long zrank(String key, String value) {
        return zSetOperations.rank(key, value);
    }

    /**
     * zset操作, 返回有序集合中分值介于min和max之间的所有成员，包括min和max在内，并按照分值从小到大的排序来返回他们
     *
     * @param: key 键
     * @param: min 最小值
     * @param: max 最大值
     * @return:
     */
    public Set<Object> zrangebyscore(String key, double min, double max) {
        return zSetOperations.rangeByScore(key, min, max);
    }

    /**
     * 功能描述:
     *
     * @auther: Gaosx  By User
     * @param:
     * @date: 2018/12/30 18:45
     */
    public void setExpire(String key, Long expire) {
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

/**************************************************************************************************************************/


}
