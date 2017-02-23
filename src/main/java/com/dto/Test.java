package com.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by surik on 2/5/17
 */
public class Test {

    private int fullRating;

    //Key - rating, Value - question
    private Map<Integer,List<Question>> questions;

    public Test(Map<Integer,List<Question>> questions){
        this.questions = questions;
        for (Integer pair : questions.keySet()){
            fullRating += questions.get(pair).size();
        }
    }

    public Map<Integer, List<Question>> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<Integer, List<Question>> questions) {
        this.questions = questions;
    }

    public int getFullRating() {
        return fullRating;
    }

    public void setFullRating(int fullRating) {
        this.fullRating = fullRating;
    }

    public List<Question> getQuestionsInApp(){
        List<Question> questionsInApp = new ArrayList<>();
        for (Integer pair : questions.keySet()){
            questionsInApp.addAll(questions.get(pair));
        }

        return questionsInApp;
    }

}
