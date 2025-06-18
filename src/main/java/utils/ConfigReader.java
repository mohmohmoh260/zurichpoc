package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties prop = new Properties();

    static {
        try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return System.getProperty(key, prop.getProperty(key));
    }

    public static String getEnv(){
        return get("env");
    }

    public static String getBaseUrl() {
        return get("url");
    }

    public static String getBrowser(){
        return get("browser");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(get("implicit.wait"));
    }

    public static int getExplicitWait(){
        return Integer.parseInt(get("explicit.wait"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless"));
    }

    public static String getUDID(){
        return get("appium.udid");
    }

    public static String getPlatform(){
        return get("appium.platform");
    }

    public static String getAppPackage(){
        return get("appium.appPackage");
    }

    public static String getAppActivity(){
        return get("appium.appActivity");
    }

    public static boolean noReset(){
        return Boolean.parseBoolean(get("appium.noReset"));
    }

    public static boolean fullReset(){
        return Boolean.parseBoolean(get("appium.fullReset"));
    }

    public static String getReportPath(){
        return get("extent.report.path");
    }
}