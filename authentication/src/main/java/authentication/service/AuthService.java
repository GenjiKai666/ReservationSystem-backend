package authentication.service;

import authentication.mapper.RedisMapper;
import authentication.mapper.UserMapper;
import authentication.pojo.User;
import authentication.utils.Utils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

@Service
@Transactional
public class AuthService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisMapper redisMapper;

    public Boolean userRegister(String username, String password) {
        if (!userMapper.exists(Wrappers.lambdaQuery(User.class).eq(User::getUsername, username))) {
            User user = new User(username, password);
            return userMapper.insert(user) > 0;
        }
        return false;
    }

    public String userLogin(String username, String password) {
        String token;
        if (userMapper.exists(Wrappers.lambdaQuery(User.class).eq(User::getUsername, username))) {
            User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, username));
            if (user.getPassword().equals(password)) {
                String key = "users:" + username;
                Jedis jedis = redisMapper.getJedisPool().getResource();
                if (jedis.exists(key)) {
                    token = jedis.get(key);
                } else {
                    token = Utils.generateToken(username, password);
                    jedis.set(key, token);
                }
                jedis.expire(key, 30 * 60L);
                return token;
            }
        }
        token = "";
        return token;
    }

    public boolean auth(String username, String token) {
        String key = "users:" + username;
        Jedis jedis = redisMapper.getJedisPool().getResource();
        if (jedis.exists(key)) {
            if (token.equals(jedis.get(key))) {
                jedis.expire(key, 30 * 60L);
                return true;
            }
        }
        return false;
    }
}
