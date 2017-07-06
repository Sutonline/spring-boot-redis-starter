package cn.kevin.spring.boot.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;

/**
 * Created by sut on 2017/7/6.
 */
@Configuration
@ConditionalOnBean
@ConfigurationProperties("redis")
public class RedisAutoConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @ConditionalOnProperty(havingValue = "url")
    @Bean
    public Jedis jedis() {
        return new Jedis(redisProperties.getUrl());
    }

    @ConditionalOnProperty(name = "urls")
    @Bean
    public JedisCluster jedisCluster() {
        HashSet<HostAndPort> set = new HashSet<>();
        if (redisProperties.getUrls() != null) {
            redisProperties.getUrls().forEach(url -> {
                set.add(new HostAndPort(url.substring(0, url.indexOf(":")),
                        Integer.parseInt(url.substring(url.indexOf(":") + 1))));
            });
        }
        JedisCluster cluster = new JedisCluster(set);
        return cluster;
    }

}
