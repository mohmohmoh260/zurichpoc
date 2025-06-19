package pages.object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPageObject {

    @FindBy(xpath = "//a[normalize-space(text())='Home']")
    public static WebElement HOME_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Products']")
    public static WebElement PRODUCTS_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Cart']")
    public static WebElement CART_TAB;

    @FindBy(xpath =  "//a[normalize-space(text())='Signup / Login']")
    public static WebElement SIGNUP_LOGIN_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Test Cases']")
    public static WebElement API_TESTING_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Video Tutorials']")
    public static WebElement VIDEO_TUTORIAL_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Contact us']")
    public static WebElement CONTACT_US_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Logout']")
    public static WebElement LOGOUT_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Logged in as']")
    public static WebElement LOGGED_IN_AS_TAB;

    @FindBy(xpath = "//input[@data-qa='login-email']")
    public static WebElement LOGIN_EMAIL_INPUT;

    @FindBy(xpath = "//input[@data-qa='login-password']")
    public static WebElement LOGIN_PASSWORD_INPUT;

    @FindBy(xpath = "//button[@data-qa='login-button']")
    public static WebElement LOGIN_BUTTON;
}
