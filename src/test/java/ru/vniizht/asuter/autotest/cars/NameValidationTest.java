package ru.vniizht.asuter.autotest.cars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.CustomAsserts;
import ru.vniizht.asuter.autotest.Messages;

import static ru.vniizht.asuter.autotest.CommonOps.setValueAndPressTab;
import static ru.vniizht.asuter.autotest.CustomAsserts.assertInputInValidState;

/**
 * Раздел "Вагоны", поле "наименование".
 */
public class NameValidationTest extends MainParametersTest {

    /**
     * Наименование: валидный ввод (пп. 1 - 7, 10).
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "1", "0", "Цистерна", "Вагон с длинным названием из пятидесятиии символов", "vagon", "1,1 1.1",
            "%истерна", "-1000"
    })
    public void testNameInputValidCases(String v) {
        setValueAndPressTab(nameInput, v);
        assertInputInValidState(nameInput, v);
    }

    /**
     * Наименование: обрезка пробелов (п. 9).
     */
    @Test
    public void testNameInputWhitespaceTrimming() {
        setValueAndPressTab(nameInput, "   Цистерна  ");
        assertInputInValidState(nameInput, "Цистерна");
    }

    /* Наименование: невалидный ввод (пп. 8, 11). */

    @ParameterizedTest
    @ValueSource(strings = {"  ", ""})
    public void testInputInvalidCases(String v) {
        setValueAndPressTab(nameInput, v);
        CustomAsserts.assertInputInWrongState(nameInput, "", Messages.FieldIsRequired);
    }
}
