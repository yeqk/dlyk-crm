package io.github.yeqk97.dlykserver.service.impl;

import io.github.yeqk97.dlykserver.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisServiceImpl(final RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setValue(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object getValue(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean deleteValue(final String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void expire(final String key, final long timeout, final TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }
}
