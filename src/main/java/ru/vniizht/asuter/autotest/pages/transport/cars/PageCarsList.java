package ru.vniizht.asuter.autotest.pages.transport.cars;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.pages.BasePage;

import static com.codeborne.selenide.Selenide.page;

@Url("cars")
public class PageCarsList extends BasePage<PageCarsList> {

    @FindBy(xpath = "//button[@title='Создать']")
    @As("Кнопка \"Создать\"")
    SelenideElement buttonCreate;

    public PageCar clickCreate() {
        buttonCreate.click();
        return page(PageCar.class);
    }
}
