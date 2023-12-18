package ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns;

import ru.vniizht.asuter.autotest.pages.transport.locomotives.PageLocomotive;

import static com.codeborne.selenide.Selenide.$$x;

/** Ряд соответствующий характеристикам рекуперативного торможения */
public class LocomotiveBreakingCharacteristicRow extends LocomotiveBaseCharacteristicRow {
    private final static String XPATH_TR_START = "//*[@id=\"rootContainer\"]/div[4]/div[2]/div[2]/div/table/tbody/tr[";

    public LocomotiveBreakingCharacteristicRow(PageLocomotive page, int rowNumber) {
        super(XPATH_TR_START, page, rowNumber);

    }

    /** Возвращает следующий ряд характеристик */
    @Override
    public LocomotiveBreakingCharacteristicRow nextRow() {
        return this.page.getBreakingCharacteristicRow(this.rowNumber + 1);
    }

    /** Нажать на кнопку Добавить. Возвращает добавленную строку */
    @Override
    public LocomotiveBaseCharacteristicRow clickAddAndReturnCreatedRow() {
        super.clickAdd();
        return new LocomotiveBreakingCharacteristicRow(this.page,this.rowNumber + 1);
    }

    /** Общее количество рядов характеристик рекуперативного торможения на странице в текущий момент времени */
    public static int totalCount() {
        return $$x(XPATH_TR_START).size();
    }

}
