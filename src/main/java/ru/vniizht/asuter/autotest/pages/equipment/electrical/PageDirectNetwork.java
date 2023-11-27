package ru.vniizht.asuter.autotest.pages.equipment.electrical;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import ru.vniizht.asuter.autotest.pages.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class PageDirectNetwork extends BasePage {

    @FindBy(xpath = "//button[@data-testid='saveBtn']")
    public SelenideElement buttonSave;

    @FindBy(xpath = "//button[@data-testid='calculateBtn']")
    public SelenideElement buttonCalculate;

    @FindBy(xpath = "//button[contains(@title, 'на')]")
    public SelenideElement buttonCopy;

    @FindBy(xpath = "//input[@data-testid='fiderCount']")
    public SelenideElement inputFeederQuantity;

    Select dropdownMenuFeederMark = new Select($x("//select[@data-testid='fiderId']"));

    @FindBy(xpath = "//input[@data-testid='contactWireCount']")
    SelenideElement inputContactWireQuantity;

    Select dropdownMenuContactWireMark = new Select($x("//select[@data-testid='contactWireId']"));

    @FindBy(xpath = "//input[@data-testid='powerWireCount']")
    SelenideElement inputPowerWireQuantity;

    Select dropdownMenuPowerWireMark = new Select($x("//select[@data-testid='powerWireId']"));

    @FindBy(xpath = "//input[@data-testid='railCount']")
    SelenideElement inputTrackQuantity;

    Select dropdownMenuTrackMark = new Select($x("//select[@data-testid='railId']"));

    @FindBy(xpath = "//a[@data-testid='supplyLineBtn']")
    public SelenideElement buttonSupplyLine;

    @FindBy(xpath = "//span[@data-testid='tractiveNetworkBtn']")
    public SelenideElement buttonTractionNetwork;

    @FindBy(xpath = "//input[@data-testid='wiresResistance']")
    public SelenideElement calculatedRks;

    @FindBy(xpath = "//input[@data-testid='railsResistance']")
    public SelenideElement calculatedRp;

    @FindBy(xpath = "//input[@data-testid='limitAmperage']")
    public SelenideElement calculatedIdop;

    @FindBy(xpath = "//input[@data-testid='limitTemperature']")
    public SelenideElement claculatedTdl;

    @FindBy(xpath = "//input[@data-testid='contactAmperage']")
    public SelenideElement calculatedIkp;

    @FindBy(xpath = "//div[@data-testid='ConfirmModal']")
    public SelenideElement confirmationWindow;

    @FindBy(xpath = "//li[@data-testid='OKBtn']")
    public SelenideElement buttonConfirmationWindowAccept;

    @FindBy(xpath = "//li[@data-testid='CancelBtn']")
    public SelenideElement buttonConfirmationWindowCancel;


    /** Нажать кнопку "Сохранить" */
    public PageDirectNetwork clickSave() {
        buttonSave.click();
        return this;
    }

    /** Нажать кнопку "Рассчитать" */
    public PageDirectNetwork clickCalculate() {
        buttonCalculate.click();
        return this;
    }

    /** Нажать кнопку "Создать на основе текущей" */
    public PageDirectNetwork clickCopy() {
        buttonCopy.click();
        return this;
    }

    /** Ввести количество несущих проводов */
    public PageDirectNetwork inputFeederWireQuantity(int value) {
        inputFeederQuantity.sendKeys(String.valueOf(value));
        return this;
    }

    /** Ввести количество несущих проводов */
    public PageDirectNetwork inputFeederWireQuantity(String value) {
        inputFeederQuantity.sendKeys(value);
        return this;
    }

    /** Ввести количество контактных проводов */
    public PageDirectNetwork inputContactWireQuantity(int value) {
        inputContactWireQuantity.sendKeys(String.valueOf(value));
        return this;
    }

    /** Ввести количество контактных проводов */
    public PageDirectNetwork inputContactWireQuantity(String value) {
        inputContactWireQuantity.sendKeys(value);
        return this;
    }

    /** Ввести количество усиливающих проводов */
    public PageDirectNetwork inputPowerWireQuantity(int value) {
        inputPowerWireQuantity.sendKeys(String.valueOf(value));
        return this;
    }

    /** Ввести количество усиливающих проводов */
    public PageDirectNetwork inputPowerWireQuantity(String value) {
        inputPowerWireQuantity.sendKeys(value);
        return this;
    }

    /** Ввести количество путей */
    public PageDirectNetwork inputTrackQuantity(int value) {
        inputTrackQuantity.sendKeys(String.valueOf(value));
        return this;
    }

    /** Ввести количество путей */
    public PageDirectNetwork inputTrackQuantity(String value) {
        inputTrackQuantity.sendKeys(value);
        return this;
    }

    /** Выбрать марку питающего/несущего (в зависимости от активной вкладки) провода из выпадающего списка по названию */
    public PageDirectNetwork selectFeederByName(String value) {
        dropdownMenuFeederMark.selectByVisibleText(value == null ? "" : value);
        return this;
    }

    /** Выбрать марку контактного провода из выпадающего списка по названию */
    public PageDirectNetwork selectContactWireByName(String value) {
        dropdownMenuContactWireMark.selectByVisibleText(value == null ? "" : value);
        return this;
    }

    /** Выбрать марку усиливающего провода из выпадающего списка по названию */
    public PageDirectNetwork selectPowerWireByName(String value) {
        dropdownMenuPowerWireMark.selectByVisibleText(value == null ? "" : value);
        return this;
    }

    /** Выбрать марку пути из выпадающего списка по названию */
    public PageDirectNetwork selectTrackByName(String value) {
        dropdownMenuTrackMark.selectByVisibleText(value == null ? "" : value);
        return this;
    }

}
