package pages;

import enums.MenuTab;
import org.openqa.selenium.By;
import pages.object.LandingPageObject;
import base.BaseTest;

public class LandingPage extends BaseTest {

    public void navigateToTab(MenuTab tab){
        switch (tab) {
            case HOME:
                click(LandingPageObject.HOME_TAB);
                break;
            case PRODUCTS:
                click(LandingPageObject.PRODUCTS_TAB);
                break;
            case CART:
                click(LandingPageObject.CART_TAB);
                break;
            case SIGNUP_LOGIN:
                click(LandingPageObject.SIGNUP_LOGIN_TAB);
                break;
            case TEST_CASES:
                break;
            case API_TESTING:
                click(LandingPageObject.API_TESTING_TAB);
                break;
            case VIDEO_TUTORIAL:
                click(LandingPageObject.VIDEO_TUTORIAL_TAB);
                break;
            case CONTACT_US:
                click(LandingPageObject.CONTACT_US_TAB);
                break;
            default:
                throw new IllegalArgumentException("Tab not handled: " + tab);
        }
    }

    public void inputCredential(String username, String password){
         sendKeys(LandingPageObject.LOGIN_EMAIL_INPUT, username);
         sendKeys(LandingPageObject.LOGIN_PASSWORD_INPUT, password);
         click(LandingPageObject.LOGIN_BUTTON);
    }

    public void verifyUserLoggedIn(String name){
        verifyElementVisible(LandingPageObject.LOGGED_IN_AS_TAB);
        verifyElementVisible(driver.get().findElement(By.xpath("//b[normalize-space(text())='" + name + "']")));
    }

    public void VerifyFalseUsernamePasswordPromptError(String error){
        verifyTextVisible(error);
    }

    public void clickLogoutButton(){
        click(LandingPageObject.LOGOUT_TAB);
    }
}
