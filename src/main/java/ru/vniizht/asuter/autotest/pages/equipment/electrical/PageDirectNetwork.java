package ru.vniizht.asuter.autotest.pages.equipment.electrical;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import ru.vniizht.asuter.autotest.pages.BasePage;
import ru.vniizht.asuter.autotest.utils.NameResolver;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class PageDirectNetwork extends BasePage<PageDirectNetwork> {

    @FindBy(xpath = "//button[@data-testid='saveBtn']")
    public SelenideElement buttonSave;

    @FindBy(xpath = "//button[@data-testid='calculateBtn']")
    public SelenideElement buttonCalculate;

    @FindBy(xpath = "//button[contains(@title, 'на')]")
    public SelenideElement buttonCopy;

    @FindBy(xpath = "//input[@data-testid='fiderCount']")
    public SelenideElement inputFeederQuantity;

    public Select dropdownMenuFeederMark = new Select($x("//select[@data-testid='fiderId']"));

    @FindBy(xpath = "//input[@data-testid='contactWireCount']")
    public SelenideElement inputContactWireQuantity;

    public Select dropdownMenuContactWireMark = new Select($x("//select[@data-testid='contactWireId']"));

    @FindBy(xpath = "//input[@data-testid='powerWireCount']")
    public SelenideElement inputPowerWireQuantity;

    public Select dropdownMenuPowerWireMark = new Select($x("//select[@data-testid='powerWireId']"));

    @FindBy(xpath = "//input[@data-testid='railCount']")
    public SelenideElement inputTrackQuantity;

    public Select dropdownMenuTrackMark = new Select($x("//select[@data-testid='railId']"));

    @FindBy(xpath = "//*[@data-testid='supplyLineBtn']")
    public SelenideElement buttonSupplyLine;

    @FindBy(xpath = "//*[@data-testid='tractiveNetworkBtn']")
    public SelenideElement buttonTractionNetwork;

    @FindBy(xpath = "//input[@data-testid='wiresResistance']")
    public SelenideElement calculatedRks;

    @FindBy(xpath = "//input[@data-testid='railsResistance']")
    public SelenideElement calculatedRp;

    @FindBy(xpath = "//input[@data-testid='limitAmperage']")
    public SelenideElement calculatedIdop;

    @FindBy(xpath = "//input[@data-testid='limitTemperature']")
    public SelenideElement calculatedTdl;

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
        return inputFeederWireQuantity(String.valueOf(value));
    }

    /** Ввести количество несущих проводов */
    public PageDirectNetwork inputFeederWireQuantity(String value) {
        inputFeederQuantity.clear();
        inputFeederQuantity.sendKeys(value);
        return this;
    }

    /** Ввести количество контактных проводов */
    public PageDirectNetwork inputContactWireQuantity(int value) {
        return inputContactWireQuantity(String.valueOf(value));
    }

    /** Ввести количество контактных проводов */
    public PageDirectNetwork inputContactWireQuantity(String value) {
        inputContactWireQuantity.clear();
        inputContactWireQuantity.sendKeys(value);
        return this;
    }

    /** Ввести количество усиливающих проводов */
    public PageDirectNetwork inputPowerWireQuantity(int value) {
        return inputPowerWireQuantity(String.valueOf(value));
    }

    /** Ввести количество усиливающих проводов */
    public PageDirectNetwork inputPowerWireQuantity(String value) {
        inputPowerWireQuantity.clear();
        inputPowerWireQuantity.sendKeys(value);
        return this;
    }

    /** Ввести количество путей */
    public PageDirectNetwork inputTrackQuantity(int value) {
        return inputTrackQuantity(String.valueOf(value));
    }

    /** Ввести количество путей */
    public PageDirectNetwork inputTrackQuantity(String value) {
        inputTrackQuantity.clear();
        inputTrackQuantity.sendKeys(value);
        return this;
    }

    /** Выбрать марку питающего/несущего (в зависимости от активной вкладки) провода из выпадающего списка по названию */
    public PageDirectNetwork selectFeederByName(String value) {
        dropdownMenuFeederMark.selectByVisibleText(value == null ? "" : value);
        return this;
    }

    /** Выбрать первую из списка марку питающего/несущего (в зависимости от активной вкладки) провода из выпадающего списка по названию */
    public PageDirectNetwork selectFirstFeeder() {
        var name = dropdownMenuFeederMark.getOptions().stream()
                .map(WebElement::getText)
                .filter(text -> !text.isBlank())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Нет доступных питающих/несущих проводов, чтобы выбрать из списка"));
        selectFeederByName(name);
        return this;
    }

    /** Выбрать марку контактного провода из выпадающего списка по названию */
    public PageDirectNetwork selectContactWireByName(String value) {
        dropdownMenuContactWireMark.selectByVisibleText(value == null ? "" : value);
        return this;
    }

    /** Выбрать первую из списка марку контактного (в зависимости от активной вкладки) провода из выпадающего списка по названию */
    public PageDirectNetwork selectFirstContactWire() {
        var name = dropdownMenuContactWireMark.getOptions().stream()
                .map(WebElement::getText)
                .filter(text -> !text.isBlank())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Нет доступных контактных проводов, чтобы выбрать из списка"));
        selectContactWireByName(name);
        return this;
    }

    /** Выбрать марку усиливающего провода из выпадающего списка по названию */
    public PageDirectNetwork selectPowerWireByName(String value) {
        dropdownMenuPowerWireMark.selectByVisibleText(value == null ? "" : value);
        return this;
    }

    /** Выбрать первую из списка марку усиливающего (в зависимости от активной вкладки) провода из выпадающего списка по названию */
    public PageDirectNetwork selectFirstPowerWire() {
        var name = dropdownMenuPowerWireMark.getOptions().stream()
                .map(WebElement::getText)
                .filter(text -> !text.isBlank())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Нет доступных усиливающих проводов, чтобы выбрать из списка"));
        selectPowerWireByName(name);
        return this;
    }

    /** Выбрать марку пути из выпадающего списка по названию */
    public PageDirectNetwork selectTrackByName(String value) {
        dropdownMenuTrackMark.selectByVisibleText(value == null ? "" : value);
        return this;
    }

    /** Выбрать первую из списка марку пути (в зависимости от активной вкладки) провода из выпадающего списка по названию */
    public PageDirectNetwork selectFirstTrack() {
        var name = dropdownMenuTrackMark.getOptions().stream()
                .map(WebElement::getText)
                .filter(text -> !text.isBlank())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Нет доступных путей, чтобы выбрать из списка"));
        selectTrackByName(name);
        return this;
    }

    /** Переключиться на вкладку "Питающая линия" */
    public PageDirectNetwork switchToSupplyLine() {
        buttonSupplyLine.click();
        return this;
    }

    /** Переключиться на вкладку "Тяговая сеть" */
    public PageDirectNetwork switchToTractionNetwork() {
        buttonTractionNetwork.click();
        return this;
    }

    public boolean isSupplyLine() {
        return buttonSupplyLine.parent().has(cssClass("active"));
    }

    /** Получить имя тяговой сети, которое будет сгенерировано на основе введенных на странице данных */
    public String resolveNameByFields() {
        String feederCountText = inputFeederQuantity.val();
        var feederCount = feederCountText == null || feederCountText.isBlank() ? 0 : Integer.parseInt(feederCountText);
        String contactWireCountText = inputContactWireQuantity.val();
        var contactWireCount = contactWireCountText == null || contactWireCountText.isBlank() ? 0 : Integer.parseInt(contactWireCountText);
        String powerWireCountText = inputPowerWireQuantity.val();
        var powerWireCount = powerWireCountText == null || powerWireCountText.isBlank() ? 0 : Integer.parseInt(powerWireCountText);
        String trackCountText = inputTrackQuantity.val();
        var trackCount = trackCountText == null || trackCountText.isBlank() ? 0 : Integer.parseInt(trackCountText);
        return NameResolver.resolveNameForNetworkDC(
                feederCount,
                dropdownMenuFeederMark.getFirstSelectedOption().getText(),
                contactWireCount,
                dropdownMenuContactWireMark.getFirstSelectedOption().getText(),
                powerWireCount,
                dropdownMenuPowerWireMark.getFirstSelectedOption().getText(),
                trackCount,
                dropdownMenuTrackMark.getFirstSelectedOption().getText()
        );
    }

}
