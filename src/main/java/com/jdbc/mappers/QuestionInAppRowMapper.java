package com.jdbc.mappers;

import com.dto.QuestionInApp;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by surik on 2/26/17
 */
public class QuestionInAppRowMapper implements RowMapper<QuestionInApp>{
    @Override
    public QuestionInApp mapRow(ResultSet resultSet, int i) throws SQLException {
        QuestionInApp question = new QuestionInApp();
        question.setQuestion(resultSet.getString("question"));
        return null;
    }
}
