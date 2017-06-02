package com.application.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Suro Smith.
 */
public class ConfigsLoader {

    private static ConfigsLoader instance;
    private static final String FILE_PATH = "D:\\Java\\Hamalsaran\\TestingSystemMaven\\src\\main\\resources\\admin\\configs.properties";
    public Properties getProperties(){
        Path configLocation = Paths.get(FILE_PATH);
        try (InputStream stream = Files.newInputStream(configLocation)) {
            Properties config = new Properties();
            config.load(stream);
            return config;
        } catch (IOException e) {
            System.out.println("file not found \n" + e.getMessage());
            return null;
        }
    }

    public void setProperties(Properties newProps){
        Path configLocation = Paths.get(FILE_PATH);
        try (OutputStream out = Files.newOutputStream(configLocation)){
            newProps.store(out,"");
        } catch (IOException e) {
            System.out.println("file not found \n" + e.getMessage());
        }
    }

    private ConfigsLoader(){
    }

    public static ConfigsLoader getInstance(){
        if (instance == null){
            instance = new ConfigsLoader();
            return instance;
        } else{
            return instance;
        }
    }
}
