package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.LogEvent;
import io.qameta.allure.selenide.AllureSelenide;
import io.qase.api.exceptions.QaseException;
import io.qase.api.services.Attachments;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;

public class CustomAllureSelenideListener extends AllureSelenide {

    @Override
    public void afterEvent(LogEvent event) {
        super.afterEvent(event);
        if (event.getStatus().equals(LogEvent.EventStatus.FAIL)) {
            attachScreenshotToQase();
        }
    }

    private void attachScreenshotToQase() {
        File screenshot = ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.FILE);
        try {
            Attachments.addAttachmentsToCurrentContext(screenshot);
        } catch (QaseException e) {
            throw new RuntimeException(e);
        }
    }
}
