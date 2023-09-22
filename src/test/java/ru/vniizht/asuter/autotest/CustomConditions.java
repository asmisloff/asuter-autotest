package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class CustomConditions {

    public static Condition[] validInput(String expectedValue) {
        return new Condition[]{
            new OuterDivIsWhite(),
            Condition.value(expectedValue)
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