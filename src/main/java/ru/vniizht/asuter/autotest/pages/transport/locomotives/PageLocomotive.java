package ru.vniizht.asuter.autotest.pages.transport.locomotives;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.pages.BasePage;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns.LocomotiveBreakingCharacteristicRow;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns.LocomotiveElectricalCharacteristicRow;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns.LocomotiveElectricalPositionRow;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns.LocomotiveThermalCharacteristicsColumn;

public class PageLocomotive extends BasePage<PageLocomotive> {

    private static final String BACKSPACE_3_TIMES = "\u0008\u0008\u0008";

    @FindBy(xpath = "//button[@id=\"saveBtn\"]")
    @As("Кнопка \"Сохранить\"")
    public SelenideElement saveButton;

    /* Элементы раздела "Основные параметры" */

    @FindBy(xpath = "//input[@data-name='name']")
    @As("Поле \"Серия, наименование\"")
    public SelenideElement nameInput;

    @FindBy(xpath = "//div[@id='rootContainer']/div/div[3]/div/div[2]/div[1]/div/input")
    @As("Радио-кнопка \"Род питающего тока. Постоянный\"")
    public SelenideElement dcRadio;

    @FindBy(xpath = "//div[@id='rootContainer']/div/div[3]/div/div[2]/div[2]/div/input")
    @As("Радио-кнопка \"Род питающего тока. Переменный\"")
    public SelenideElement acRadio;

    @FindBy(xpath = "//input[@data-name='length']")
    @As("Поле \"Длина\"")
    public SelenideElement lengthInput;

    @FindBy(xpath = "//select[@data-name='type']")
    @As("Выпадающий список \"Тип, назначение (род службы)\"")
    public SelenideElement typeSelect;

    @FindBy(xpath = "//input[@data-name='weight']")
    @As("Поле \"Масса учётная\"")
    public SelenideElement weightInput;

    @FindBy(xpath = "//input[@data-name='maxSpeed']")
    @As("Поле \"Конструкционная скорость\"")
    public SelenideElement maxSpeedInput;

    @FindBy(xpath = "//div[@id=\"rootContainer\"]/div[1]/div[1]/span/span")
    @As("Кнопка отображения/скрытия подраздела \"Дополнительные настройки\"")
    public SelenideElement additionalSettingsIcon;

    @FindBy(xpath = "//input[@data-name='power']")
    @As("Поле \"Мощность часового режима\"")
    public SelenideElement powerInput;

    @FindBy(xpath = "//input[@data-name='motorType']")
    @As("Поле \"Серия тягового электродвигателя\"")
    public SelenideElement motorTypeInput;

    @FindBy(xpath = "//input[@data-name='powerSelfConsumption']")
    @As("Поле \"Мощность собственных нужд\"")
    public SelenideElement powerSelfConsumptionInput;

    @FindBy(xpath = "//input[@data-name='amperageSelfConsumption']")
    @As("Поле \"Ток собственных нужд\"")
    public SelenideElement amperageSelfConsumptionInput;

    /* Элементы раздела "Коэффициенты основного удельного сопротивления движению" */

    @FindBy(xpath = "//h4[contains(.,'Коэффициенты основного удельного сопротивления движению')]")
    @As("Заголовок раздела \"Коэффициенты основного удельного сопротивления движению\"")
    public SelenideElement coefficientsOfResistanceHeader;

    @FindBy(xpath="//td[3]/div/input")
    @As("Поле коэффициента \"Звеньевой путь Wo B\"")
    public SelenideElement coefficientComponentRailWoB;

    @FindBy(xpath="//td[4]/div/input")
    @As("Поле коэффициента \"Звеньевой путь Wo C\"")
    public SelenideElement coefficientComponentRailWoC;

    @FindBy(xpath="//td[5]/div/input")
    @As("Поле коэффициента \"Звеньевой путь Wo D\"")
    public SelenideElement coefficientComponentRailWoD;

