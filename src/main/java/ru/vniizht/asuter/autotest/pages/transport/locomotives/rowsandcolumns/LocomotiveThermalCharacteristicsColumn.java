package ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns;

import com.codeborne.selenide.SelenideElement;
import ru.vniizht.asuter.autotest.pages.BasePage;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.PageLocomotive;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Столбец тепловых характеристик двигателя.
 */
public class LocomotiveThermalCharacteristicsColumn extends BasePage<LocomotiveThermalCharacteristicsColumn> {

    private static final String xpathTrStart = "//*[@id=\"heat\"]/table/tbody/tr";
    private static final String xpathStart = xpathTrStart + "[";
    protected final PageLocomotive page;

    /** Номер строки (которая визуально на странице выглядит как столбец) */
    protected final int rowNumber;

    public SelenideElement motorAmperage;
    public SelenideElement balancingOverheat;

    /**
     * Создает новый экземпляр столбца тепловых характеристик двигателя.
     * @param page контроллер текущей страницы
     * @param columnNumber номер столбца (счет начинается с 1)
     */
    public LocomotiveThermalCharacteristicsColumn(PageLocomotive page, int columnNumber) {
        this.page = page;
        this.rowNumber = columnNumber; // визуально это столбец, но структурно это строка таблицы
        this.motorAmperage = $x(xpathStart + columnNumber + "]/td[1]/div/input");
        this.balancingOverheat = $x(xpathStart + columnNumber + "]/td[2]/div/input");
    }

    /** Контроллер страницы, в которой выбран данный столбец характеристик */
    public PageLocomotive page() {
        return this.page;
    }

    public LocomotiveThermalCharacteristicsColumn nextColumn() {
        return new LocomotiveThermalCharacteristicsColumn(this.page, this.rowNumber + 1);
    }

    /** Ввести ток тягового электродвигателя */
    public LocomotiveThermalCharacteristicsColumn inputMotorAmperage(String value) {
        motorAmperage.clear();
        motorAmperage.sendKeys(value);
        return this;
    }

    /** Ввести установившееся превышение температуры обмоток тягового электродвигателя */
    public LocomotiveThermalCharacteristicsColumn inputBalancingOverheat(String value) {
        balancingOverheat.clear();
        balancingOverheat.sendKeys(value);
        return this;
    }
}
