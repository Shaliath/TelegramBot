package infra;

import infra.manager.ConfigManager;
import infra.manager.IConstants;
import infra.manager.ITestConfig;

import java.util.Optional;

public class Configuration {

    private static ITestConfig config;
    private static IConstants constants;

    private Configuration() {
    }

    public static ITestConfig getConfig() {
        if (config == null) {
            config = initConfiguration("conf/config.properties", ITestConfig.class);
        }
        return config;
    }

    public static IConstants getConstants() {
        if (constants == null) {
            constants = initConfiguration("conf/constants.properties", IConstants.class);
        }
        return constants;
    }

    private static <T> T initConfiguration(String configFile, Class<T> clazz, String prefix) {
        ConfigManager configManager = new ConfigManager();
        configManager
                .setConfigFilename(configFile)
                .init();
        return configManager.readConfigurationOf(clazz, Optional.ofNullable(prefix).orElse("cloud"));
    }

    private static <T> T initConfiguration(String configFile, Class<T> clazz) {
        return initConfiguration(configFile, clazz, System.getenv("ENVIRONMENT"));
    }

}