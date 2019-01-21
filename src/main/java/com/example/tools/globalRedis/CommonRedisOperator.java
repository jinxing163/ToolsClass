package com.example.tools.globalRedis;

import com.zhihuishu.toolkit.jedis.JedisHandler;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author JinXing
 * @date 2018/6/29 13:17
 */
public class CommonRedisOperator {
    private static final Logger logger = LoggerFactory.getLogger(CommonRedisOperator.class);
    private static String uri = "http://conf.zhihuishu.com/global/redis.properties";
    private static Properties uriProps = new Properties();
    private static JedisPool jedisPoolMaster;
    private static JedisPool jedisPoolSlave;
    private static final int MAX_HEARTBEAT_RETRIES = 3;
    private static final AtomicInteger masterStatus = new AtomicInteger(3);
    private static final AtomicInteger SLAVE_STATUS = new AtomicInteger(3);
    private static String poolConfigFile = "global_redis_pool.properties";

    public CommonRedisOperator() {
    }

    private static final boolean heartbeat(JedisPool jedisPool) {
        if (jedisPool == null) {
            return false;
        } else {
            Jedis jedis = null;

            try {
                label130:
                {
                    jedis = jedisPool.getResource();
                    if (jedis != null && jedis.isConnected()) {
                        String key = UUID.randomUUID().toString();
                        String value = String.valueOf(System.currentTimeMillis());
                        jedis.setex(key, 1, value);
                        String redisValue = jedis.get(key);
                        if (redisValue != null && redisValue.equals(value)) {
                            break label130;
                        }

                        boolean var5 = false;
                        return var5;
                    }

                    boolean var2 = false;
                    return var2;
                }
            } catch (Exception var9) {
                logger.debug("对连接池[{}]心跳检查失败!", jedisPool, var9);
                boolean var3 = false;
                return var3;
            } finally {
                if (jedis != null) {
                    jedis.close();
                }

            }

            logger.debug("[{}]心跳执行成功!", jedisPool.toString());
            return true;
        }
    }

