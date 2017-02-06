package com.application.utils;

import com.dto.Question;
import com.dto.Test;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.services.QuestionsService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * Created by surik on 2/6/17
 */
public class TestUtil {

    private final int [] ratings = { 1, 2, 3 };
    private QuestionsDAO questionsService;

    // here is saving all questions by ratings
    private Map< Integer,List<Question> > questionsFromDB;

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring_context.xml");
    public TestUtil(){
        questionsService = context.getBean("questionsDAO", QuestionsService.class);
        fillQuestionsFromDB();
    }

    private void fillQuestionsFromDB(){
        //TODO get questions by all ratings

    }

    public Map<Integer, Question> chooseFromListByTopic(String topic){
        //TODO choose questions by topic

        return null;
    }

}
