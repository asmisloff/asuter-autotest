package ru.vniizht.asuter.autotest;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

@ParametersAreNonnullByDefault
public class CommonOps {

    private static final Logger logger = LoggerFactory.getLogger(CommonOps.class);
    private static final NumberFormat ruNumberFmt = DecimalFormat.getNumberInstance(new Locale("ru"));

    public static final String ROOT = "http://asuter-dev.vniizht.lan";

    public static void login() {
        open(ROOT + "/login");
        if (webdriver().object().getCurrentUrl().endsWith("/login")) {
            $(By.id("username")).sendKeys("autotest");
            $(By.id("password")).sendKeys("AutoTest_113");
            $(By.id("button-login")).click();
        }
        logger.info("Пользователь авторизован");
    }

    public static SelenideElement byTestId(String testId) {
        return $(Selectors.by("data-testid", testId));
    }

    public static void setValueAndPressTab(SelenideElement tgt, String value) {
        tgt.setValue(value);
        tgt.pressTab();
    }

    public static void wait(int seconds) {
        logger.info(String.format("Пауза %d секунд", seconds));
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String tr(Number n, int maxFractionDigits) {
        ruNumberFmt.setMaximumFractionDigits(maxFractionDigits);
        return ruNumberFmt.format(n);
    }
}
