package com.jdbc.services;

import com.application.utils.TestUtil;
import com.application.utils.TopicUtil;
import com.dto.Question;
import com.dto.Test;
import com.jdbc.dao.TestDAO;

import java.util.List;
import java.util.Random;

/**
 * Created by surik on 2/5/17
 */

public class TestService implements TestDAO{

    private TestUtil testUtil = new TestUtil();
    private Random random = new Random();

    private final int [] questionsCountByRating = {8, 3, 2};

    @Override
    public Test generateTest() {

        //TODO get all questions by all ratings and generate TEST
        //if need add new method


        return null;
    }

    private List<Question> generateFinalQuestionsOfTopic(int rating, int countOfQuestions,TopicUtil... topics){
        
        List<Question> choosenFromListByTopic = testUtil.chooseFromListByTopic(rating, topics);
        for (TopicUtil pair : topics){
            
        }
        
        return null;
    }


}
