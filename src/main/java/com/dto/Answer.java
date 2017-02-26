package com.dto;

/**
 * Created by Suro Smith on 2/22/2017
 */
public class Answer {

    private String text;
    private Integer toQuestion;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getToQuestion() {
        return toQuestion;
    }

    public void setToQuestion(Integer toQuestion) {
        this.toQuestion = toQuestion;
    }
}
