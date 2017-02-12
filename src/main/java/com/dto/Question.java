package com.dto;

/**
 * Created by surik on 2/4/17
 */
public class Question {

    private String question;
    private int rating;
    private String topic;
    private String answer;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question1 = (Question) o;

        if (getRating() != question1.getRating()) return false;
        if (!getQuestion().equals(question1.getQuestion())) return false;
        if (!getTopic().equals(question1.getTopic())) return false;
        return getAnswer().equals(question1.getAnswer());
    }

    @Override
    public String toString() {
        return "{" +
                " question='" + question + '\'' +
                ", rating=" + rating +
                ", topic='" + topic + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
