package com.scnu.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractMessageExecutor <K, V>
{
    protected ThreadPoolExecutor threadPoolExecutor;
    public static final String POOLSIZE = "task.poolSize";
    public static final String QUEUECAPACITY = "task.queueCapacity";
    public static final String KEEPALIVETIME = "task.keepAliveTime";
    public static final String WATERMARK = "task.watermark";
    public static final String THREADNAMEPREFIX = "task.threadNamePrefix";

    public AbstractMessageExecutor()
    {
        int poolSize = 0;
        long keepAliveTime = 0;
        int queueCapacity = 0;
        int watermark = 0;
        String threadNamePrefix = "";
        this.threadPoolExecutor = null;//new BlockedThreadPoolExecutor(poolSize, poolSize * 2, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue(queueCapacity), new ThreadFactory(threadNamePrefix), new ThreadPoolExecutor.DiscardPolicy(), watermark);
    }

    public void dispatch(List<ConsumerRecord<K, V>> consumerRecords)
    {
        for (ConsumerRecord consumerRecord : consumerRecords)
            dispatch(consumerRecord);
    }

    public void dispatch(final ConsumerRecord<K, V> consumerRecord)
    {
        final String topic = consumerRecord.topic();
        final MessageExecutor messageExecutor = (MessageExecutor)getMessageExecutor().get(topic);
        if (messageExecutor != null)
            this.threadPoolExecutor.execute(new Runnable()
            {
                public void run()
                {
                    try {
                        messageExecutor.execute(Arrays.asList(new ConsumerRecord[] { consumerRecord }));
                    } catch (Exception e) {
                        log.error("execute error,topic:{},data:{}", new Object[] { topic, consumerRecord.value(), e });
                    }
                }
            });
    }

    public abstract Map<String, MessageExecutor<K, V>> getMessageExecutor();
}
