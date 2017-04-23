package com.jdbc.services;

import com.dto.Answer;
import com.dto.Question;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.mappers.AnswerRowMapper;
import com.jdbc.mappers.QuestionRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
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

    @Override
    public List<Answer> getAnswersByQuestionId(int id) {
        String query = "SELECT * FROM answers WHERE to_question=?";
        return  jdbc.query(query,new Object[]{id}, new AnswerRowMapper());
    }

    @Override
    public Map<Question, List<Answer>> getAllQuestions(){
        String query = "SELECT * FROM questions";
        Map<Question,List<Answer>> result = new HashMap<>();

        List<Question> questions = jdbc.query(query,new QuestionRowMapper());
        for (Question eachQuestion : questions){
            result.put(eachQuestion,getAnswersByQuestionId(eachQuestion.getId()));
        }

        if(result.isEmpty()){
            return null;
        }

        return result;
    }
}
