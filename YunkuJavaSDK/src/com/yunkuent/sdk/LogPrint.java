package com.yunkuent.sdk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.util.logging.Level;

import static com.yunkuent.sdk.DebugConfig.*;

/**
 * 输出日志   System.out.println
 */
class LogPrint {

    private static final String INFO = "info";

    private static final String ERROR = "error";

    private static final String WARN = "warn";


    static {
        logConfiguration();
    }

    public static void print(String log, Class<?> clazz) {
        print(Level.INFO, log, clazz);
    }

    public static void print(Level level, String log, Class<?> clazz) {
        if (DebugConfig.PRINT_LOG) {
            Logger logger = LogManager.getLogger(clazz);
            switch (level.toString()) {
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

    /**
     * 日志打印配置信息
     */
    private static void logConfiguration() {
        if (DebugConfig.PRINT_LOG) {
            ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
            builder.setStatusLevel(org.apache.logging.log4j.Level.INFO);
            builder.setConfigurationName("RollingBuilder");
            AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE")
                    .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
            appenderBuilder.add(builder.newLayout("PatternLayout")
                    .addAttribute("pattern", LOG_PRINT_PATTERN));
            builder.add(appenderBuilder);

            switch (PRINT_LOG_TYPE) {
                case PRINT_LOG_TO_CONSOLE:
                    builder.add(builder.newLogger("YunKuJavaSDK", org.apache.logging.log4j.Level.INFO)
                            .add(builder.newAppenderRef("Stdout")));
                    builder.add(builder.newRootLogger(org.apache.logging.log4j.Level.INFO)
                            .add(builder.newAppenderRef("Stdout")));
                    Configurator.initialize(builder.build());
                    break;
                case PRINT_LOG_IN_FILE:
                    LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout")
                            .addAttribute("pattern", LOG_PRINT_PATTERN);
                    ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
                            .addComponent(builder.newComponent("CronTriggeringPolicy").addAttribute("schedule", "0 0 0 * * ?"))
                            .addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", PRINT_LOG_FILE_SIZE));
                    ComponentBuilder defaultRolloverStrategy = builder.newComponent("DefaultRolloverStrategy")
                            .addAttribute("fileIndex", "min")
                            .addAttribute("min", 1)
                            .addAttribute("max", 1024);
                    appenderBuilder = builder.newAppender("rolling", "RollingFile")
                            .addAttribute("fileName", "target/YunkuJavaSDK.log")
                            .addAttribute("filePattern", "target/YunkuJavaSDK-%d{yyyy-MM}-%i.log")
                            .add(layoutBuilder)
                            .addComponent(triggeringPolicy)
                            .addComponent(defaultRolloverStrategy);
                    builder.add(appenderBuilder);
                    builder.add(builder.newLogger("YunKuJavaSDK", org.apache.logging.log4j.Level.INFO)
                            .add(builder.newAppenderRef("Stdout"))
                            .add(builder.newAppenderRef("rolling"))
                            .addAttribute("additivity", false));
                    builder.add(builder.newRootLogger(org.apache.logging.log4j.Level.INFO)
                            .add(builder.newAppenderRef("Stdout"))
                            .add(builder.newAppenderRef("rolling")));
                    Configurator.initialize(builder.build());
                    break;
            }
        }
    }

}
