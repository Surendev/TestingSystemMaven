package com.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by surik on 2/5/17
 */
public class Test {

    private int index;

    private List<Question> questions = new ArrayList<>();
    private List<Answer> wrongAnswers;
    private Map<Integer,Integer> studentAnswers = new HashMap<>();

    public Test(Map<Integer,List<Question>> questions){
        for (Integer pair : questions.keySet()) {
            this.questions.addAll(questions.get(pair));
        }
        //TODO fill wrong answers from db
    }

    public QuestionInApp getQuestion(int currIndex) {
        index = currIndex-1;
        //TODO transform Question to QuestionInApp
        return null;
    }

    public void markAnswerToQuestion(int answerIndex){
        //todo mark answer in studentAnswers
    }
}
