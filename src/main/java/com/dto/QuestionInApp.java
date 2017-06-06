package com.dto;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by surik on 2/25/17
 */
public class QuestionInApp {

    private Integer questionId;
    private String question;
    private String topic;
    private Integer rating;
    private String rightAnswer;
    private String answer1;
    private String answer2;
    private String answer3;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
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
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswers(List<Answer> answers, String rightAnswer) throws UnsupportedEncodingException {
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            if (rightAnswer.equals(answer.getEncrypted())) {
                this.rightAnswer = answer.getText();
                this.answer1 = answer.getText();
            } else if (answer2 == null) {
                this.answer2 = answer.getText();
            } else if (answer3 == null) {
                this.answer3 = answer.getText();
            }
        }
    }
}
