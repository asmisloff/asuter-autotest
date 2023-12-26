package ru.vniizht.asuter.autotest.pages.transport.cars;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.annotation.Url;
import ru.vniizht.asuter.autotest.pages.BasePage;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Url("cars")
public class PageCarsList extends BasePage<PageCarsList> {

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

    @FindBy(xpath = "//div[@data-testid='ConfirmModal']/p")
    @As("Текст модального окна подтверждения")
    public SelenideElement confirmModalText;

    @FindBy(xpath = "//div[@id=\"modalContainer\"]/div[1]/div/div[2]/div[1]/ul[1]/ul[1]/li[2]/a[normalize-space(text())='Все']")
    @As("Кнопка отображения всех вагонов в списке")
    public SelenideElement displayAllPagesLink;

    public PageCar clickCreate() {
        buttonCreate.click();
        return page(PageCar.class);
    }

    /** Подождать загрузки элементов таблицы. */
    public PageCarsList waitTableLoading() {
        // Пока условие не выполняется, будет ждать до 4-х секунд
        $x(TBODY_XPATH).should(exist);
        $$x(TBODY_XPATH + "/tr").shouldHave(sizeGreaterThan(0));
        return this;
    }

    /** Нажать кнопку "Удалить" в контекстном меню */
    public PageCarsList clickDelete() {
        deleteButtonInContextMenu.click();
        return this;
    }

    /** Нажать кнопку "Копировать" в контекстном меню */
    public PageCarsList clickCopy() {
        copyButtonInContextMenu.click();
        return this;
    }

    /** Нажать кнопку "Удалить" в модальном окне */
    public PageCarsList clickModalDeleteOk() {
        okModalDeleteButton.click();
        return this;
    }

    /** Нажать кнопку "Отменить" в модальном окне */
    public PageCarsList clickModalCancel() {
        cancelModalButton.click();
        return this;
    }

    public CarListRow getFirstCarRow() {
        return new CarListRow(this, 1);
    }

    public CarListRow findCarRowByName(String name) {
        String xpathByName = String.format(TBODY_XPATH + "/tr[td[normalize-space(text()) = '%s']]", name);
        SelenideElement trByName = $(By.xpath(xpathByName));
        ElementsCollection precedingSiblings = trByName.$$(By.xpath("./preceding-sibling::tr"));
        int rowNumber = precedingSiblings.size() + 1;
        return new CarListRow(this, rowNumber);
    }

    public boolean containsCarRowByName(String name) {
        ElementsCollection rows = $$(By.xpath(TBODY_XPATH + "/tr"));
        for (int i = 0; i < rows.size(); i++) {
            int rowNumber = i + 1;
            CarListRow row = new CarListRow(this, rowNumber);
            if (name.equals(row.carName.text())) {
                return true;
            }
        }
        return false;
    }

    public PageCarsList clickDisplayAllPages() {
        displayAllPagesLink.click();
        return this;
    }
}
