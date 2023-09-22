package ru.vniizht.asuter.autotest.acceptance.ex1;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import ru.vniizht.asuter.autotest.wire.and.rail.WireAndRailController;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Ex1 {

    @Test
    public void test() throws InterruptedException {
        WireAndRailController w = new WireAndRailController();
        w.navigate();
        w.unlockInterface();
//        for (int i = 0; i < 4; i++) {
//            w.insertRow(-1);
//        }
//        w.getNameInput(0).shouldHave(value("АС-240 ПКЭ"));
//        w.getDcResistanceInput(0).shouldHave(value("0.131"));
//        w.getAcResistanceInput(0).shouldHave(value("0.131"));
//        w.getRadiusInput(0).shouldHave(value("1.07"));
//        w.getLimitAmpInput(0).shouldHave(value("605"));
//        w.getLimitTempInput(0).shouldHave(value("90"));
//        w.getThermalConductivityInput(0).shouldHave(value("200"));
//        w.getCrossSectionAreaInput(0).shouldHave(value("240"));
//        w.getMaterialSelect(0).getSelectedOption().shouldHave(value(WireMaterial.STEEL_ALUMINIUM.value));

        w.save();
        $(By.xpath("//div[text()='Сохранено.']")).shouldBe(visible);
    }
}
