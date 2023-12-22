package ru.vniizht.asuter.autotest.car.save;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.car.Car;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCar;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.vniizht.asuter.autotest.CustomConditions.validInput;

@DisplayName("Вагоны: Сохранение")
public class CarSaveTest extends BaseTest {
    private static final String CAR_NAME = "car_save_autotest";
    private static final String CONTAINER_MASS_FOR_Q_EQUALS_6 = "24";
    private static final String CONTAINER_MASS_FOR_Q_GREATER_6 = "50";
    private static final String CONTAINER_MASS_FOR_Q_LESS_6 = "10";
    private static final String VALID_LENGTH = "1";
    private static final String DEFAULT_NUMBER_OF_AXLES = "4";
    private static final String EXPECTED_MASS_PER_AXLE_EQUALS_6 = "6";
    private static final String EXPECTED_MASS_PER_AXLE_GREATER_6 = "12,5";
    private static final String EXPECTED_MASS_PER_AXLE_LESS_6 = "2,5";
    private static final String EMPTY_A = "";
    static PageCar pageCar;

    @BeforeAll
    public static void init() {
        loginIfNeeded();
        pageCar = open(PageCarsList.class).clickCreate();
    }

    @Test
    @QaseId(262)
    @DisplayName("Сохранение вагона при q = 6 с заполненным коэффициентом А звеньевого пути")
    public void testSavingCar_262() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_EQUALS_6,
                new String[] {"1", "1", "1", "1", EMPTY_A, "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_EQUALS_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_EQUALS_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(263)
    @DisplayName("Сохранение вагона при q = 6 с заполненным коэффициентом А бесстыкового пути")
    public void testSavingCar_263() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_EQUALS_6,
                new String[] {EMPTY_A, "1", "1", "1", "1", "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_EQUALS_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_EQUALS_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(264)
    @DisplayName("Сохранение вагона при q = 6 с заполненными коэффициентами А звеньевого пути при редактировании")
    public void testSavingCar_264() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_EQUALS_6,
                new String[] {"1", "1", "1", "1", "1", "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .clickEdit()
                .clearComponentRailA()
                .pressTab()
                .clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_EQUALS_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_EQUALS_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.setComponentRailA(EMPTY_A).checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(265)
    @DisplayName("Сохранение вагона при q = 6 с заполненными коэффициентами А бесстыкового пути при редактировании")
    public void testSavingCar_265() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_EQUALS_6,
                new String[] {"1", "1", "1", "1", "1", "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .clickEdit()
                .clearComponentRailA()
                .pressTab()
                .clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_EQUALS_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_EQUALS_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.setComponentRailA(EMPTY_A).checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(266)
    @DisplayName("Сохранение вагона при q > 6 с заполненным коэффициентом А звеньевого пути")
    public void testSavingCar_266() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_GREATER_6,
                new String[] {"1", "1", "1", "1", EMPTY_A, "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_GREATER_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_GREATER_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.setContinuousRailA(EMPTY_A).checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(267)
    @DisplayName("Сохранение вагона при q > 6 с заполненным коэффициентом А бесстыкового пути")
    public void testSavingCar_267() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_GREATER_6,
                new String[] {EMPTY_A, "1", "1", "1", "1", "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_GREATER_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_GREATER_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.setComponentRailA(EMPTY_A).checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(268)
    @DisplayName("Сохранение вагона при q > 6 с заполненными коэффициентами А звеньевого пути при редактировании")
    public void testSavingCar_268() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_GREATER_6,
                new String[] {"1", "1", "1", "1", "1", "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .clickEdit()
                .clearContinuousRailA()
                .pressTab()
                .clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_GREATER_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_GREATER_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.setContinuousRailA(EMPTY_A).checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(269)
    @DisplayName("Сохранение вагона при q > 6 с заполненными коэффициентами А бесстыкового пути при редактировании")
    public void testSavingCar_269() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_GREATER_6,
                new String[] {"1", "1", "1", "1", "1", "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .clickEdit()
                .clearComponentRailA()
                .pressTab()
                .clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_GREATER_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_GREATER_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.setComponentRailA(EMPTY_A).checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(270)
    @DisplayName("Сохранение вагона при q < 6 с заполненным коэффициентом А звеньевого пути")
    public void testSavingCar_270() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_LESS_6,
                new String[] {"1", "1", "1", "1", EMPTY_A, "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_LESS_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_LESS_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(271)
    @DisplayName("Сохранение вагона при q < 6 с заполненным коэффициентом А бесстыкового пути")
    public void testSavingCar_271() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_LESS_6,
                new String[] {EMPTY_A, "1", "1", "1", "1", "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_LESS_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_LESS_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(272)
    @DisplayName("Сохранение вагона при q < 6 с заполненными коэффициентами А звеньевого пути при редактировании")
    public void testSavingCar_272() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_LESS_6,
                new String[] {"1", "1", "1", "1", "1", "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .clickEdit()
                .clearContinuousRailA()
                .pressTab()
                .clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_LESS_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_LESS_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.setContinuousRailA(EMPTY_A).checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(273)
    @DisplayName("Сохранение вагона при q < 6 с заполненными коэффициентами А бесстыкового пути при редактировании")
    public void testSavingCar_273() {
        Car car = inputCar(
                CONTAINER_MASS_FOR_Q_LESS_6,
                new String[] {"1", "1", "1", "1", "1", "1", "1", "1"}
        );
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .clickEdit()
                .clearComponentRailA()
                .pressTab()
                .clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(car.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(car.name()));
                    c.typeSelect.shouldHave(selectedText(""));
                    c.numberOfAxlesSelect.shouldHave(selectedText(DEFAULT_NUMBER_OF_AXLES));
                    c.containerMassInput.shouldHave(validInput(car.containerMass()));
                    c.netMassInput.shouldHave(validInput(""));
                    c.fullMassInput.shouldHave(validInput(CONTAINER_MASS_FOR_Q_LESS_6));
                    c.massPerAxleInput.shouldHave(validInput(EXPECTED_MASS_PER_AXLE_LESS_6));
                    c.lengthInput.shouldHave(validInput(car.length()));
                    car.setComponentRailA(EMPTY_A).checkCoefficientsEqual(c);
                });

        deleteCarByName(car.name());
    }

    @Test
    @QaseId(274)
    @DisplayName("Сохранение вагона после редактирования нового вагона")
    public void testSavingCar_274() {
        Car oldCar = inputCar(
                CONTAINER_MASS_FOR_Q_GREATER_6,
                new String[] {"1", "1", "1", "1", "1", "1", "1", "1"}
        );
        pageCar.clickSave();

        Car modifiedCar = Car.builder()
                .name(CAR_NAME + "_2")
                .type("пассажирский")
                .numberOfAxles("8")
                .containerMass("40")
                .netMass("40")
                .length("2")
                .coefficients(new String[] {"2", "2", "2", "2", "2", "2", "2", "2"})
                .build();
        pageCar.clickEdit();
        modifiedCar.inputTo(pageCar);
        pageCar.clickSave();
        assertFalse(
                open(PageCarsList.class)
                        .waitTableLoading()
                        .clickDisplayAllPages()
                        .containsCarRowByName(oldCar.name())
        );

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(modifiedCar.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(modifiedCar.name()));
                    c.typeSelect.shouldHave(selectedText(modifiedCar.type()));
                    c.numberOfAxlesSelect.shouldHave(selectedText(modifiedCar.numberOfAxles()));
                    c.containerMassInput.shouldHave(validInput(modifiedCar.containerMass()));
                    c.netMassInput.shouldHave(validInput(modifiedCar.netMass()));
                    c.fullMassInput.shouldHave(validInput("80"));
                    c.massPerAxleInput.shouldHave(validInput("10"));
                    c.lengthInput.shouldHave(validInput(modifiedCar.length()));
                    modifiedCar.checkCoefficientsEqual(c);
                });

        deleteCarByName(modifiedCar.name());
    }

    @Test
    @QaseId(275)
    @DisplayName("Сохранение вагона после редактирования вагона")
    public void testSavingCar_275() {
        Car oldCar = inputCar(
                CONTAINER_MASS_FOR_Q_GREATER_6,
                new String[] {"1", "1", "1", "1", "1", "1", "1", "1"}
        );
        pageCar.clickSave();

        pageCar = open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(oldCar.name())
                .openCar()
                .clickEdit();
        Car modifiedCar = Car.builder()
                .name(CAR_NAME + "_2")
                .type("пассажирский")
                .numberOfAxles("8")
                .containerMass("40")
                .netMass("40")
                .length("2")
                .coefficients(new String[] {"2", "2", "2", "2", "2", "2", "2", "2"})
                .build();
        modifiedCar.inputTo(pageCar);
        pageCar.clickSave();

        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(modifiedCar.name())
                .openCar()
                .check(c -> {
                    c.nameInput.shouldHave(validInput(modifiedCar.name()));
                    c.typeSelect.shouldHave(selectedText(modifiedCar.type()));
                    c.numberOfAxlesSelect.shouldHave(selectedText(modifiedCar.numberOfAxles()));
                    c.containerMassInput.shouldHave(validInput(modifiedCar.containerMass()));
                    c.netMassInput.shouldHave(validInput(modifiedCar.netMass()));
                    c.fullMassInput.shouldHave(validInput("80"));
                    c.massPerAxleInput.shouldHave(validInput("10"));
                    c.lengthInput.shouldHave(validInput(modifiedCar.length()));
                    modifiedCar.checkCoefficientsEqual(c);
                });
        assertFalse(
                open(PageCarsList.class)
                        .waitTableLoading()
                        .clickDisplayAllPages()
                        .containsCarRowByName(oldCar.name())
        );

        deleteCarByName(modifiedCar.name());
    }

    private void deleteCarByName(String name) {
        loginIfNeeded();
        open(PageCarsList.class)
                .waitTableLoading()
                .findCarRowByName(name)
                .delete();
    }

    private Car inputCar(String containerMass, String[] coefficients) {
        return Car.builder()
                .name(CAR_NAME)
                .length(VALID_LENGTH)
                .containerMass(containerMass)
                .coefficients(coefficients)
                .build()
                .inputTo(pageCar);
    }

}
