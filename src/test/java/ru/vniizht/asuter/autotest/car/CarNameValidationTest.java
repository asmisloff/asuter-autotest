package ru.vniizht.asuter.autotest.car;

import com.codeborne.selenide.Condition;
import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.Messages;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCar;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import java.util.stream.Stream;

import static ru.vniizht.asuter.autotest.CustomConditions.*;

/**
 * Раздел "Вагоны", поле "Наименование вагона".
 * Тесты соответствуют @QaseId(9).
 */
@DisplayName("Вагоны: Валидация поля \"Наименование вагона\"")
public class CarNameValidationTest extends BaseTest {

    static PageCar page;

    @BeforeAll
    public static void init() {
        loginIfNeeded();
        page = open(PageCarsList.class)
                .clickCreate();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1", "0", "Цистерна", "Вагон с длинным названием из пятидесятиии символов", "vagon", "1,1 1.1",
            "%истерна", "-1000"
    })
    @DisplayName("Валидный ввод, пп. 1-7, 10")
    public void testNameInputValidCases(String v) {
        page.inputName(v)
                .pressTab()
                .check(p -> {
                    p.nameInput.shouldHave(validInput(v));
                });
    }

    @Test
    @DisplayName("Валидный ввод с автокоррекцией: Обрезка пробелов")
    public void testNameInputWhitespaceTrimming() {
        page.inputName("   Цистерна  ")
                .pressTab()
                .check(p -> {
                    p.nameInput.shouldHave(validInput("   Цистерна  "));
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", ""})
    @DisplayName("Невалидный ввод, пп. 8, 11")
    public void testInputInvalidCases(String v) {
        page.inputName(v)
                .pressTab()
                .check(p -> {
                    p.nameInput.shouldHave(classInputNotValid("", Messages.FieldIsRequired));
                });
    }

    private static Stream<Arguments> inputAndExpectedConditions() {
        return Stream.of(
                valid("1"),
                valid("0"),
                valid("Цистерна"),
                valid("Вагон с длинным названием из пятидесятиии символов"),
                valid("vagon"),
                valid("1,1 1.1"),
                valid("%истерна"),
                valid("-1000"),
                validButTrimmed("   Цистерна  ", "Цистерна"),
                invalid("  ", ""),
                invalid("", "")
        );
    }

    // Текущий кейс 9 содержит как валидный, так и невалидный ввод
    // Данный метод объединяет (и повторяет) проверки из методов:
    // testNameInputValidCases(), testNameInputWhitespaceTrimming(), testInputInvalidCases()
    // После переработки кейса 9 с разделением валидного и невалидного ввода можно
    // его удалить и переназначить новые QaseId соответствующим методам.
    @QaseId(9)
    @ParameterizedTest
    @MethodSource("inputAndExpectedConditions")
    @DisplayName("Валидный и невалидный ввод, обрезка пробелов")
    public void testInputAllCases(String v, Condition[] conditions) {
        page.inputName(v)
                .pressTab()
                .check(p -> {
                    p.nameInput.shouldHave(conditions);
                });
    }

    private static Arguments valid(String v) {
        return Arguments.of(v, validInput(v));
    }

    private static Arguments validButTrimmed(String v, String expected) {
        return Arguments.of(v, validInput(expected));
    }

    private static Arguments invalid(String v, String expectedValue) {
        return Arguments.of(v, classInputNotValid(expectedValue, Messages.FieldIsRequired));
    }
}
