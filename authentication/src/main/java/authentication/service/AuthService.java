package authentication.service;

import authentication.mapper.AdminMapper;
import authentication.mapper.RedisMapper;
import authentication.mapper.UserMapper;
import authentication.pojo.Admin;
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
    AdminMapper adminMapper;
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

    public Boolean adminRegister(String username, String password) {
        if (!adminMapper.exists(Wrappers.lambdaQuery(Admin.class).eq(Admin::getUsername, username))) {
            Admin admin = new Admin(username, password);
            return adminMapper.insert(admin) > 0;
        }
        return false;
    }

    public String adminLogin(String username, String password) {
        String token;
        if (adminMapper.exists(Wrappers.lambdaQuery(Admin.class).eq(Admin::getUsername, username))) {
            Admin admin = adminMapper.selectOne(Wrappers.lambdaQuery(Admin.class).eq(Admin::getUsername, username));
            if (admin.getPassword().equals(password)) {
                String key = "admins:" + username;
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

    public boolean auth(String username, String token, String type) {
        String key = type + "s:" + username;
        Jedis jedis = redisMapper.getJedisPool().getResource();
        if (jedis.exists(key)) {
            if (token.equals(jedis.get(key))) {
                jedis.expire(key, 30 * 60L);
                return true;
            }
        }
        return false;
    }
    public User getUserInfo(String username){
        User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername,username));
        if(user!=null){
            user.setPassword(null);
        }
        else {
            user = new User();
        }
        return user;
    }
}
