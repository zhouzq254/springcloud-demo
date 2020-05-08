package com.scnu.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class SpringKafkaProducer<K,T> {

    private static final String TAG = "[SpringKafkaProducer]=>";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(final String topic, final String message)
    {
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>()
        {
            public void onSuccess(SendResult<String, String> message)
            {
                log.debug("[SpringKafkaProducer]=> Sent message= " + (String)message.getProducerRecord().value() + "with topic= " + message.getProducerRecord().topic() + " and offset= " + message.getRecordMetadata().offset());
            }

            public void onFailure(Throwable throwable)
            {
                SpringKafkaProducer.log.error("[SpringKafkaProducer]=> Unable to send message= {} with topic= {}", new Object[] { message, topic, throwable.getMessage() });
            }
        });
    }

    public void send(final String topic, String key, final String message)
    {
        ListenableFuture future = this.kafkaTemplate.send(topic, key, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>()
        {
            public void onSuccess(SendResult<String, String> message)
            {
                SpringKafkaProducer.log.debug("[SpringKafkaProducer]=> Sent message= " + (String)message.getProducerRecord().value() + "with topic= " + message.getProducerRecord().topic() + " and offset= " + message.getRecordMetadata().offset());
            }

            public void onFailure(Throwable throwable)
            {
                SpringKafkaProducer.log.error("[SpringKafkaProducer]=>Unable to send message= {} with topic= {}", new Object[] { message, topic, throwable.getMessage() });
            }
        });
    }
}
