package com.dto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by surik on 2/25/17
 */
public class QuestionInApp {

    private String question;
    private String topic;
    private Integer rating;
    private String rightAnswer;
    private String answer1;
    private String answer2;
    private String answer3;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(List<Answer> answers, String rightAnswer) throws UnsupportedEncodingException {
        for (Answer answer : answers) {
            if(rightAnswer.equals(answer.getEncrypted())){
                this.rightAnswer = answer.getText();
                answers.remove(answer);
                break;
            }
        }
        try {
            this.answer1 = answers.get(0).getText();
            this.answer2 = answers.get(1).getText();
            this.answer3 = answers.get(2).getText();
        }catch(IndexOutOfBoundsException ignored){
        }
    }
}
