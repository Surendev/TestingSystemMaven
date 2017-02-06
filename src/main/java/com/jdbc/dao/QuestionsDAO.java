package com.jdbc.dao;

import com.dto.Question;

import java.util.List;

/**
 * Created by surik on 2/6/17
 */
public interface QuestionsDAO {

    List<Question> getQuestionsByRating(int rating);

}
