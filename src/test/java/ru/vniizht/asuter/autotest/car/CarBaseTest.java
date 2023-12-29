package ru.vniizht.asuter.autotest.car;

import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

public class CarBaseTest extends BaseTest {
    protected static final String CAR_NAME = "car_save_autotest";
    protected static final String CONTAINER_MASS_FOR_Q_GREATER_6 = "50";
    protected static final String VALID_LENGTH = "1";
    protected static final String[] coefficients = {"1", "1", "1", "1", "1", "1", "1", "1"};


    /**
     * Создает экземпляр вагона, имя которого имеет суффикс в виде номера тест кейса,
     * что в случае поломки теста создавшего данный вагон и не удалившего его
     * позволяет избежать падения других тестов из-за повторяемости имен вагонов.
     * @param caseNumber номер текст кейса
     */
    protected static Car createTestCarForCase(int caseNumber) {
        return Car.builder()
                .name(String.format("%s_case-%d", CAR_NAME, caseNumber))
                .length(VALID_LENGTH)
                .containerMass(CONTAINER_MASS_FOR_Q_GREATER_6)
                .coefficients(coefficients)
                .build();
    }
}
