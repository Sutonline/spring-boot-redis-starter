package cn.kevin.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * Created by sut on 2017/7/6.
 */
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {


    private String url;

    private Set<String> urls;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }
}
