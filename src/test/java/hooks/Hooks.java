package hooks;

import org.testng.ISuiteListener;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import report.ExtentReport;

public class Hooks implements ISuiteListener {

    @BeforeSuite
    public void setupSuite(){
        ExtentReport.getInstance().setSystemInfo("OS", System.getProperty("os.name"));
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        ExtentReport.flush();
    }

}
