package ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns;

import ru.vniizht.asuter.autotest.pages.transport.locomotives.PageLocomotive;

import static com.codeborne.selenide.Selenide.$$x;

/** Ряд соответствующий одной из характеристик тягового режима. */
public class LocomotiveElectricalCharacteristicRow extends LocomotiveBaseCharacteristicRow {
    private final static String XPATH_TR_START = "//*[@id=\"rootContainer\"]/div[3]/div[2]/div[2]/div/table/tbody/tr";

    public LocomotiveElectricalCharacteristicRow(PageLocomotive page, int rowNumber) {
        super(XPATH_TR_START, page, rowNumber);
    }

    /** Возвращает следующий ряд характеристик */
    public LocomotiveElectricalCharacteristicRow nextRow() {
        return this.page.getElectricalCharacteristicRow(this.rowNumber + 1);
    }

    /** Нажать на кнопку Добавить. Возвращает добавленную строку */
    public LocomotiveElectricalCharacteristicRow clickAddAndReturnCreatedRow() {
        super.clickAdd();
        return new LocomotiveElectricalCharacteristicRow(this.page,this.rowNumber + 1);
    }

    /** Общее количество рядов характеристик тягового режима на странице в текущий момент времени */
    public static int totalCount() {
        return $$x(XPATH_TR_START).size();
    }

}
