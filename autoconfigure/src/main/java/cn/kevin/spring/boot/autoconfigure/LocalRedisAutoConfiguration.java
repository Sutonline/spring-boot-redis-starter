package cn.kevin.spring.boot.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@ConditionalOnClass(Jedis.class)
@EnableConfigurationProperties(LocalRedisProperties.class)
@ConditionalOnProperty(value = "redis.enabled", matchIfMissing = true)
public class LocalRedisAutoConfiguration {

    @Autowired
    private LocalRedisProperties localRedisProperties;

    @Bean(name = "jedis")
    public Jedis jedis() {
        if (localRedisProperties.getUrls() != null) return null;
        return new Jedis(localRedisProperties.getUrl().substring(0, localRedisProperties.getUrl().indexOf(":")),
                Integer.parseInt(localRedisProperties.getUrl().substring(localRedisProperties.getUrl().indexOf(":") + 1)),
                5000);
    }

    @ConditionalOnProperty(name = "redis.urls")
    @Bean(name = "jedisCluster")
    public JedisCluster jedisCluster() {
        HashSet<HostAndPort> set = new HashSet<>();
        if (localRedisProperties.getUrls() != null) {
            localRedisProperties.getUrls().forEach(url -> {
                set.add(new HostAndPort(url.substring(0, url.indexOf(":")),
                        Integer.parseInt(url.substring(url.indexOf(":") + 1))));
            });
        }
        return new JedisCluster(set);
    }

}
