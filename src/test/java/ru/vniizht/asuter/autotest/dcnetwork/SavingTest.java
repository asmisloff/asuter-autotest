package ru.vniizht.asuter.autotest.dcnetwork;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.pages.equipment.electrical.PageDirectNetwork;
import ru.vniizht.asuter.autotest.pages.equipment.electrical.PageDirectNetworkList;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Selenide.element;

@DisplayName("Сохранение")
public class SavingTest extends BaseTest {

    private final Queue<String> namesToDelete = new LinkedList<>();

    @AfterEach
    public void deleteCreated() {
        PageDirectNetworkList page = open(PageDirectNetworkList.class)
                .waitTime();
        while (!namesToDelete.isEmpty()) {
            page.findRowByNetworkName(namesToDelete.poll()).delete().waitTime();
        }
    }

    @Test
    @QaseId(130)
    @DisplayName("Сохранение питающей линии с валидными данными")
    public void validSupplyLineData() {
        // значения расчетных данных на странице
        AtomicReference<String> calculatedRks = new AtomicReference<>();
        AtomicReference<String> calculatedRp = new AtomicReference<>();
        AtomicReference<String> calculatedIdop = new AtomicReference<>();
        AtomicReference<String> calculatedTdl = new AtomicReference<>();
        AtomicReference<String> calculatedIkp = new AtomicReference<>();
        // выбранные марки на странице
        AtomicReference<String> feederMark = new AtomicReference<>();
        AtomicReference<String> trackMark = new AtomicReference<>();

        loginIfNeeded();
        String networkName = open(PageDirectNetworkList.class)
                .clickCreate()
                .inputTrackQuantity(1)
                .pressTab()
                .selectFirstTrack()
                .selectFirstFeeder()
                .clickCalculate()
                .waitTime()
                .also(p -> {
                    // марки
                    feederMark.set(p.dropdownMenuFeederMark.getFirstSelectedOption().getText());
                    trackMark.set(p.dropdownMenuTrackMark.getFirstSelectedOption().getText());
                    // расчетные данные
                    calculatedRks.set(p.calculatedRks.val());
                    calculatedRp.set(p.calculatedRp.val());
                    calculatedIdop.set(p.calculatedIdop.val());
                    calculatedTdl.set(p.calculatedTdl.val());
                    calculatedIkp.set(p.calculatedIkp.val());
                })
                .clickSave()
                .resolveNameByFields();

        namesToDelete.add(networkName);

        var row = open(PageDirectNetworkList.class)
                .waitTime()
                .findRowByNetworkName(networkName);

        // Проверка: данные совпадают с сохраненными (в списке)
        row.cellRks.shouldHave(exactText(calculatedRks.get().replaceAll("-$", "")));
        row.cellRp.shouldHave(exactText(calculatedRp.get().replaceAll("-$", "")));
        row.cellIdop.shouldHave(exactText(calculatedIdop.get().replaceAll("-$", "")));
        row.cellTdl.shouldHave(exactText(calculatedTdl.get().replaceAll("-$", "")));
        row.cellIkp.shouldHave(exactText(calculatedIkp.get().replaceAll("-$", "")));

        // Проверка: данные совпадают с сохраненными (при открытии страницы сети заново)
        PageDirectNetwork pageDirectNetwork = row.openNetwork();
        // Количество
        pageDirectNetwork.inputFeederQuantity.shouldHave(value("1"));
        pageDirectNetwork.inputTrackQuantity.shouldHave(value("1"));
        pageDirectNetwork.inputPowerWireQuantity.shouldBe(empty);
        pageDirectNetwork.inputContactWireQuantity.shouldBe(empty);
        // Марки
        element(pageDirectNetwork.dropdownMenuTrackMark.getFirstSelectedOption())
                .shouldHave(text(trackMark.get()));
        element(pageDirectNetwork.dropdownMenuFeederMark.getFirstSelectedOption())
                .shouldHave(text(feederMark.get()));
        element(pageDirectNetwork.dropdownMenuContactWireMark.getFirstSelectedOption())
                .shouldBe(empty);
        element(pageDirectNetwork.dropdownMenuPowerWireMark.getFirstSelectedOption())
                .shouldBe(empty);
        // Расчетные данные
        pageDirectNetwork.calculatedRks.shouldHave(value(calculatedRks.get()));
        pageDirectNetwork.calculatedRp.shouldHave(value(calculatedRp.get()));
        pageDirectNetwork.calculatedIdop.shouldHave(value(calculatedIdop.get()));
        pageDirectNetwork.calculatedTdl.shouldHave(value(calculatedTdl.get()));
        pageDirectNetwork.calculatedIkp.shouldHave(value(calculatedIkp.get()));
        // Питающая линия или тяговая сеть
        pageDirectNetwork.buttonSupplyLine.parent().shouldHave(cssClass("active")); // ищем у родителя - так реализовано на фронте
        pageDirectNetwork.buttonTractionNetwork.parent().shouldHave(cssClass("disabled")); // ищем у родителя - так реализовано на фронте
    }


