package com.jdbc.services;

import com.dto.Answer;
import com.dto.Question;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.mappers.QuestionRowMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.jws.WebMethod;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
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
    public String addNewQuestion(String question, int rating,String topic, String rightAnswer, String... answers) {
        String questionQuery = "INSERT INTO questions ( question,  rating,  topic,  answer) VALUES (?, ?, ?, ?)";
        String answersQuery = "INSERT INTO answers (text, to_question) VALUES (?,?)";
        int questionId = jdbc.update(questionQuery, question, rating,topic,rightAnswer);
        for (String answer :  answers) {
            jdbc.update(answersQuery, answer, questionId);
        }
        return (questionId + "-րդ հարցը հաջողությամբ ներմուծված է");
    }


}
