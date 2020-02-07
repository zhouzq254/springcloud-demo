package com.scnu.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
public final class ConsumerListener extends AbstractMessageExecutor<String, String>
        implements MessageListener<String, String>
{

    @Resource(name="messageExecutorMap")
    private Map<String, MessageExecutor<String, String>> messageExecutorMap;

    public void onMessage(ConsumerRecord<String, String> consumerRecord)
    {
        String value = (String)consumerRecord.value();
        if (StringUtils.isNotBlank(value))
            super.dispatch(consumerRecord);
        else
            log.warn("topic:{},value is blank", new Object[] { consumerRecord.topic() });
    }

    public Map<String, MessageExecutor<String, String>> getMessageExecutor()
    {
        return this.messageExecutorMap;
    }
}
