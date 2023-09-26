package ru.vniizht.asuter.autotest.wire.and.rail;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.List;

public record WireAndRailRow(
    SelenideElement nameInput,
    SelenideElement dcResistanceInput,
    SelenideElement acResistanceInput,
    SelenideElement radiusInput,
    SelenideElement limitAmpInput,
    SelenideElement limitTempInput,
    SelenideElement thermalCapacityInput,
    SelenideElement crossSectionAreaInput,
    SelenideElement materialSelect,
    SelenideElement addButton,
    SelenideElement delButton
) {

    public static WireAndRailRow from(SelenideElement row) {
        return new WireAndRailRow(
            row.$(By.xpath("./td[position()=1]/div/input")),
            row.$(By.xpath("./td[position()=2]/div/input")),
            row.$(By.xpath("./td[position()=3]/div/input")),
            row.$(By.xpath("./td[position()=4]/div/input")),
            row.$(By.xpath("./td[position()=5]/div/input")),
            row.$(By.xpath("./td[position()=6]/div/input")),
            row.$(By.xpath("./td[position()=7]/div/input")),
            row.$(By.xpath("./td[position()=8]/div/input")),
            row.$(By.xpath("./td[position()=9]/div/select")),
            row.$(By.xpath("./td[position()=10]/button[text()='↓']")),
            row.$(By.xpath("./td[position()=10]/button[text()='\uD83D\uDDD9']"))
        );
    }

    public List<SelenideElement> getElements() {
        return List.of(
            nameInput, dcResistanceInput, acResistanceInput, radiusInput, limitAmpInput,
            limitTempInput, thermalCapacityInput, crossSectionAreaInput, materialSelect,
            addButton, delButton
        );
    }
}
