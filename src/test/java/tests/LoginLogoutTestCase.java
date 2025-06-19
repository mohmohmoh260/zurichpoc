package tests;

import base.BaseTest;
import enums.MenuTab;
import excel.ExcelReader;
import org.testng.annotations.*;
import pages.LandingPage;
import report.ExtentReport;

import java.lang.reflect.Method;

public class LoginLogoutTestCase extends BaseTest {

    @BeforeTest
    public void initDriver(){
        setupBrowserDriver();
    }

    @BeforeMethod
    public void setupTestBlock(Method method){
        ExtentReport.setTestExtent(method, driver.get());
    }

    LandingPage landingPage = new LandingPage();

    @Test(priority = 1)
    public void loginVerifyLogout(){
        landingPage.navigateToTab(MenuTab.SIGNUP_LOGIN);
        landingPage.inputCredential(ExcelReader.readCellValue("src/test/resources/testdata/Book1.xlsx", "Credentials", "Username", 1),
                ExcelReader.readCellValue("src/test/resources/testdata/Book1.xlsx", "Credentials", "Password", 1));
        landingPage.verifyUserLoggedIn(ExcelReader.readCellValue("src/test/resources/testdata/Book1.xlsx", "Credentials", "Name", 1));
        landingPage.clickLogoutButton();
    }

    @Test(priority = 2)
    public void verifyFalseUsername(){
        landingPage.inputCredential(ExcelReader.readCellValue("src/test/resources/testdata/Book1.xlsx", "Credentials", "Username", 2),
                ExcelReader.readCellValue("src/test/resources/testdata/Book1.xlsx", "Credentials", "Password", 2));
        landingPage.VerifyFalseUsernamePasswordPromptError(ExcelReader.readCellValue("src/test/resources/testdata/Book1.xlsx", "Credentials", "Error Message", 2));
    }

    @AfterTest
    public void afterTest(){
        driver.get().close();
        driver.get().quit();
    }
}
