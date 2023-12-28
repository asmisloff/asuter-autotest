package ru.vniizht.asuter.autotest.pages.transport.trains;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class TrainListRow {
    private static final String xpathTrStart = "//div[@id=\"rootContainer\"]/div/table/tbody/tr";
    private static final String xpathStart = xpathTrStart + "[";

    private final PageTrainsList page;

    public SelenideElement trainName;
    public SelenideElement numCars;
    public SelenideElement numAxles;
    public SelenideElement weight;
    public SelenideElement length;
    public SelenideElement brakePadType;
    public SelenideElement pressingForceOnBrakePad;
    public SelenideElement powerOfUndercarGenerator;
    public SelenideElement changeTime;

    public TrainListRow(PageTrainsList page, int rowNumber) {
        this.page = page;
        trainName = $x(xpathStart + rowNumber + "]/td[3]");
        numCars = $x(xpathStart + rowNumber + "]/td[4]");
        numAxles = $x(xpathStart + rowNumber + "]/td[5]");
        weight = $x(xpathStart + rowNumber + "]/td[6]");
        length = $x(xpathStart + rowNumber + "]/td[7]");
        brakePadType = $x(xpathStart + rowNumber + "]/td[8]");
        pressingForceOnBrakePad = $x(xpathStart + rowNumber + "]/td[9]");
        powerOfUndercarGenerator = $x(xpathStart + rowNumber + "]/td[10]");
        changeTime = $x(xpathStart + rowNumber + "]/td[11]");
    }

    /** Кликнуть ПКМ на тякущем ряду списка */
    public PageTrainsList contextClick() {
        trainName.contextClick();
        return page;
    }

    /** Удалить состав в текущем ряду списка */
    public PageTrainsList delete() {
        trainName.contextClick();
        page.clickDelete().clickModalDeleteOk();
        return page;
    }

    /** Создать копию состава из состава в текущем ряду списка */
    public PageTrainsList copy() {
        trainName.contextClick();
        page.clickCopy();
        return page;
    }

    /** Открыть состав из списка */
    public PageTrain openTrain() {
        trainName.doubleClick();
        return Selenide.page(PageTrain.class);
    }
}
