package com.application.controllers;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by surik on 2/4/17
 */
public abstract class AbstractController {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring_context.xml");

}
