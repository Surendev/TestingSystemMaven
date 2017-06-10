package com.jdbc.dao;

import com.dto.Answer;
import com.dto.Question;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by surik on 2/6/17
 */
public interface QuestionsDAO {

    List<Question> getQuestionsByRating(int rating);

    boolean deleteQuestionById(String id);

    int addOrUpdateQuestion(boolean update, String id, String question, Integer rating, String topic, String rightAnswer, String... answers) throws UnsupportedEncodingException;

    List<Answer> getAnswersByQuestionId(int id);

    Map<Question,List<Answer>> getAllQuestions();
}
