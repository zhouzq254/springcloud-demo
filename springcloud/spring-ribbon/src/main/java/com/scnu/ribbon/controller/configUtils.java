package com.scnu.ribbon.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ConfigureUtil
{
  private static Logger LOGGER = LoggerFactory.getLogger(ConfigureUtil.class);

  private static Map<String, PropertiesConfiguration> map = new ConcurrentHashMap();

  private static PropertiesConfiguration getPropertiesConfiguration(String configPath)
  {
    PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration)map.get(configPath);
    if (propertiesConfiguration == null) {
      try {
        ClassPathResource resource = new ClassPathResource(configPath);
        boolean isExists = resource.exists();
        if (isExists) {
          URL url = resource.getURL();
          propertiesConfiguration = new PropertiesConfiguration();
          FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
          strategy.setRefreshDelay(30000L);
          propertiesConfiguration.setDelimiterParsingDisabled(true);
          propertiesConfiguration.setReloadingStrategy(strategy);
          propertiesConfiguration.load(url);
          map.put(configPath, propertiesConfiguration);
          LOGGER.info("Loading properties file from class path resource [{}]", configPath);
        } else {
          LOGGER.debug("Failed to Loading properties file from class path resource [{}]", configPath);
        }
      } catch (Exception e) {
        LOGGER.error("Loading properties file error", e);
      }
    }
    return propertiesConfiguration;
  }

  public static Properties loadAllProperties(String configPath) throws IOException {
    Properties properties = PropertiesLoaderUtils.loadAllProperties(configPath);
    return properties;
  }

  public static String getString(String configPath, String key)
  {
    PropertiesConfiguration propertiesConfiguration = getPropertiesConfiguration(configPath);
    String value = null;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getString(key);
    }
    return value;
  }

  public static String getValue(String configPath, String key, String defaultValue) {
    PropertiesConfiguration propertiesConfiguration = getPropertiesConfiguration(configPath);
    String value = defaultValue;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getString(key, defaultValue);
    }
    return value;
  }

  public static long getLong(String configPath, String key) {
    PropertiesConfiguration propertiesConfiguration = getPropertiesConfiguration(configPath);
    long value = 0L;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getLong(key);
    }
    return value;
  }

  public static long getLong(String configPath, String key, long defaultValue) {
    PropertiesConfiguration propertiesConfiguration = getPropertiesConfiguration(configPath);
    long value = defaultValue;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getLong(key, defaultValue);
    }
    return value;
  }

  public static int getInt(String configPath, String key) {
    PropertiesConfiguration propertiesConfiguration = getPropertiesConfiguration(configPath);
    int value = 0;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getInt(key);
    }
    return value;
  }

  public static int getInt(String configPath, String key, int defaultValue) {
    PropertiesConfiguration propertiesConfiguration = getPropertiesConfiguration(configPath);
    int value = defaultValue;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getInt(key, defaultValue);
    }
    return value;
  }

  public static boolean getBoolean(String configPath, String key) {
    PropertiesConfiguration propertiesConfiguration = getPropertiesConfiguration(configPath);
    boolean value = false;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getBoolean(key);
    }
    return value;
  }

  public static boolean getBoolean(String configPath, String key, boolean defaultValue) {
    PropertiesConfiguration propertiesConfiguration = getPropertiesConfiguration(configPath);
    boolean value = defaultValue;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getBoolean(key, defaultValue);
    }
    return value;
  }

  public static double getDouble(String configPath, String key) {
    PropertiesConfiguration propertiesConfiguration = getPropertiesConfiguration(configPath);
    double value = 0.0D;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getDouble(key);
    }
    return value;
  }

  public static double getDouble(String configPath, String key, double defaultValue) {
    PropertiesConfiguration propertiesConfiguration = getPropertiesConfiguration(configPath);
    double value = defaultValue;
    if (propertiesConfiguration != null) {
      value = propertiesConfiguration.getDouble(key, defaultValue);
    }
    return value;
  }
}