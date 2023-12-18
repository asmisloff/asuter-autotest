package ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns;

import com.codeborne.selenide.SelenideElement;
import ru.vniizht.asuter.autotest.pages.BasePage;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.PageLocomotive;

import static com.codeborne.selenide.Selenide.$x;

abstract public class LocomotiveBaseCharacteristicRow extends BasePage<LocomotiveBaseCharacteristicRow> {
    protected final String xpathTrStart;
    protected final String xpathStart;
    protected final PageLocomotive page;
    protected final int rowNumber;

    public SelenideElement speedInput;
    public SelenideElement forceInput;
    public SelenideElement motorAmperageInput;
    public SelenideElement activeCurrentAmperageInput;
    public SelenideElement efficiencyOutputField;
    public SelenideElement buttonAdd;
    public SelenideElement buttonRemove;

    public LocomotiveBaseCharacteristicRow(String xpathTrStart, PageLocomotive page, int rowNumber) {
        this.xpathTrStart = xpathTrStart;
        this.xpathStart = xpathTrStart + "[";
        this.page = page;
        this.rowNumber = rowNumber;
        this.speedInput = $x(xpathStart + rowNumber + "]/td[1]/div/input");
        this.forceInput = $x(xpathStart + rowNumber + "]/td[2]/div/input");
        this.motorAmperageInput = $x(xpathStart + rowNumber + "]/td[3]/div/input");
        this.activeCurrentAmperageInput = $x(xpathStart + rowNumber + "]/td[4]/div/input");
        this.efficiencyOutputField = $x(xpathStart + rowNumber + "]/td[5]/div/input");
        this.buttonAdd = $x(xpathStart + rowNumber + "]/td[6]/button[1]");
        this.buttonRemove = $x(xpathStart + rowNumber + "]/td[6]/button[2]");
    }

    /** Контроллер страницы, в которой выбрана данная строка характеристик */
    public PageLocomotive page() {
        return this.page;
    }

    /** Возвращает следующий ряд характеристик */
    public abstract LocomotiveBaseCharacteristicRow nextRow();

    /** Нажать на кнопку Добавить. Возвращает добавленную строку */
    public abstract LocomotiveBaseCharacteristicRow clickAddAndReturnCreatedRow();

    /** Ввести скорость движения */
    public LocomotiveBaseCharacteristicRow inputSpeed(String value) {
        speedInput.clear();
        speedInput.sendKeys(value);
        return this;
    }

    /** Ввести силу тяги касательную */
    public LocomotiveBaseCharacteristicRow inputForce(String value) {
        forceInput.clear();
        forceInput.sendKeys(value);
        return this;
    }

    /** Ввести ток тягового электродвигателя */
    public LocomotiveBaseCharacteristicRow inputMotorAmperage(String value) {
        motorAmperageInput.clear();
        motorAmperageInput.sendKeys(value);
        return this;
    }

    /** Ввести ток полный потребляемый на тягу */
    public LocomotiveBaseCharacteristicRow inputActiveCurrentAmperage(String value) {
        activeCurrentAmperageInput.clear();
        activeCurrentAmperageInput.sendKeys(value);
        return this;
    }

    /** Нажать на кнопку Добавить */
    public LocomotiveBaseCharacteristicRow clickAdd() {
        buttonAdd.click();
        return this;
    }

    /** Нажать на кнопку Удалить */
    public LocomotiveBaseCharacteristicRow clickRemove() {
        buttonRemove.click();
        return this;
    }
}
