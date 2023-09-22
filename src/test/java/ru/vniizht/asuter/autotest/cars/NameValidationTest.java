package ru.vniizht.asuter.autotest.cars;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.Messages;

import static ru.vniizht.asuter.autotest.CommonOps.setValueAndPressTab;
import static ru.vniizht.asuter.autotest.CustomConditions.invalidInput;
import static ru.vniizht.asuter.autotest.CustomConditions.validInput;

/**
 * Раздел "Вагоны", поле "наименование".
 */
public class NameValidationTest extends MainParametersTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "1", "0", "Цистерна", "Вагон с длинным названием из пятидесятиии символов", "vagon", "1,1 1.1",
        "%истерна", "-1000"
    })
    @DisplayName("Валидный ввод, пп. 1-7, 10")
    public void testNameInputValidCases(String v) {
        setValueAndPressTab(nameInput, v);
        nameInput.shouldBe(validInput(v));
    }

    @Test
    @DisplayName("Обрезка пробелов")
    public void testNameInputWhitespaceTrimming() {
        setValueAndPressTab(nameInput, "   Цистерна  ");
        nameInput.shouldBe(validInput("Цистерна"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", ""})
    @DisplayName("Наименование: невалидный ввод, пп. 8, 11")
    public void testInputInvalidCases(String v) {
        setValueAndPressTab(nameInput, v);
        nameInput.shouldBe(invalidInput("", Messages.FieldIsRequired));
    }
}
