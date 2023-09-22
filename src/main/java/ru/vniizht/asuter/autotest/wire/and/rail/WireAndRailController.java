package ru.vniizht.asuter.autotest.wire.and.rail;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static ru.vniizht.asuter.autotest.CommonOps.ROOT;
import static ru.vniizht.asuter.autotest.CommonOps.login;

public class WireAndRailController {

    public void navigate() {
        login();
        open(ROOT + "/database");
        $(By.xpath("//td[text()='Электрическое оборудование']")).doubleClick();
        $(By.xpath("//td[text()='Провода и рельсы']")).doubleClick();
    }

    public void unlockInterface() {
        $(By.xpath("//button[@title='Редактировать']")).click();
    }

    public void insertRow(int idx) {
        getAddButton(idx).click();
    }

    public void deleteRow(int idx) {
        getDelButton(idx).click();
    }

    public SelenideElement getNameInput(int rowIdx) {
        return findRow(rowIdx).$(By.xpath("./td[position()=1]/div/input"));
    }

    public SelenideElement getDcResistanceInput(int rowIdx) {
        return findRow(rowIdx).$(By.xpath("./td[position()=2]/div/input"));
    }

    public SelenideElement getAcResistanceInput(int rowIdx) {
        return findRow(rowIdx).$(By.xpath("./td[position()=3]/div/input"));
    }

    public SelenideElement getRadiusInput(int rowIdx) {
        return findRow(rowIdx).$(By.xpath("./td[position()=4]/div/input"));
    }

    public SelenideElement getLimitAmpInput(int rowIdx) {
        return findRow(rowIdx).$(By.xpath("./td[position()=5]/div/input"));
    }

    public SelenideElement getLimitTempInput(int rowIdx) {
        return findRow(rowIdx).$(By.xpath("./td[position()=6]/div/input"));
    }

    public SelenideElement getThermalConductivityInput(int rowIdx) {
        return findRow(rowIdx).$(By.xpath("./td[position()=7]/div/input"));
    }

    public SelenideElement getCrossSectionAreaInput(int rowIdx) {
        return findRow(rowIdx).$(By.xpath("./td[position()=8]/div/input"));
    }

    public SelenideElement getMaterialSelect(int rowIdx) {
        return findRow(rowIdx).$(By.xpath("./td[position()=9]/div/select"));
    }

    public SelenideElement getAddButton(int rowIdx) {
        if (rowIdx > 0) --rowIdx;
        return findRow(rowIdx).$(By.xpath("./td[position()=10]/button[text()='↓']"));
    }

    public SelenideElement getDelButton(int rowIdx) {
        return findRow(rowIdx).$(By.xpath("./td[position()=10]/button[text()='\uD83D\uDDD9']"));
    }

    public void save() {
        $(By.xpath("//button[@title='Сохранить']")).click();
    }

    private static SelenideElement findRow(int idx) {
        String pos = idx >= 0 ? Integer.toString(idx + 1) : String.format("last()+1-%d", -idx);
        return $(By.xpath(String.format("//tbody/tr[position()=%s]", pos)));
    }

    private static ElementsCollection findAllRows() {
        return $$(By.xpath("//tbody/tr"));
    }
}
