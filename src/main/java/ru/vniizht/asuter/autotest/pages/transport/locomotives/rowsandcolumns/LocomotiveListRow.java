package ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.PageLocomotive;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.PageLocomotivesList;

import static com.codeborne.selenide.Selenide.$x;

public class LocomotiveListRow {
    private static final String xpathTrStart = "//*[@id=\"rootContainer\"]/div/table/tbody/tr";
    private static final String xpathStart = xpathTrStart + "[";

    private final PageLocomotivesList page;
    public SelenideElement locomotiveName;
    public SelenideElement currentType;
    public SelenideElement locomotiveType;

    public LocomotiveListRow(PageLocomotivesList page, int rowNumber) {
        this.page = page;
        this.locomotiveName = $x(xpathStart + rowNumber + "]/td[3]");
        this.currentType = $x(xpathStart + rowNumber + "]/td[5]");
        this.locomotiveType = $x(xpathStart + rowNumber + "]/td[6]");
    }

    /** Удалить локомотив в текущем ряду списка */
    public PageLocomotivesList delete() {
        locomotiveName.contextClick();
        page.clickDelete().clickModalDeleteOk();
        return page;
    }

    /** Создать копию локомотива из локомотива в текущем ряду списка */
    public PageLocomotivesList copy() {
        locomotiveName.contextClick();
        page.clickCopy();
        return page;
    }

    /** Открыть локомотив из списка */
    public PageLocomotive openLocomotive() {
        locomotiveName.doubleClick();
        return Selenide.page(PageLocomotive.class);
    }
}
