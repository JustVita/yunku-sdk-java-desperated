import com.yunkuent.sdk.YKConfig;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.net.URI;

/**
 * Created by qp on 2017/2/17.
 */
@Plugin(name = "CustomConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(50)
public class CustomConfigurationFactory extends ConfigurationFactory {

    static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {

        if (YKConfig.PRINT_LOG){
            builder.setConfigurationName(name);
            builder.setStatusLevel(Level.INFO);
            builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL).addAttribute("level", Level.INFO));
            switch (YKConfig.PRINT_LOG_TYPE){
                case YKConfig.PRINT_LOG_TO_CONSOLE:
                    AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE")
                            .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
                    appenderBuilder.add(builder.newLayout("PatternLayout")
                            .addAttribute("pattern", YKConfig.LOG_PRINT_PATTERN));
                    builder.add(appenderBuilder);
                    builder.add(builder.newLogger("YunkuJavaSDK", Level.INFO).add(builder.newAppenderRef("Stdout"))
                            .addAttribute("additivity", false));
                    builder.add(builder.newRootLogger(Level.INFO).add(builder.newAppenderRef("Stdout")));
                    break;
                case YKConfig.PRINT_LOG_IN_FILE:
                    AppenderComponentBuilder appenderFileBuilder = builder.newAppender("Stdout", "CONSOLE")
                            .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
                    appenderFileBuilder.add(builder.newLayout("PatternLayout")
                            .addAttribute("pattern", YKConfig.LOG_PRINT_PATTERN));
                    builder.add(appenderFileBuilder);
                    LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout")
                            .addAttribute("pattern", YKConfig.LOG_PRINT_PATTERN);
                    ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
                            .addComponent(builder.newComponent("CronTriggeringPolicy")
                                    .addAttribute("schedule", "0 0 0 * * ?"))
                            .addComponent(builder.newComponent("SizeBasedTriggeringPolicy")
                                    .addAttribute("size", YKConfig.getPrintLogFileSize()));
                    ComponentBuilder defaultRolloverStrategy = builder.newComponent("DefaultRolloverStrategy")
                            .addAttribute("fileIndex","min")
                            .addAttribute("min",1)
                            .addAttribute("max",1024);
                    appenderFileBuilder = builder.newAppender("rolling", "RollingFile")
                            .addAttribute("fileName", "target/YunkuJavaSDK.log")
                            .addAttribute("filePattern", "target/YunkuJavaSDK-%d{yyyy-MM}-%i.log")
                            .add(layoutBuilder)
                            .addComponent(triggeringPolicy)
                            .addComponent(defaultRolloverStrategy);
                    builder.add(appenderFileBuilder);
                    builder.add(builder.newLogger("YunkuJavaSDK", Level.INFO).add(builder.newAppenderRef("Stdout"))
                            .add(builder.newAppenderRef("rolling")).addAttribute("additivity", false));
                    builder.add(builder.newRootLogger(Level.INFO).add(builder.newAppenderRef("Stdout"))
                            .add(builder.newAppenderRef("rolling")));
                    break;
            }
        }
        return builder.build();
    }
    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[] {"*"};
    }

}

