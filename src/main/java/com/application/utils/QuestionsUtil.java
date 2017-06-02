package com.application.utils;

import com.dto.Answer;
import com.dto.Question;
import com.dto.QuestionInApp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionsUtil {

    public static List<QuestionInApp> getInAppFromQuestions(Map<Question,List<Answer>> questions) throws UnsupportedEncodingException {
        List<QuestionInApp> result = new ArrayList<>();
        QuestionInApp forApp;
         for (Question current : questions.keySet()){
            forApp = new QuestionInApp();
            forApp.setQuestion(current.getQuestion());
            forApp.setRating(current.getRating());
            forApp.setTopic(current.getTopic());
            forApp.setAnswers(questions.get(current), current.getAnswer());
            result.add(forApp);
         }
        return result;
    }
}
