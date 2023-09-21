package ru.vniizht.asuter.autotest.cars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.annotation.Nonnull;
import java.util.Objects;

import static ru.vniizht.asuter.autotest.CommonOps.setValueAndPressTab;
import static ru.vniizht.asuter.autotest.CustomAsserts.assertInputInValidState;
import static ru.vniizht.asuter.autotest.CustomAsserts.assertInputInWrongState;

/** Раздел "Вагоны", поле "масса тары". */
public class ContainerMassValidationTest extends MainParametersTest {

    /**
     * Масса тары: валидный ввод (пп. 1, 3).
     */
    @ParameterizedTest
    @ValueSource(strings = {"0.001", "1000"})
    public void testSelfWeightInputValidCases(String v) {
        String expectedValue = v.replace('.', ',');
        setValueAndPressTab(containerMassInput, v);
        assertInputInValidState(containerMassInput, expectedValue);
        assertInputInValidState(fullMassInput, expectedValue);
    }

    /**
     * Масса тары: валидный ввод с автокоррекцией (п. 5).
     */
    @Test
    public void testSelfWeightInputValidCasesWithAutoCorrection() {
        String expectedValue = "5";
        setValueAndPressTab(containerMassInput, "05");
        assertInputInValidState(containerMassInput, expectedValue);
        assertInputInValidState(fullMassInput, expectedValue);
        @Nonnull String fullWeightValue = Objects.requireNonNull(fullMassInput.getValue());
        @Nonnull String numberOfAxlesValue = Objects.requireNonNull(numberOfAxlesSelect.getSelectedOptionValue());
        assertInputInValidState(
                massPerAxleInput,
                massPerAxle(fullWeightValue, numberOfAxlesValue)
        );
    }

    /**
     * Масса тары: невалидный ввод (пп. 2, 4, 6, 7, 8, 9).
     */
    @ParameterizedTest
    @CsvSource(
            value = {
                    "0:", "10000.001:10000,001", "1.5555:1,5555", "-1000:-1000", "№№№:", "e:",
                    "0.001+100000000000000:0,0011", "0.00 1:0,001"
            },
            delimiter = ':'
    )
    public void testSelfWeightInputValidCasesWithAutoCorrection(String v, String expected) {
        setValueAndPressTab(containerMassInput, v);
        if (expected == null) {
            expected = "";
        }
        assertInputInWrongState(containerMassInput, expected);
        if ("".equals(expected)) {
            assertInputInValidState(fullMassInput, "");
            assertInputInValidState(massPerAxleInput, "");
        } else {
            assertInputInWrongState(fullMassInput, expected);
            @Nonnull String fullWeightValue = Objects.requireNonNull(fullMassInput.getValue());
            @Nonnull String numberOfAxlesValue = Objects.requireNonNull(numberOfAxlesSelect.getSelectedOptionValue());
            assertInputInWrongState(
                    massPerAxleInput,
                    massPerAxle(fullWeightValue, numberOfAxlesValue)
            );
        }
    }
}
