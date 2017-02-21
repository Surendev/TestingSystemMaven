package com.dto;

/**
 * Created by surik on 2/4/17
 */
public class Question {

    private String question;
    private int rating;
    private String topic;
    private String answer;
    private String answer_1;
    private String answer_2;
    private String answer_3;


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

    public String getAnswer_1() {return answer_1;}

    public void setAnswer_1(String answer_1) {this.answer_1 = answer_1; }

    public String getAnswer_2() {return answer_2; }

    public void setAnswer_2(String answer_2) {this.answer_2 = answer_2; }

    public String getAnswer_3() {return answer_3; }

    public void setAnswer_3(String answer_3) {this.answer_3 = answer_3; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question1 = (Question) o;

        if (getRating() != question1.getRating()) return false;
        if (!getQuestion().equals(question1.getQuestion())) return false;
        if (!getTopic().equals(question1.getTopic())) return false;
        if (!getAnswer_1().equals(question1.getAnswer_1())) return false;
        if (!getAnswer_2().equals(question1.getAnswer_2())) return false;
        if (!getAnswer_3().equals(question1.getAnswer_3())) return false;

        return getAnswer().equals(question1.getAnswer());
    }

    @Override
    public String toString() {
        return "{" +
                " question='" + question + '\'' +
                ", rating=" + rating +
                ", topic='" + topic + '\'' +
                ", right answer='" + answer + '\'' +
                ", answer_1='" + answer_1 + '\'' +
                ", answer_2='" + answer_2 + '\'' +
                ", answer_3='" + answer_3 + '\'' +
                '}';
    }
}
