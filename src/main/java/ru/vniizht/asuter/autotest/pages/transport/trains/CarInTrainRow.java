package ru.vniizht.asuter.autotest.pages.transport.trains;

import com.codeborne.selenide.SelenideElement;
import ru.vniizht.asuter.autotest.pages.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class CarInTrainRow extends BasePage<CarInTrainRow> {

    private final static String xpathRowStart = "//div[@id=\"rootContainer\"]/div/div[2]/div[2]/div/div[1]/div[2]/div/div";
    private final static String xpathStart = xpathRowStart + "[";
    private final PageTrain pageTrain;
    private final int rowNumber;
    public SelenideElement carNameSelect;
    public SelenideElement carTypeLabel;
    public SelenideElement numCarsInput;
    public SelenideElement carNumAxlesLabel;
    public SelenideElement carLengthLabel;
    public SelenideElement carWeightLabel;
    public SelenideElement carMassPerAxleLabel;
    public SelenideElement buttonAdd;
    public SelenideElement buttonRemove;

    public CarInTrainRow(PageTrain pageTrain, int rowNumber) {
        this.pageTrain = pageTrain;
        this.rowNumber = rowNumber;
        carNameSelect = $x(xpathStart + rowNumber + "]/div[2]/div/select");
        carTypeLabel = $x(xpathStart + rowNumber + "]/div[3]/div/input");
        numCarsInput = $x(xpathStart + rowNumber + "]/div[4]/div/input");
        carNumAxlesLabel = $x(xpathStart + rowNumber + "]/div[5]/div/input");
        carLengthLabel = $x(xpathStart + rowNumber + "]/div[6]/div/input");
        carWeightLabel = $x(xpathStart + rowNumber + "]/div[7]/div/input");
        carMassPerAxleLabel = $x(xpathStart + rowNumber + "]/div[8]/div/input");
        buttonAdd = $x(xpathStart + rowNumber + "]/div[9]/button[1]");
        buttonRemove = $x(xpathStart + rowNumber + "]/div[9]/button[2]");
    }

    public PageTrain getPageTrain() {
        return pageTrain;
    }

    public CarInTrainRow clickCarNameSelect() {
        carNameSelect.click();
        return this;
    }

    /** Выбрать наименование вагона */
    public CarInTrainRow selectCarName(String carName) {
        carNameSelect.selectOption(carName);
        return this;
    }

    /** Выбрать наименование вагона по индексу (нумерация с 0) */
    public CarInTrainRow selectCarNameByIndex(int index) {
        carNameSelect.selectOption(index);
        return this;
    }

    public CarInTrainRow inputNumCars(String value) {
        numCarsInput.clear();
        numCarsInput.sendKeys(value);
        return this;
    }

}
