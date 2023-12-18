package ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns;

import com.codeborne.selenide.SelenideElement;
import ru.vniizht.asuter.autotest.pages.BasePage;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.PageLocomotive;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/** Ряд соответствующий позиции характеристик тягового режима. */
public class LocomotiveElectricalPositionRow extends BasePage<LocomotiveElectricalPositionRow> {

    private final static String xpathTrStart = "/html/body/div/div[2]/div[1]/div/div[2]/div[2]/div[3]/div[2]/div[1]/table/tbody/tr";
    private final static String xpathStart = xpathTrStart + "[";
    private final PageLocomotive page;
    private final int rowNumber;

    public SelenideElement nameInput;
    public SelenideElement buttonAdd;
    public SelenideElement buttonRemove;

    public LocomotiveElectricalPositionRow(PageLocomotive page, int rowNumber) {
        this.page = page;
        this.rowNumber = rowNumber;
        nameInput = $x(xpathStart + rowNumber + "]/td[1]/div/input");
        buttonAdd = $x(xpathStart + rowNumber + "]/td[2]/button[@data-testid='addBtn_']");
        buttonRemove = $x(xpathStart + rowNumber + "]/td[2]/button[@data-testid='delBtn_']");
    }

    /** Возвращает последний ряд Позиций характеристик тягового режима */
    public LocomotiveElectricalPositionRow(PageLocomotive page) {
        this(page, $$x(xpathTrStart).size());
    }

    /** Контроллер страницы, в которой выбрана данная позиция характеристик тягового режима */
    public PageLocomotive page() {
        return this.page;
    }

    /** Общее количество позиции характеристик тягового режима на странице в текущий момент времени */
    public static int totalCount() {
        return $$x(xpathTrStart).size();
    }

    public LocomotiveElectricalPositionRow inputName(String value) {
        if ("true".equals(nameInput.getAttribute("disabled"))) {
            clickToEnableNameInput();   // необходимо, чтобы снять атрибут 'disabled' для авто-теста
        }
        nameInput.clear();
        nameInput.sendKeys(value);
        return this;
    }

    /** Используется для выбора позиции характеристик */
    public LocomotiveElectricalPositionRow activatePositionRow() {
        clickToEnableNameInput();
        return this;
    }

    /** Убирает с поля ввода имени атрибут 'disabled' чтобы иметь возможность впечатывать текст в авто-тесте. */
    public LocomotiveElectricalPositionRow clickToEnableNameInput() {
        nameInput.parent().click();
        return this;
    }

    /** Нажать на кнопку Добавить */
    public LocomotiveElectricalPositionRow clickAdd() {
        buttonAdd.parent().click(); // без этого клика ряд не добавляется
        buttonAdd.click();
        return this;
    }

    /** Нажать на кнопку Удалить */
    public LocomotiveElectricalPositionRow clickRemove() {
        buttonRemove.click();
        return this;
    }

    /** Нажать на кнопку Добавить. Возвращает добавленную строку */
    public LocomotiveElectricalPositionRow clickAddAndReturnCreatedRow() {
        buttonAdd.click();
        return page.getElectricalPositionRow(this.rowNumber + 1);
    }

    /**
     * Получить ряд соответствующий ряду характеристик тягового режима.
     * @param rowNumber номер ряда (нумерация начинается с 1)
     */
    public LocomotiveElectricalCharacteristicRow getElectricalCharacteristicRow(int rowNumber) {
        return page.getElectricalCharacteristicRow(rowNumber);
    }
}
