package ru.vniizht.asuter.autotest.car.validate.old;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCar;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import static ru.vniizht.asuter.autotest.CustomConditions.*;
import static ru.vniizht.asuter.autotest.Messages.*;

/**
 * Раздел "Вагоны", поле "Масса тары".
 * Тесты соответствуют @QaseId(10) частично, так как правила валидации изменились.
 */
@DisplayName("Вагоны: Валидация поля \"Масса тары\"")
public class CarContainerMassValidationTest extends BaseTest {
    static PageCar page;

    @BeforeAll
    public static void init() {
        loginIfNeeded();
        page = open(PageCarsList.class)
                .clickCreate();
    }

    @ParameterizedTest
    @CsvSource(
            value = {"1:1", "1.001:1,001", "10000:10000"},
            delimiter = ':'
    )
    @DisplayName("Валидный ввод")
    public void testInputValidCases(String value, String expected) {
        page.inputContainerMass(value)
                .pressTab()
                .check(p -> {
                    p.containerMassInput.shouldHave(classInputValid(expected));
                    p.fullMassInput.shouldHave(classInputValid(expected));
                });
    }

    @Test
    @DisplayName("Валидный ввод с автокоррекцией, п. 5")
    public void testInputValidCasesWithAutoCorrection() {
        String expected = "5";
        page.inputContainerMass("05")
                .pressTab()
                .check(p -> {
                    p.containerMassInput.shouldHave(validInput(expected));
                    p.fullMassInput.shouldHave(validInput(expected));
                    var expectedMassPerAxle = p.calculateMassPerAxleFromExpectedWeight(expected);
                    p.massPerAxleInput.shouldHave(validInput(expectedMassPerAxle));
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"№№№", "e"})
    @DisplayName("Невалидный ввод (игнорируемые символы), пп. 8, 9")
    public void testInputInvalidCasesAutoCorrectedToEmpty(String v) {
        page.inputContainerMass(v)
                .pressTab()
                .check(p -> {
                    p.containerMassInput.shouldHave(invalidEmptyInput(FieldIsRequired));
                    p.fullMassInput.shouldHave(validEmptyInput());
                    p.massPerAxleInput.shouldHave(validEmptyInput());
                });
    }

    @ParameterizedTest
    @CsvSource(
            value = {"10000.001:10000,001", "1.5555:1,5555"},
            delimiter = ':'
    )
    @DisplayName("Невалидный ввод, пп. 4, 6")
    public void testInputInvalidCases(String v, String expectedInput) {
        String expected = (expectedInput == null) ? "" : expectedInput;
        page.inputContainerMass(v)
                .pressTab()
                .check(p -> {
                    p.containerMassInput.shouldHave(classInputNotValid(expected, MUST_BE_FROM_1_TO_10000));
                    p.fullMassInput.shouldHave(classInputNotValid(expected, MUST_BE_FROM_1_TO_10000));
                    p.massPerAxleInput.shouldHave(classInputNotValid(p.calculateMassPerAxle(), MUST_BE_FROM_1_TO_100));
                });
    }

    @ParameterizedTest
    @CsvSource(
            value = {"0:0", "0.001+100000000000000:0,0011", "0.00 1:0,001"},
            delimiter = ':'
    )
    @DisplayName("Невалидный ввод с автокоррекцией (масса на ось -> 0), пп. 2, 10, 11")
    public void testInputInvalidCasesWithAutoCorrection(String v, String expectedInput) {
        String expected = (expectedInput == null) ? "" : expectedInput;
        page.inputContainerMass(v)
                .pressTab()
                .check(p -> {
                    p.containerMassInput.shouldHave(classInputNotValid(expected, MUST_BE_FROM_1_TO_10000));
                    p.fullMassInput.shouldHave(classInputNotValid(expected, MUST_BE_FROM_1_TO_10000));
                    // При текущих входных данных massPerAxle вычисляется в 0 и это не приводит к нарушению валидации
                    p.massPerAxleInput.shouldHave(validInput(p.calculateMassPerAxle()));
                });
    }

    @Test
    @DisplayName("Невалидный ввод (отрицательный), пп. 7")
    public void testInputInvalidNegativeValues() {
        var negative1000 = "-1000";
        page.inputContainerMass("-1000")
                .pressTab()
                .check(p -> {
                    p.containerMassInput.shouldHave(classInputNotValid(negative1000, MUST_BE_FROM_1_TO_10000));
                    p.fullMassInput.shouldHave(classInputNotValid(negative1000, MUST_BE_FROM_1_TO_10000));
                });
    }
}
