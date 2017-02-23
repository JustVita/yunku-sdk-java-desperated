package com.yunkuent.sdk;

/**
 * 调试配置
 */
public class DebugConfig {

    public static boolean PRINT_LOG = false;
    public static int PRINT_LOG_TYPE = 0;

    public static final int PRINT_LOG_TO_CONSOLE = 0;//在控制台里写日志

    public static final int PRINT_LOG_IN_FILE = 1;//在控制台里写日志，，并写入文件

    public static String LOG_PATH = "LogPath/";


    public static final String LOG_PRINT_PATTERN = "%d{HH:mm:ss.SSS} %-5level YunkuJavaSDK %class{36} %M - %msg%xEx%n";

    public static final boolean PRINT_LOG_FILE = false;

    public static String PRINT_LOG_FILE_SIZE = "1MB";

}
