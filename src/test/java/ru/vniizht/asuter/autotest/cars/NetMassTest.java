package ru.vniizht.asuter.autotest.cars;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.Messages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

import static ru.vniizht.asuter.autotest.CommonOps.setValueAndPressTab;
import static ru.vniizht.asuter.autotest.CustomAsserts.assertInputInValidState;
import static ru.vniizht.asuter.autotest.CustomAsserts.assertInputInWrongState;

/**
 * Раздел "Вагоны", поле "масса нетто".
 */
public class NetMassTest extends MainParametersTest {

    @ParameterizedTest
    @CsvSource(
            value = {"0,001:0,001", "10000:10000", "05:5"},
            delimiter = ':'
    )
    public void testValidUserInput(@Nonnull String value, @Nullable String expected) {
        expected = Objects.requireNonNullElse(expected, "");
        setValueAndPressTab(netMassInput, value);
        assertInputInValidState(netMassInput, expected);
        assertInputInValidState(fullMassInput, expected);
        String numberOfAxles = Objects.requireNonNull(numberOfAxlesSelect.getSelectedOptionValue());
        assertInputInValidState(
                massPerAxleInput,
                massPerAxle(expected, numberOfAxles)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "№№№", "e"})
    public void testFilteredUserInput(@Nonnull String value) {
        setValueAndPressTab(netMassInput, value);
        assertInputInWrongState(netMassInput, "", Messages.FieldIsRequired);
        assertInputInValidState(fullMassInput, "");
        String numberOfAxles = Objects.requireNonNull(numberOfAxlesSelect.getSelectedOptionValue());
        assertInputInValidState(
                massPerAxleInput,
                massPerAxle("", numberOfAxles)
        );
    }

    @ParameterizedTest
    @CsvSource(
            value = {"10000.001:10000,001", "1.5555:1,5555", "-1000:-1000", "0.001+100000000000000:0,0011", "0.00 1:0,001"},
            delimiter = ':'
    )
    public void testInvalidUserInput(@Nonnull String value, @Nonnull String expected) {
        setValueAndPressTab(netMassInput, value);
        assertInputInWrongState(
                netMassInput,
                expected,
                Messages.numberOutOfRange(1, 10_000, 3)
        );
        assertInputInWrongState(
                fullMassInput,
                expected,
                Messages.numberOutOfRange(1, 10_000, 3)
        );
        String numberOfAxles = Objects.requireNonNull(numberOfAxlesSelect.getSelectedOptionValue());
        assertInputInWrongState(
                massPerAxleInput,
                massPerAxle("", numberOfAxles),
                Messages.numberOutOfRange(1, 100, 3)
        );
    }
}
