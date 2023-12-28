package ru.vniizht.asuter.autotest.pages.transport.trains;

import com.codeborne.selenide.As;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.pages.BasePage;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;

@Url("trains")
public class PageTrainsList extends BasePage<PageTrainsList> {

    private static final String TBODY_XPATH = "//div[@id=\"rootContainer\"]/div/table/tbody";

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
    public PageTrain clickCreate() {
        buttonCreate.click();
        return page(PageTrain.class);
    }

    /** Подождать загрузки элементов таблицы. */
    public PageTrainsList waitTableLoading() {
        // Пока условие не выполняется, будет ждать до 4-х секунд
        $x(TBODY_XPATH).should(exist);
        $$x(TBODY_XPATH + "/tr").shouldHave(sizeGreaterThan(0));
        return this;
    }

    /** Нажать кнопку "Удалить" в контекстном меню */
    public PageTrainsList clickDelete() {
        deleteButtonInContextMenu.click();
        return this;
    }

    /** Нажать кнопку "Копировать" в контекстном меню */
    public PageTrainsList clickCopy() {
        copyButtonInContextMenu.click();
        return this;
    }

    /** Нажать кнопку "Удалить" в модальном окне */
    public PageTrainsList clickModalDeleteOk() {
        okModalDeleteButton.click();
        return this;
    }

    /** Нажать кнопку "Отменить" в модальном окне */
    public PageTrainsList clickModalCancel() {
        cancelModalButton.click();
        return this;
    }

    public TrainListRow findTrainRowByName(String trainName) {
        String xpathByName = String.format(TBODY_XPATH + "/tr[td[3][normalize-space(text()) = '%s']]", trainName);
        SelenideElement trByName = $(By.xpath(xpathByName));
        ElementsCollection precedingSiblings = trByName.$$(By.xpath("./preceding-sibling::tr"));
        int rowNumber = precedingSiblings.size() + 1;
        return new TrainListRow(this, rowNumber);
    }

}
