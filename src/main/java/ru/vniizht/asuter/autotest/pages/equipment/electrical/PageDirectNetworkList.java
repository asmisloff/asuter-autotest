package ru.vniizht.asuter.autotest.pages.equipment.electrical;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.pages.BasePage;

@Url("direct-network")
public class PageDirectNetworkList extends BasePage {

    @FindBy(xpath = "//button[@title='Создать']")
    WebElement buttonCreate;


    public PageDirectNetwork clickCreate() {
        buttonCreate.click();
        return Selenide.page(PageDirectNetwork.class);
    }
}
