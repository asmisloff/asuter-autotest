package ru.vniizht.asuter.autotest.pages.transport.cars;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;
import ru.vniizht.asuter.autotest.pages.BasePage;

import javax.annotation.Nonnull;
import java.util.Objects;

import static ru.vniizht.asuter.autotest.CommonOps.tr;

public class PageCar extends BasePage<PageCar> {

    @FindBy(xpath = "//input[@data-testid='name']")
    @As("Поле \"Наименование вагона\"")
    public SelenideElement nameInput;

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

    public PageCar inputName(String value) {
        nameInput.clear();
        nameInput.sendKeys(value);
        return this;
    }

    public PageCar inputNetMass(String value) {
        netMassInput.clear();
        netMassInput.sendKeys(value);
        return this;
    }

    public PageCar selectNumberOfAxles(String n) {
        numberOfAxlesSelect.selectOption(n);
        return this;
    }

    public PageCar inputContainerMass(String value) {
        containerMassInput.clear();
        containerMassInput.sendKeys(value);
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
