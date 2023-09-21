package ru.vniizht.asuter.autotest.cars;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.vniizht.asuter.autotest.CommonOps.*;

public class CarTest {

    protected static SelenideElement nameInput = null;
    protected static SelenideElement containerMassInput = null;
    protected static SelenideElement fullMassInput = null;
    protected static SelenideElement netMassInput = null;
    protected static SelenideElement massPerAxleInput = null;
    protected static SelenideElement numberOfAxlesSelect = null;

    @BeforeAll
    protected static void basicSetup() {
        SelenideLogger
                .addListener("AllureSelenide", new AllureSelenide()
                        .screenshots(true).savePageSource(false));
        login();
        navigateToCatalogue();
    }

    private static void navigateToCatalogue() {
        open("http://asuter-dev.vniizht.lan/database");
        $(".MuiTableRow-hover:nth-child(1) > .MuiTableCell-root:nth-child(3)").doubleClick();
        $(".MuiTableRow-root:nth-child(2) > .MuiTableCell-root:nth-child(3)").doubleClick();
    }

    public static void createNewCar() {
        $(Selectors.byXpath("//button[@title='Создать']")).click();
    }

    @ParametersAreNonnullByDefault
    public static String massPerAxle(String fullWeight, String numberOfAxles) {
        if (fullWeight.isBlank()) {
            return "";
        }
        double w = Double.parseDouble(fullWeight.replace(',', '.'));
        int n = Integer.parseInt(numberOfAxles);
        return StringUtils.remove(tr(w / n, 3), ' ');
    }

    protected static void findMainParametersGuiElements() {
        nameInput = $(byTestId("name"));
        containerMassInput = $(byTestId("selfWeight"));
        fullMassInput = $(Selectors.by("data-name", "weight"));
        netMassInput = $(Selectors.by("data-name", "netto"));
        massPerAxleInput = $(Selectors.by("data-name", "singleAxleWeight"));
        numberOfAxlesSelect = $(byTestId("numberOfAxles"));
    }
}
