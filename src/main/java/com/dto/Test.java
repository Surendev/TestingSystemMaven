package com.dto;


import com.jdbc.dao.QuestionsDAO;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by surik on 2/5/17
 */
public class Test {

    private int index;


    private List<QuestionInApp> appQuestions = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();
    private List<Answer> wrongAnswers = new ArrayList<>();
    private List<Integer> studentAnswers = new ArrayList<>();

    public Test(Map<Integer, List<Question>> questions, QuestionsDAO service) {
        for (Integer pair : questions.keySet()) {
            this.questions.addAll(questions.get(pair));
        }
        for (Question pair : this.questions) {
            wrongAnswers.addAll(service.getAnswersByQuestionId(pair.getId()));
        }
        for (int i = 0; i < questions.size(); i++) {
            QuestionInApp questionInApp = new QuestionInApp();
            questionInApp.setQuestion(this.questions.get(i).getQuestion());
            questionInApp.setRating(this.questions.get(i).getRating());
            questionInApp.setTopic(this.questions.get(i).getTopic());
            try {
                questionInApp.setAnswers(wrongAnswers.subList(i, i + 3), this.questions.get(i).getAnswer());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            appQuestions.add(setRandomAnswers(questionInApp));
        }
    }

    public QuestionInApp getQuestion(int currIndex) throws UnsupportedEncodingException {
        index = currIndex - 1;
        return appQuestions.get(index);
    }

    public void markAnswerToQuestion(int[] answerIndexes) {
        for (int next : answerIndexes) studentAnswers.add(next);
    }
    public List<Integer> getStudentAnswers() {
        return studentAnswers;
    }

    public int getQuestionsSize(){
        return questions.size();
    }

    private QuestionInApp setRandomAnswers(QuestionInApp appQuestion){
        Random random = new Random();
        int r = random.nextInt(6);
        String [] tempAnswers = new String[]{appQuestion.getAnswer1(),appQuestion.getAnswer2(),appQuestion.getAnswer3()};
        switch (r){
            case 0 : {
                appQuestion.setAnswer1(tempAnswers[0]);
                appQuestion.setAnswer2(tempAnswers[1]);
                appQuestion.setAnswer3(tempAnswers[2]);
                break;
            }
            case 1 : {
                appQuestion.setAnswer1(tempAnswers[0]);
                appQuestion.setAnswer2(tempAnswers[2]);
                appQuestion.setAnswer3(tempAnswers[1]);
                break;
            }
            case 2 : {
                appQuestion.setAnswer1(tempAnswers[1]);
                appQuestion.setAnswer2(tempAnswers[0]);
                appQuestion.setAnswer3(tempAnswers[2]);
                break;
            }
            case 3 : {
                appQuestion.setAnswer1(tempAnswers[1]);
                appQuestion.setAnswer2(tempAnswers[2]);
                appQuestion.setAnswer3(tempAnswers[0]);
                break;
            }
            case 4 : {
                appQuestion.setAnswer1(tempAnswers[2]);
                appQuestion.setAnswer2(tempAnswers[1]);
                appQuestion.setAnswer3(tempAnswers[0]);
                break;
            }
            case 5 : {
                appQuestion.setAnswer1(tempAnswers[2]);
                appQuestion.setAnswer2(tempAnswers[0]);
                appQuestion.setAnswer3(tempAnswers[1]);
                break;
            }
            default:break;
        }
        return appQuestion;
    }

    public double qualifyTest(){
        double result = 0.0;
        for (int i = 0; i < studentAnswers.size(); i++) {
            if (appQuestions.get(i).getRightIndex() == studentAnswers.get(i))
                result+=appQuestions.get(i).getRating();
        }
        return result;
    }

}
