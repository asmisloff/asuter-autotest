package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

import static org.junit.Assert.*;

@ParametersAreNonnullByDefault
public class CustomAsserts {

    public static void assertInputInWrongState(SelenideElement input, String expectedValue) {
        assertEquals("Неверное значение input-а", expectedValue, input.getValue());
        var outerDiv = input.parent();
        String outerDivCssClass = Objects.requireNonNull(outerDiv.getAttribute("class"));
        assertTrue(
                "Ожидалось, что поле не валидно",
                StringUtils.containsIgnoreCase(outerDivCssClass, "notValid")
        );
    }

    public static void assertInputInWrongState(SelenideElement input, String expectedValue, String expectedTitle) {
        assertInputInWrongState(input, expectedValue);
        String title = Objects.requireNonNull(input.getAttribute("title")).strip();
        assertEquals("Неверный title", expectedTitle, title);
    }

    public static void assertInputInValidState(SelenideElement input, String expectedValue) {
        input.shouldHave(Condition.value(expectedValue));
        var outerDiv = input.parent();
        @Nonnull String clazz = Objects.requireNonNull(outerDiv.getAttribute("class"));
        assertFalse(
                "Ожидалось, что поле валидно",
                StringUtils.containsIgnoreCase(clazz, "notValid")
        );
    }
}
