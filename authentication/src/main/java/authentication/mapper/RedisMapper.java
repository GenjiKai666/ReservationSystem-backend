package authentication.mapper;

import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

@Component
public class RedisMapper {
    private final JedisPool jedisPool;

    public RedisMapper() {
        jedisPool = new JedisPool("10.100.164.15", 6379, null, "7758258");
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }
}
