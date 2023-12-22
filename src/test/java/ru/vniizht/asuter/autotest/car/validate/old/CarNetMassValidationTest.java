package ru.vniizht.asuter.autotest.car.validate.old;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCar;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import static ru.vniizht.asuter.autotest.CustomConditions.*;
import static ru.vniizht.asuter.autotest.Messages.*;

/**
 * Раздел "Вагоны", поле "Масса нетто (грузоподъёмность)".
 * Тесты соответствуют @QaseId(11) частично, так как правила валидации изменились.
 */
@DisplayName("Вагоны: Валидация поля \"Масса нетто (грузоподъёмность)\"")
public class CarNetMassValidationTest extends BaseTest {

    static PageCar page;

    @BeforeAll
    public static void init() {
        loginIfNeeded();
        page = open(PageCarsList.class)
                .clickCreate();
    }

    @ParameterizedTest
    @CsvSource(
            value = {"10000:10000", "9999.999:9999,999", "04:4", "1:1"},
            delimiter = ':'
    )
    @DisplayName("Валидный ввод")
    public void testValidUserInput(String value, String expected) {
        page.inputNetMass(value)
                .pressTab()
                .check(p -> {
                    p.netMassInput.shouldHave(classInputValid(expected));
                    p.fullMassInput.shouldHave(classInputValid(expected));
                });
    }

    @ParameterizedTest
    @CsvSource(
            value = {"3600:36:3600", "3599.999:36:3599,999", "400:4:400", "04:4:4"},
            delimiter = ':'
    )
    @DisplayName("Валидный ввод: валидация с учетом рассчитанной массы на ось")
    public void testValidPerAxleUserInput(String value, String numAxles, String expected) {
        page.selectNumberOfAxles(numAxles)
                .inputNetMass(value)
                .pressTab()
                .check(p -> {
                    p.netMassInput.shouldHave(classInputValid(expected));
                    p.fullMassInput.shouldHave(classInputValid(expected));
                    var expectedMassPerAxle = p.calculateMassPerAxleFromExpectedWeight(expected);
                    p.massPerAxleInput.shouldHave(classInputValid(expectedMassPerAxle));
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"№№№", "e"})
    @DisplayName("Ввод игнорируемых символов")
    public void testFilteredUserInput(String value) {
        page.inputNetMass(value)
                .pressTab()
                .check(p -> {
                    p.netMassInput.shouldHave(classInputNotValid("", FieldIsRequired));
                    p.fullMassInput.shouldHave(validEmptyInput());
                    p.massPerAxleInput.shouldBe(validEmptyInput());
                });
    }

    @ParameterizedTest
    @CsvSource(
            value = {"0:0", "0.001:0,001", "10000.001:10000,001", "1.5555:1,5555", "0.001+100000000000000:0,0011", "0.00 1:0,001"},
            delimiter = ':'
    )
    @DisplayName("Невалидный ввод: числа вне допустимого диапазона и с неверным числом знаков после запятой")
    public void testInvalidUserInput(String value, String expected) {
        page.inputNetMass(value)
                .pressTab()
                .check(p -> {
                    p.netMassInput.shouldHave(classInputNotValid(expected, MUST_BE_FROM_1_TO_10000));
                    p.fullMassInput.shouldHave(classInputNotValid(expected, MUST_BE_FROM_1_TO_10000));
                });
    }

    @ParameterizedTest
    @CsvSource(
            value = {"-1:-1", "-10000:-10000"},
            delimiter = ':'
    )
    @DisplayName("Невалидный ввод: отрицательные числа")
    public void testNegativeInvalidUserInput(String value, String expected) {
        page.inputNetMass(value)
                .pressTab()
                .check(p -> {
                    p.netMassInput.shouldHave(classInputNotValid(expected, MUST_BE_FROM_1_TO_10000));
                    p.fullMassInput.shouldHave(classInputNotValid(expected, MUST_BE_FROM_1_TO_10000));
                });
    }
}
