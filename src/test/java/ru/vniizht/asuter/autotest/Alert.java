package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.Objects;

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

    public void close() {
        closeButton.click();
    }

    public void shouldHaveText(String expected) {
        h3.shouldHave(text(expected));
    }
}
