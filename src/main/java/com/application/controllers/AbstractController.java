package com.application.controllers;

import com.application.utils.ConfigsLoader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

/**
 * Created by surik on 2/4/17
 */
abstract class AbstractController {

    ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("/spring_context.xml");

    Properties adminConfigs = ConfigsLoader.getInstance().getProperties();
}