    @Test
    @QaseId(138)
    @DisplayName("Сохранение тяговой сети с валидными данными")
    public void validTractionNetworkData() {
        // значения расчетных данных на странице
        AtomicReference<String> calculatedRks = new AtomicReference<>();
        AtomicReference<String> calculatedRp = new AtomicReference<>();
        AtomicReference<String> calculatedIdop = new AtomicReference<>();
        AtomicReference<String> calculatedTdl = new AtomicReference<>();
        AtomicReference<String> calculatedIkp = new AtomicReference<>();
        // выбранные марки на странице
        AtomicReference<String> feederMark = new AtomicReference<>();
        AtomicReference<String> trackMark = new AtomicReference<>();
        AtomicReference<String> powerWireMark = new AtomicReference<>();
        AtomicReference<String> contactWireMark = new AtomicReference<>();

        loginIfNeeded();
        String networkName = open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputTrackQuantity(1)
                .inputFeederWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputContactWireQuantity(1)
                .pressTab()
                .selectFirstTrack()
                .selectFirstFeeder()
                .selectFirstPowerWire()
                .selectFirstContactWire()
                .clickCalculate()
                .waitTime()
                .also(p -> {
                    // марки
                    feederMark.set(p.dropdownMenuFeederMark.getFirstSelectedOption().getText());
                    trackMark.set(p.dropdownMenuTrackMark.getFirstSelectedOption().getText());
                    powerWireMark.set(p.dropdownMenuPowerWireMark.getFirstSelectedOption().getText());
                    contactWireMark.set(p.dropdownMenuContactWireMark.getFirstSelectedOption().getText());
                    // расчетные данные
                    calculatedRks.set(p.calculatedRks.val());
                    calculatedRp.set(p.calculatedRp.val());
                    calculatedIdop.set(p.calculatedIdop.val());
                    calculatedTdl.set(p.calculatedTdl.val());
                    calculatedIkp.set(p.calculatedIkp.val());
                })
                .clickSave()
                .resolveNameByFields();

        namesToDelete.add(networkName);

        var row = open(PageDirectNetworkList.class)
                .waitTime()
                .findRowByNetworkName(networkName);

        // Проверка: данные совпадают с сохраненными (в списке)
        row.cellRks.shouldHave(exactText(calculatedRks.get().replaceAll("-$", "")));
        row.cellRp.shouldHave(exactText(calculatedRp.get().replaceAll("-$", "")));
        row.cellIdop.shouldHave(exactText(calculatedIdop.get().replaceAll("-$", "")));
        row.cellTdl.shouldHave(exactText(calculatedTdl.get().replaceAll("-$", "")));
        row.cellIkp.shouldHave(exactText(calculatedIkp.get().replaceAll("-$", "")));

        // Проверка: данные совпадают с сохраненными (при открытии страницы сети заново)
        PageDirectNetwork pageDirectNetwork = row.openNetwork();
        // Количество
        pageDirectNetwork.inputFeederQuantity.shouldHave(value("1"));
        pageDirectNetwork.inputTrackQuantity.shouldHave(value("1"));
        pageDirectNetwork.inputPowerWireQuantity.shouldHave(value("1"));
        pageDirectNetwork.inputContactWireQuantity.shouldHave(value("1"));
        // Марки
        element(pageDirectNetwork.dropdownMenuTrackMark.getFirstSelectedOption())
                .shouldHave(text(trackMark.get()));
        element(pageDirectNetwork.dropdownMenuFeederMark.getFirstSelectedOption())
                .shouldHave(text(feederMark.get()));
        element(pageDirectNetwork.dropdownMenuContactWireMark.getFirstSelectedOption())
                .shouldBe(text(contactWireMark.get()));
        element(pageDirectNetwork.dropdownMenuPowerWireMark.getFirstSelectedOption())
                .shouldBe(text(powerWireMark.get()));
        // Расчетные данные
        pageDirectNetwork.calculatedRks.shouldHave(value(calculatedRks.get()));
        pageDirectNetwork.calculatedRp.shouldHave(value(calculatedRp.get()));
        pageDirectNetwork.calculatedIdop.shouldHave(value(calculatedIdop.get()));
        pageDirectNetwork.calculatedTdl.shouldHave(value(calculatedTdl.get()));
        pageDirectNetwork.calculatedIkp.shouldHave(value(calculatedIkp.get()));
        // Питающая линия или тяговая сеть
        pageDirectNetwork.buttonSupplyLine.parent().shouldHave(cssClass("disabled")); // ищем у родителя - так реализовано на фронте
        pageDirectNetwork.buttonTractionNetwork.parent().shouldHave(cssClass("active")); // ищем у родителя - так реализовано на фронте
    }


