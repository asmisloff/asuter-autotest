package ru.vniizht.asuter.autotest.pages.equipment.electrical;

import com.codeborne.selenide.As;
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
    @As("Кнопка сохранить")
    public SelenideElement buttonSave;

    @FindBy(xpath = "//button[@data-testid='calculateBtn']")
    @As("Кнопка рассчитать")
    public SelenideElement buttonCalculate;

    @FindBy(xpath = "//button[contains(@title, 'на')]")
    @As("Кнопка создать на основе текущей")
    public SelenideElement buttonCopy;

    @FindBy(xpath = "//input[@data-testid='fiderCount']")
    @As("Поле количества питающих/несущих проводов")
    public SelenideElement inputFeederQuantity;

    public Select dropdownMenuFeederMark = new Select($x("//select[@data-testid='fiderId']").as("Марка питающего/несущего провода"));

    @FindBy(xpath = "//input[@data-testid='contactWireCount']")
    @As("Поле количества контактных проводов")
    public SelenideElement inputContactWireQuantity;

    public Select dropdownMenuContactWireMark = new Select($x("//select[@data-testid='contactWireId']").as("Марка контактного провода"));

    @FindBy(xpath = "//input[@data-testid='powerWireCount']")
    @As("Поле количества усиливающих проводов")
    public SelenideElement inputPowerWireQuantity;

    public Select dropdownMenuPowerWireMark = new Select($x("//select[@data-testid='powerWireId']").as("Марка усиливающего провода"));

    @FindBy(xpath = "//input[@data-testid='railCount']")
    @As("Поле количества путей")
    public SelenideElement inputTrackQuantity;

    public Select dropdownMenuTrackMark = new Select($x("//select[@data-testid='railId']").as("Марка путей"));

    @FindBy(xpath = "//*[@data-testid='supplyLineBtn']")
    @As("Кнопка переключения на вкладку \"Питающая линия\"")
    public SelenideElement buttonSupplyLine;

    @FindBy(xpath = "//*[@data-testid='tractiveNetworkBtn']")
    @As("Кнопка переключения на вкладку \"Тяговая сеть\"")
    public SelenideElement buttonTractionNetwork;

    @FindBy(xpath = "//input[@data-testid='wiresResistance']")
    @As("Расчетные данные сопротивления контактной подвески")
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

    @FindBy(xpath = "/html/body/div/div[2]/div[4]/div/div/div/button")
    @As("Кнопка \"X\" (закрыть всплывающее окно)")
    public WebElement buttonAlertClose;


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

    /** Нажать на кнопку "Нет" во всплывающем модальном окне */
    public PageDirectNetwork clickCancel() {
        buttonConfirmationWindowCancel.click();
        return this;
    }

    /** Нажать на кнопку "Да" во всплывающем модальном окне */
    public PageDirectNetwork clickConfirm() {
        buttonConfirmationWindowAccept.click();
        return this;
    }

    /** Нажать на кнопку закрытия оповещения */
    public PageDirectNetwork clickCloseAlert() {
        buttonAlertClose.click();
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
