package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public record Alert(SelenideElement modal, SelenideElement h3, SelenideElement closeButton) {

    public static Alert find() {
        SelenideElement modal = $("#openModal");
        SelenideElement h3 = modal.$(By.tagName("h3"));
        SelenideElement closeButton = modal.$(By.tagName("button"));
        return new Alert(modal, h3, closeButton);
    }

    public boolean isVisible() {
        return !Objects.requireNonNull(modal.getAttribute("class")).contains("modalClosed");
    }

    public String getText() {
        return h3.text();
    }

    public void closeAndCheckIfClosed() {
        closeButton.click();
        shouldBeHidden();
    }

    public Alert shouldBeVisible() {
        modal.shouldNotHave(
            attributeMatching("class", ".*modalClosed.*"),
            Duration.ofSeconds(10)
        );
        return this;
    }

    public void shouldBeHidden() {
        modal.shouldHave(attributeMatching("class", ".*modalClosed.*"));
    }

    public Alert shouldHaveText(String expected) {
        h3.shouldHave(text(expected), Duration.ofSeconds(10));
        return this;
    }
}
