package pages;

import enums.MenuTab;
import org.openqa.selenium.By;
import pages.object.LandingPageObject;
import base.BaseTest;

public class LandingPage extends BaseTest {

    private final LandingPageObject landingPageObject;

    public LandingPage() {
        landingPageObject = new LandingPageObject();
    }

    public void navigateToTab(MenuTab tab){
        switch (tab) {
            case HOME:
                click(landingPageObject.HOME_TAB);
                break;
            case PRODUCTS:
                click(landingPageObject.PRODUCTS_TAB);
                break;
            case CART:
                click(landingPageObject.CART_TAB);
                break;
            case SIGNUP_LOGIN:
                click(landingPageObject.SIGNUP_LOGIN_TAB);
                break;
            case TEST_CASES:
                break;
            case API_TESTING:
                click(landingPageObject.API_TESTING_TAB);
                break;
            case VIDEO_TUTORIAL:
                click(landingPageObject.VIDEO_TUTORIAL_TAB);
                break;
            case CONTACT_US:
                click(landingPageObject.CONTACT_US_TAB);
                break;
            default:
                throw new IllegalArgumentException("Tab not handled: " + tab);
        }
    }

    public void inputCredential(String username, String password){
         sendKeys(landingPageObject.LOGIN_EMAIL_INPUT, username);
         sendKeys(landingPageObject.LOGIN_PASSWORD_INPUT, password);
         click(landingPageObject.LOGIN_BUTTON);
    }

    public void verifyUserLoggedIn(String name){
        verifyElementVisible(landingPageObject.LOGGED_IN_AS_TAB);
        verifyElementVisible(driver.get().findElement(By.xpath("//b[normalize-space(text())='" + name + "']")));
    }

    public void VerifyFalseUsernamePasswordPromptError(String error){
        verifyTextVisible(error);
    }

    public void clickLogoutButton(){
        click(landingPageObject.LOGOUT_TAB);
    }
}
