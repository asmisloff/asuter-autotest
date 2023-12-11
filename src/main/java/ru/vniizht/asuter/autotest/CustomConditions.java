package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@ParametersAreNonnullByDefault
public class CustomConditions {

    public final static String MINUS_SIGN = "\u2212";
    public static final String INPUT_NOT_VALID_CLASS_PATTERN = ".*inputNotValidField.*";

    public static Condition[] validInput(String expectedValue) {
        return new Condition[]{
            new OuterDivIsWhite(),
            Condition.value(expectedValue)
        };
    }

    public static Condition[] validEmptyInput() {
        return new Condition[]{
                new OuterDivIsWhite(),
                Condition.empty
        };
    }

    public static Condition[] invalidInput(String expectedValue, String expectedTitle) {
        return new Condition[]{
            new OuterDivIsPink(),
            Condition.value(expectedValue),
            Condition.attribute("title", expectedTitle)
        };
    }

    public static Condition[] invalidInput(String expectedValue) {
        return new Condition[]{
            new OuterDivIsPink(),
            Condition.value(expectedValue)
        };
    }

    public static Condition[] invalidEmptyInput(String expectedTitle) {
        return new Condition[]{
                classInputNotValid(),
                Condition.empty,
                Condition.attribute("title", expectedTitle)
        };
    }

    public static Condition[] classInputNotValid(String expectedValue, String expectedTitle) {
        return new Condition[]{
                classInputNotValid(),
                clearedValueMatch(expectedValue),
                Condition.attribute("title", expectedTitle)
        };
    }

    public static Condition[] classInputValid(String expectedValue) {
        return new Condition[]{
                Condition.not(classInputNotValid()),
                clearedValueMatch(expectedValue),
        };
    }

    public static Condition classInputNotValid() {
        return Condition.attributeMatching("class", INPUT_NOT_VALID_CLASS_PATTERN);
    }

    /**
     * Перед сравнением с ожидаемым значением очищает значение элемента (аттрибут value)
     * от non-breaking space и заменяет символ минуса (Unicode 0x2212) на обычный минус.
     * @param expectedValue ожидаемое значение
     */
    public static Condition clearedValueMatch(String expectedValue) {
        return Condition.match("should have value " + expectedValue ,
                webElement -> {
                    String value = webElement.getAttribute("value");
                    if (value.startsWith(MINUS_SIGN)) {
                        value = value.replace(MINUS_SIGN.charAt(0), '-');
                    }
                    value = StringUtils.remove(value, ' ');
                    return expectedValue.equals(value);
                }
        );
    }

    public static void notificationShouldAppear(String text) {
        SelenideElement notification = $(By.xpath("//div[@data-testid='notification']"));
        notification
            .shouldHave(text(text))
            .shouldNot(exist, Duration.ofSeconds(10));
//        $(By.className("Notification__notificationContainer--2osg1"))
//            .shouldHave(text(text))
//            .shouldNot(exist, Duration.ofSeconds(10));
//        $(By.xpath(String.format("//div[@class='Notification' and contains(text(), '%s')]", text))).shouldBe(visible);
    }
}

@ParametersAreNonnullByDefault
class OuterDivIsWhite extends Condition {

    public OuterDivIsWhite() {
        super("bgColor=white");
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, WebElement element) {
        WebElement outerDiv = element.findElement(By.xpath("./.."));
        String outerDivCssClass = Objects.requireNonNull(outerDiv.getAttribute("class"));
        if (outerDivCssClass.contains("notValid")) {
            return new CheckResult(false, "bgColor=pink");
        }
        return CheckResult.accepted();
    }
}

@ParametersAreNonnullByDefault
class OuterDivIsPink extends Condition {

    public OuterDivIsPink() {
        super("bgColor=pink");
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, WebElement element) {
        WebElement outerDiv = element.findElement(By.xpath("./.."));
        String outerDivCssClass = Objects.requireNonNull(outerDiv.getAttribute("class"));
        if (!outerDivCssClass.contains("notValid")) {
            return new CheckResult(false, "bgColor=white");
        }
        return CheckResult.accepted();
    }
}