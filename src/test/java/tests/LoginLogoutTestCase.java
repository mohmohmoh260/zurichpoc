package tests;

import base.BaseTest;
import enums.MenuTab;
import excel.ExcelReader;
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
        landingPage.inputCredential(ExcelReader.readCellValue("src/test/resources/testdata/Book1.xlsx", "Credentials", "Username", 1),
                ExcelReader.readCellValue("src/test/resources/testdata/Book1.xlsx", "Credentials", "Password", 1));
    }

    @AfterTest
    public void afterTest(){
        driver.get().close();
        driver.get().quit();
    }
}
