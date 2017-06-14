package com.jdbc.services;

import com.application.utils.ConfigsLoader;
import com.application.utils.TestUtil;
import com.dto.Question;
import com.dto.Test;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.dao.TestDAO;

import java.util.*;

/**
 * Created by surik on 2/5/17
 */

public class TestService implements TestDAO {

    private TestUtil testUtil = new TestUtil();
    private Random random = new Random();

    private final int[] questionsCountByRating;

    public TestService() {
        String [] countsArr = ConfigsLoader.getInstance().getProperties().getProperty("test.countOfRatings").split(",");
        int[] counts = new int[countsArr.length];
        for (int i = 0;i < countsArr.length;++i){
            counts[i] = Integer.valueOf(countsArr[i]);
        }
        questionsCountByRating = counts;
    }

    @Override
    public Test generateTest(QuestionsDAO questionsService) {
        Map<Integer, List<Question>> testMap = new TreeMap<>();
        for (int i = 0; i < questionsCountByRating.length; i++){
            testMap.put(testUtil.ratings[i],
                    generateFinalQuestionsByCount(testUtil.ratings[i], questionsCountByRating[i]));
        }
        return new Test(testMap,questionsService);
    }

    private List<Question> generateFinalQuestionsByCount(int rating, int countOfQuestions) {

        List<Question> chosenFromListByTopic = testUtil.chooseFromListByTopics(rating);
        List<Question> lastSorted = new ArrayList<>();
        generateQuestionsListByCount(countOfQuestions, lastSorted, chosenFromListByTopic);

        return lastSorted;
    }

    private void generateQuestionsListByCount(double countOfQuestion, List<Question> lastSorted, List<Question> chosenFromListByTopic) {

        if (countOfQuestion > chosenFromListByTopic.size())
            throw new NoSuchElementException("there is no such questions in base");
        if (countOfQuestion == chosenFromListByTopic.size()) {
            lastSorted.addAll(chosenFromListByTopic);
            return;
        }
        if (countOfQuestion > 1)
            generateQuestionsListByCount(Math.round(countOfQuestion / 2), lastSorted,
                chosenFromListByTopic.subList(0,chosenFromListByTopic.size() / 2) );
        if (countOfQuestion == 1) {
            int r = random.nextInt(chosenFromListByTopic.size());
            lastSorted.add(chosenFromListByTopic.get(r));
        }
        if (countOfQuestion > 1)
            generateQuestionsListByCount(countOfQuestion / 2, lastSorted,
                chosenFromListByTopic.subList(chosenFromListByTopic.size() / 2, chosenFromListByTopic.size()) );
    }


}
