package ru.vniizht.asuter.autotest.dcnetwork;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.pages.equipment.electrical.PageDirectNetworkList;

import java.util.concurrent.atomic.AtomicReference;

@DisplayName("Тяговые сети постоянного тока: удаление питающей линии")
public class DeletingTest extends BaseTest {

    @Test
    @QaseId(193)
    @DisplayName("Удаление питающей линии")
    public void deleteSupplyLine() {
        AtomicReference<String> name = new AtomicReference<>();
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputFeederWireQuantity(1)
                .pressTab()
                .selectFirstFeeder()
                .clickCalculate()
                .waitTime()
                .also(p -> name.set(p.resolveNameByFields()))
                .clickSave()
                .waitTime();
        PageDirectNetworkList listPage = open(PageDirectNetworkList.class).waitTime();
        PageDirectNetworkList.DirectNetworkListRow row = listPage.findRowByNetworkName(name.get());
        int sameName = listPage.findRowsByNetworkName(name.get()).size();
        row.delete();
        listPage.waitTime();
        Assertions.assertEquals(sameName - 1, listPage.findRowsByNetworkName(name.get()).size());
    }

    @Test
    @QaseId(194)
    @DisplayName("Удаление тяговой сети")
    public void deleteTractionNetwork() {
        AtomicReference<String> name = new AtomicReference<>();
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .clickCalculate()
                .waitTime()
                .also(p -> name.set(p.resolveNameByFields()))
                .clickSave()
                .waitTime();
        PageDirectNetworkList listPage = open(PageDirectNetworkList.class).waitTime();
        PageDirectNetworkList.DirectNetworkListRow row = listPage.findRowByNetworkName(name.get());
        int sameName = listPage.findRowsByNetworkName(name.get()).size();
        row.delete();
        listPage.waitTime();
        Assertions.assertEquals(sameName - 1, listPage.findRowsByNetworkName(name.get()).size());
    }

}
