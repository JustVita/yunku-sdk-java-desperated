package com.yunkuent.sdk;

import com.yunkuent.sdk.log.YunkuMemoryHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 输出日志   System.out.println
 */
class LogPrint {

    public static void print(String log) {

        if (ParentEngine.PRINT_LOG) {
            YunkuMemoryHandler mt = new YunkuMemoryHandler();
            // 在MemoryHandler中缓存日志记录
            mt.getLogger().log(Level.INFO, log);
            mt.getMhandler().push();
        }

    }
}
