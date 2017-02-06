package com.jdbc.services;

import com.application.utils.TestUtil;
import com.dto.Test;
import com.jdbc.dao.TestDAO;

/**
 * Created by surik on 2/5/17
 */

public class TestService implements TestDAO{

    private TestUtil testUtil = new TestUtil();

    @Override
    public Test generateTest() {

        //TODO get all questions by all ratings and generate TEST
        //if need add new method
        return null;
    }


}
