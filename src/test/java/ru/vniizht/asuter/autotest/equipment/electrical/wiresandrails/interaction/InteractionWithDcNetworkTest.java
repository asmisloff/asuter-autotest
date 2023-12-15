package ru.vniizht.asuter.autotest.equipment.electrical.wiresandrails.interaction;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.constants.Color;
import ru.vniizht.asuter.autotest.constants.WireMaterial;
import ru.vniizht.asuter.autotest.pages.equipment.electrical.PageDirectNetworkList;
import ru.vniizht.asuter.autotest.pages.equipment.electrical.PageWiresAndRails;

import java.util.UUID;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.element;

@DisplayName("Провода и рельсы: взаимодействие со страницей тяговых сетей постоянного тока")
public class InteractionWithDcNetworkTest extends BaseTest {

    private String wireOrRailName = "";
    private String networkName = "";

    @AfterEach
    public void deleteCreatedWireOrRail() {
        if (wireOrRailName.isBlank()) return;
        loginIfNeeded();
        open(PageWiresAndRails.class)
                .clickEdit()
                .findEditableRowByWireName(wireOrRailName)
                .clickRemove()
                .clickSave();
        wireOrRailName = "";
    }

    @AfterEach
    public void deleteCreatedNetwork() {
        if (networkName.isBlank()) return;
        open(PageDirectNetworkList.class)
                .waitTime()
                .findRowByNetworkName(networkName)
                .delete()
                .waitTime();
    }

    private void createNewWire() {
        createNewWire(wireOrRailName);
    }

    private PageWiresAndRails createNewWire(String name) {
        return open(PageWiresAndRails.class)
                .clickEdit()
                .getEditableRow(1)
                .clickAddAndReturnCreatedRow()
                .inputName(name)
                .inputDcResistance("0,02")
                .inputAcResistance("0,2")
                .inputRadius("11,14")
                .inputLimitAmperage("600")
                .inputLimitTemperature("120")
                .inputThermalCapacity("99")
                .inputCrossSectionArea("200")
                .selectMaterial(WireMaterial.ALUMINIUM)
                .page()
                .clickSave();
    }

    @Test
    @QaseId(247)
    @DisplayName("Созданный провод отображается и можно выбрать при создании питающей линии постоянного тока")
    public void createdWireCanBeSelectedBySupplyLine() {
        wireOrRailName = "М-96-autotest-" + UUID.randomUUID();
        loginIfNeeded();
        createNewWire();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .selectFeederByName(wireOrRailName)
                .check(p -> element(p.dropdownMenuFeederMark.getFirstSelectedOption()).shouldHave(text(wireOrRailName)));
    }

    @Test
    @QaseId(248)
    @DisplayName("Созданный провод отображается и можно выбрать при создании тяговой сети постоянного тока")
    public void createdWireCanBeSelectedByTractionNetwork() {
        wireOrRailName = "М-96-autotest-" + UUID.randomUUID();
        loginIfNeeded();
        createNewWire();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .selectFeederByName(wireOrRailName)
                .check(p -> element(p.dropdownMenuFeederMark.getFirstSelectedOption()).shouldHave(text(wireOrRailName)));
    }

    @Test
    @QaseId(249)
    @DisplayName("Созданный рельс отображается и можно выбрать при создании тяговой сети постоянного тока")
    public void createdRailCanBeSelectedByTractionNetwork() {
        wireOrRailName = "Р-75-autotest-" + UUID.randomUUID();
        loginIfNeeded();
        createNewWire();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputTrackQuantity(2)
                .pressTab()
                .selectTrackByName(wireOrRailName)
                .check(p -> element(p.dropdownMenuTrackMark.getFirstSelectedOption()).shouldHave(text(wireOrRailName)));
    }

    @Test
    @QaseId(250)
    @DisplayName("Созданный рельс отображается и можно выбрать при создании питающей линии постоянного тока")
    public void createdRailCanBeSelectedBySupplyLine() {
        wireOrRailName = "Р-75-autotest-" + UUID.randomUUID();
        loginIfNeeded();
        createNewWire();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputTrackQuantity(2)
                .pressTab()
                .selectTrackByName(wireOrRailName)
                .check(p -> element(p.dropdownMenuTrackMark.getFirstSelectedOption()).shouldHave(text(wireOrRailName)));
    }

