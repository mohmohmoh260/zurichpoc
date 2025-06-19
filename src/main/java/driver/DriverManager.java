package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class DriverManager {

    protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    public void setupBrowserDriver() {
        switch (ConfigReader.getBrowser().toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (ConfigReader.isHeadless()) {
                    chromeOptions.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
                }
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (ConfigReader.isHeadless()) {
                    firefoxOptions.addArguments("--headless", "--width=1920", "--height=1080");
                }
                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (ConfigReader.isHeadless()) {
                    edgeOptions.addArguments("--headless=new");
                }
                edgeOptions.addArguments("--window-size=1920,1080");
                driver.set(new EdgeDriver(edgeOptions));
                break;

            case "safari":
                WebDriverManager.safaridriver().setup();
               driver.set(new SafariDriver());
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + ConfigReader.getBrowser());
        }

        wait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(ConfigReader.getExplicitWait())));

        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.get().manage().window().maximize();
        driver.get().get(ConfigReader.getBaseUrl());
    }
}
