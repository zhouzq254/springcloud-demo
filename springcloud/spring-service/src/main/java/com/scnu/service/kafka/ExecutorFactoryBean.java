package com.scnu.service.kafka;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

@Slf4j
public class ExecutorFactoryBean implements InitializingBean {

    private Map<String, MessageExecutor<String, String>> map;
    private String[] topics;


    public Map<String, MessageExecutor<String, String>> getMap()
    {
        return this.map;
    }

    public void setMap(Map<String, MessageExecutor<String, String>> map) {
        this.map = map;
    }

    public String[] getTopics() {
        return this.topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }


    public void afterPropertiesSet() throws Exception
    {
        if (this.map != null) {
            this.topics = ((String[])this.map.keySet().toArray(new String[this.map.size()]));
            log.info("topics:{}", new Object[] { JSON.toJSONString(this.topics) });
        }
    }
}
