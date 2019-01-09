package com.scnu.ribbon.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtil
{
  private static Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

  private static Map<String, PropertiesConfiguration> map = new ConcurrentHashMap();

  private static void init(Config config)
  {
    String name = config.name();
    String file = config.getFile();
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(name);
    if (propertiesConfiguration == null)
      try {
        ClassPathResource resource = new ClassPathResource(file);
        boolean isExists = resource.exists();
        if (isExists) {
          URL url = resource.getURL();
          propertiesConfiguration = new PropertiesConfiguration();
          FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
          strategy.setRefreshDelay(30000L);
          propertiesConfiguration.setDelimiterParsingDisabled(true);
          propertiesConfiguration.setReloadingStrategy(strategy);
          propertiesConfiguration.load(url);
          map.put(name, propertiesConfiguration);
          LOGGER.info("Loading properties file from class path resource [{}]", file);
        } else {
          LOGGER.debug("Failed to Loading properties file from class path resource [{}]", file);
        }
      } catch (Exception e) {
        LOGGER.error("Loading properties file error", e);
      }
  }

  public static Properties loadAllProperties(Config config) throws IOException
  {
    String filePath = config.getFile();
    Properties properties = PropertiesLoaderUtils.loadAllProperties(filePath);
    return properties;
  }

  public static Properties loadAllProperties(String filePath) throws IOException {
    Properties properties = PropertiesLoaderUtils.loadAllProperties(filePath);
    return properties;
  }

  public static String getString(String path, String key) {
    PropertiesConfiguration propertiesConfiguration = null;
    try {
      propertiesConfiguration = new PropertiesConfiguration(path);
    } catch (ConfigurationException e) {
      LOGGER.error("new  PropertiesConfiguration failed! path:{}", path, e);
    }
    String value = null;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getString(key);
    }
    return value;
  }

  public static String getString(Config config, String key) {
    String fileName = config.name();
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(fileName);
    String value = null;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getString(key);
    }
    return value;
  }

  public static String getValue(Config config, String key, String defaultValue) {
    String fileName = config.name();
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(fileName);
    String value = defaultValue;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getString(key, defaultValue);
    }
    return value;
  }

  public static long getLong(Config config, String key) {
    String fileName = config.name();
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(fileName);
    long value = 0L;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getLong(key);
    }
    return value;
  }

  public static long getLong(Config config, String key, long defaultValue) {
    String fileName = config.name();
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(fileName);
    long value = defaultValue;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getLong(key, defaultValue);
    }
    return value;
  }

  public static int getInt(Config config, String key) {
    String fileName = config.name();
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(fileName);
    int value = 0;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getInt(key);
    }
    return value;
  }

  public static int getInt(Config config, String key, int defaultValue) {
    String fileName = config.name();
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(fileName);
    int value = defaultValue;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getInt(key, defaultValue);
    }
    return value;
  }

  public static boolean getBoolean(Config config, String key) {
    String fileName = config.name();
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(fileName);
    boolean value = false;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getBoolean(key);
    }
    return value;
  }

  public static boolean getBoolean(Config config, String key, boolean defaultValue) {
    String fileName = config.name();
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(fileName);
    boolean value = defaultValue;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getBoolean(key, defaultValue);
    }
    return value;
  }

  public static double getDouble(Config config, String key) {
    String fileName = config.name();
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(fileName);
    double value = 0.0D;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getDouble(key);
    }
    return value;
  }

  public static double getDouble(Config config, String key, double defaultValue) {
    String fileName = config.name();
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(fileName);
    double value = defaultValue;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getDouble(key, defaultValue);
    }
    return value;
  }

  static
  {
    Config[] configs = Config.values();
    for (Config config : configs)
      init(config);
  }

  public static enum Config
  {
    configFilePath("conf/config.properties"), 

    producerConfigFilePath("conf/producer.properties"), 
    consumerConfigFilePath("conf/consumer.properties"), 
    elasticsearchConfigFilePath("conf/elasticsearch.properties"), 
    greenplumConfigFilePath("conf/greenplum.properties"), 
    canalConfigFilePath("conf/canal.properties"), 
    cassandraConfigFilePath("conf/cassandra.properties"), 
    jettyConfigFilePath("conf/jetty.properties"), 

    threadPoolConfigFilePath("conf/threadpool.properties"), 
    commonConfigFilePath("conf/commonConfig.properties"), 
    schedulerConfigFilePath("conf/scheduler.properties"), 
    distributedLockConfigFilePath("conf/distributedLock.properties"), 
    guavaCacheConfigFilePath("conf/guavaCacheConfig.properties"), 
    timerCacheConfigFilePath("conf/timerCacheConfig.properties"), 

    dataServiceConfigFilePath("conf/dataServiceConfig.properties"), 
    sftpConfigFilePath("conf/sftpConfig.properties");

    private String file;

    private Config(String file) {
      this.file = file;
    }

    public String getFile() {
      return this.file;
    }
  }
}