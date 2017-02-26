package com.dto;


import com.jdbc.dao.QuestionsDAO;
import com.jdbc.services.QuestionsService;

import java.util.*;

/**
 * Created by surik on 2/5/17
 */
public class Test {

    private int index;


    private List<Question> questions = new ArrayList<>();
    private List<Answer> wrongAnswers;
    private Map<Integer,Integer> studentAnswers = new HashMap<>();

    public Test(Map<Integer,List<Question>> questions){
        for (Integer pair : questions.keySet()) {
            this.questions.addAll(questions.get(pair));
        }
        //TODO fill wrong answers from db
        QuestionsDAO agent = new QuestionsService();
        for (Question pair : this.questions) {
            Collections.addAll(wrongAnswers, agent.getAnswersByQuestionId(pair.getId()));
        }

    }

    public QuestionInApp getQuestion(int currIndex) {
        index = currIndex-1;
        //TODO transform Question to QuestionInApp
        QuestionInApp questionInApp = new QuestionInApp();
        questionInApp.setQuestion(questions.get(index).getQuestion());
        questionInApp.setTopic(questions.get(index).getTopic());
        List<String> tempList = new ArrayList<>();
        for(Answer pair : wrongAnswers){
            if (pair.getToQuestion() == index) tempList.add(pair.getText());
        }
        questionInApp.setAnswers((String[]) tempList.toArray());


        return questionInApp;
    }

    public void markAnswerToQuestion(int answerIndex){
        //todo mark answer in studentAnswers
        studentAnswers.put(index, answerIndex);
    }
}
