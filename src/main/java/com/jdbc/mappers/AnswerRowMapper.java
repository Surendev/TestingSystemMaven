package com.jdbc.mappers;

import com.dto.Answer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Atom on 2/26/2017.
 */
public class AnswerRowMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet resultSet, int i) throws SQLException {
        Answer answer = new Answer();
        answer.setText(resultSet.getString("text"));
        answer.setToQuestion(resultSet.getInt("to_question"));
        return answer;
    }
}
