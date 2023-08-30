package com.catstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static final int MAX_TOTAL = 8;

    //最小空闲连接数, 默认0
    private static final int MIN_IDLE = 0;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    //最大空闲连接数, 默认8个
    private static final int MAX_IDLE = 8;

    //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
    private static final long MAX_WAIT_MILLIS = 5000L;

    private static final int TIMEOUT = 10000;

    //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
    private static final boolean BLOCK_WHEN_EXHAUSTED = false;

    //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
    private static final String EVICTION_POLICY_CLASSNAME = "org.apache.commons.pool2.impl.DefaultEvictionPolicy";

    //是否启用pool的jmx管理功能, 默认true
    private static final boolean JMX_ENABLED = true;

    //MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
    private static final String JMX_NAME_PREFIX = "pool";

    //是否启用后进先出, 默认true
    private static final boolean LIFO = true;

    //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
    private static final int NUM_TESTS_PER_EVICTION_RUN = 3;

    //含义：这两个参数是说，客户端向连接池借用或归还时，是否会在内部进行有效性检测（ping），无效的资源将被移除 【默认值：false】
    //使用建议：建议false，在高并发场景下，因为这样无形给每次增加了两次ping操作，对QPS有影响，如果不是高并发环境，可以考虑开启，或者自己来检测。
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    //在获取连接的时候检查有效性, 默认false
    private static final boolean TEST_ON_BORROW = false;

    private static final boolean TEST_ON_RETURN = false;

    //在空闲时检查有效性, 默认false
    private static final boolean TEST_WHILE_IDLE = false;

    /**
     * 初始化Redis连接池
     */
    @Bean
    JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxWaitMillis(MAX_WAIT_MILLIS);
        config.setBlockWhenExhausted(BLOCK_WHEN_EXHAUSTED);
        config.setEvictionPolicyClassName(EVICTION_POLICY_CLASSNAME);
        config.setJmxEnabled(JMX_ENABLED);
        config.setJmxNamePrefix(JMX_NAME_PREFIX);
        config.setLifo(LIFO);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxTotal(MAX_TOTAL);
        config.setMinIdle(MIN_IDLE);
        config.setNumTestsPerEvictionRun(NUM_TESTS_PER_EVICTION_RUN);
        config.setTestOnBorrow(TEST_ON_BORROW);
        config.setTestOnReturn(TEST_ON_RETURN);
        config.setTestWhileIdle(TEST_WHILE_IDLE);
        return new JedisPool(config, host, Integer.parseInt(port), TIMEOUT);
    }
}
