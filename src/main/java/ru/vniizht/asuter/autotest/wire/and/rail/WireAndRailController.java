package ru.vniizht.asuter.autotest.wire.and.rail;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Selenide.*;
import static ru.vniizht.asuter.autotest.CommonOps.ROOT;
import static ru.vniizht.asuter.autotest.CommonOps.login;

@ParametersAreNonnullByDefault
public class WireAndRailController {

    private final Logger logger = LoggerFactory.getLogger(WireAndRailController.class);

    public void navigate() {
        login();
        open(ROOT + "/database");
        $(By.xpath("//td[text()='Электрическое оборудование']")).doubleClick();
        $(By.xpath("//td[text()='Провода и рельсы']")).doubleClick();
    }

    public void unlockInterface() {
        try {
            $(By.xpath("//button[@title='Редактировать']")).click();
        } catch (Throwable e) {
            logger.warn("Кнопка 'Редактировать' недоступна. Возможно, интерфейс уже разблокирован для редактирования.");
        }
    }

    public void pushSaveButton() {
        $(By.xpath("//button[@title='Сохранить']")).click();
    }

    public WireAndRailRow insertRow(int idx) {
        if (idx == 0) {
            throw new IllegalArgumentException("Вставка строки в нулевую позицию не предусмотрена");
        } else if (idx > 0) { // для вставки строки на i-ю позицию нужно нажать на кнопку в предыдущей строке
            --idx;
        }
        getAddButton(idx).click();
        return WireAndRailRow.from(findRow(idx));
    }

    public void deleteRow(int idx) {
        getDelButton(idx).click();
    }

    public SelenideElement getNameInput(int rowIdx) {
        return findRow(rowIdx).$(By.xpath("./td[position()=1]/div/input"));
    }

    public SelenideElement findNameInputByValue(String value) {
        return $(By.xpath(String.format("//tbody/tr/td/div/input[@value='%s']", value)));
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

    public SelenideElement getThermalCapacityInput(int rowIdx) {
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

    public WireAndRailRow findRowByWireName(String name) {
        var nameInput = $(By.xpath(String.format("//tbody/tr/td/div/input[@value='%s']", name)));
        return WireAndRailRow.from(nameInput.parent().parent().parent());
    }

    private static SelenideElement findRow(int idx) {
        String pos = idx >= 0 ? Integer.toString(idx + 1) : String.format("last()+1-%d", -idx);
        return $(By.xpath(String.format("//tbody/tr[position()=%s]", pos)));
    }

    private static ElementsCollection findAllRows() {
        return $$(By.xpath("//tbody/tr"));
    }
}

