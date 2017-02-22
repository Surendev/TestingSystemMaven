package com.jdbc.services;

import com.dto.Question;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.mappers.QuestionRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.jws.WebMethod;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created by surik on 2/6/17
 */
public class QuestionsService implements QuestionsDAO {
    
    private JdbcTemplate jdbc;

    public void setDataSource(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Question> getQuestionsByRating(int rating) {
        String query = "SELECT * FROM questions WHERE rating=?";
        return jdbc.query(query,new Object[]{rating},new QuestionRowMapper());
    }

    @Override
    public String addNewQuestion(Object... params) {
        //TODO insert question info to db
        return null;
    }



}
