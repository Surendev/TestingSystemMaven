package com.jdbc.services;

import com.application.utils.SecurityUtil;
import com.dto.Answer;
import com.dto.Question;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.mappers.AnswerRowMapper;
import com.jdbc.mappers.QuestionRowMapper;
import com.sun.org.apache.xpath.internal.SourceTree;
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

    public String addOrUpdateQuestion(boolean update, String id, String question,
                                    Integer rating, String topic, String newRightAnswer, String[] answers, String[] questionsOldValues)
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
            return "Ավելացված է";
        } else {
//            query = new StringBuilder("UPDATE questions SET ");
//            query.append("question='" + question + "'")
//                    .append("rating='" + rating + "'")
//                    .append("topic='" + topic + "'")
//                    .append(" WHERE id=").append(id);
            if (!(questionsOldValues[0].equals(question)
                && questionsOldValues[1].equals(topic)
                && questionsOldValues[2].equals(Integer.toString(rating))
                && questionsOldValues[3].equals(newRightAnswer)))           {

                jdbc.update("UPDATE questions SET question=?,rating=?,topic=?,answer=? WHERE id=?",
                        question,rating,topic,SecurityUtil.encrypt(newRightAnswer), id);
            }
            if (!(questionsOldValues[3].equals(newRightAnswer))) jdbc.update("UPDATE answers SET text=? WHERE text=?", newRightAnswer, questionsOldValues[3]);
            if (!(questionsOldValues[4].equals(answers[0]))) jdbc.update("UPDATE answers SET text=? WHERE text=?", answers[0], questionsOldValues[4]);
            if (!(questionsOldValues[5].equals(answers[1]))) jdbc.update("UPDATE answers SET text=? WHERE text=?", answers[1], questionsOldValues[5]);
            System.out.println();
            return  "Փոփոխված է";
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
        if (jdbc.update(query, questionId) <= 0) {
            throw new SQLException("Չի ստացվել ջնջել հարցը ID: " + questionId);
        }
    }

    @Override
    public Integer getQuestionIdByRightAnswer(String answer) {
        if (answer.isEmpty() || answer.length() != 64) {
            return null;
        }
        final String query = "SELECT id FROM questions WHERE answer=?";

        return jdbc.queryForObject(query, new Object[]{answer}, (resultSet, i) -> resultSet.getInt("id"));
    }
}
