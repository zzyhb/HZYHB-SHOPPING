package com.yhb.redis.service;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/11 19:02
 * @Description:
 */
public interface RedisService {
    boolean setRedisValue(String key,Object value);

    boolean setRedisValue(String key,Object value,long time);

    Object getRedisValue(String key);

    void removeRedis(String key);
}
