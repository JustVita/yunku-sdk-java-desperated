package com.yunkuent.sdk.log;

import com.yunkuent.sdk.utils.Util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;

/**
 * Created by Brandon on 2014/8/6.
 */
public class YunkuMemoryHandler {
    public static final int NUM_LINES = 10000;

    FileHandler fhandler;
    Logger logger;
    MemoryHandler mhandler;

    public FileHandler getFhandler() {
        return fhandler;
    }

    public void setFhandler(FileHandler fhandler) {
        this.fhandler = fhandler;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public MemoryHandler getMhandler() {
        return mhandler;
    }

    public void setMhandler(MemoryHandler mhandler) {
        this.mhandler = mhandler;
    }

    public YunkuMemoryHandler() {
        try {

            // 构造名为my.log的日志记录文件
            fhandler = new FileHandler("yunkusdk_"+ Util.formateTime(System.currentTimeMillis(),"yyyy_MM_dd")+".log");
            mhandler = new MemoryHandler(fhandler, NUM_LINES, Level.OFF);
            // 构造一个记录器
            logger = Logger.getLogger("LogFileWriter");
            // 为记录器添加一个MemoryHandler
            logger.addHandler(mhandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
