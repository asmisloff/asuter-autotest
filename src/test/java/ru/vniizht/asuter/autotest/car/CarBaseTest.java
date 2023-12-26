package ru.vniizht.asuter.autotest.car;

import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

public class CarBaseTest extends BaseTest {
    protected static final String CAR_NAME = "car_save_autotest";
    protected static final String CONTAINER_MASS_FOR_Q_GREATER_6 = "50";
    protected static final String VALID_LENGTH = "1";
    protected static final String[] coefficients = {"1", "1", "1", "1", "1", "1", "1", "1"};

    protected static void deleteCarByName(String name) {
        loginIfNeeded();
        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(name)
                .delete();
    }
}
