package test;

import base.BaseTest;
import enums.MenuTab;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LandingPage;
import report.ExtentReport;

import java.lang.reflect.Method;

public class LoginLogoutTestCase extends BaseTest {

    @BeforeMethod
    public void setupTestBlock(Method method){
        setupBrowserDriver();
        ExtentReport.setTestExtent(method, driver.get());
    }

    @Test
    public void loginVerifyLogout(){
        LandingPage landingPage = new LandingPage();
        landingPage.navigateToTab(MenuTab.SIGNUP_LOGIN);
        LandingPage.inputCredential("dds", "dsad");
    }

    @AfterTest
    public void afterTest(){
        driver.get().close();
        driver.get().quit();
    }
}
