package com.jdbc.mappers;

import com.dto.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by surik on 2/5/17
 */
public class QuestionRowMapper implements RowMapper<Question> {

    @Override
    public Question mapRow(ResultSet resultSet, int i) throws SQLException {
        Question question = new Question();
        question.setId(resultSet.getLong("id"));
        question.setQuestion(resultSet.getString("question"));
        question.setTopic(resultSet.getString("topic"));
        question.setAnswer(resultSet.getString("answer"));
        question.setRating(resultSet.getInt("rating"));
        return question;
    }

}