    public static final void init(GenericObjectPoolConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("Redis连接池配置错误!");
        } else {
            logger.info("初始化公共缓存客户端连接池[max.idle={},min.idle={},wait.millis={}]", new Object[]{config.getMaxIdle(), config.getMinIdle(), config.getMaxWaitMillis()});
            jedisPoolMaster = new JedisPool(config, URI.create(uriProps.getProperty("redis.common.master.uri")));
            jedisPoolSlave = new JedisPool(config, URI.create(uriProps.getProperty("redis.common.slave.uri")));
        }
    }

    private static final <T> T handle(JedisPool jedisPool, JedisHandler<T> handler) {
        Jedis jedis = null;

        T var3;
        try {
            jedis = jedisPool.getResource();
            var3 = handler.handle(jedis);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    private static final <T> T write(JedisHandler<T> handler) {
        T result = null;
        if (masterStatus.get() >= 0) {
            result = handle(jedisPoolMaster, handler);
        }

        if (SLAVE_STATUS.get() >= 0) {
            result = handle(jedisPoolSlave, handler);
        }

        return result;
    }

    private static final <T> T read(JedisHandler<T> handler) {
        JedisPool jedisPool = null;
        if (masterStatus.get() >= 0) {
            jedisPool = jedisPoolMaster;
        } else {
            if (SLAVE_STATUS.get() < 0) {
                throw new RuntimeException("公共缓存主、备皆不能完成心跳，请运维检查公共缓存服务是否正常!");
            }

            jedisPool = jedisPoolSlave;
        }

        return handle(jedisPool, handler);
    }

    public static final String set(final String key, final String value) {
        return (String) write(new JedisHandler<String>() {
            @Override
            public String handle(Jedis jedis) {
                return jedis.set(key, value);
            }
        });
    }

    public static final Long setnx(final String key, final String value) {
        return (Long) write(new JedisHandler<Long>() {
            @Override
            public Long handle(Jedis jedis) {
                return jedis.setnx(key, value);
            }
        });
    }

    public static final String hmset(final String key, final Map<String, String> hash) {
        return (String) write(new JedisHandler<String>() {
            @Override
            public String handle(Jedis jedis) {
                return jedis.hmset(key, hash);
            }
        });
    }

    public static final Long hset(final String key, final String field, final String value) {
        return (Long) write(new JedisHandler<Long>() {
            @Override
            public Long handle(Jedis jedis) {
                return jedis.hset(key, field, value);
            }
        });
    }

    public static final String get(final String key) {
        return (String) read(new JedisHandler<String>() {
            @Override
            public String handle(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    public static final String hget(final String key, final String field) {
        return (String) read(new JedisHandler<String>() {
            @Override
            public String handle(Jedis jedis) {
                return jedis.hget(key, field);
            }
        });
    }

    public static final List<String> hmget(final String key, final String... fields) {
        return (List) read(new JedisHandler<List<String>>() {
            @Override
            public List<String> handle(Jedis jedis) {
                return jedis.hmget(key, fields);
            }
        });
    }

    public static final Map<String, String> hgetAll(final String key) {
        return (Map) read(new JedisHandler<Map<String, String>>() {
            @Override
            public Map<String, String> handle(Jedis jedis) {
                return jedis.hgetAll(key);
            }
        });
    }

    public static final Long lpush(final String key, final String... values) {
        return (Long) write(new JedisHandler<Long>() {
            @Override
            public Long handle(Jedis jedis) {
                return jedis.lpush(key, values);
            }
        });
    }

    public static final List<String> lrange(final String key, final long start, final long end) {
        return (List) read(new JedisHandler<List<String>>() {
            @Override
            public List<String> handle(Jedis jedis) {
                return jedis.lrange(key, start, end);
            }
        });
    }

    public static final Long llen(final String key) {
        return (Long) read(new JedisHandler<Long>() {
            @Override
            public Long handle(Jedis jedis) {
                return jedis.llen(key);
            }
        });
    }

    public static final Long sadd(final String key, final String... members) {
        return (Long) write(new JedisHandler<Long>() {
            @Override
            public Long handle(Jedis jedis) {
                return jedis.sadd(key, members);
            }
        });
    }

    public static final Set<String> smembers(final String key) {
        return (Set) read(new JedisHandler<Set<String>>() {
            @Override
            public Set<String> handle(Jedis jedis) {
                return jedis.smembers(key);
            }
        });
    }

    static {
        Properties poolProps = new Properties();

        try {
            poolProps.load(CommonRedisOperator.class.getClassLoader().getResourceAsStream(poolConfigFile));
            logger.info("公共缓存客户端通过CLASSPATH下的<{}>配置文件配置缓存连接池!", poolConfigFile);
        } catch (Exception var3) {
            logger.warn("公共缓存客户端在CLASSPATH下未包含<{}>配置文件，将使用默认连接池配置!", poolConfigFile);
            poolProps.setProperty("pool.max.idle", "50");
            poolProps.setProperty("pool.min.idle", "10");
            poolProps.setProperty("max.wait.millis", "3000");
        }

        try {
            uriProps.load(URI.create(uri).toURL().openStream());
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(Integer.valueOf(poolProps.getProperty("pool.max.idle")));
            config.setMaxIdle(Integer.valueOf(poolProps.getProperty("pool.max.idle")));
            config.setMinIdle(Integer.valueOf(poolProps.getProperty("pool.min.idle")));
            config.setMaxWaitMillis((long) Integer.valueOf(poolProps.getProperty("max.wait.millis")));
            config.setTimeBetweenEvictionRunsMillis(3600000L);
            config.setTestWhileIdle(true);
            init(config);
        } catch (Exception var2) {
            logger.error("配置公共缓存出错!", var2);
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                        if (CommonRedisOperator.heartbeat(CommonRedisOperator.jedisPoolMaster)) {
                            CommonRedisOperator.masterStatus.set(3);
                        } else {
                            CommonRedisOperator.masterStatus.decrementAndGet();
                        }

                        if (CommonRedisOperator.heartbeat(CommonRedisOperator.jedisPoolSlave)) {
                            CommonRedisOperator.SLAVE_STATUS.set(3);
                        } else {
                            CommonRedisOperator.SLAVE_STATUS.decrementAndGet();
                        }
                    } catch (Exception var2) {
                        var2.printStackTrace();
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.setName("global-redis-daemon");
        thread.start();
    }
}