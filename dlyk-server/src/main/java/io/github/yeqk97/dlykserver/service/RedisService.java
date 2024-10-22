package io.github.yeqk97.dlykserver.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    void setValue(String key, Object value);

    Object getValue(String key);

    Boolean deleteValue(String key);

    void expire(String key, long timeOut, TimeUnit timeUnit);
}
