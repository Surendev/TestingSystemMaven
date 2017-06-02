package com.dto;


import com.jdbc.dao.QuestionsDAO;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by surik on 2/5/17
 */
public class Test {

    private int index;


    private List<Question> questions = new ArrayList<>();
    private List<Answer> wrongAnswers = new ArrayList<>();
    private Map<Integer,Integer> studentAnswers = new HashMap<>();

    public Test(Map<Integer,List<Question>> questions, QuestionsDAO service){
        for (Integer pair : questions.keySet()) {
            this.questions.addAll(questions.get(pair));
        }
        for (Question pair : this.questions) {
            wrongAnswers.addAll(service.getAnswersByQuestionId(pair.getId()));
        }

    }

    public QuestionInApp getQuestion(int currIndex) throws UnsupportedEncodingException {
        index = currIndex-1;
        Question currentQuestion = questions.get(index);

        QuestionInApp questionInApp = new QuestionInApp();
        questionInApp.setQuestion(currentQuestion.getQuestion());
        questionInApp.setTopic(currentQuestion.getTopic());
        questionInApp.setRating(currentQuestion.getRating());

        List<Answer> tempList = new ArrayList<>();
        for(int i = index; i < index + 3; i++){
            tempList.add(wrongAnswers.get(i));
        }
        questionInApp.setAnswers(tempList, currentQuestion.getAnswer());
        return questionInApp;
    }

    public void markAnswerToQuestion(int answerIndex){
        studentAnswers.put(index, answerIndex);
    }

    public Map<Integer, Integer> getStudentAnswers() {
        return studentAnswers;
    }
}
