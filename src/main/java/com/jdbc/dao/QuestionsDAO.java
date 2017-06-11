package com.jdbc.dao;

import com.dto.Answer;
import com.dto.Question;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by surik on 2/6/17
 */
public interface QuestionsDAO {

    List<Question> getQuestionsByRating(int rating);

    void addOrUpdateQuestion(boolean update, String id, String question, Integer rating, String topic, String rightAnswer, String... answers) throws UnsupportedEncodingException, SQLException;


    List<Answer> getAnswersByQuestionId(Long id);

    Map<Question,List<Answer>> getAllQuestions();

    void deleteQuestion(Long questionId) throws SQLException;

    Integer getQuestionIdByRightAnswer(String answer);
}
