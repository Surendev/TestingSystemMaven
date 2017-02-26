package com.jdbc.dao;

import com.dto.Answer;
import com.dto.Question;

import java.util.List;

/**
 * Created by surik on 2/6/17
 */
public interface QuestionsDAO {

    List<Question> getQuestionsByRating(int rating);

    String addNewQuestion(String question, int rating,String topic, String rightAnswer, String... answers);

    List<Answer> getAnswersByQuestionId(int id);
}
