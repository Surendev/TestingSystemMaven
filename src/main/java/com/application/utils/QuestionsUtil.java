package com.application.utils;

import com.dto.Answer;
import com.dto.Question;
import com.dto.QuestionInApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by surik on 2/26/17
 */
public class QuestionsUtil {

    public static List<QuestionInApp> getInAppFromQuestions(Map<Question,List<Answer>> questions){
        List<QuestionInApp> result = new ArrayList<>();
        QuestionInApp forApp;
         for (Question current : questions.keySet()){
            forApp = new QuestionInApp();
            forApp.setQuestion(current.getQuestion());
            forApp.setRating(current.getRating());
            forApp.setTopic(current.getTopic());
            forApp.setAnswers(questions.get(current));
            result.add(forApp);
        }
        return result;
    }
}
