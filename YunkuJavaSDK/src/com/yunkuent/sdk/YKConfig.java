package com.yunkuent.sdk;

/**
 * Created by qp on 2017/2/20.
 */
public class YKConfig extends Config{

    public static final String LOG_PRINT_PATTERN = "%d{HH:mm:ss.SSS} %-5level YunkuJavaSDK %class{36} %M - %msg%xEx%n";

    public static boolean PRINT_LOG = false;

    public static final boolean PRINT_LOG_FILE = false;

    public static String PRINT_LOG_FILE_SIZE = "1MB";

    public static final int PRINT_LOG_TYPE = 1;

    public static final int PRINT_LOG_TO_CONSOLE = 0;//在控制台里写日志

    public static final int PRINT_LOG_IN_FILE = 1;//在控制台里写日志，，并写入文件

    public static String getPrintLogFileSize() {
        return PRINT_LOG_FILE_SIZE;
    }

    public static void setPrintLogFileSize(String printFileSize) {
        PRINT_LOG_FILE_SIZE = printFileSize;
    }
}
