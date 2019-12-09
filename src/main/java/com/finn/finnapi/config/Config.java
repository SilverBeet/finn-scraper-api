package com.finn.finnapi.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Config {
    public static InputStream input;
    public static Properties prop;
    public static String path = "C:/Users/Phil Ka/Desktop/Coding/Java/finn-api-v2/src/main/java/com/finn/finnapi/config.properties";

    public static List<String> getExclude() {
        try {
            input = new FileInputStream(path);
            prop = new Properties();
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(prop.getProperty("exclude").split(", "));
    }

    public static List<String> getInclude() {
        try {
            input = new FileInputStream(path);
            prop = new Properties();
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(prop.getProperty("include").split(", "));
    }
}