    @Test
    @QaseId(251)
    @DisplayName("Удаленный провод отсутствует в выпадающем списке при создании питающей линии постоянного тока")
    public void deletedWireCanNotBeSelectedBySupplyLine() {
        wireOrRailName = "";
        loginIfNeeded();
        String name = "М-96-autotest-" + UUID.randomUUID();
        createNewWire(name)
                .clickEdit()
                .findEditableRowByWireName(name)
                .clickRemove()
                .clickSave();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .dropdownMenuFeederMark
                .getOptions()
                .forEach(option -> element(option).shouldNotHave(text(name)));
    }

    @Test
    @QaseId(252)
    @DisplayName("Удаленный провод отсутствует в выпадающем списке при создании тяговой сети постоянного тока")
    public void deletedWireCanNotBeSelectedByTractionNetwork() {
        wireOrRailName = "";
        loginIfNeeded();
        String name = "М-96-autotest-" + UUID.randomUUID();
        createNewWire(name)
                .clickEdit()
                .findEditableRowByWireName(name)
                .clickRemove()
                .clickSave();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .dropdownMenuFeederMark
                .getOptions()
                .forEach(option -> element(option).shouldNotHave(text(name)));
    }

    @Test
    @QaseId(253)
    @DisplayName("Удаленный рельс отсутствует в выпадающем списке при создании тяговой сети постоянного тока")
    public void deletedRailCanNotBeSelectedByTractionNetwork() {
        wireOrRailName = "";
        loginIfNeeded();
        String name = "Р-75-autotest-" + UUID.randomUUID();
        createNewWire(name)
                .clickEdit()
                .findEditableRowByWireName(name)
                .clickRemove()
                .clickSave();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputTrackQuantity(2)
                .pressTab()
                .dropdownMenuTrackMark
                .getOptions()
                .forEach(option -> element(option).shouldNotHave(text(name)));
    }

    @Test
    @QaseId(254)
    @DisplayName("Удаленный рельс отсутствует в выпадающем списке при создании тяговой сети постоянного тока")
    public void deletedRailCanNotBeSelectedBySupplyLine() {
        wireOrRailName = "";
        loginIfNeeded();
        String name = "М-96-autotest-" + UUID.randomUUID();
        createNewWire(name)
                .clickEdit()
                .findEditableRowByWireName(name)
                .clickRemove()
                .clickSave();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputTrackQuantity(2)
                .pressTab()
                .dropdownMenuTrackMark
                .getOptions()
                .forEach(option -> element(option).shouldNotHave(text(name)));
    }

