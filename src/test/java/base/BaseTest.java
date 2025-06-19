package base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Media;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import driver.DriverManager;
import report.ExtentReport;

import java.io.File;

public class BaseTest extends DriverManager {

    protected void waitForVisibility(WebElement element) {
        wait.get().until(ExpectedConditions.visibilityOf(element));
    }

    protected void click(WebElement element) {
        waitForVisibility(element);
        try{
            element.click();
        }catch (Throwable t){
            ExtentReport.getExtent().fail(t.getMessage(), takeScreenshot());
            Assert.fail(t.getMessage());
            return;
        }
        ExtentReport.getExtent().pass("Element " + element + " is clicked", takeScreenshot());
    }

    protected void sendKeys(WebElement element, String text) {
        waitForVisibility(element);
        try{
            element.clear();
            element.sendKeys(text);
        }catch (Throwable t){
            ExtentReport.getExtent().fail(t.getMessage(), takeScreenshot());
            Assert.fail(t.getMessage());
            return;
        }
        ExtentReport.getExtent().pass("Sendkey \"" + text + "\" into element " + element, takeScreenshot());
    }

    protected String getText(WebElement element) {
        String text = "";
        try{
            waitForVisibility(element);
            text = element.getText();
            ExtentReport.getExtent().pass("Get text: " + text);
        }catch (Throwable t){
            ExtentReport.getExtent().fail(t.getMessage(), takeScreenshot());
            Assert.fail(t.getMessage());
        }
        return text;
    }

    protected void verifyElementVisible(WebElement element){
        try{
            Assert.assertTrue(element.isDisplayed());
        } catch (Throwable t) {
            ExtentReport.getExtent().fail(t.getMessage(), takeScreenshot());
            Assert.fail(t.getMessage());
            return;
        }
        ExtentReport.getExtent().pass(element + " is visible", takeScreenshot());
    }

    protected void verifyTextVisible(String text){
        try{
            Assert.assertTrue(driver.get().findElement(By.xpath("//*[normalize-space(text())='" + text + "']")).isDisplayed());
        } catch (Throwable t) {
            ExtentReport.getExtent().fail(t.getMessage(), takeScreenshot());
            Assert.fail(t.getMessage());
            return;
        }
        ExtentReport.getExtent().fail("\"" + text + "\" is visible", takeScreenshot());
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
