package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.SelenideElement;
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