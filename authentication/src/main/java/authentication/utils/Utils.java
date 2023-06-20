package authentication.utils;

public class Utils {
    public static String generateToken(String username,String password){
        String token;
        long timestamp = System.currentTimeMillis();
        // 生成一个四位数的随机数
        int randomInt = (int) ((Math.random() * 9 + 1) * Math.pow(10, 4 - 1));
        String info = username+password+timestamp+randomInt;
        token = String.valueOf(info.hashCode());
        return token;
    }
}
