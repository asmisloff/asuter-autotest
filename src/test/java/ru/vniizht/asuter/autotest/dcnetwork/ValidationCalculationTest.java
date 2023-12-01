package ru.vniizht.asuter.autotest.dcnetwork;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.pages.equipment.electrical.PageDirectNetworkList;

import static com.codeborne.selenide.Condition.exactText;

@DisplayName("Валидация при нажатии на кнопку Рассчитать")
public class ValidationCalculationTest extends BaseTest {

    @ParameterizedTest
    @QaseId(146)
    @DisplayName("Невозможность рассчитать с невалидными значениями в поле \"Количество\" питающего провода")
    @ValueSource(strings = { "", "0", "-1", "21", "99", "19,99", "0,00000000001", "9999999999999" })
    public void unableToCalculateWithInvalidFeederQuantity(String invalidValue) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .inputFeederWireQuantity(invalidValue)
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }

    @ParameterizedTest
    @QaseId(169)
    @DisplayName("Невозможность рассчитать с невалидными значениями в поле \"Количество\" путей питающей линии")
    @ValueSource(strings = { "-1", "21", "99", "19,99", "0,00000000001", "9999999999999" })
    public void unableToCalculateWithInvalidTrackQuantity(String invalidValue) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .selectFirstFeeder()
                .waitTime()
                .inputTrackQuantity(invalidValue)
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }

    @ParameterizedTest
    @QaseId(171)
    @DisplayName("Невозможность рассчитать с невалидными значениями в поле \"Количество\" несущего провода тяговой сети")
    @ValueSource(strings = { "", "0", "-1", "21", "99", "19,99", "0,00000000001", "9999999999999" })
    public void unableToCalculateWithInvalidSupplyQuantity(String invalidValue) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .inputFeederWireQuantity(invalidValue)
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }

    @ParameterizedTest
    @QaseId(172)
    @DisplayName("Невозможность рассчитать с невалидными значениями в поле \"Количество\" контактного провода тяговой сети")
    @ValueSource(strings = { "", "0", "-1", "21", "99", "19,99", "0,00000000001", "9999999999999" })
    public void unableToCalculateWithInvalidContactQuantity(String invalidValue) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .inputContactWireQuantity(invalidValue)
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }

    @ParameterizedTest
    @QaseId(173)
    @DisplayName("Невозможность рассчитать с невалидными значениями в поле \"Количество\" усиливающего провода тяговой сети")
    @ValueSource(strings = { "", "0", "-1", "21", "99", "19,99", "0,00000000001", "9999999999999" })
    public void unableToCalculateWithInvalidPowerQuantity(String invalidValue) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .inputPowerWireQuantity(invalidValue)
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }

    @ParameterizedTest
    @QaseId(174)
    @DisplayName("Невозможность рассчитать с невалидными значениями в поле \"Количество\" путей тяговой сети")
    @ValueSource(strings = { "", "0", "-1", "21", "99", "19,99", "0,00000000001", "9999999999999" })
    public void unableToCalculateWithInvalidTrackQuantityTractiveNetwork(String invalidValue) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .inputTrackQuantity(invalidValue)
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }

    @Test
    @QaseId(175)
    @DisplayName("Невозможность рассчитать с пустым значением в выпадающем списке \"Марка\" питающего провода питающей линии")
    public void unableToCalculateWithInvalidFeederMarkSupplyLine() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }

    @Test
    @QaseId(176)
    @DisplayName("Невозможность рассчитать с пустым значением в выпадающем списке \"Марка\" путей питающей линии")
    public void unableToCalculateWithInvalidTrackMarkSupplyLine() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .selectFirstFeeder()
                .inputTrackQuantity(1)
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }

    @Test
    @QaseId(178)
    @DisplayName("Невозможность рассчитать с пустым значением в выпадающем списке \"Марка\" несущего провода тяговой сети")
    public void unableToCalculateWithInvalidFeederMarkTractiveNetwork() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .selectFeederByName(null)
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }

    @Test
    @QaseId(177)
    @DisplayName("Невозможность рассчитать с пустым значением в выпадающем списке \"Марка\" контактного провода тяговой сети")
    public void unableToCalculateWithInvalidContactMarkTractiveNetwork() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .selectContactWireByName(null)
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }

    @Test
    @QaseId(179)
    @DisplayName("Невозможность рассчитать с пустым значением в выпадающем списке \"Марка\" усиливающего провода тяговой сети")
    public void unableToCalculateWithInvalidPowerMarkTractiveNetwork() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .selectPowerWireByName(null)
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }

    @Test
    @QaseId(180)
    @DisplayName("Невозможность рассчитать с пустым значением в выпадающем списке \"Марка\" путей тяговой сети")
    public void unableToCalculateWithInvalidTrackMarkTractiveNetwork() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .selectFirstContactWire()
                .selectFirstPowerWire()
                .selectFirstTrack()
                .selectTrackByName(null)
                .clickCalculate()
                .waitTime()
                .check(p -> p.alertMessage.shouldHave(exactText("Не все данные введены корректно.")))
                .clickCloseAlert();
    }



}
