package com.application.utils;

/**
 * Created by surik on 4/23/17
 */
public class ConvertSymbols {

    public static String convertFromHex(String textToConvert){
        if (textToConvert==null)
            return null;
        if (!textToConvert.matches(".*\\\\.*")) {
            return textToConvert;
        }
        StringBuilder buffer = new StringBuilder("");
        StringBuilder toConvert = new StringBuilder(textToConvert);
        int index;
            while((index = toConvert.indexOf("\\"))>= 0) {
                buffer.append(toConvert.substring(0, index));

                String convert = toConvert.substring(index, index + 6);
                char cha = (char) Integer.parseInt(convert.substring(2), 16);

                buffer.append(cha);

                String notFixed = toConvert.substring(index+6);
                toConvert.delete(0, toConvert.length()).append(notFixed);
            }
        buffer.append(toConvert);
        return buffer.toString();
    }
}
