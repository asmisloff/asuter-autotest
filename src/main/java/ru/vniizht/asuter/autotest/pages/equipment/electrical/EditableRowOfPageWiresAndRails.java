package ru.vniizht.asuter.autotest.pages.equipment.electrical;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.vniizht.asuter.autotest.constants.WireMaterial;

import static com.codeborne.selenide.Selenide.$x;

public class EditableRowOfPageWiresAndRails {

    private final static String xpathStart = "/html/body/div/div[2]/div[1]/div/div[2]/div[2]/section/table/tbody/tr[";

    private final PageWiresAndRails page;
    private final int rowIndex;


    public WebElement inputName;
    public WebElement inputDcResistance;
    public WebElement inputAcResistance;
    public WebElement inputRadius;
    public WebElement inputLimitAmperage;
    public WebElement inputLimitTemperature;
    public WebElement inputThermalCapacity;
    public WebElement inputCrossSectionArea;
    public Select dropdownMaterial;
    public WebElement buttonAdd;
    public WebElement buttonRemove;


    public EditableRowOfPageWiresAndRails(PageWiresAndRails page, int rowIndex) {
        this.page = page;
        this.rowIndex = rowIndex;
        inputName = $x(xpathStart + rowIndex + "]//input[contains(@data-testid, 'text')]");
        inputDcResistance = $x(xpathStart + rowIndex + "]//input[contains(@data-testid, 'directResistance')]");
        inputAcResistance = $x(xpathStart + rowIndex + "]//input[contains(@data-testid, 'alternateResistance')]");
        inputRadius = $x(xpathStart + rowIndex + "]//input[contains(@data-testid, 'radius')]");
        inputLimitAmperage = $x(xpathStart + rowIndex + "]//input[contains(@data-testid, 'limitAmperage')]");
        inputLimitTemperature = $x(xpathStart + rowIndex + "]//input[contains(@data-testid, 'limitTemperature')]");
        inputThermalCapacity = $x(xpathStart + rowIndex + "]//input[contains(@data-testid, 'thermalCapacity')]");
        inputCrossSectionArea = $x(xpathStart + rowIndex + "]//input[contains(@data-testid, 'crossSectionArea')]");
        dropdownMaterial = new Select($x(xpathStart + rowIndex + "]//select[contains(@data-testid, 'material')]"));
        buttonAdd = $x(xpathStart + rowIndex + "]//button[contains(@data-testid, 'addBtn')]");
        buttonRemove = $x(xpathStart + rowIndex + "]//button[contains(@data-testid, 'delBtn')]");
    }

    /** Страница, в которой выбрана эта строка */
    public PageWiresAndRails page() {
        return page;
    }

    /** Возвращает следующую строку в таблице с проводами */
    public EditableRowOfPageWiresAndRails nextRow() {
        return page.getEditableRow(rowIndex + 1);
    }

    /** Возвращает предыдущую строку в таблице с проводами */
    public EditableRowOfPageWiresAndRails previousRow() {
        return page.getEditableRow(rowIndex - 1);
    }

    /** Нажать на кнопку добавить */
    public EditableRowOfPageWiresAndRails clickAdd() {
        buttonAdd.click();
        return this;
    }

    /** Нажать на кнопку добавить. Возвращает добавленную строку */
    public EditableRowOfPageWiresAndRails clickAddAndReturnCreatedRow() {
        clickAdd();
        return page.getEditableRow(rowIndex + 1);
    }

    /** Нажать на кнопку удалить. Возвращает страницу, в которой находилась удаляемая строка */
    public PageWiresAndRails clickRemove() {
        buttonRemove.click();
        return page;
    }

    /** Марка */
    public EditableRowOfPageWiresAndRails inputName(String value) {
        inputName.sendKeys(value);
        return this;
    }

    /** Сопротивление постоянному току */
    public EditableRowOfPageWiresAndRails inputDcResistance(double value) {
        inputDcResistance.sendKeys(String.valueOf(value));
        return this;
    }

    /** Сопротивление постоянному току */
    public EditableRowOfPageWiresAndRails inputDcResistance(String value) {
        inputDcResistance.sendKeys(value);
        return this;
    }

    /** Сопротивление переменному току */
    public EditableRowOfPageWiresAndRails inputAcResistance(double value) {
        inputAcResistance.sendKeys(String.valueOf(value));
        return this;
    }

    /** Сопротивление переменному току */
    public EditableRowOfPageWiresAndRails inputAcResistance(String value) {
        inputAcResistance.sendKeys(value);
        return this;
    }

    /** Радиус */
    public EditableRowOfPageWiresAndRails inputRadius(double value) {
        inputRadius.sendKeys(String.valueOf(value));
        return this;
    }

    /** Радиус */
    public EditableRowOfPageWiresAndRails inputRadius(String value) {
        inputRadius.sendKeys(value);
        return this;
    }

    /** Допустимый длительный ток */
    public EditableRowOfPageWiresAndRails inputLimitAmperage(double value) {
        inputLimitAmperage.sendKeys(String.valueOf(value));
        return this;
    }

    /** Допустимый длительный ток */
    public EditableRowOfPageWiresAndRails inputLimitAmperage(String value) {
        inputLimitAmperage.sendKeys(value);
        return this;
    }

    /** Допустимая температура */
    public EditableRowOfPageWiresAndRails inputLimitTemperature(int value) {
        inputLimitTemperature.sendKeys(String.valueOf(value));
        return this;
    }

    /** Допустимая температура */
    public EditableRowOfPageWiresAndRails inputLimitTemperature(String value) {
        inputLimitTemperature.sendKeys(value);
        return this;
    }

    /** Удельная теплоемкость провода */
    public EditableRowOfPageWiresAndRails inputThermalCapacity(double value) {
        inputThermalCapacity.sendKeys(String.valueOf(value));
        return this;
    }

    /** Удельная теплоемкость провода */
    public EditableRowOfPageWiresAndRails inputThermalCapacity(String value) {
        inputThermalCapacity.sendKeys(value);
        return this;
    }

    /** Площадь поперечного сечения */
    public EditableRowOfPageWiresAndRails inputCrossSectionArea(int value) {
        inputCrossSectionArea.sendKeys(String.valueOf(value));
        return this;
    }

    /** Площадь поперечного сечения */
    public EditableRowOfPageWiresAndRails inputCrossSectionArea(String value) {
        inputCrossSectionArea.sendKeys(value);
        return this;
    }

    /** Материал провода */
    public EditableRowOfPageWiresAndRails selectMaterial(WireMaterial value) {
        dropdownMaterial.selectByVisibleText(value == null ? "" : value.displayedText);
        return this;
    }
}