    @Test
    @QaseId(255)
    @DisplayName("Отображение удаленного провода в сохраненной тяговой сети постоянного тока")
    public void deletedWireShownInSavedTractionNetwork() {
        wireOrRailName = "";
        loginIfNeeded();
        String wireName = ("М-96-autotest-" + UUID.randomUUID()).substring(0, 25);
        createNewWire(wireName);
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(2)
                .inputContactWireQuantity(2)
                .inputPowerWireQuantity(2)
                .inputTrackQuantity(2)
                .selectFeederByName(wireName)
                .selectContactWireByName(wireName)
                .selectPowerWireByName(wireName)
                .selectFirstTrack()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .also(p -> networkName = p.resolveNameByFields())
                .waitTime();
        open(PageWiresAndRails.class)
                .clickEdit()
                .findEditableRowByWireName(wireName)
                .clickRemove()
                .clickSave();
        open(PageDirectNetworkList.class)
                .waitTime()
                .findRowByNetworkName(networkName)
                .openNetwork()
                .waitTime()
                .check(p -> {
                    element(p.dropdownMenuFeederMark.getFirstSelectedOption()).shouldHave(text(wireName));
                    element(p.dropdownMenuContactWireMark.getFirstSelectedOption()).shouldHave(text(wireName));
                    element(p.dropdownMenuPowerWireMark.getFirstSelectedOption()).shouldHave(text(wireName));
                    element(p.dropdownMenuFeederMark.getWrappedElement()).parent().shouldNotHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue));
                    element(p.dropdownMenuContactWireMark.getWrappedElement()).parent().shouldNotHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue));
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).parent().shouldNotHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue));
                    element(p.dropdownMenuFeederMark.getWrappedElement()).shouldHave(attribute("title", "Провод был удалён из базы данных, но может быть использован для проведения расчёта на основе текущего"));
                    element(p.dropdownMenuContactWireMark.getWrappedElement()).shouldHave(attribute("title", "Провод был удалён из базы данных, но может быть использован для проведения расчёта на основе текущего"));
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldHave(attribute("title", "Провод был удалён из базы данных, но может быть использован для проведения расчёта на основе текущего"));
                });
    }

    @Test
    @QaseId(256)
    @DisplayName("Отображение удаленного провода в сохраненной питающей линии постоянного тока")
    public void deletedWireShownInSavedSupplyLine() {
        wireOrRailName = "";
        loginIfNeeded();
        String wireName = ("М-96-autotest-" + UUID.randomUUID()).substring(0, 25);
        createNewWire(wireName);
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputFeederWireQuantity(2)
                .inputTrackQuantity(2)
                .selectFeederByName(wireName)
                .selectFirstTrack()
                .clickCalculate()
                .waitTime()
                .clickSave()
                .also(p -> networkName = p.resolveNameByFields())
                .waitTime();
        open(PageWiresAndRails.class)
                .clickEdit()
                .findEditableRowByWireName(wireName)
                .clickRemove()
                .clickSave();
        open(PageDirectNetworkList.class)
                .waitTime()
                .findRowByNetworkName(networkName)
                .openNetwork()
                .waitTime()
                .check(p -> {
                    element(p.dropdownMenuFeederMark.getFirstSelectedOption()).shouldHave(text(wireName));
                    element(p.dropdownMenuFeederMark.getWrappedElement()).parent().shouldNotHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue));
                    element(p.dropdownMenuFeederMark.getWrappedElement()).shouldHave(attribute("title", "Провод был удалён из базы данных, но может быть использован для проведения расчёта на основе текущего"));
                });
    }

    @Test
    @QaseId(257)
    @DisplayName("Отображение удаленного рельса в сохраненной тяговой сети постоянного тока")
    public void deletedRailShownInSavedTractionNetwork() {
        wireOrRailName = "";
        loginIfNeeded();
        String wireName = ("Р-75-autotest-" + UUID.randomUUID()).substring(0, 25);
        createNewWire(wireName);
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(2)
                .inputContactWireQuantity(2)
                .inputPowerWireQuantity(2)
                .inputTrackQuantity(2)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectTrackByName(wireName)
                .clickCalculate()
                .waitTime()
                .clickSave()
                .also(p -> networkName = p.resolveNameByFields())
                .waitTime();
        open(PageWiresAndRails.class)
                .clickEdit()
                .findEditableRowByWireName(wireName)
                .clickRemove()
                .clickSave();
        open(PageDirectNetworkList.class)
                .waitTime()
                .findRowByNetworkName(networkName)
                .openNetwork()
                .waitTime()
                .check(p -> {
                    element(p.dropdownMenuTrackMark.getFirstSelectedOption()).shouldHave(text(wireName));
                    element(p.dropdownMenuTrackMark.getWrappedElement()).parent().shouldNotHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue));
                    element(p.dropdownMenuTrackMark.getWrappedElement()).shouldHave(attribute("title", "Рельс был удалён из базы данных, но может быть использован для проведения расчёта на основе текущего"));
                });
    }

    @Test
    @QaseId(258)
    @DisplayName("Отображение удаленного рельса в сохраненной питающей линии постоянного тока")
    public void deletedRailShownInSavedSupplyLine() {
        wireOrRailName = "";
        loginIfNeeded();
        String wireName = ("Р-75-autotest-" + UUID.randomUUID()).substring(0, 25);
        createNewWire(wireName);
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputFeederWireQuantity(2)
                .inputTrackQuantity(2)
                .selectFirstFeeder()
                .selectTrackByName(wireName)
                .clickCalculate()
                .waitTime()
                .clickSave()
                .also(p -> networkName = p.resolveNameByFields())
                .waitTime();
        open(PageWiresAndRails.class)
                .clickEdit()
                .findEditableRowByWireName(wireName)
                .clickRemove()
                .clickSave();
        open(PageDirectNetworkList.class)
                .waitTime()
                .findRowByNetworkName(networkName)
                .openNetwork()
                .waitTime()
                .check(p -> {
                    element(p.dropdownMenuTrackMark.getFirstSelectedOption()).shouldHave(text(wireName));
                    element(p.dropdownMenuTrackMark.getWrappedElement()).parent().shouldNotHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue));
                    element(p.dropdownMenuTrackMark.getWrappedElement()).shouldHave(attribute("title", "Рельс был удалён из базы данных, но может быть использован для проведения расчёта на основе текущего"));
                });
    }



}
