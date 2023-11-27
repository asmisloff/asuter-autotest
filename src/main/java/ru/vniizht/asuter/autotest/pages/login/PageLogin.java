package ru.vniizht.asuter.autotest.pages.login;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.constants.Urls;
import ru.vniizht.asuter.autotest.pages.BasePage;
import ru.vniizht.asuter.autotest.pages.main.PageMain;


@Url("login")
public class PageLogin extends BasePage {

    public static final String loginErrorUrl = Urls.baseUrl + "loginError";

    @FindBy(id = "username")
    public WebElement inputUsername;

    @FindBy(id = "password")
    public WebElement inputPassword;

    @FindBy(id = "button-login")
    public WebElement buttonLogin;

    @FindBy(id = "password-recovery")
    public WebElement linkPasswordRecovery;

    @FindBy(id = "error-message")
    public WebElement divErrorMessage;

    @FindBy(xpath = "/html/body/div[3]/div/form/div[3]/div")
    public WebElement divInvalidFeedbackPassword;

    @FindBy(xpath = "/html/body/div[3]/div/form/div[2]/div")
    public WebElement divInvalidFeedbackLogin;

    public PageLogin enterUsername(String text) {
        inputUsername.clear();
        inputUsername.sendKeys(text);
        return this;
    }

    public PageLogin enterPassword(String text) {
        inputPassword.clear();
        inputPassword.sendKeys(text);
        return this;
    }

    public PageLogin clearUsername() {
        inputUsername.clear();
        return this;
    }

    public PageLogin clearPassword() {
        inputPassword.clear();
        return this;
    }

    public PageMain clickLogin() {
        buttonLogin.click();
        return Selenide.page(PageMain.class);
    }

    public PageLogin clickPasswordRecovery() {
        linkPasswordRecovery.click();
        return this; // TODO maybe return another page
    }

    public boolean isErrorMessageDisplayed() {
        return divErrorMessage.isDisplayed();
    }

    public String getErrorMessageText() {
        return divErrorMessage.getText();
    }

}
