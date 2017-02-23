package com.application.utils;

import com.dto.Question;
import com.dto.Test;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.services.QuestionsService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * Created by surik on 2/6/17
 */
public class TestUtil {

    public final int[] ratings = {1, 2, 3, 4};
    private QuestionsDAO questionsService;
    private TopicUtil[] topics;

    // here is saving all questions by ratings
    private Map<Integer, List<Question>> questionsFromDB;

    private ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("/spring_context.xml");

    public TestUtil() {
        questionsService = context.getBean("questionsDAO", QuestionsService.class);
        fillQuestionsFromDB();
    }

    private void fillQuestionsFromDB() {

        for (Integer rating : ratings) {
            questionsFromDB.put(rating, questionsService.getQuestionsByRating(rating));
        }
    }

    public List<Question> chooseFromListByTopic(int rating) {
        List<Question> questionsOfTopic = new ArrayList<>();

        for (TopicUtil eachTopic : topics) {

            String topic = eachTopic.getTopic();

            for (Question eachQuestion : questionsFromDB.get(rating)) {
                if (eachQuestion.getTopic().equals(topic)) {
                    questionsOfTopic.add(eachQuestion);
                }
            }
        }

        return questionsOfTopic;
    }

    public void setTopics(String [] topics){

        this.topics = new TopicUtil[topics.length];

        for (int i=0;i<topics.length;i++){
            this.topics[i] = TopicUtil.valueOf(topics[i]);
        }
    }

}
