package com.jdbc.services;

import com.application.utils.TestUtil;
import com.application.utils.TopicUtil;
import com.dto.Question;
import com.dto.Test;
import com.jdbc.dao.TestDAO;

import java.util.*;

/**
 * Created by surik on 2/5/17
 */

public class TestService implements TestDAO {

    private TestUtil testUtil = new TestUtil();
    private Random random = new Random();

    private final int[] questionsCountByRating = {8, 3, 2};

    @Override
    public Test generateTest() {

        //TODO get all questions by all ratings and generate TEST
        //if need add new method
        Map<Integer, List<Question>> testMap = new TreeMap<>();
        for (int i = 1; i < questionsCountByRating.length; i++){
            testMap.put(testUtil.ratings[i], generateFinalQuestionsByCount(testUtil.ratings[i], questionsCountByRating[i]));
        }


        return new Test(testMap);
    }

    private List<Question> generateFinalQuestionsByCount(int rating, int countOfQuestions) {

        List<Question> choosenFromListByTopic = testUtil.chooseFromListByTopic(rating);
        List<Question> lastSorted = new ArrayList<>();
        generateQuestionsListByCount(countOfQuestions, lastSorted, (Question[]) choosenFromListByTopic.toArray());

        return lastSorted;
    }

    private void generateQuestionsListByCount(int countOfQuestion, List<Question> lastSorted, Question[] choosenFromListByTopic) {

        if (countOfQuestion > choosenFromListByTopic.length)
            throw new NoSuchElementException("there is no such questions in base");
        if (countOfQuestion == choosenFromListByTopic.length) {
            lastSorted.addAll(Arrays.asList(choosenFromListByTopic));
            return;
        }
        if (countOfQuestion > 1) generateQuestionsListByCount(Math.round(countOfQuestion / 2), lastSorted,
                Arrays.copyOf(choosenFromListByTopic, choosenFromListByTopic.length / 2));
        if (countOfQuestion == 1) {
            int r = random.nextInt(1);
            lastSorted.add(choosenFromListByTopic[r]);
        }
        if (countOfQuestion > 1) generateQuestionsListByCount(countOfQuestion / 2, lastSorted,
                Arrays.copyOfRange(choosenFromListByTopic, choosenFromListByTopic.length / 2, choosenFromListByTopic.length));
    }


}
