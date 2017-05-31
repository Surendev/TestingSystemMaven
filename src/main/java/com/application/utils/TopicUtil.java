package com.application.utils;

/**
 * Created by Atom on 2/10/2017
 */
public enum TopicUtil {

    TOPIC_1("ԱՐԱՏՈՐՈՇՈՒՄ"), TOPIC_2("TOPIC_2"), TOPIC_3("TOPIC_3"), TOPIC_4("TOPIC_4"), TOPIC_5("TOPIC_5");

    private  String topic;

    TopicUtil(String topic) {
        this.topic = topic;
    }

    public String getTopic(){
        return topic;
    }
}
