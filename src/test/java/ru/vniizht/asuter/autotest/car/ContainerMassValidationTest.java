package ru.vniizht.asuter.autotest.car;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.Messages;

import javax.annotation.Nonnull;
import java.util.Objects;

import static ru.vniizht.asuter.autotest.CommonOps.setValueAndPressTab;
import static ru.vniizht.asuter.autotest.CustomConditions.invalidInput;
import static ru.vniizht.asuter.autotest.CustomConditions.validInput;

/**
 * Раздел "Вагоны", поле "масса тары".
 */
public class ContainerMassValidationTest extends MainParametersTest {

    @ParameterizedTest
    @ValueSource(strings = {"0.001", "1000"})
    @DisplayName("Валидный ввод, пп. 1, 3")
    public void testSelfWeightInputValidCases(String v) {
        String expectedValue = v.replace('.', ',');
        setValueAndPressTab(containerMassInput, v);
        containerMassInput.shouldBe(validInput(expectedValue));
        fullMassInput.shouldBe(validInput(expectedValue));
    }

    @Test
    @DisplayName("Валидный ввод с автокоррекцией, п. 5")
    public void testSelfWeightInputValidCasesWithAutoCorrection() {
        String expectedValue = "5";
        setValueAndPressTab(containerMassInput, "05");
        containerMassInput.shouldBe(validInput(expectedValue));
        fullMassInput.shouldBe(validInput(expectedValue));
        @Nonnull String fullWeightValue = Objects.requireNonNull(fullMassInput.getValue());
        @Nonnull String numberOfAxlesValue = Objects.requireNonNull(numberOfAxlesSelect.getSelectedOptionValue());
        massPerAxleInput.shouldBe(validInput(massPerAxle(fullWeightValue, numberOfAxlesValue)));
    }

    @ParameterizedTest
    @CsvSource(
        value = {
            "0:", "10000.001:10000,001", "1.5555:1,5555", "-1000:-1000", "№№№:", "e:",
            "0.001+100000000000000:0,0011", "0.00 1:0,001"
        },
        delimiter = ':'
    )
    @DisplayName("Невалидный ввод, пп. 2, 4, 6, 7, 8, 9")
    public void testSelfWeightInputValidCasesWithAutoCorrection(String v, String expected) {
        setValueAndPressTab(containerMassInput, v);
        if (expected == null) {
            expected = "";
        }
        String outOfRangeMsg1 = Messages.numberOutOfRange(1, 10_000, 3);
        String outOfRangeMsg2 = Messages.numberOutOfRange(1, 100, 3);
        containerMassInput.shouldBe(invalidInput(expected, outOfRangeMsg1));
        if ("".equals(expected)) {
            fullMassInput.shouldBe(validInput(""));
            massPerAxleInput.shouldBe(validInput(""));
        } else {
            fullMassInput.shouldBe(invalidInput(expected, outOfRangeMsg1));
            @Nonnull String fullWeightValue = Objects.requireNonNull(fullMassInput.getValue());
            @Nonnull String numberOfAxlesValue = Objects.requireNonNull(numberOfAxlesSelect.getSelectedOptionValue());
            massPerAxleInput.shouldBe(invalidInput(massPerAxle(fullWeightValue, numberOfAxlesValue), outOfRangeMsg2));
        }
    }
}