    @Test
    @QaseId(139)
    @DisplayName("Сохранение питающей линии созданной на основании текущей")
    public void powerLineBasedOnExistingOne() {
        // значения расчетных данных на странице
        AtomicReference<String> calculatedRks = new AtomicReference<>();
        AtomicReference<String> calculatedRp = new AtomicReference<>();
        AtomicReference<String> calculatedIdop = new AtomicReference<>();
        AtomicReference<String> calculatedTdl = new AtomicReference<>();
        AtomicReference<String> calculatedIkp = new AtomicReference<>();
        // выбранные марки на странице
        AtomicReference<String> feederMark = new AtomicReference<>();

        loginIfNeeded();
        String networkName = open(PageDirectNetworkList.class)
                .clickCreate()
                .selectFirstFeeder()
                .clickCalculate()
                .waitTime()
                .also(p -> {
                    // марки
                    feederMark.set(p.dropdownMenuFeederMark.getFirstSelectedOption().getText());
                    // расчетные данные
                    calculatedRks.set(p.calculatedRks.val());
                    calculatedRp.set(p.calculatedRp.val());
                    calculatedIdop.set(p.calculatedIdop.val());
                    calculatedTdl.set(p.calculatedTdl.val());
                    calculatedIkp.set(p.calculatedIkp.val());
                })
                .clickSave()
                .clickCopy()
                .waitTime()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .resolveNameByFields();

        namesToDelete.add(networkName);
        namesToDelete.add(networkName);

        var listPage = open(PageDirectNetworkList.class).waitTime();

        listPage.findRowsByNetworkName(networkName).stream()
                .limit(2)
                .forEach(row -> {
                    // Проверка: данные совпадают с сохраненными (в списке)
                    row.cellRks.shouldHave(exactText(calculatedRks.get().replaceAll("-$", "")));
                    row.cellRp.shouldHave(exactText(calculatedRp.get().replaceAll("-$", "")));
                    row.cellIdop.shouldHave(exactText(calculatedIdop.get().replaceAll("-$", "")));
                    row.cellTdl.shouldHave(exactText(calculatedTdl.get().replaceAll("-$", "")));
                    row.cellIkp.shouldHave(exactText(calculatedIkp.get().replaceAll("-$", "")));
                    // Проверка: данные совпадают с сохраненными (при открытии страницы сети заново)
                    PageDirectNetwork pageDirectNetwork = row.openNetwork();
                    // Количество
                    pageDirectNetwork.inputFeederQuantity.shouldHave(value("1"));
                    pageDirectNetwork.inputTrackQuantity.shouldHave(value("0"));
                    pageDirectNetwork.inputPowerWireQuantity.shouldBe(empty);
                    pageDirectNetwork.inputContactWireQuantity.shouldBe(empty);
                    // Марки
                    element(pageDirectNetwork.dropdownMenuFeederMark.getFirstSelectedOption())
                            .shouldHave(text(feederMark.get()));
                    element(pageDirectNetwork.dropdownMenuTrackMark.getFirstSelectedOption())
                            .shouldBe(empty);
                    element(pageDirectNetwork.dropdownMenuContactWireMark.getFirstSelectedOption())
                            .shouldBe(empty);
                    element(pageDirectNetwork.dropdownMenuPowerWireMark.getFirstSelectedOption())
                            .shouldBe(empty);
                    // Расчетные данные
                    pageDirectNetwork.calculatedRks.shouldHave(value(calculatedRks.get()));
                    pageDirectNetwork.calculatedRp.shouldHave(value(calculatedRp.get()));
                    pageDirectNetwork.calculatedIdop.shouldHave(value(calculatedIdop.get()));
                    pageDirectNetwork.calculatedTdl.shouldHave(value(calculatedTdl.get()));
                    pageDirectNetwork.calculatedIkp.shouldHave(value(calculatedIkp.get()));
                    // Питающая линия или тяговая сеть
                    pageDirectNetwork.buttonSupplyLine.parent().shouldHave(cssClass("active")); // ищем у родителя - так реализовано на фронте
                    pageDirectNetwork.buttonTractionNetwork.parent().shouldHave(cssClass("disabled")); // ищем у родителя - так реализовано на фронте
                    // возвращаемся в список
                    open(PageDirectNetworkList.class).waitTime();
                });
    }