    @FindBy(xpath="//td[2]/div/input")
    @As("Поле коэффициента \"Звеньевой путь Wx B\"")
    public SelenideElement coefficientComponentRailWxB;

    @FindBy(xpath="//tr[2]/td[3]/div/input")
    @As("Поле коэффициента \"Звеньевой путь Wx C\"")
    public SelenideElement coefficientComponentRailWxC;

    @FindBy(xpath="//tr[2]/td[4]/div/input")
    @As("Поле коэффициента \"Звеньевой путь Wx D\"")
    public SelenideElement coefficientComponentRailWxD;

    @FindBy(xpath = "//tr[4]/td[3]/div/input")
    @As("Поле коэффициента \"Бесстыковой путь Wo B\"")
    public SelenideElement coefficientContinuousRailWoB;

    @FindBy(xpath = "//tr[4]/td[4]/div/input")
    @As("Поле коэффициента \"Бесстыковой путь Wo C\"")
    public SelenideElement coefficientContinuousRailWoC;

    @FindBy(xpath = "//tr[4]/td[5]/div/input")
    @As("Поле коэффициента \"Бесстыковой путь Wo D\"")
    public SelenideElement coefficientContinuousRailWoD;

    @FindBy(xpath = "//tr[5]/td[2]/div/input")
    @As("Поле коэффициента \"Бесстыковой путь Wx B\"")
    public SelenideElement coefficientContinuousRailWxB;

    @FindBy(xpath = "//tr[5]/td[3]/div/input")
    @As("Поле коэффициента \"Бесстыковой путь Wx C\"")
    public SelenideElement coefficientContinuousRailWxC;

    @FindBy(xpath = "//tr[5]/td[4]/div/input")
    @As("Поле коэффициента \"Бесстыковой путь Wx D\"")
    public SelenideElement coefficientContinuousRailWxD;

    /* Элементы раздела "Характеристики тягового режима" */

    @FindBy(xpath = "//h4[contains(.,'Характеристики тягового режима')]")
    @As("Заголовок раздела \"Характеристики тягового режима\"")
    public SelenideElement tractionCharacteristicsHeader;

    @FindBy(xpath = "//*[@id=\"#modeProps\"]/h5/div/select")
    @As("Выбор отображения зависимостей на графике характеристик тягового режима")
    public SelenideElement selectModePlotType;

    /* Элементы раздела "Характеристики рекуперативного торможения" */

    @FindBy(xpath = "//h4[contains(.,'Характеристики рекуперативного торможения')]")
    @As("Заголовок раздела \"Характеристики рекуперативного торможения\"")
    public SelenideElement regenerativeBrakingCharacteristicsHeader;

    @FindBy(xpath = "//*[@id=\"rootContainer\"]/div[4]/div[2]/div[1]/table/tbody/tr[1]/td/div/input")
    @As("Наименование позиции \"Огранич. рекупер.\"")
    public SelenideElement limitBreaking;

    @FindBy(xpath = "//*[@id=\"rootContainer\"]/div[4]/div[2]/div[1]/table/tbody/tr[2]/td/div/input")
    @As("Наименование позиции \"Макс. рекупер.\"")
    public SelenideElement maxBreaking;

    @FindBy(xpath = "//*[@id=\"#brakeProps\"]/h5/div/select")
    @As("Выбор отображения зависимостей на графике характеристик рекуперативного торможения")
    public SelenideElement selectBrakePlotType;

    /* Элементы раздела "Тепловые характеристики двигателя" */
    @FindBy(xpath = "//h4[contains(.,'Тепловые характеристики двигателя')]")
    @As("Заголовок раздела \"Тепловые характеристики двигателя\"")
    public SelenideElement engineThermalCharacteristicsHeader;

    @FindBy(xpath = "//input[@data-name='overheatTolerance']")
    @As("Поле \"Допустимое превышение температуры обмоток тягового электродвигателя\"")
    public SelenideElement overheatToleranceInput;

