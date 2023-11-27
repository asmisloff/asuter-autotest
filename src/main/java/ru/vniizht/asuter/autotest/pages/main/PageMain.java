package ru.vniizht.asuter.autotest.pages.main;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.pages.BasePage;
import ru.vniizht.asuter.autotest.pages.login.PageLogin;

import static com.codeborne.selenide.Selenide.page;

@Url("database")
public class PageMain extends BasePage {

    @FindBy(xpath = "//button[@data-testid='exitBtn']")
    public SelenideElement buttonLogout;


    public PageLogin clickLogout() {
        buttonLogout.click();
        return page(PageLogin.class);
    }

}
