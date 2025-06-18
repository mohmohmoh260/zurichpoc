package driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import utils.ConfigReader;
import utils.PageInitializer;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {
    protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public void setupBrowserDriver() {
        switch (ConfigReader.getBrowser().toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (ConfigReader.isHeadless()) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if(ConfigReader.isHeadless()){
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }
                driver.set(new FirefoxDriver());
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if(ConfigReader.isHeadless()){
                    edgeOptions.addArguments("--headless=new"); // new headless mode supported in Edge Chromium
                }
                edgeOptions.addArguments("--window-size=1920,1080");
                driver.set(new EdgeDriver());
                break;

            case "safari":
                WebDriverManager.edgedriver().setup();
                driver.set(new SafariDriver());
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + ConfigReader.getBrowser());
        }
        try{
            PageInitializer.pageFactoryInitializer(driver.get(), "src/test/java/pages/object", "pages.object");
        }catch (Exception ignored){
        }
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.get().manage().window().maximize();
        driver.get().get(ConfigReader.getBaseUrl());
    }

    public void createMobileDriver(){
        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
        uiAutomator2Options
                .setUdid(ConfigReader.getUDID())
                .setAutomationName("UiAutomator2")
                .setAppPackage(ConfigReader.getAppPackage())
                .setAppActivity(ConfigReader.getAppActivity())
                .setFullReset(ConfigReader.fullReset())
                .setNoReset(ConfigReader.noReset())
                .setNewCommandTimeout(Duration.ofSeconds(600))
                .setAutoGrantPermissions(true);

        int min = 5000;
        int max = 65535;
        String port = String.valueOf((int) (Math.random() * (max - min + 1)) + min);

        startAppiumService(port);

        try {
            driver.set(new AndroidDriver(new URL("http://127.0.0.1:" + port), uiAutomator2Options));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final ThreadLocal<AppiumDriverLocalService> appiumDriverLocalService = new ThreadLocal<>();

    private void startAppiumService(String port){
        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
        appiumServiceBuilder.withIPAddress("127.0.0.1");
        appiumServiceBuilder.usingPort(Integer.parseInt(port));
        appiumDriverLocalService.set(AppiumDriverLocalService.buildService(appiumServiceBuilder));
        appiumDriverLocalService.get().start();
    }
}
