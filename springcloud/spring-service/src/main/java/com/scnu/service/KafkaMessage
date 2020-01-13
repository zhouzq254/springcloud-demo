package com.scnu.service.kafka;

import com.alibaba.fastjson.JSONObject;

public class KafkaMessage {
    private String topic;
    private String key;
    private String groupId;
    private JSONObject value;

    public KafkaMessage()
    {
    }

    public KafkaMessage(String topic, String key, String groupId, JSONObject value)
    {
        this.topic = topic;
        this.key = key;
        this.groupId = groupId;
        this.value = value;
    }

    public String getTopic()
    {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public JSONObject getValue() {
        return this.value;
    }

    public void setValue(JSONObject value) {
        this.value = value;
    }
}
