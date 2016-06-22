package com.yjy.common.redis;

import com.yjy.common.utils.JsonUtils;
import com.yjy.web.vo.JedisVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JedisTemplate 提供了一个template方法，负责对Jedis连接的获取与归还。 JedisAction<T> 和
 * JedisActionNoResult两种回调接口，适用于有无返回值两种情况。 同时提供一些最常用函数的封装, 如get/set/zadd等。
 * Created by yaobo on 2014/5/17.
 */
public class JedisTemplate implements Serializable {
    private static Log logger = LogFactory.getLog(JedisTemplate.class);

    private Pool<Jedis> jedisPool;

    public JedisTemplate(String host, int port, int timeout, int threadCount) {
        // 设置Pool大小，设为与线程数等大，并屏蔽掉idle checking
        JedisPoolConfig poolConfig = JedisUtils.createPoolConfig(100, 100);


        // create jedis pool
        this.jedisPool = new JedisPool(poolConfig, host, port, timeout,"123");

    }

    /**
     * 执行有返回结果的action。
     */
    public <T> T execute(JedisAction<T> jedisAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {

            jedis = jedisPool.getResource();
            return jedisAction.action(jedis);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            broken = true;
            throw e;
        } finally {
            closeResource(jedis, broken);
        }
    }

    /**
     * 执行无返回结果的action。
     */
    public void execute(JedisActionNoResult jedisAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = jedisPool.getResource();
            jedisAction.action(jedis);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            broken = true;
            throw e;
        } finally {
            closeResource(jedis, broken);
        }
    }

    /**
     * 根据连接是否已中断的标志，分别调用returnBrokenResource或returnResource。
     */
    protected void closeResource(Jedis jedis, boolean connectionBroken) {
        if (jedis != null) {
            try {
                if (connectionBroken) {
                    jedisPool.returnBrokenResource(jedis);
                } else {
                    jedisPool.returnResource(jedis);
                }
            } catch (Exception e) {
                logger.error(
                        "Error happen when return jedis to pool, try to close it directly.",
                        e);
                JedisUtils.closeJedis(jedis);
            }
        }
    }

    /**
     * 获取内部的pool做进一步的动作。
     */
    public Pool<Jedis> getJedisPool() {
        return jedisPool;
    }

    /**
     * 有返回结果的回调接口定义。
     */
    public interface JedisAction<T> {
        T action(Jedis jedis);

    }

    /**
     * 无返回结果的回调接口定义。
     */
    public interface JedisActionNoResult {
        void action(Jedis jedis);
    }

    // ////////////// 常用方法的封装 ///////////////////////// //

    // ////////////// 公共 ///////////////////////////

    /**
     * 删除key, 如果key存在返回true, 否则返回false。
     */
    public Boolean del(final String... keys) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.del(keys) == 1 ? true : false;
            }
        });
    }

    public Boolean exists(final String key) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.exists(key);
            }
        });
    }

    public Long delByPrefix(final String reg) {
        return execute(new JedisAction<Long>() {

            @Override
            public Long action(Jedis jedis) {
                Set<String> keys = jedis.keys(reg);
                if (keys == null || keys.isEmpty())
                    return 0L;
                return jedis.del(keys.toArray(new String[keys.size()]));
            }
        });
    }

    public void flushDB() {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                jedis.flushDB();
            }
        });
    }

    // ////////////// 关于String ///////////////////////////

    /**
     * 如果key不存在, 返回null.
     */
    public String get(final String key) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    /**
     * 模糊取redis的值
     * * @param pattern
     * * @return
     * * @date:2015-7-2
     * * @author: zengyang
     */
    public List<String> getByReg(final String pattern) {
        return execute(new JedisAction<List<String>>() {

            @Override
            public List<String> action(Jedis jedis) {
                List<String> values = new ArrayList<String>();
                Set<String> keys = jedis.keys(pattern);
                for (String key : keys) {
                    String value = jedis.get(key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return values;
            }
        });
    }

    /**
     * 模糊删除
     * * @param reg
     * * @date:2015-7-6
     * * @author: zengyang
     */
    public void deleteByReg(final String reg) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                Set<String> keys = jedis.keys(reg);
                if(keys != null && keys.size() > 0){
                	jedis.del(keys.toArray(new String[keys.size()]));
                }
            }
        });
    }

    /**
     * 如果key不存在, 返回null.
     */
    public Long getAsLong(final String key) {
        String result = get(key);
        return result != null ? Long.valueOf(result) : null;
    }

    /**
     * 如果key不存在, 返回null.
     */
    public Integer getAsInt(final String key) {
        String result = get(key);
        return result != null ? Integer.valueOf(result) : null;
    }

    public void set(final String key, final String value) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                jedis.set(key, value);
            }
        });
    }

    public void pipset(final List<JedisVo> jedisVos) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                Pipeline pipelined = jedis.pipelined();
                 for(JedisVo jedisVo:jedisVos){
                     pipelined.set(jedisVo.getKey(),jedisVo.getValue());
                 }
                pipelined.sync();
            }
        });
    }

    public void pipdel(final List<JedisVo> jedisVos) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                Pipeline pipelined = jedis.pipelined();
                for(JedisVo jedisVo:jedisVos){
                    pipelined.del(jedisVo.getKey());
                }
                pipelined.sync();
            }
        });
    }

    public void setex(final String key, final String value, final int seconds) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                jedis.setex(key, seconds, value);
            }
        });
    }

    /**
     * 如果key还不存在则进行设置，返回true，否则返回false.
     */
    public Boolean setnx(final String key, final String value) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.setnx(key, value) == 1 ? true : false;
            }
        });
    }

    /**
     * 综合setNX与setEx的效果。
     */
    public Boolean setnxex(final String key, final String value,
                           final int seconds) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                String result = jedis.set(key, value, "NX", "EX", seconds);
                return JedisUtils.isStatusOk(result);
            }
        });
    }

    public Long incr(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.incr(key);
            }
        });
    }

    public Long decr(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.decr(key);
            }
        });
    }

    // ////////////// 关于List ///////////////////////////
    public void lpush(final String key, final String... values) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.lpush(key, values);

            }
        });
    }

    public void rpush(final String key, final String... values) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.rpush(key, values);

            }
        });
    }

    public List<String> Lrange(final String key) {
        return execute(new JedisAction<List<String>>() {

            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(key, 0, -1);
            }
        });
    }

    public String rpop(final String key) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                return jedis.rpop(key);
            }
        });
    }

    /**
     * 返回List长度, key不存在时返回0，key类型不是list时抛出异常.
     */
    public Long llen(final String key) {
        return execute(new JedisAction<Long>() {

            @Override
            public Long action(Jedis jedis) {
                return jedis.llen(key);
            }
        });
    }

    /**
     * 删除List中的第一个等于value的元素，value不存在或key不存在时返回false.
     */
    public Boolean lremOne(final String key, final String value) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                Long count = jedis.lrem(key, 1, value);
                return (count == 1);
            }
        });
    }

    /**
     * 删除List中的所有等于value的元素，value不存在或key不存在时返回false.
     */
    public Boolean lremAll(final String key, final String value) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                Long count = jedis.lrem(key, 0, value);
                return (count > 0);

            }
        });
    }

    // ////////////// 关于Sorted Set ///////////////////////////

    /**
     * 加入Sorted set, 如果member在Set里已存在, 只更新score并返回false, 否则返回true.
     */
    public Boolean zadd(final String key, final String member,
                        final double score) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.zadd(key, score, member) == 1 ? true : false;

            }
        });
    }

    /**
     * 删除sorted set中的元素，成功删除返回true，key或member不存在返回false。
     */
    public Boolean zrem(final String key, final String member) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.zrem(key, member) == 1 ? true : false;
            }
        });
    }

    /**
     * 当key不存在时返回null.
     */
    public Double zscore(final String key, final String member) {
        return execute(new JedisAction<Double>() {

            @Override
            public Double action(Jedis jedis) {
                return jedis.zscore(key, member);
            }
        });
    }

    /**
     * 返回sorted set长度, key不存在时返回0.
     */
    public Long zcard(final String key) {
        return execute(new JedisAction<Long>() {

            @Override
            public Long action(Jedis jedis) {
                return jedis.zcard(key);
            }
        });
    }

    /**
     * 返回匹配pattern的key set.
     */
    public Set<String> patternSet(final String pattern) {
        return execute(new JedisAction<Set<String>>() {

            public Set<String> action(Jedis jedis) {

                return jedis.keys(pattern);
            }
        });
    }

    /**
     * 返回匹配pattern的List<Map></>.
     */
    public List<Map> interventionList(final String pattern) {
        return execute(new JedisAction<List<Map>>() {

            public List<Map> action(Jedis jedis) {
                Set<String> keySet = jedis.keys(pattern);
                List<Map> list = new ArrayList<Map>();
                for (String key : keySet) {
                    Map<String, String> map = jedis.hgetAll(key);
                    String index = key.substring(key.indexOf("location:")
                            + "location:".length());
                    map.put("location", index);
                    list.add(map);
                }
                return list;

            }
        });
    }

    /* 将Map数据放入radis中，key-String, value-set */
    public Long hset(final String key, final String field, final String value) {
        return execute(new JedisAction<Long>() {
            public Long action(Jedis jedis) {
                return jedis.hset(key, field, value);
            }
        });
    }

    public void hset(final Map<String, Map> mainMap) {
        execute(new JedisActionNoResult() {

            public void action(Jedis jedis) {
                for (Map.Entry<String, Map> entry : mainMap.entrySet()) {
                    String key = entry.getKey();
                    Map<String, Object> paramMap = entry.getValue();
                    for (Map.Entry<String, Object> paramEntry : paramMap
                            .entrySet()) {
                        jedis.hset(key, paramEntry.getKey(),
                                String.valueOf(paramEntry.getValue()));
                    }
                }
            }
        });
    }

    public String hget(final String key, final String field) {
        return execute(new JedisAction<String>() {
            public String action(Jedis jedis) {
                return jedis.hget(key, field);
            }
        });
    }

    public Map<String, String> hgetAll(final String key) {
        return execute(new JedisAction<Map<String, String>>() {
            public Map<String, String> action(Jedis jedis) {
                return jedis.hgetAll(key);
            }
        });
    }

    public long hdel(final String key, final String... fields) {
        return execute(new JedisAction<Long>() {
            public Long action(Jedis jedis) {
                return jedis.hdel(key, fields);
            }
        });
    }

    // ////////////// 关于Set ///////////////////////////
    public long sadd(final String key, final String... members) {
        return execute(new JedisAction<Long>() {
            public Long action(Jedis jedis) {
                return jedis.sadd(key, members);
            }
        });
    }

    public Set<String> smembers(final String key) {
        return execute(new JedisAction<Set<String>>() {
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(key);
            }
        });
    }

    // ////////////// 补充zSet的一些用法 ///////////////////////////
    // 批量更新插入
    public void zdddMap(final String key, final Map<String, Double> map) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.zadd(key, map);
            }
        });

    }

    // 返回正序排序表（分数从小到大）
    public Set<String> zRange(final String key, final int start, final int end) {
        return execute(new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.zrange(key, start, end);
            }
        });
    }

    // 返回逆序排序表（分数从大到小）
    public Set<String> zReRange(final String key, final int start, final int end) {
        return execute(new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.zrevrange(key, start, end);
            }
        });
    }

    public List<String> mget(final String... keys) {
        return execute(new JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.mget(keys);
            }
        });
    }

    /** redis放入和取出对象 */

    /**
     * redis存入对象
     *
     * @param key     缓存key
     * @param obj     缓存对象
     * @param seconds 过期时间
     */
    public void setObject(String key, Object obj, int seconds) {
        this.setex(key, JsonUtils.toJson(obj), seconds);
    }

    /**
     * redis存入对象，永不过期
     *
     * @param key 缓存key
     * @param obj 缓存对象
     */
    public void setObject(String key, Object obj) {
        this.set(key, JsonUtils.toJson(obj));
    }

    /**
     * 取模糊的序列化对象
     * * @param <T>
     * * @param pattern
     * * @param clazz
     * * @return
     * * @date:2015-7-2
     * * @author: zengyang
     */
    public <T> List<T> getListObjectByReg(final String pattern, Class<T> clazz) {
        List<String> values = this.getByReg(pattern);
        List<T> list = new ArrayList<T>();
        for (String value : values) {
            list.add(JsonUtils.readToT(value, clazz));
        }
        return list;
    }

    /**
     * redis 取出对象
     *
     * @param key   缓存key
     * @param clazz 缓存对象的类
     * @param <T>
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz) {
        return JsonUtils.readToT(get(key), clazz);
    }

    /**
     * 取出List对象
     *
     * @param key   缓存key
     * @param clazz 缓存对象的类
     * @param <T>
     * @return
     */
    public <T> List<T> getListObject(String key, Class<T> clazz) {
        return JsonUtils.readToList(get(key), clazz);
    }


    /**
     * 订阅
     *
     * @return
     */
    public void subscribe(final String key ,final JedisPubSub jedisPubSub) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {

                jedis.subscribe(jedisPubSub,key);
            }
        });
    }

    /**
     * 停止订阅
     *
     * @return
     */
    public void unsubscribe(final String key ) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {

                jedis.pubsubChannels(key);
            }
        });
    }

    /**
     * 发布
     *
     * @return
     */
    public void publish(final String key ,final String value){
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {

                jedis.publish(key,value );
            }
        });
    }

    public static void main(String[] args) {
        final   JedisTemplate jedisTemplate=new JedisTemplate("192.168.2.12",6379,15000,5);
        final  String key=RedisKey.REPORT_OF_DAY;
                List<JedisVo> jedisVos=new ArrayList<>();
                JedisVo jedisVo1=    new JedisVo("key1","va1");
                JedisVo jedisVo2=    new JedisVo("key2","va2");
                JedisVo jedisVo3=    new JedisVo("key3","va3");
            jedisVos.add(jedisVo1);
            jedisVos.add(jedisVo2);
            jedisVos.add(jedisVo3);

            jedisTemplate.pipset(jedisVos);
            logger.info(jedisTemplate.get("key1"));
            logger.info(jedisTemplate.get("key2"));
            logger.info(jedisTemplate.get("key3"));


        jedisTemplate.pipdel(jedisVos);

        logger.info(jedisTemplate.get("key1"));
        logger.info(jedisTemplate.get("key2"));
        logger.info(jedisTemplate.get("key3"));




    }


}
