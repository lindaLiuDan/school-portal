package com.info.manager;

import com.alibaba.fastjson.JSON;
import com.info.redis.RedisUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 功能描述: 基本的redis CRUD操作实现层
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/6/15 18:42
 * @Return:
 */
@Component
public class CrudRedisManager<E> implements ICrudRedisManager<E> {


    /**
     * 功能描述: 日志方法调用
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:33
     * @Return:
     */
    private static final Logger logger = LoggerFactory.getLogger(CrudRedisManager.class);


    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:36
     * @Return:
     */
    @Autowired
    private RedisUtils redisUtils;


    /**
     * 功能描述: hash的缓存操作
     *
     * @param tabKey key
     * @param rowId  field
     * @param value  值的大小 可能是钻石 魅力值 关注数 等等
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:29
     * @Return:
     */
    @Override
    public void hset(String tabKey, String rowId, String value, String message) {
        try {
            redisUtils.hset(tabKey, rowId, value);
        } catch (Exception e) {
            logger.error(message, e);
        }
    }

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
    @Override
    public void hset(String tabKey, String rowId, String value, String message, Integer expire) {
        try {
            redisUtils.hset(tabKey, rowId, value,expire);
        } catch (Exception e) {
            logger.error(message, e);
        }

    }

    /**
     * 功能描述: 判断 是否存进去了 不能使用redisTemplate.hasKey(tabKey)方法
     * 因为table也就是KEY 所有用户的关注数 粉丝数的KEY都是一样的
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:31
     * @Return:
     */
    @Override
    public String hget(String key, String field, String message) {
        try {
            return redisUtils.hget(key, field);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: hget返回一个实体信息--和上面的方法对应的
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:33
     * @Return:
     */
    @Override
    public <T> T hget(String key, String field, Class<T> clazz, String message) {
        try {
            return redisUtils.hget(key, field, clazz);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: 递增-原子性递增
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:35
     * @Return:
     */
    @Override
    public long incr(String key, Integer delta, String message) {
        try {
            return redisUtils.incr(key, delta);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return 0;
    }

    /**
     * 功能描述: 递减做原子性递减上面成对的
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:37
     * @Return:
     */
    @Override
    public long decr(String key, Integer delta, String message) {
        try {
            return redisUtils.decr(key, delta);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return 0;
    }

    /**
     * 功能描述: 删除hash中指定key的字段field的值
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:38
     * @Return:
     */
    @Override
    public Long hdel(String key, String message, String... field) {
        try {
            return redisUtils.hdel(key, field);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: set集合操作, 添加操作
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:39
     * @Return:
     */
    @Override
    public Long sadd(String key, String message, String... value) {
        try {
            return redisUtils.sadd(key, value);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: 查询set集合中的所有数据
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:40
     * @Return:
     */
    @Override
    public Set<Object> smembers(String keys, String message) {
        try {
            return redisUtils.smembers(keys);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: 判断set集合中是否包含指定元素
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:42
     * @Return:
     */
    @Override
    public Boolean sismember(String key, String value, String message) {
        try {
            return redisUtils.sismember(key, value);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return false;
    }

    /**
     * 功能描述: 删除set集合中的一个或者多个元素
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:43
     * @Return:
     */
    @Override
    public Long srem(String key, String message, String... value) {
        try {
            return redisUtils.srem(key, value);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: Zset添加操作
     *
     * @param: key   键
     * @param: start 查询起始值
     * @param: end   查询结束值
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:45
     * @Return:
     */
    @Override
    public Boolean zadd(String key, String value, double score, String message) {
        try {
            return redisUtils.zadd(key, value, score);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return false;
    }

    /**
     * 功能描述:  Zset操作, 按照索引从大到小的顺序返回指定索引start到stop之间的元素,参数WITHSCORES指定显示分数
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:47
     * @Return:
     */
    @Override
    public Set<Object> zrevrange(String key, long start, long end, String message) {
        try {
            return redisUtils.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: Zset获得集合中元素的数量
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:48
     * @Return:
     */
    @Override
    public Long zcard(String key, String message) {
        try {
            return redisUtils.zcard(key);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: Zset操作, 删除一个或者多个元素，返回删除元素的个数
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:50
     * @Return:
     */
    @Override
    public Long zrem(String key, String message, String... value) {
        try {
            return redisUtils.zrem(key, value);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: Zset操作, 删除指定区间的值
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:52
     * @Return:
     */
    @Override
    public Long zremrangebyLex(String key, Integer min, Integer max, String message) {
        try {
            return redisUtils.zremrangebyLex(key, min, max);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: zset操作, 根据元素值返回元素对应的索引位置
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:53
     * @Return:
     */
    @Override
    public Long zrank(String key, String value, String message) {
        try {
            return redisUtils.zrank(key, value);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: zset操作, 返回有序集合中分值介于min和max之间的所有成员，包括min和max在内，并按照分值从小到大的排序来返回他们
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:54
     * @Return:
     */
    @Override
    public Set<Object> zrangebyscore(String key, double min, double max, String message) {
        try {
            return redisUtils.zrangebyscore(key, min, max);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: 将实体缓存值redis中 自定义有效期
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 20:01
     * @Return:
     */
    @Override
    public void set(String key, Object object, long expire, String message) {
        try {
            redisUtils.set(key, object, expire);
        } catch (Exception e) {
            logger.error(message, e);
        }
    }

    /**
     * 功能描述: string中的Set方法  默认设置有效期
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:36
     * @Return:
     */
    @Override
    public void set(String key, Object object, String message) {
        try {
            redisUtils.set(key, object);
        } catch (Exception e) {
            logger.error(message, e);
        }
    }

    /**
     * 功能描述: string中的get方法 然后对应的转成对应的实体对象的方法
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:39
     * @Return:
     */
    @Override
    public <T> T get(String key, Class<T> clazz, long expire, String message) {
        try {
            return redisUtils.get(key, clazz, expire);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: string中的get方法
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:39
     * @Return:
     */
    @Override
    public <T> T get(String key, Class<T> clazz, String message) {
        try {
            return redisUtils.get(key, clazz);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: string中的get方法
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:47
     * @Return:
     */
    @Override
    public String get(String key, String message) {
        try {
            return redisUtils.get(key);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: string中的delete方法
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:48
     * @Return:
     */
    @Override
    public void delete(String key, String message) {
        try {
            redisUtils.delete(key);
        } catch (Exception e) {
            logger.error(message, e);
        }
    }

    /**
     * 功能描述: 批量删除key
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:50
     * @Return:
     */
    @Override
    public void delete(Set<String> keys, String message) {
        try {
            redisUtils.delete(keys);
        } catch (Exception e) {
            logger.error(message, e);
        }
    }

    /**
     * 功能描述: 简单的往数组里面添加元素
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:53
     * @Return:
     */
    @Override
    public void listSet(String key, String value, long expire, String message) {
        try {
            redisUtils.listSet(key, value, expire);
        } catch (Exception e) {
            logger.error(message, e);
        }
    }

    /**
     * 功能描述: 简单的往数组里面添加元素
     *
     * @Params: * @param null
     * @Author: 960889426@qq.com By User
     * @Date: 2019/6/15 10:53
     * @Return:
     */
    @Override
    public void listSet(String key, List<E> E, long expire, String message) {
        try {
            E.forEach(info -> {
                redisUtils.listSet(key, JSON.toJSONString(info), expire);
            });
        } catch (Exception e) {
            logger.error(message, e);
        }
    }

    /**
     * 功能描述: 获取指定下标的数组元素  @param key    list键 @param offset 索引位置
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:55
     * @Return:
     */
    @Override
    public String listGet(String key, int offset, String message) {
        try {
            return redisUtils.listGet(key, offset);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: 获取list集合长度
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/16 18:02
     * @Return:
     */
    @Override
    public Long llen(String key, String message) {
        Long size = 0L;
        try {
            size = redisUtils.llen(key);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return size;
    }

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
    @Override
    public List<Object> lrange(String key, Long start, Long end, Class<T> clazz, String message) {
        try {
            return redisUtils.lrange(key, start, end);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述:移除List中的元素
     *
     * @param key   被操作的List
     * @param count 要删除的元素个数【为负数表示从尾部，向头部删除;正数则相反】
     * @param value 要删除的元素值
     * @return 移除完毕之后，当前List的长度
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:57
     */
    @Override
    public void remove(String key, long count, String value, String message) {
        try {
            redisUtils.remove(key, count, value);
        } catch (Exception e) {
            logger.error(message, e);
        }
    }

    /**
     * 功能描述: 在redis中做原子加1
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:18
     * @Return:
     */
    @Override
    public int atomAddOne(String tabKey, Integer rowId, String message) {
        try {
            return redisUtils.atomAddOne(tabKey, rowId);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return 0;
    }

    /**
     * 功能描述: 在redis中做原子减1
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @return 原子减之后的结果
     */
    @Override
    public int atomMinusOne(String tabKey, Integer rowId, String message) {
        try {
            return redisUtils.atomMinusOne(tabKey, rowId);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return 0;
    }

    /**
     * 在redis中做原子加操作
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @return 原子加之后的结果
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:23
     */
    @Override
    public int atomAdd(String tabKey, Integer rowId, Integer value, String message) {
        try {
            return redisUtils.atomAdd(tabKey, rowId, value);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return 0;
    }

    /**
     * 功能描述: 在redis中做原子加操作
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @return 原子加之后的结果
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:25
     */
    @Override
    public int atomMinus(String tabKey, Integer rowId, Integer value, String message) {
        try {
            return redisUtils.atomMinus(tabKey, rowId, value);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return 0;
    }

    /**
     * 功能描述: 在redis中做原子加或者减操作
     *
     * @param tabKey hash table在redis中的key
     * @param rowId  要修改的行ID
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @param value  要增加的数量, 如果为null, 则默认为1
     * @return 原子加之后的结果
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 11:27
     */
    @Override
    public int atomAddOrMinus(String tabKey, Integer rowId, Integer value, String message) {
        try {
            return redisUtils.atomAddOrMinus(tabKey, rowId, value);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return 0;
    }


    /**
     * 功能描述: 将实体值redis中删除掉
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 20:01
     * @Return:
     */
    @Override
    public void removeEntityById(Integer id) {

    }

    /**
     * 功能描述: 将缓存值更更新擦做
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 20:01
     * @Return:
     */
    @Override
    public void updateEntity(E entity) {

    }

    /**
     * 功能描述: 根据用户主键获取缓存值
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 20:01
     * @Return:
     */
    @Override
    public E getEntityById(Integer id) {

        return null;
    }

    /**
     * 功能描述: 获取缓存值redis中lis集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 20:01
     * @Return:
     */
    @Override
    public List<E> getAllEntity(String key, String message) {
        try {
            redisUtils.get(key);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: 获取缓存中所有zifuchuan
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:23
     * @Return:
     */
    @Override
    public String getAllString(String key, String message) {
        try {
            return redisUtils.get(key);
        } catch (Exception e) {
            logger.error(message, e);
        }
        return null;
    }

    /**
     * 功能描述: 删除缓存中的所有集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 20:01
     * @Return:
     */
    @Override
    public void removeAllEntity(String key, String message) {

    }
}
