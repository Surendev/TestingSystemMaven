package com.application.utils;

import com.dto.Question;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.services.QuestionsService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtil{

    public final int[] ratings;
    private QuestionsDAO questionsService;

    // here is saving all questions by ratings
    private Map<Integer, List<Question>> questionsFromDB = new HashMap<>();

    public TestUtil() {
        String[] ratingsArr = ConfigsLoader.getInstance().getProperties().getProperty("test.ratings").split(",");
        int[] ratings = new int[ratingsArr.length];
        for (int i = 0; i < ratingsArr.length; i++) {
            ratings[i] = Integer.valueOf(ratingsArr[i]);
        }
        this.ratings = ratings;
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring_context.xml");
        questionsService = context.getBean("questionsService", QuestionsService.class);
        fillQuestionsFromDB();
    }

    private void fillQuestionsFromDB() {

        for (Integer rating : ratings) {
            questionsFromDB.put(rating, questionsService.getQuestionsByRating(rating));
        }
    }

    public List<Question> chooseFromListByTopics(int rating) {
        List<Question> questionsOfTopic = new ArrayList<>();

        for (TopicUtil eachTopic : TopicUtil.topics) {

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
