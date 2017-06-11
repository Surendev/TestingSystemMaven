package com.dto;

import com.application.utils.ConverteSymbols;
import com.application.utils.SecurityUtil;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Suro Smith on 2/22/2017
 */
public class Answer {

    private Long id;
    private String text;
    private Integer toQuestion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getToQuestion() {
        return toQuestion;
    }

    public void setToQuestion(Integer toQuestion) {
        this.toQuestion = toQuestion;
    }

    public String getEncrypted() throws UnsupportedEncodingException {
        return SecurityUtil.encrypt(ConverteSymbols.converteFromHex(text));
    }
}
