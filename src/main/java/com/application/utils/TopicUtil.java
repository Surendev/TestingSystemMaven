package com.application.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Atom on 2/10/2017
 */
public class TopicUtil {

    public static List<TopicUtil> topics = new ArrayList<>();

    static {
        Arrays.stream(ConfigsLoader.getInstance().getProperties().getProperty("test.topics").split(","))
                .forEach(s -> topics.add(new TopicUtil(s)));
    }
    private  String topic;

    TopicUtil(String topic) {
        this.topic = topic;
    }

    public String getTopic(){
        return topic;
    }
}
