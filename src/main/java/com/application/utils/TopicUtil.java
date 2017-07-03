package com.application.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Atom on 2/10/2017
 */
public class TopicUtil {

    public static List<TopicUtil> topics = new ArrayList<>();

    static {
        Arrays.stream(ConfigsLoader.getInstance().getProperties().getProperty("test.topics").split(","))
                .forEach(s -> topics.add(new TopicUtil(s)));
    }

    private String topic;

    public TopicUtil(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }


    public static void main(String[] args) {
        Path path = Paths.get("D:\\user\\Projects\\TestingSystemMaven\\src\\main\\resources\\db\\answersFile.sql");
        StringBuilder fileString = null;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            fileString = new StringBuilder();
            String s;
            int i = 1;
            while ((s = reader.readLine()) != null) {
                fileString.append("UPDATE questions SET answer='")
                        .append(SecurityUtil.encrypt(ConvertSymbols.convertFromHex(s)))
                        .append("'").append(" WHERE id=").append(i).append("\n");
                ++i;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            assert (fileString != null ? fileString.toString() : null) != null;
            writer.write(fileString.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("");
    }

    @Override
    public String toString() {
        return topic;
    }
}
