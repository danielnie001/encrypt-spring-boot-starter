package com.daniel.encrypt.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author niehaisheng
 * @date 2022/6/6 14:23
 */
@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptProperties {

    private final static String DEFAULT_KEY = "danilenie001.com";

    private String key = DEFAULT_KEY;

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }
}
