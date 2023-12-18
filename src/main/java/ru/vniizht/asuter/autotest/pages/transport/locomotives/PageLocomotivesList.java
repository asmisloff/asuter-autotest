package ru.vniizht.asuter.autotest.pages.transport.locomotives;


import com.codeborne.selenide.As;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.pages.BasePage;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns.LocomotiveListRow;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

@Url("locomotives")
public class PageLocomotivesList extends BasePage<PageLocomotivesList> {
    @FindBy(xpath = "//button[@title='Создать']")
    @As("Кнопка \"Создать\"")
    SelenideElement buttonCreate;

    @FindBy(xpath = "//ul[@role='menu']/li[1][@tabindex='0']")
    @As("Кнопка \"Удалить\" в контекстном меню")
    public SelenideElement deleteButtonInContextMenu;

    @FindBy(xpath = "//ul[@role='menu']/li[2][@tabindex='-1']")
    @As("Кнопка \"Копировать\" в контекстном меню")
    public SelenideElement copyButtonInContextMenu;

    @FindBy(xpath = "//li[@data-testid='OKBtn'][normalize-space(text())='Удалить']")
    @As("Кнопка подтверждения в модальном окне")
    public SelenideElement okModalDeleteButton;

    @FindBy(xpath = "//li[@data-testid='CancelBtn']")
    @As("Кнопка отмены в модальном окне")
    public SelenideElement cancelModalButton;

    /** Нажать кнопку "Создать" */
    public PageLocomotive clickCreate() {
        buttonCreate.click();
        return page(PageLocomotive.class);
    }

    /** Нажать кнопку "Удалить" в контекстном меню */
    public PageLocomotivesList clickDelete() {
        deleteButtonInContextMenu.click();
        return this;
    }

    /** Нажать кнопку "Копировать" в контекстном меню */
    public PageLocomotivesList clickCopy() {
        copyButtonInContextMenu.click();
        return this;
    }

    /** Нажать кнопку "Удалить" в модальном окне */
    public PageLocomotivesList clickModalDeleteOk() {
        okModalDeleteButton.click();
        return this;
    }

    /** Нажать кнопку "Отменить" в модальном окне */
    public PageLocomotivesList clickModalCancel() {
        cancelModalButton.click();
        return this;
    }

    /** Найти ряд существующего локомотива в списке */
    public LocomotiveListRow findLocomotiveRowByName(String name) {
        return findLocomotiveRowByName(name, false);
    }

    /**
     * Найти ряд локомотива в списке.
     * @param name наименование локомотива
     * @param refreshAndWait флаг обновления страницы с последующим ожиданием,
     *                       должен быть true для поиска сразу после добавления
     */
    public LocomotiveListRow findLocomotiveRowByName(String name, boolean refreshAndWait) {
        String xpathByName = String.format("//div[@id=\"rootContainer\"]/div/table/tbody/tr[td[normalize-space(text()) = '%s']]", name);
        if (refreshAndWait) {
            Selenide.refresh();
            waitTime(400); // без этой задержки искомый ряд не будет найден
        }
        SelenideElement trByName = $(By.xpath(xpathByName));
        ElementsCollection precedingSiblings = trByName.$$(By.xpath("./preceding-sibling::tr"));
        int rowNumber = precedingSiblings.size() + 1;
        return new LocomotiveListRow(this, rowNumber);
    }

}
