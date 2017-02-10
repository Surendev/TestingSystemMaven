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

    public final int[] ratings = {1, 2, 3};
    private QuestionsDAO questionsService;

    // here is saving all questions by ratings
    private Map<Integer, List<Question>> questionsFromDB;

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring_context.xml");

    public TestUtil() {
        questionsService = context.getBean("questionsDAO", QuestionsService.class);
        fillQuestionsFromDB();
    }

    private void fillQuestionsFromDB() {
        //TODO get questions by all ratings
        for (Integer rating : ratings) {
            questionsFromDB.put(rating, questionsService.getQuestionsByRating(rating));
        }
    }

    public List<Question> chooseFromListByTopic(int rating, TopicUtil... topicArray) {
        //TODO choose questions by topic
        List<Question> questionsOfTopic = new ArrayList<>();

        for (TopicUtil eachTopic : topicArray) {
            String topic = eachTopic.getTopic();
            for (Question eachQuestion : questionsFromDB.get(rating)) {
                if (eachQuestion.getTopic().equals(topic)) {
                    questionsOfTopic.add(eachQuestion);
                }
            }
        }

        return questionsOfTopic;
    }

}