    @Test
    @QaseId(140)
    @DisplayName("Сохранение тяговой сети созданной на основании текущей")
    public void tractionNetworkBasedOnExistingOne() {
        // значения расчетных данных на странице
        AtomicReference<String> calculatedRks = new AtomicReference<>();
        AtomicReference<String> calculatedRp = new AtomicReference<>();
        AtomicReference<String> calculatedIdop = new AtomicReference<>();
        AtomicReference<String> calculatedTdl = new AtomicReference<>();
        AtomicReference<String> calculatedIkp = new AtomicReference<>();
        // выбранные марки на странице
        AtomicReference<String> feederMark = new AtomicReference<>();
        AtomicReference<String> trackMark = new AtomicReference<>();
        AtomicReference<String> powerWireMark = new AtomicReference<>();
        AtomicReference<String> contactWireMark = new AtomicReference<>();

        loginIfNeeded();
        String networkName = open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputTrackQuantity(1)
                .inputFeederWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputContactWireQuantity(1)
                .pressTab()
                .selectFirstTrack()
                .selectFirstFeeder()
                .selectFirstPowerWire()
                .selectFirstContactWire()
                .clickCalculate()
                .waitTime()
                .also(p -> {
                    // марки
                    feederMark.set(p.dropdownMenuFeederMark.getFirstSelectedOption().getText());
                    trackMark.set(p.dropdownMenuTrackMark.getFirstSelectedOption().getText());
                    powerWireMark.set(p.dropdownMenuPowerWireMark.getFirstSelectedOption().getText());
                    contactWireMark.set(p.dropdownMenuContactWireMark.getFirstSelectedOption().getText());
                    // расчетные данные
                    calculatedRks.set(p.calculatedRks.val());
                    calculatedRp.set(p.calculatedRp.val());
                    calculatedIdop.set(p.calculatedIdop.val());
                    calculatedTdl.set(p.calculatedTdl.val());
                    calculatedIkp.set(p.calculatedIkp.val());
                })
                .clickSave()
                .clickCopy()
                .waitTime()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .resolveNameByFields();

        namesToDelete.add(networkName);
        namesToDelete.add(networkName);

        var listPage = open(PageDirectNetworkList.class).waitTime();

        listPage.findRowsByNetworkName(networkName).stream()
                .limit(2)
                .forEach(row -> {
                    // Проверка: данные совпадают с сохраненными (в списке)
                    row.cellRks.shouldHave(exactText(calculatedRks.get().replaceAll("-$", "")));
                    row.cellRp.shouldHave(exactText(calculatedRp.get().replaceAll("-$", "")));
                    row.cellIdop.shouldHave(exactText(calculatedIdop.get().replaceAll("-$", "")));
                    row.cellTdl.shouldHave(exactText(calculatedTdl.get().replaceAll("-$", "")));
                    row.cellIkp.shouldHave(exactText(calculatedIkp.get().replaceAll("-$", "")));
                    // Проверка: данные совпадают с сохраненными (при открытии страницы сети заново)
                    PageDirectNetwork pageDirectNetwork = row.openNetwork();
                    // Количество
                    pageDirectNetwork.inputFeederQuantity.shouldHave(value("1"));
                    pageDirectNetwork.inputTrackQuantity.shouldHave(value("1"));
                    pageDirectNetwork.inputPowerWireQuantity.shouldHave(value("1"));
                    pageDirectNetwork.inputContactWireQuantity.shouldHave(value("1"));
                    // Марки
                    element(pageDirectNetwork.dropdownMenuTrackMark.getFirstSelectedOption())
                            .shouldHave(text(trackMark.get()));
                    element(pageDirectNetwork.dropdownMenuFeederMark.getFirstSelectedOption())
                            .shouldHave(text(feederMark.get()));
                    element(pageDirectNetwork.dropdownMenuContactWireMark.getFirstSelectedOption())
                            .shouldBe(text(contactWireMark.get()));
                    element(pageDirectNetwork.dropdownMenuPowerWireMark.getFirstSelectedOption())
                            .shouldBe(text(powerWireMark.get()));
                    // Расчетные данные
                    pageDirectNetwork.calculatedRks.shouldHave(value(calculatedRks.get()));
                    pageDirectNetwork.calculatedRp.shouldHave(value(calculatedRp.get()));
                    pageDirectNetwork.calculatedIdop.shouldHave(value(calculatedIdop.get()));
                    pageDirectNetwork.calculatedTdl.shouldHave(value(calculatedTdl.get()));
                    pageDirectNetwork.calculatedIkp.shouldHave(value(calculatedIkp.get()));
                    // Питающая линия или тяговая сеть
                    pageDirectNetwork.buttonSupplyLine.parent().shouldHave(cssClass("disabled")); // ищем у родителя - так реализовано на фронте
                    pageDirectNetwork.buttonTractionNetwork.parent().shouldHave(cssClass("active")); // ищем у родителя - так реализовано на фронте
                    // возвращаемся в список
                    open(PageDirectNetworkList.class).waitTime();
                });
    }


}
