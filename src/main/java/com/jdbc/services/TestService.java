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
            testMap.put(testUtil.ratings[i],
                    generateFinalQuestionsByCount(testUtil.ratings[i], questionsCountByRating[i]));
        }


        return new Test(testMap);
    }

    private List<Question> generateFinalQuestionsByCount(int rating, int countOfQuestions) {

        List<Question> chosenFromListByTopic = testUtil.chooseFromListByTopic(rating);
        List<Question> lastSorted = new ArrayList<>();
        generateQuestionsListByCount(countOfQuestions, lastSorted, (Question[]) chosenFromListByTopic.toArray());

        return lastSorted;
    }

    private void generateQuestionsListByCount(int countOfQuestion, List<Question> lastSorted, Question[] chosenFromListByTopic) {

        if (countOfQuestion > chosenFromListByTopic.length)
            throw new NoSuchElementException("there is no such questions in base");
        if (countOfQuestion == chosenFromListByTopic.length) {
            lastSorted.addAll(Arrays.asList(chosenFromListByTopic));
            return;
        }
        if (countOfQuestion > 1)
            generateQuestionsListByCount(Math.round(countOfQuestion / 2), lastSorted,
                Arrays.copyOf(chosenFromListByTopic, chosenFromListByTopic.length / 2));
        if (countOfQuestion == 1) {
            int r = random.nextInt(1);
            lastSorted.add(chosenFromListByTopic[r]);
        }
        if (countOfQuestion > 1)
            generateQuestionsListByCount(countOfQuestion / 2, lastSorted,
                Arrays.copyOfRange(chosenFromListByTopic, chosenFromListByTopic.length / 2, chosenFromListByTopic.length));
    }

    public void setTestTopics(String [] topics){
        testUtil.setTopics(topics);
    }


}
