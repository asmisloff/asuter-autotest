package ru.vniizht.asuter.autotest.pages.transport.cars;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.pages.BasePage;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Objects;

import static ru.vniizht.asuter.autotest.CommonOps.tr;

public class PageCar extends BasePage<PageCar> {

    @FindBy(xpath = "//button[@id=\"saveBtn\"][@data-testid=\"saveBtn\"]")
    @As("Кнопка \"Сохранить\"")
    public SelenideElement saveButton;

    @FindBy(xpath = "//button[@id=\"saveBtn\"][@data-testid=\"editBtn\"]")
    @As("Кнопка \"Сохранить\"")
    public SelenideElement editButton;

    @FindBy(xpath = "//input[@data-testid='name']")
    @As("Поле \"Наименование вагона\"")
    public SelenideElement nameInput;

    @FindBy(xpath = "//select[@data-testid='type']")
    @As("Выпадающий список \"Тип\"")
    public SelenideElement typeSelect;

    @FindBy(xpath = "//input[@data-testid='selfWeight']")
    @As("Поле \"Масса тары\"")
    public SelenideElement containerMassInput;

    @FindBy(xpath = "//input[@data-testid='weight']")
    @As("Поле \"Масса брутто\"")
    public SelenideElement fullMassInput;

    @FindBy(xpath = "//input[@data-testid='netto']")
    @As("Поле \"Масса нетто\"")
    public SelenideElement netMassInput;

    @FindBy(xpath = "//input[@data-testid='singleAxleWeight']")
    @As("Поле \"Масса на ось\"")
    public SelenideElement massPerAxleInput;

    @FindBy(xpath = "//select[@data-testid='numberOfAxles']")
    @As("Поле \"Число осей\"")
    public SelenideElement numberOfAxlesSelect;

    @FindBy(xpath = "//input[@data-testid='length']")
    @As("Поле \"Длина по осям сцепления\"")
    public SelenideElement lengthInput;

    @FindBy(xpath = "//input[@data-testid='componentRailA']")
    @As("Поле \"Коэффициент А звеньевого пути\"")
    public SelenideElement componentRailAInput;

    @FindBy(xpath = "//input[@data-testid='componentRailB']")
    @As("Поле \"Коэффициент B звеньевого пути\"")
    public SelenideElement componentRailBInput;

    @FindBy(xpath = "//input[@data-testid='componentRailC']")
    @As("Поле \"Коэффициент C звеньевого пути\"")
    public SelenideElement componentRailCInput;

    @FindBy(xpath = "//input[@data-testid='componentRailD']")
    @As("Поле \"Коэффициент D звеньевого пути\"")
    public SelenideElement componentRailDInput;

    @FindBy(xpath = "//input[@data-testid='continuousRailA']")
    @As("Поле \"Коэффициент А бесстыкового пути\"")
    public SelenideElement continuousRailAInput;

    @FindBy(xpath = "//input[@data-testid='continuousRailB']")
    @As("Поле \"Коэффициент B бесстыкового пути\"")
    public SelenideElement continuousRailBInput;

    @FindBy(xpath = "//input[@data-testid='continuousRailC']")
    @As("Поле \"Коэффициент C бесстыкового пути\"")
    public SelenideElement continuousRailCInput;

    @FindBy(xpath = "//input[@data-testid='continuousRailD']")
    @As("Поле \"Коэффициент D бесстыкового пути\"")
    public SelenideElement continuousRailDInput;

    /** Ввести наименование вагона */
    public PageCar inputName(String value) {
        nameInput.clear();
        nameInput.sendKeys(value);
        return this;
    }

    /** Выбрать тип */
    public PageCar selectType(String type) {
        typeSelect.selectOption(type);
        return this;
    }

    /** Ввести массу нетто */
    public PageCar inputNetMass(String value) {
        netMassInput.clear();
        netMassInput.sendKeys(value);
        return this;
    }

    /** Выбрать число осей */
    public PageCar selectNumberOfAxles(String n) {
        numberOfAxlesSelect.selectOption(n);
        return this;
    }

    /** Ввести массу тары */
    public PageCar inputContainerMass(String value) {
        containerMassInput.clear();
        containerMassInput.sendKeys(value);
        return this;
    }

