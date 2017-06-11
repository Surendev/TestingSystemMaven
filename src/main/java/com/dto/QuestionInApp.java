package com.dto;

import com.application.utils.ConverteSymbols;
import com.application.utils.SecurityUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by surik on 2/25/17
 */
public class QuestionInApp {

    private Long questionId;
    private String question;
    private String topic;
    private Integer rating;
    private String rightAnswer;
    private String answer1;
    private String answer2;
    private String answer3;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

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
        return ConverteSymbols.converteFromHex(question);
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return ConverteSymbols.converteFromHex(answer1);
    }

    public String getAnswer2() {
        return ConverteSymbols.converteFromHex(answer2);
    }

    public String getAnswer3() {
        return ConverteSymbols.converteFromHex(answer3);
    }

    public void setAnswers(List<Answer> answers, String rightAnswer) throws UnsupportedEncodingException {
        for (Answer answer : answers) {
            if (rightAnswer.equals(answer.getEncrypted())) {
                this.rightAnswer = SecurityUtil.encrypt(answer.getText());
                this.answer1 = answer.getText();
            } else if (answer2 == null) {
                this.answer2 = answer.getText();
            } else if (answer3 == null) {
                this.answer3 = answer.getText();
            }
        }
    }

    @Override
    public String toString() {
        return "QuestionInApp{" +
                "questionId=" + questionId +
                ", question='" + question + '\'' +
                ", topic='" + topic + '\'' +
                ", rating=" + rating +
                ", rightAnswer='" + rightAnswer + '\'' +
                ", answer1='" + answer1 + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", answer3='" + answer3 + '\'' +
                '}';
    }
}
