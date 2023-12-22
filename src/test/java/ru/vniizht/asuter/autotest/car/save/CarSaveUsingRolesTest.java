package ru.vniizht.asuter.autotest.car.save;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.car.Car;
import ru.vniizht.asuter.autotest.constants.User;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCar;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Вагоны: Сохранение, роли пользователей")
public class CarSaveUsingRolesTest extends CarBaseTest {

    private static final String EXPECTED_MESSAGE = "Запрос отклонён: действие недоступно для данной учетной записи." +
            " Для расширения прав обратитесь к администратору.";
    private static Car testCar;

    @BeforeAll
    public static void setup() {
        loginIfNeeded(User.NSI);
        testCar = Car.builder()
                .name(CAR_NAME)
                .length(VALID_LENGTH)
                .containerMass(CONTAINER_MASS_FOR_Q_GREATER_6)
                .coefficients(coefficients)
                .build();
        PageCar pageCar = open(PageCarsList.class).clickCreate();
        testCar.inputTo(pageCar);
        pageCar.clickSave();
        logout();
    }

    @Test
    @QaseId(277)
    @DisplayName("Невозможность сохранения вагона с правом просмотра данных")
    public void testNotSavingWithRoleRead() {
        loginIfNeeded(User.READ);
        Car car = Car.builder()
                .name("any_" + CAR_NAME)
                .length(VALID_LENGTH)
                .containerMass(CONTAINER_MASS_FOR_Q_GREATER_6)
                .coefficients(coefficients)
                .build();
        PageCar pageCar = open(PageCarsList.class).clickCreate();
        car.inputTo(pageCar);
        pageCar.clickSave()
                .check(p -> {
                    p.alertModalHeader.shouldHave(text(EXPECTED_MESSAGE));
                    p.alertModalCloseButton.click();
                });

        assertFalse(
                open(PageCarsList.class)
                        .waitTableLoading()
                        .clickDisplayAllPages()
                        .containsCarRowByName(car.name())
        );
    }

    @Test
    @QaseId(277)
    @DisplayName("Невозможность редактирования вагона с правом просмотра данных")
    public void testNotUpdatingWithRoleRead() {
        loginIfNeeded(User.READ);
        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(testCar.name())
                .openCar()
                .clickEdit()
                .inputName(testCar.name() + "_changed")
                .pressTab()
                .clickSave()
                .check(p -> {
                    p.alertModalHeader.shouldHave(text(EXPECTED_MESSAGE));
                    p.alertModalCloseButton.click();
                });

        open(PageCarsList.class)
                .waitTableLoading()
                .clickDisplayAllPages()
                .check(p -> {
                    p.findCarRowByName(testCar.name())
                            .check(r -> r.carName.shouldHave(text(testCar.name())));
                    assertFalse(p.containsCarRowByName(testCar.name() + "_changed"));
                });
    }

    @Test
    @QaseId(279)
    @DisplayName("Невозможность сохранения вагона с правом проведения расчетов")
    public void testNotSavingWithRoleCalc() {
        loginIfNeeded(User.CALC);
        Car car = Car.builder()
                .name("any_" + CAR_NAME)
                .length(VALID_LENGTH)
                .containerMass(CONTAINER_MASS_FOR_Q_GREATER_6)
                .coefficients(coefficients)
                .build();
        PageCar pageCar = open(PageCarsList.class).clickCreate();
        car.inputTo(pageCar);
        pageCar.clickSave()
                .check(p -> {
                    p.alertModalHeader.shouldHave(text(EXPECTED_MESSAGE));
                    p.alertModalCloseButton.click();
                });

        assertFalse(
                open(PageCarsList.class)
                        .waitTableLoading()
                        .clickDisplayAllPages()
                        .containsCarRowByName(car.name())
        );
    }

    @Test
    @QaseId(280)
    @DisplayName("Невозможность редактирования вагона с правом проведения расчетов")
    public void testNotUpdatingWithRoleCalc() {
        loginIfNeeded(User.CALC);
        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(testCar.name())
                .openCar()
                .clickEdit()
                .inputName(testCar.name() + "_changed")
                .pressTab()
                .clickSave()
                .check(p -> {
                    p.alertModalHeader.shouldHave(text(EXPECTED_MESSAGE));
                    p.alertModalCloseButton.click();
                });

        open(PageCarsList.class)
                .waitTableLoading()
                .clickDisplayAllPages()
                .check(p -> {
                    p.findCarRowByName(testCar.name())
                            .check(r -> r.carName.shouldHave(text(testCar.name())));
                    assertFalse(p.containsCarRowByName(testCar.name() + "_changed"));
                });
    }

    @AfterAll
    public static void tearDown() {
        // Удалить первоначальный Вагон
        deleteCarByName(testCar.name());
    }
}
