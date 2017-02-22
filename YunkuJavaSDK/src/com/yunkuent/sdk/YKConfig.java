package com.yunkuent.sdk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qp on 2017/2/20.
 */
public class YKConfig extends Config {

    public static final String LOG_PRINT_PATTERN = "%d{HH:mm:ss.SSS} %-5level YunkuJavaSDK %class{36} %M - %msg%xEx%n";

    public static boolean PRINT_LOG = false;

    public static final boolean PRINT_LOG_FILE = false;

    public static String PRINT_LOG_FILE_SIZE = "1MB";

    public static final String INFO = "info";

    public static final String ERROR = "error";

    public static final String WARN = "warn";

    public static final int PRINT_LOG_TYPE = 0;

    public static final int PRINT_LOG_TO_CONSOLE = 0;//在控制台里写日志

    public static final int PRINT_LOG_IN_FILE = 1;//在控制台里写日志，，并写入文件

    private static LoggerContext ctx;

    private static HashMap<Level, String> LOG_LEVEL_HASH_MAP;

    static {
        LOG_LEVEL_HASH_MAP = new HashMap<>();
        LOG_LEVEL_HASH_MAP.put(Level.INFO,"info");
        LOG_LEVEL_HASH_MAP.put(Level.ERROR,"error");
        LOG_LEVEL_HASH_MAP.put(Level.WARN,"warn");
    }


    public static void printLog(String level, String log, Class<?> clazz){
        logConfiguration();
        Logger logger = LogManager.getLogger(clazz);
        for (Map.Entry<Level,String> entry : LOG_LEVEL_HASH_MAP.entrySet()) {
            if (entry.getValue().equals(level)) {
                switch (level) {
                    case INFO:
                        logger.info(log);
                        break;
                    case ERROR:
                        logger.error(log);
                        break;
                    case WARN:
                        logger.warn(log);
                        break;
                }
            }
        }
    }

    /**
     * 日志打印配置信息
     */
    private static void logConfiguration() {
        if (PRINT_LOG){
            ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
            builder.setStatusLevel(Level.INFO);
            builder.setConfigurationName("RollingBuilder");
            AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE")
                    .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
            appenderBuilder.add(builder.newLayout("PatternLayout")
                    .addAttribute("pattern", LOG_PRINT_PATTERN));
            builder.add(appenderBuilder);

            switch (PRINT_LOG_TYPE){
                case PRINT_LOG_TO_CONSOLE:
                    builder.add(builder.newLogger("YunKuJavaSDK", Level.INFO)
                            .add(builder.newAppenderRef("Stdout")));
                    builder.add(builder.newRootLogger(Level.INFO)
                            .add(builder.newAppenderRef("Stdout")));
                    ctx = Configurator.initialize(builder.build());
                    break;
                case PRINT_LOG_IN_FILE:
                    LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout")
                            .addAttribute("pattern", LOG_PRINT_PATTERN);
                    ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
                            .addComponent(builder.newComponent("CronTriggeringPolicy").addAttribute("schedule", "0 0 0 * * ?"))
                            .addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", PRINT_LOG_FILE_SIZE));
                    ComponentBuilder defaultRolloverStrategy = builder.newComponent("DefaultRolloverStrategy")
                            .addAttribute("fileIndex","min")
                            .addAttribute("min",1)
                            .addAttribute("max",1024);
                    appenderBuilder = builder.newAppender("rolling", "RollingFile")
                            .addAttribute("fileName", "target/YunkuJavaSDK.log")
                            .addAttribute("filePattern", "target/YunkuJavaSDK-%d{yyyy-MM}-%i.log")
                            .add(layoutBuilder)
                            .addComponent(triggeringPolicy)
                            .addComponent(defaultRolloverStrategy);
                    builder.add(appenderBuilder);
                    builder.add(builder.newLogger("YunKuJavaSDK", Level.INFO)
                            .add(builder.newAppenderRef("Stdout"))
                            .add(builder.newAppenderRef("rolling"))
                            .addAttribute("additivity", false));
                    builder.add(builder.newRootLogger(Level.INFO)
                            .add(builder.newAppenderRef("Stdout"))
                            .add(builder.newAppenderRef("rolling")));
                    ctx = Configurator.initialize(builder.build());
                    break;
            }
        }
    }
}
