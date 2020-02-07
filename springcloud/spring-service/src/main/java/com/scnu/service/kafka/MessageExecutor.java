package com.scnu.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public abstract interface MessageExecutor<K, V> {
    public abstract void execute(List<ConsumerRecord<K, V>> paramList);
}
