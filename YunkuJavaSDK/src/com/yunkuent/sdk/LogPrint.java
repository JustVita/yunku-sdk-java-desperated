package com.yunkuent.sdk;

import java.util.logging.Level;

/**
 * 输出日志   System.out.println
 */
class LogPrint {

    private static YunkuMemoryHandler mHander;

    private synchronized static YunkuMemoryHandler getInstance() {
        if (mHander == null) {
            mHander = new YunkuMemoryHandler();
        }
        return mHander;
    }

    public static void print(String log) {
        print(Level.INFO, log);
    }


    public static void print(Level level, String log) {
        if (DebugConfig.PRINT_LOG) {
            YunkuMemoryHandler mt = getInstance();
            // 在MemoryHandler中缓存日志记录
            mt.getLogger().log(level, log);
            mt.getMhandler().push();
        }
    }
}
