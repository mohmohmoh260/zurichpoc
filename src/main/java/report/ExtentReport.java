package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.ConfigReader;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReport {

    public static final String baseReportFolder;
    public static final String baseScreenshotFolder;
    private static final ExtentReports extentReports = new ExtentReports();
    private static final ThreadLocal<ExtentTest> extent = new ThreadLocal<>();
    static {
        String timeStamp = new SimpleDateFormat("dd.MM.yy_HH-mm-ss").format(new Date());
        String os = System.getProperty("os.name");

        if (os.contains("Mac OS")) {
            baseReportFolder = ConfigReader.getReportPath() + "/" + timeStamp + "/";
            baseScreenshotFolder = baseReportFolder + "screenshot/";
        } else {
            baseReportFolder = ConfigReader.getReportPath() + "\\" + timeStamp + "\\";
            baseScreenshotFolder = baseReportFolder + "screenshot\\";
        }

        ExtentSparkReporter spark = new ExtentSparkReporter(baseReportFolder + "ExtentReport.html")
                .viewConfigurer()
                .viewOrder()
                .as(new ViewName[]{
                        ViewName.DASHBOARD,
                        ViewName.TEST,
                        ViewName.CATEGORY,
                        ViewName.AUTHOR,
                        ViewName.DEVICE,
                        ViewName.EXCEPTION,
                        ViewName.LOG
                }).apply();

        spark.config(
                ExtentSparkReporterConfig.builder()
                        .theme(Theme.STANDARD)
                        .documentTitle("Automation Report")
                        .timelineEnabled(true)
                        .reportName("kym")
                        .build()
        );

        extentReports.attachReporter(spark);
    }

    public static synchronized ExtentReports getInstance() {
        return extentReports;
    }

    public static synchronized ExtentTest getExtent() {
        return extent.get();
    }

    private static synchronized void setExtent(ExtentTest extentTest) {
        extent.set(extentTest);
    }

    public static synchronized void setTestExtent(Method method, WebDriver driver){
        ExtentTest extentTest = ExtentReport.getInstance().createTest(
                method.getDeclaringClass().getSimpleName() + " - " + method.getName()
        );
        ExtentReport.setExtent(extentTest);

        if(driver instanceof AppiumDriver){
            ExtentReport.getExtent().assignDevice(
                    "<b>Platform Name:&nbsp;</b>" + ((AppiumDriver) driver).getCapabilities().getPlatformName() + "<br>" +
                            "<b>Platform Version:&nbsp;</b>" + ((AppiumDriver) driver).getCapabilities().getCapability("platformVersion").toString() + "<br>" +
                            "<b>Device Name:&nbsp;</b>" + ((AppiumDriver) driver).getCapabilities().getCapability("deviceName").toString()
            );
        }else if (driver instanceof RemoteWebDriver){
            ExtentReport.getExtent().assignDevice(
                    "<b>Browser Name:&nbsp;</b>" + ((RemoteWebDriver) driver).getCapabilities().getBrowserName() + "<br>" +
                            "<b>Version:&nbsp;</b>" + ((RemoteWebDriver) driver).getCapabilities().getBrowserVersion()
            );
        }
    }

    public static synchronized void flush() {
        extentReports.flush();
    }
}
