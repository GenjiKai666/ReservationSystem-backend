package reservation.utils;

public class ConstantData {
    //预约状态
    public static final Integer STATUS_WAIT = 0;
    public static final Integer STATUS_TO_BE_SIGNED = 1;
    public static final Integer STATUS_SIGNED = 2;
    public static final Integer STATUS_BREAK = 3;
    public static final Integer STATUS_CANCELED = 4;

    public static final Integer STATUS_NORMAL = 0;
    public static final Integer STATUS_BLOCKED = 1;
    public static final Integer CREDIT_INITIAL = 100;
    public static final Integer CREDIT_THRESHOLD = 60;

    public static final Integer CODE_NORMAL = 200;
    public static final Integer CODE_OPERATION_FAILED = 400;
}
