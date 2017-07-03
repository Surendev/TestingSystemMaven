package com.application.utils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Suro Smith.
 */
public class ConfigsLoader {

    private static ConfigsLoader instance;
    private static final String FILE_PATH = "/admin/configs.properties";

    public Properties getProperties() {
        String path = ConfigsLoader.class.getResource(FILE_PATH).getFile();
        Path configLocation = Paths.get(System.getProperty("os.name").contains("indow") ? path.contains("file:/") ? path.substring(6) : path.substring(1) : path);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(configLocation.toString()), "UTF8"))) {
            Properties config = new Properties();
            config.load(reader);
            return config;
        } catch (IOException e) {
            System.out.println("file not found \n" + e.getMessage());
            return null;
        }
    }

    public void setProperties(Properties newProps) {
        String path = ConfigsLoader.class.getResource(FILE_PATH).getFile();
        Path configLocation = Paths.get(System.getProperty("os.name").contains("indow") ? path.substring(1) : path);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(configLocation.toString()), "UTF8"))) {

            newProps.store(writer, "");
        } catch (IOException e) {
            System.out.println("file not found \n" + e.getMessage());
        }
    }

    public static ConfigsLoader getInstance() {
        if (instance == null) {
            instance = new ConfigsLoader();
            return instance;
        } else {
            return instance;
        }
    }

    private ConfigsLoader() {
    }
}
