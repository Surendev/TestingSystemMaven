package com.jdbc.services;

import com.application.utils.SecurityUtil;
import com.dto.Answer;
import com.dto.Question;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.mappers.AnswerRowMapper;
import com.jdbc.mappers.QuestionRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QuestionsService implements QuestionsDAO {

    private JdbcTemplate jdbc;

    public void setDataSource(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Question> getQuestionsByRating(int rating) {
        String query = "SELECT * FROM questions WHERE rating=?";
        return jdbc.query(query, new Object[]{rating}, new QuestionRowMapper());
    }

    public void addOrUpdateQuestion(boolean update, String id, String question,
                                    Integer rating, String topic, String newRightAnswer, String... answers)
            throws UnsupportedEncodingException, SQLException {
        StringBuilder query;
        int questionId;
        if (!update) {
            query = new StringBuilder("INSERT INTO questions(question,rating,topic,answer) VALUES(?,?,?,?)");
            jdbc.update(query.toString(), question, rating, topic, SecurityUtil.encrypt(newRightAnswer));
            query = new StringBuilder("INSERT INTO answers(text,to_question) VALUES(?,?)");
            questionId = this.getQuestionIdByRightAnswer(SecurityUtil.encrypt(newRightAnswer));
            for (String each : answers) {
                jdbc.update(query.toString(), each, questionId);
            }
            jdbc.update(query.toString(), newRightAnswer, questionId);
        } else {

            if(question.isEmpty() && rating == 0 && topic.isEmpty()){
                questionId = Integer.valueOf(id);
            }else{
                query = new StringBuilder("UPDATE questions SET ");
                query.append(question.equals("") ? "" : "question='" + question + "'")
                        .append(rating == 0 ? "" : "rating='" + rating + "'")
                        .append(topic.equals("") ? "" : "topic='" + topic + "'")
                        .append(" WHERE id=").append(id);

                questionId = jdbc.update(query.toString());
            }

            if(!newRightAnswer.isEmpty() && !answers[0].isEmpty() && !answers[1].isEmpty()){
                query = new StringBuilder("UPDATE questions SET answer = ? WHERE id=?");
                jdbc.update(String.valueOf(query), SecurityUtil.encrypt(newRightAnswer),questionId);
                for (int i = 0; i < 3; i++) {
                    jdbc.update("DELETE FROM answers WHERE to_question=?", questionId);
                }
                query = new StringBuilder("INSERT INTO answers(text,to_question) VALUES(?,?)");
                jdbc.update(String.valueOf(query), newRightAnswer,questionId);
                jdbc.update("INSERT INTO answers(text, to_question) VALUES(?,?)", answers[0], questionId);
                jdbc.update("INSERT INTO answers(text, to_question) VALUES(?,?)", answers[1], questionId);
            }


        }

    }

    @Override
    public List<Answer> getAnswersByQuestionId(Long id) {
        String query = "SELECT * FROM answers WHERE to_question=?";
        return jdbc.query(query, new Object[]{id}, new AnswerRowMapper());
    }

    @Override
    public Map<Question, List<Answer>> getAllQuestions() {
        String query = "SELECT * FROM questions";
        Map<Question, List<Answer>> result = new HashMap<>();

        List<Question> questions = jdbc.query(query, new QuestionRowMapper());
        for (Question eachQuestion : questions) {
            result.put(eachQuestion, getAnswersByQuestionId(eachQuestion.getId()));
        }

        if (result.isEmpty()) {
            return null;
        }

        return result;
    }

    @Override
    public void deleteQuestion(Long questionId) throws SQLException {
        final String query = "DELETE FROM questions WHERE id = ?";
        if(jdbc.update(query,questionId) <= 0){
            throw new SQLException("Չի ստացվել ջնջել հարցը ID: " + questionId);
        }
    }

    @Override
    public Integer getQuestionIdByRightAnswer(String answer){
        if(answer.isEmpty() || answer.length()!=64){
            return null;
        }
        final String query = "SELECT id FROM questions WHERE answer=?";

        return jdbc.queryForObject(query,new Object[]{answer},(resultSet, i) -> resultSet.getInt("id"));
    }
}
