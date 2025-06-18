package base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Media;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import driver.DriverManager;
import report.ExtentReport;

import java.io.File;
import java.time.Duration;

public class BaseTest extends DriverManager {

    protected void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(ConfigReader.getExplicitWait()));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void click(WebElement element) {
        waitForVisibility(element);
        element.click();
        ExtentReport.getExtent().pass("Element " + element + "is clicked", takeScreenshot());
    }

    protected void sendKeys(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
        ExtentReport.getExtent().pass("Send Key " + text + " into element " + element, takeScreenshot());
    }

    protected String getText(WebElement element) {
        waitForVisibility(element);
        String text =  element.getText();
        ExtentReport.getExtent().pass("Get text: " + text);
        return text;
    }

    protected void takeScreenshot(Status status, String message) {
        String relativePath;
        String absolutePath = "";
        try {
            relativePath = ExtentReport.baseScreenshotFolder + System.currentTimeMillis() + ".png";
            absolutePath = new File(relativePath).getAbsolutePath();

            File srcFile = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(absolutePath));

        } catch (Exception ignored) {
        }
        switch (status) {
            case PASS:
                ExtentReport.getExtent().pass(message, MediaEntityBuilder.createScreenCaptureFromPath(absolutePath).build());
                break;

            case FAIL:
                ExtentReport.getExtent().fail(message, MediaEntityBuilder.createScreenCaptureFromPath(absolutePath).build());
                break;

            case INFO:
                ExtentReport.getExtent().info(message, MediaEntityBuilder.createScreenCaptureFromPath(absolutePath).build());
                break;

            case SKIP:
                ExtentReport.getExtent().skip(message, MediaEntityBuilder.createScreenCaptureFromPath(absolutePath).build());
                break;

            default:
                break;
        }
    }

    public static Media takeScreenshot() {
        if (driver.get() == null) {
            return null; // Return null to prevent errors
        }
        String relativePath;
        String absolutePath = "";
        try {
            relativePath = ExtentReport.baseScreenshotFolder + System.currentTimeMillis() + ".png";
            absolutePath = new File(relativePath).getAbsolutePath();

            File srcFile = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(absolutePath));

        } catch (Exception e) {
            int retry = 0;
            while (retry < 3){
                try{
                    takeScreenshot();
                    break;
                }catch (Exception f){
                    retry++;
                }
            }
        }

        if (absolutePath.isEmpty()) {
            return null;
        } else {
            return MediaEntityBuilder.createScreenCaptureFromPath(absolutePath).build();
        }
    }
}
