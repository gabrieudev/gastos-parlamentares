package com.api.gastos.service;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public void limparCache() {
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        redisConnection.serverCommands().flushAll();
    }
}
