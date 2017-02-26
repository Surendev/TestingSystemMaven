package com.dto;


import com.jdbc.dao.QuestionsDAO;

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

    public QuestionInApp getQuestion(int currIndex) {
        index = currIndex-1;
        QuestionInApp questionInApp = new QuestionInApp();
        questionInApp.setQuestion(questions.get(index).getQuestion());
        questionInApp.setTopic(questions.get(index).getTopic());
        List<String> tempList = new ArrayList<>();
        //TODO optimize getting questions from wrongAnswers.
        // HINT. see adding wrongAnswers
        for(int i = index; i < index + 3; i++){
            tempList.add(wrongAnswers.get(i).getText());
        }
        questionInApp.setAnswers(tempList);
        return questionInApp;
    }

    public void markAnswerToQuestion(int answerIndex){
        studentAnswers.put(index, answerIndex);
    }

    public Map<Integer, Integer> getStudentAnswers() {
        return studentAnswers;
    }
}
