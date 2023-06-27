package manage.utils;

public class Utils {
    public static String long2hhmm(Long time) {
        long a = time / (1000 * 60 * 60);
        long b = time % (1000 * 60 * 60) / (1000 * 60);
        String str_a = a + "";
        String str_b = b + "";
        if (a < 10) {
            str_a = "0" + str_a;
        }
        if (b < 10)
            str_b = "0" + str_b;
        return str_a + ":" + str_b;
    }

    public static String long2minutes(Long time) {
        long minutes = time / (1000 * 60);
        return minutes + "";
    }

    public static Long hhmm2long(String hhmm) {
        String[] str = hhmm.split(":");
        int hour = Integer.parseInt(str[0]);
        int minute = Integer.parseInt(str[1]);
        Long time = hour*60*60*1000L + minute*60*1000L;
        return time;
    }
}
