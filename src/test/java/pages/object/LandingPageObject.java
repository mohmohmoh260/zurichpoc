package pages.object;

import driver.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPageObject extends DriverManager{

    public LandingPageObject() {
        PageFactory.initElements(driver.get(), this);
    }

    @FindBy(xpath = "//a[normalize-space(text())='Home']")
    public WebElement HOME_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Products']")
    public WebElement PRODUCTS_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Cart']")
    public WebElement CART_TAB;

    @FindBy(xpath =  "//a[normalize-space(text())='Signup / Login']")
    public WebElement SIGNUP_LOGIN_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Test Cases']")
    public WebElement API_TESTING_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Video Tutorials']")
    public WebElement VIDEO_TUTORIAL_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Contact us']")
    public WebElement CONTACT_US_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Logout']")
    public WebElement LOGOUT_TAB;

    @FindBy(xpath = "//a[normalize-space(text())='Logged in as']")
    public WebElement LOGGED_IN_AS_TAB;

    @FindBy(xpath = "//input[@data-qa='login-email']")
    public WebElement LOGIN_EMAIL_INPUT;

    @FindBy(xpath = "//input[@data-qa='login-password']")
    public WebElement LOGIN_PASSWORD_INPUT;

    @FindBy(xpath = "//button[@data-qa='login-button']")
    public WebElement LOGIN_BUTTON;
}