    @FindBy(xpath = "//input[@data-name='thermalTimeConstant']")
    @As("Поле \"Тепловая постоянная времени\"")
    public SelenideElement thermalTimeConstantInput;

    // @FindBy(xpath = "//*[@id=\"actionCell\"]/button[1]")  // выбор элемента по ID не работает - они не уникальны!
    @FindBy(xpath = "/html/body/div/div[2]/div[1]/div/div[2]/div[2]/div[5]/div[2]/div[2]/table/tbody/tr/td[3]/button[1]")
    @As("Кнопка добавления столбца тепловых характеристик двигателя")
    public SelenideElement addEngineThermalCharacteristicsFirstButton;

    /** Ввести наименование локомотива */
    public PageLocomotive inputName(String value) {
        typeIn(nameInput, value);
        return this;
    }

    /** Выбрать постоянный питающий ток */
    public PageLocomotive clickDcRadio() {
        dcRadio.click();
        return this;
    }

    /** Выбрать переменный питающий ток */
    public PageLocomotive clickAcRadio() {
        acRadio.click();
        return this;
    }

    /** Ввести длину локомотива */
    public PageLocomotive inputLength(String value) {
        typeIn(lengthInput, value);
        return this;
    }

    /**
     * Выбрать тип локомотива
     * @param value одно из значений: "Электропоезд", "Электровоз пассажирский", "Электровоз грузовой"
     */
    public PageLocomotive selectType(String value) {
        typeSelect.selectOption(value);
        return this;
    }

    /** Ввести массу учетную */
    public PageLocomotive inputWeight(String value) {
        typeIn(weightInput, value);
        return this;
    }

    /** Ввести конструкционную скорость */
    public PageLocomotive inputMaxSpeed(String value) {
        typeIn(maxSpeedInput, value);
        return this;
    }

    /** Клик на кнопке отображения/скрытия подраздела "Дополнительные настройки" */
    public PageLocomotive clickAdditionalSettingsIcon() {
        additionalSettingsIcon.click();
        return this;
    }

    /** Ввести мощность часового режима */
    public PageLocomotive inputPower(String value) {
        typeIn(powerInput, value);
        return this;
    }

    /** Ввести серию тягового электродвигателя */
    public PageLocomotive inputMotorType(String value) {
        typeIn(motorTypeInput, value);
        return this;
    }

    /** Ввести мощность собственных нужд */
    public PageLocomotive inputPowerSelfConsumption(String value) {
        typeIn(powerSelfConsumptionInput, value);
        return this;
    }

    /** Ввести ток собственных нужд */
    public PageLocomotive inputAmperageSelfConsumption(String value) {
        typeIn(amperageSelfConsumptionInput, value);
        return this;
    }

    /** Клик на заголовке "Коэффициенты основного удельного сопротивления движению" */
    public PageLocomotive clickCoefficientsOfResistanceHeader() {
        coefficientsOfResistanceHeader.click();
        return this;
    }

    /**
     * Ввод коэффициентов основного удельного сопротивления движению.
     * @param coefficients список из значений 12 коэффициентов
     */
    public PageLocomotive inputCoefficientsOfResistance(String[] coefficients) {
        if (coefficients.length != 12) {
            throw new IllegalArgumentException("Предполагается передача 12 коэффициентов");
        }
        typeIn(coefficientComponentRailWoB, coefficients[0]);
        typeIn(coefficientComponentRailWoC, coefficients[1]);
        typeIn(coefficientComponentRailWoD, coefficients[2]);
        typeIn(coefficientComponentRailWxB, coefficients[3]);
        typeIn(coefficientComponentRailWxC, coefficients[4]);
        typeIn(coefficientComponentRailWxD, coefficients[5]);
        typeIn(coefficientContinuousRailWoB, coefficients[6]);
        typeIn(coefficientContinuousRailWoC, coefficients[7]);
        typeIn(coefficientContinuousRailWoD, coefficients[8]);
        typeIn(coefficientContinuousRailWxB, coefficients[9]);
        typeIn(coefficientContinuousRailWxC, coefficients[10]);
        typeIn(coefficientContinuousRailWxD, coefficients[11]);
        return this;
    }

