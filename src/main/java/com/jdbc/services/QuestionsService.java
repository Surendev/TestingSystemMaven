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

    public int addOrUpdateQuestion(boolean update, String id, String question, int rating, String topic, String rightAnswer, String... answers) {
        StringBuilder query;
        int questionId;
        if (!update) {
            query = new StringBuilder("INSERT INTO questions(question,rating,topic,answer) VALUES(?,?,?,?)");
            questionId = jdbc.update(query.toString(), question, rating, topic, rightAnswer);
            if (questionId != 1) return questionId;
            query = new StringBuilder("INSERT INTO answers(text,to_question) VALUES(?,?)");
            for (String each : answers) {
                jdbc.update(query.toString(), each, questionId);
            }
            jdbc.update(query.toString(), rightAnswer, questionId);
        } else {
            query = new StringBuilder("UPDATE questions SET ");
            try {
                query.append(question.equals("") ? "" : "question='" + question + "'")
                        .append(rating == 0 ? "" : "rating='" + rating + "'")
                        .append(topic.equals("") ? "" : "topic='" + topic + "'")
                        .append(rightAnswer.equals("") ? "" : "answer=" + SecurityUtil.encrypt(rightAnswer) + "'")
                        .append("WHERE id=" + id);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            questionId = jdbc.update(query.toString());
            if (questionId != 1) return questionId;
            query = new StringBuilder("UPDATE answers SET ");
            for (int i = 0; i < answers.length; i++){
                query.append(answers[0].equals("") ? "" : "text='" + answers[0] + "'")
                     .append("WHERE to_question=" + questionId);
                jdbc.update(query.toString());
            }

        }

        return questionId;
    }

    @Override
    public List<Answer> getAnswersByQuestionId(int id) {
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


}
