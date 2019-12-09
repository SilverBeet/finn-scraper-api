package com.finn.finnapi.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static InputStream input;
    private static Properties prop;
    private static Dotenv dotenv = Dotenv.load();
    private static String path = dotenv.get("CONFIG_STRING");

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
