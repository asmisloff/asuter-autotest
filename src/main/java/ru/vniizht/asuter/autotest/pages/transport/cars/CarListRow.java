package ru.vniizht.asuter.autotest.pages.transport.cars;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.vniizht.asuter.autotest.pages.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class CarListRow extends BasePage<CarListRow> {
    private static final String xpathTrStart = "//*[@id=\"rootContainer\"]/div/table/tbody/tr";
    private static final String xpathStart = xpathTrStart + "[";

    private final PageCarsList page;
    public SelenideElement absoluteRowNumber;
    public SelenideElement carName;
    public SelenideElement numberOfAxles;
    public SelenideElement fullMass;
    public SelenideElement length;
    public SelenideElement carType;
    public SelenideElement changeTime;

    /**
     * Инициализирует экземпляр строки страницы Вагонов
     * @param page страница Вагонов
     * @param rowNumber номер строки (ряда) на странице, начиная с 1
     */
    public CarListRow(PageCarsList page, int rowNumber) {
        this.page = page;
        this.absoluteRowNumber = $x(xpathStart + rowNumber + "]/td[1]");
        this.carName = $x(xpathStart + rowNumber + "]/td[2]");
        this.numberOfAxles = $x(xpathStart + rowNumber + "]/td[3]");
        this.fullMass = $x(xpathStart + rowNumber + "]/td[4]");
        this.length = $x(xpathStart + rowNumber + "]/td[5]");
        this.carType = $x(xpathStart + rowNumber + "]/td[6]");
        this.changeTime = $x(xpathStart + rowNumber + "]/td[7]");
    }

    public PageCarsList getPage() {
        return page;
    }

    /** Кликнуть ПКМ на текущем ряду списка */
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

    /** Получить текст абсолютного номера строки вагона */
    public String getAbsoluteRowNumber() {
        return absoluteRowNumber.text();
    }

    /** Получить текст наименования вагона */
    public String getNameText() {
        return carName.text();
    }

    public String tryToGetNameText() {
        String name = null;
        int attemptCount = 0;
        while (name == null) {
            try {
                name = carName.text();
            } catch (org.openqa.selenium.NoSuchElementException ex) {
                attemptCount++;
                if (attemptCount == 5) {
                    throw new RuntimeException("Элемент carName не найден после 5 попыток", ex);
                }
                waitTime(100);
            }
        }
        return name;
    }

    /** Получить текст числа осей вагона */
    public String getNumberOfAxlesText() {
        return numberOfAxles.text();
    }

    /** Получить текст учетной массы брутто вагона */
    public String getFullMassText() {
        return fullMass.text();
    }

    /** Получить текст длины вагона */
    public String getLengthText() {
        return length.text();
    }

    /** Получить текст типа вагона */
    public String getCarTypeText() {
        return carType.text();
    }

    /** Получить текст времени редактирования вагона */
    public String getChangeTimeText() {
        return changeTime.text();
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s",
                getNameText(), getNumberOfAxlesText(), getFullMassText(), getLengthText(), getCarTypeText(), getChangeTimeText());
    }
}