    /** Клик на заголовке "Характеристики тягового режима" */
    public PageLocomotive clickTractionCharacteristicsHeader() {
        tractionCharacteristicsHeader.click();
        return this;
    }

    /**
     * Получить ряд соответствующий позиции характеристик тягового режима.
     * @param rowNumber номер ряда (нумерация начинается с 1)
     */
    public LocomotiveElectricalPositionRow getElectricalPositionRow(int rowNumber) {
        return new LocomotiveElectricalPositionRow(this, rowNumber);
    }

    /** Получить последний ряд характеристик тягового режима */
    public LocomotiveElectricalPositionRow getLastElectricalPositionRow() {
        return new LocomotiveElectricalPositionRow(this);
    }

    /**
     * Получить ряд соответствующий ряду характеристик тягового режима.
     * @param rowNumber номер ряда (нумерация начинается с 1)
     */
    public LocomotiveElectricalCharacteristicRow getElectricalCharacteristicRow(int rowNumber) {
        return new LocomotiveElectricalCharacteristicRow(this, rowNumber);
    }

    /**
     * Получить ряд соответствующий ряду характеристик рекуперативного торможения.
     * @param rowNumber номер ряда (нумерация начинается с 1)
     */
    public LocomotiveBreakingCharacteristicRow getBreakingCharacteristicRow(int rowNumber) {
        return new LocomotiveBreakingCharacteristicRow(this, rowNumber);
    }

    /** Клик на заголовке "Характеристики рекуперативного торможения" */
    public PageLocomotive clickRegenerativeBrakingCharacteristicsHeader() {
        regenerativeBrakingCharacteristicsHeader.click();
        return this;
    }

    /** Клик на наименовании позиции "Огранич. рекупер." */
    public PageLocomotive clickLimitBreaking() {
        limitBreaking.click();
        return this;
    }

    /** Клик на наименовании позиции "Макс. рекупер." */
    public PageLocomotive clickMaxBreaking() {
        maxBreaking.click();
        return this;
    }

    /** Клик на наименовании заголовке раздела "Тепловые характеристики двигателя" */
    public PageLocomotive clickEngineThermalCharacteristicsHeader() {
        engineThermalCharacteristicsHeader.click();
        return this;
    }

    /** Ввести допустимое превышение температуры обмоток тягового электродвигателя */
    public PageLocomotive inputOverheatToleranceInput(String value) {
        // overheatToleranceInput.clear(); // по необъяснимой причине после вызова clear() поле не очищается
        overheatToleranceInput.sendKeys(BACKSPACE_3_TIMES); // поэтому шлем backspace 3 раза
        overheatToleranceInput.sendKeys(value);
        return this;
    }

    /** Ввести тепловую постоянную времени */
    public PageLocomotive inputThermalTimeConstantInput(String value) {
        // thermalTimeConstantInput.clear();
        thermalTimeConstantInput.sendKeys(BACKSPACE_3_TIMES);
        thermalTimeConstantInput.sendKeys(value);
        return this;
    }

    /** Клик для добавления столбца тепловых характеристик двигателя */
    public PageLocomotive clickToAddThermalCharacteristicsColumn() {
        this.addEngineThermalCharacteristicsFirstButton.click();
        return this;
    }

    /**
     * Получить столбец соответствующий столбцу характеристик рекуперативного торможения.
     * @param columnNumber номер столбца (нумерация начинается с 1)
     */
    public LocomotiveThermalCharacteristicsColumn getThermalCharacteristicsColumn(int columnNumber) {
        return new LocomotiveThermalCharacteristicsColumn(this, columnNumber);
    }

    /** Нажать кнопку Сохранить */
    public PageLocomotive clickSave() {
        saveButton.click();
        return this;
    }
}
