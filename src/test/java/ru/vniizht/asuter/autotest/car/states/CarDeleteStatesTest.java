package ru.vniizht.asuter.autotest.car.states;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.car.Car;
import ru.vniizht.asuter.autotest.car.CarBaseTest;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import static com.codeborne.selenide.Condition.*;

@DisplayName("Вагоны: Состояния при удалении")
public class CarDeleteStatesTest extends CarBaseTest {

    private static final String DELETE_CAR_NAME = "car-delete-autotest";
    static PageCarsList pageCarsList;

    @BeforeAll
    public static void setup() {
        loginIfNeeded();
        pageCarsList = open(PageCarsList.class);
        Car car = Car.builder()
                .name(DELETE_CAR_NAME)
                .length(VALID_LENGTH)
                .containerMass(CONTAINER_MASS_FOR_Q_GREATER_6)
                .coefficients(coefficients)
                .build();
        pageCarsList.clickCreate()
                .also(car::inputTo)
                .clickSave();
    }

    @QaseId(281)
    @Test
    @DisplayName("Модальное окно всплывает при удалении вагона")
    public void testDeleteOpensModal() {
        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(DELETE_CAR_NAME)
                .contextClick()
                .clickDelete()
                .check(p -> {
                    String expectedText = String.format("Удалить объект %s?", DELETE_CAR_NAME);
                    p.confirmModalText.shouldHave(text(expectedText));
                    p.okModalDeleteButton.should(exist);
                    p.cancelModalButton.should(exist);
                })
                .clickModalCancel();
    }

    @QaseId(283)
    @Test
    @DisplayName("Отмена действия в модальное окне при удалении вагона")
    public void testDeleteThenCancel() {
        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(DELETE_CAR_NAME)
                .contextClick()
                .clickDelete()
                .clickModalCancel()
                .check(p -> {
                    p.findCarRowByName(DELETE_CAR_NAME)
                            .check(r -> r.carName.shouldHave(text(DELETE_CAR_NAME)));
                });
    }

    @AfterAll
    public static void tearDown() {
        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(DELETE_CAR_NAME)
                .contextClick()
                .clickDelete()
                .clickModalDeleteOk();
    }
}