    /** Ввести длину по осям сцепления */
    public PageCar inputLength(String value) {
        lengthInput.clear();
        lengthInput.sendKeys(value);
        return this;
    }

    /** Ввести коэффициент A звеньевого пути */
    public PageCar inputComponentRailA(String value) {
        componentRailAInput.clear();
        componentRailAInput.sendKeys(value);
        return this;
    }

    /** Удалить коэффициент A звеньевого пути */
    public PageCar clearComponentRailA() {
        componentRailAInput.clear();
        return this;
    }

    /** Ввести коэффициент B звеньевого пути */
    public PageCar inputComponentRailB(String value) {
        componentRailBInput.clear();
        componentRailBInput.sendKeys(value);
        return this;
    }

    /** Ввести коэффициент C звеньевого пути */
    public PageCar inputComponentRailC(String value) {
        componentRailCInput.clear();
        componentRailCInput.sendKeys(value);
        return this;
    }

    /** Ввести коэффициент D звеньевого пути */
    public PageCar inputComponentRailD(String value) {
        componentRailDInput.clear();
        componentRailDInput.sendKeys(value);
        return this;
    }

    /** Ввести коэффициент A бесстыкового пути */
    public PageCar inputContinuousRailA(String value) {
        continuousRailAInput.clear();
        continuousRailAInput.sendKeys(value);
        return this;
    }

    /** Удалить коэффициент A бесстыкового пути */
    public PageCar clearContinuousRailA() {
        continuousRailAInput.clear();
        return this;
    }

    /** Ввести коэффициент B бесстыкового пути */
    public PageCar inputContinuousRailB(String value) {
        continuousRailBInput.clear();
        continuousRailBInput.sendKeys(value);
        return this;
    }

    /** Ввести коэффициент C бесстыкового пути */
    public PageCar inputContinuousRailC(String value) {
        continuousRailCInput.clear();
        continuousRailCInput.sendKeys(value);
        return this;
    }

    /** Ввести коэффициент D бесстыкового пути */
    public PageCar inputContinuousRailD(String value) {
        continuousRailDInput.clear();
        continuousRailDInput.sendKeys(value);
        return this;
    }

    public PageCar inputAllCoefficients(String[] values) {
        if (values.length != 8) {
            throw new IllegalArgumentException("Требуется передать 8 коэффициентов");
        }
        inputComponentRailA(values[0]);
        inputComponentRailB(values[1]);
        inputComponentRailC(values[2]);
        inputComponentRailD(values[3]);
        inputContinuousRailA(values[4]);
        inputContinuousRailB(values[5]);
        inputContinuousRailC(values[6]);
        inputContinuousRailD(values[7]);
        return this;
    }

    public PageCar fillAllCoefficients(String value) {
        String[] values = new String[8];
        Arrays.fill(values, value);
        inputAllCoefficients(values);
        return this;
    }

    /** Нажать кнопку Сохранить */
    public PageCar clickSave() {
        saveButton.click();
        return this;
    }

    /** Нажать кнопку Редактировать */
    public PageCar clickEdit() {
        editButton.click();
        return this;
    }

    public String numberOfAxles() {
        return Objects.requireNonNull(numberOfAxlesSelect.getSelectedOptionValue());
    }

    public String calculateMassPerAxle() {
        @Nonnull String fullWeightValue = Objects.requireNonNull(fullMassInput.getValue());
        return calculatedMassPerAxle(fullWeightValue, numberOfAxles());
    }

    /** Вычисляет массу на ось на основании значений полей "масса брутто" и "число осей". */
    public String calculateMassPerAxleFromExpectedWeight(String expectedWeight) {
        return calculatedMassPerAxle(Objects.requireNonNull(expectedWeight), numberOfAxles());
    }

    private String calculatedMassPerAxle(String fullWeight, String numberOfAxles) {
        if (fullWeight.isBlank()) {
            return "";
        }
        double w = Double.parseDouble(fullWeight.replace(',', '.').replace(" ", ""));
        int n = Integer.parseInt(numberOfAxles);
        return StringUtils.remove(tr(w / n, 3), ' ');
    }
}
