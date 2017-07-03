package com.dto;

/**
 * Created by surik on 2/4/17
 */
public class Question {

    private Long id;
    private String question;
    private int rating;
    private String topic;
    private String answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

        return getRating() == question1.getRating() && getQuestion().equals(question1.getQuestion())
                && getTopic().equals(question1.getTopic()) && getAnswer().equals(question1.getAnswer());

    }

    @Override
    public String toString() {
        return "{" +
                " question='" + question + '\'' +
                ", rating=" + rating +
                ", topic='" + topic + '\'' +
                ", right answer='" + answer + '\'' +
                '}';
    }
}
