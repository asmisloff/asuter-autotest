package ru.vniizht.asuter.autotest.pages.transport.trains;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.pages.BasePage;

public class PageTrain extends BasePage<PageTrain> {
    @FindBy(xpath = "//button[@id=\"saveBtn\"][@data-testid=\"saveBtn\"]")
    @As("Кнопка \"Сохранить\"")
    public SelenideElement saveButton;

    @FindBy(xpath = "//button[@id=\"saveBtn\"][@data-testid=\"editBtn\"]")
    @As("Кнопка \"Сохранить\"")
    public SelenideElement editButton;

    /* Элементы раздела "Основные параметры" */

    @FindBy(xpath = "//input[@data-name='name']")
    @As("Поле \"Наименование\"")
    public SelenideElement nameInput;

    /* Элементы раздела "Составность" */

    @FindBy(xpath = "/html/body/div/div[2]/div[1]/div/div[2]/div[2]/div/div[2]/div[1]/span/svg")
    @As("Иконка изменения исходных данных")
    public SelenideElement originalDataChangedIcon;

    @FindBy(xpath = "//div[@id=\"rootContainer\"]/div/div[2]/div[1]/span/svg/title")
    @As("Текст подсказки \"Исходные данные были изменены\"")
    public SelenideElement originalDataChangedTitle;


    /** Нажать кнопку Сохранить */
    public PageTrain clickSave() {
        saveButton.click();
        return this;
    }

    /** Нажать кнопку Редактировать */
    public PageTrain clickEdit() {
        editButton.click();
        return this;
    }

    /** Ввести наименование локомотива */
    public PageTrain inputName(String value) {
        typeIn(nameInput, value);
        return this;
    }

    public CarInTrainRow getFirstCarRow() {
        return new CarInTrainRow(this, 1) ;
    }
}
