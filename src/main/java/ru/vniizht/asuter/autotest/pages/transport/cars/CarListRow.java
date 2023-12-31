package ru.vniizht.asuter.autotest.pages.transport.cars;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.vniizht.asuter.autotest.pages.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class CarListRow extends BasePage<CarListRow> {
    private static final String xpathTrStart = "//*[@id=\"rootContainer\"]/div/table/tbody/tr";
    private static final String xpathStart = xpathTrStart + "[";

    private final PageCarsList page;
    public SelenideElement carName;
    public SelenideElement numberOfAxles;
    public SelenideElement fullMass;
    public SelenideElement length;
    public SelenideElement carType;

    public CarListRow(PageCarsList page, int rowNumber) {
        this.page = page;
        this.carName = $x(xpathStart + rowNumber + "]/td[3]");
        this.numberOfAxles = $x(xpathStart + rowNumber + "]/td[5]");
        this.fullMass = $x(xpathStart + rowNumber + "]/td[6]");
        this.length = $x(xpathStart + rowNumber + "]/td[7]");
        this.carType = $x(xpathStart + rowNumber + "]/td[8]");
    }

    /** Кликнуть ПКМ на тякущем ряду списка */
    public PageCarsList contextClick() {
        carName.contextClick();
        return page;
    }

    /** Удалить вагон в текущем ряду списка */
    public PageCarsList delete() {
        contextClick();
        page.clickDelete().clickModalDeleteOk();
        return page;
    }

    /** Создать копию вагона из вагона в текущем ряду списка */
    public PageCarsList copy() {
        carName.contextClick();
        page.clickCopy();
        return page;
    }

    /** Открыть вагон из списка */
    public PageCar openCar() {
        carName.doubleClick();
        return Selenide.page(PageCar.class);
    }
}
