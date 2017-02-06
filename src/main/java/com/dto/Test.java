package com.dto;

import java.util.Map;

/**
 * Created by surik on 2/5/17
 */
public class Test {

    private int fullRating;

    //Key - rating, Value question
    private Map<Integer,Question> questions;

    public Test(Map<Integer,Question> questions){
        this.questions = questions;
        Object [] keySet = questions.keySet().toArray();
        for (int i=0;i<questions.size();i++){
            fullRating +=(int) keySet[i];
        }
    }

    public Map<Integer, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<Integer, Question> questions) {
        this.questions = questions;
    }

    public int getFullRating() {
        return fullRating;
    }

    public void setFullRating(int fullRating) {
        this.fullRating = fullRating;
    }

}
