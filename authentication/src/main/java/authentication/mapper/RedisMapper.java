package authentication.mapper;

import authentication.utils.Utils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisMapper {
    private final JedisPool jedisPool;

    public RedisMapper() {
        jedisPool = new JedisPool("10.100.164.15", 6379, null, "7758258");
    }

    public synchronized boolean tokenExist(String key,String token){
        synchronized (jedisPool){
            Jedis jedis = jedisPool.getResource();
            if (jedis.exists(key)){
                if (jedis.get(key).equals(token)){
                    jedis.close();
                    return true;
                }
                else {
                    jedis.close();
                    return false;
                }
            }
            else {
                jedis.close();
                return false;
            }
        }
    }
    public synchronized String getToken(String key,String username,String password){
        synchronized (jedisPool){
            Jedis jedis = jedisPool.getResource();
            String token;
            if (jedis.exists(key)) {
                token = jedis.get(key);
                jedis.expire(key,30*60L);
            } else {
                token = Utils.generateToken(username, password);
                jedis.set(key, token);
                jedis.expire(key,30*60L);
            }
            jedis.close();
            return token;
        }

    }
}
