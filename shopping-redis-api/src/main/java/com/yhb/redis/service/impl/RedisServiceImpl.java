package com.yhb.redis.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhb.redis.service.RedisService;
import com.yhb.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/11 19:04
 * @Description:
 */
@Component
@Service(version = "1.0.0")
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public boolean setRedisValue(String key, Object value) {
        return redisUtil.set(key,value);
    }

    @Override
    public boolean setRedisValue(String key, Object value,long time) {
        return redisUtil.set(key,value,time);
    }

    @Override
    public Object getRedisValue(String key) {
        return redisUtil.get(key);
    }

    @Override
    public void removeRedis(String key) {
        redisUtil.del(key);
    }
}
