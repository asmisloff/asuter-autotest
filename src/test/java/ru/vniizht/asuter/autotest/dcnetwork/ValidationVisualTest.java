package ru.vniizht.asuter.autotest.dcnetwork;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.constants.Color;
import ru.vniizht.asuter.autotest.pages.equipment.electrical.PageDirectNetworkList;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.element;

@DisplayName("Валидация (красные поля + подсказка)")
public class ValidationVisualTest extends BaseTest {

    @ParameterizedTest
    @QaseId(170)
    @DisplayName("Белый фон при вводе валидных значений в поле \"Количество\" питающего провода")
    @ValueSource(strings = { "1", "2", "19", "20" })
    public void validColorBackgroundFeederQuantity(String value) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputFeederWireQuantity(value)
                .pressTab()
                .check(p -> p.inputFeederQuantity.parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue)));
    }

    @ParameterizedTest
    @QaseId(182)
    @DisplayName("Белый фон при вводе валидных значений в поле \"Количество\" у пути питающей линии")
    @ValueSource(strings = { "1", "2", "19", "20", "", "  ", "0" })
    public void validColorBackgroundTrackQuantity(String value) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputTrackQuantity(value)
                .pressTab()
                .check(p -> p.inputTrackQuantity.parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue)));
    }

    @ParameterizedTest
    @QaseId(183)
    @DisplayName("Белый фон при вводе валидных значений в поле \"Количество\" обязательных проводов тяговой сети")
    @ValueSource(strings = { "1", "2", "19", "20" })
    public void validColorBackgroundRequiredTractiveNetworkFieldsQuantity(String value) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(value)
                .inputContactWireQuantity(value)
                .inputTrackQuantity(value)
                .pressTab()
                .check(p -> {
                    p.inputFeederQuantity.parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue));
                    p.inputContactWireQuantity.parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue));
                    p.inputTrackQuantity.parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue));
                });
    }

    @ParameterizedTest
    @QaseId(238)
    @DisplayName("Белый фон при вводе валидных значений в поле \"Количество\" усиливающего провода тяговой сети")
    @ValueSource(strings = { "1", "2", "19", "20", "", "  ", "0" })
    public void validColorBackgroundTractiveNetworkPowerWireQuantity(String value) {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputPowerWireQuantity(value)
                .pressTab()
                .check(p -> p.inputPowerWireQuantity.parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue)));
    }

    @ParameterizedTest
    @QaseId(181)
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поле \"Количество\" питающего провода")
    @CsvSource(value = {
            "empty:Поле, обязательное для заполнения",
            "0:Должно быть более 0",
            "21:Должно быть не более 20",
            "19,99:Должно быть целым числом",
            "E:todo", // TODO правильная подсказка
            "0,00000000001+2:todo", // TODO правильная подсказка
            "0,00000000001:Должно быть целым числом",
            "!1:todo", // TODO правильная подсказка
            "9 9:Должно быть не более 20"
    }, delimiter = ':')
    public void invalidColorBackgroundFeederQuantity(String value, String message) {
        var actualMessage = " " + message; // не подтягивает пробелы из @CsvSource
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputFeederWireQuantity(value.replace("empty", ""))
                .pressTab()
                .check(p -> {
                    p.inputFeederQuantity.parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    p.inputFeederQuantity.shouldHave(attribute("title", actualMessage));
                });
    }

    @ParameterizedTest
    @QaseId(184)
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поле \"Количество\" у пути питающей линии")
    @CsvSource(value = {
            "-1:Должно быть более 0",
            "21:Должно быть не более 20",
            "19,99:Должно быть целым числом",
            "E:todo", // TODO правильная подсказка
            "0,00000000001+2:todo", // TODO правильная подсказка
            "0,00000000001:Должно быть целым числом",
            "!1:todo", // TODO правильная подсказка
            "9 9:Должно быть не более 20"
    }, delimiter = ':')
    public void invalidColorBackgroundTrackQuantity(String value, String message) {
        var actualMessage = " " + message; // не подтягивает пробелы из @CsvSource
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputTrackQuantity(value.replace("empty", ""))
                .pressTab()
                .check(p -> {
                    p.inputTrackQuantity.parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    p.inputTrackQuantity.shouldHave(attribute("title", actualMessage));
                });
    }

    @ParameterizedTest
    @QaseId(185)
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поле \"Количество\" несущего провода тяговой сети")
    @CsvSource(value = {
            "empty:Поле, обязательное для заполнения",
            "0:Должно быть более 0",
            "-1:Должно быть более 0",
            "21:Должно быть не более 20",
            "19,99:Должно быть целым числом",
            "E:todo", // TODO правильная подсказка
            "0,00000000001+2:todo", // TODO правильная подсказка
            "0,00000000001:Должно быть целым числом",
            "!1:todo", // TODO правильная подсказка
            "9 9:Должно быть не более 20"
    }, delimiter = ':')
    public void invalidColorBackgroundFeederTractiveNetworkQuantity(String value, String message) {
        var actualMessage = " " + message; // не подтягивает пробелы из @CsvSource
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(value.replace("empty", ""))
                .pressTab()
                .check(p -> {
                    p.inputFeederQuantity.parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    p.inputFeederQuantity.shouldHave(attribute("title", actualMessage));
                });
    }

    @ParameterizedTest
    @QaseId(186)
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поле \"Количество\" контактного провода тяговой сети")
    @CsvSource(value = {
            "empty:Поле, обязательное для заполнения",
            "0:Должно быть более 0",
            "-1:Должно быть более 0",
            "21:Должно быть не более 20",
            "19,99:Должно быть целым числом",
            "E:todo", // TODO правильная подсказка
            "0,00000000001+2:todo", // TODO правильная подсказка
            "0,00000000001:Должно быть целым числом",
            "!1:todo", // TODO правильная подсказка
            "9 9:Должно быть не более 20"
    }, delimiter = ':')
    public void invalidColorBackgroundContactWireTractiveNetworkQuantity(String value, String message) {
        var actualMessage = " " + message; // не подтягивает пробелы из @CsvSource
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputContactWireQuantity(value.replace("empty", ""))
                .pressTab()
                .check(p -> {
                    p.inputContactWireQuantity.parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    p.inputContactWireQuantity.shouldHave(attribute("title", actualMessage));
                });
    }

    @ParameterizedTest
    @QaseId(187)
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поле \"Количество\" усиливающего провода тяговой сети")
    @CsvSource(value = {
            "-1:Должно быть более 0",
            "21:Должно быть не более 20",
            "19,99:Должно быть целым числом",
            "E:todo", // TODO правильная подсказка
            "0,00000000001+2:todo", // TODO правильная подсказка
            "0,00000000001:Должно быть целым числом",
            "!1:todo", // TODO правильная подсказка
            "9 9:Должно быть не более 20"
    }, delimiter = ':')
    public void invalidColorBackgroundPowerWireTractiveNetworkQuantity(String value, String message) {
        var actualMessage = " " + message; // не подтягивает пробелы из @CsvSource
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputPowerWireQuantity(value.replace("empty", ""))
                .pressTab()
                .check(p -> {
                    p.inputPowerWireQuantity.parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    p.inputPowerWireQuantity.shouldHave(attribute("title", actualMessage));
                });
    }

    @ParameterizedTest
    @QaseId(188)
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поле \"Количество\" у пути тяговой сети")
    @CsvSource(value = {
            "empty:Поле, обязательное для заполнения",
            "0:Должно быть более 0",
            "-1:Должно быть более 0",
            "21:Должно быть не более 20",
            "19,99:Должно быть целым числом",
            "E:todo", // TODO правильная подсказка
            "0,00000000001+2:todo", // TODO правильная подсказка
            "0,00000000001:Должно быть целым числом",
            "!1:todo", // TODO правильная подсказка
            "9 9:Должно быть не более 20"
    }, delimiter = ':')
    public void invalidColorBackgroundTrackTractiveNetworkQuantity(String value, String message) {
        var actualMessage = " " + message; // не подтягивает пробелы из @CsvSource
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputTrackQuantity(value.replace("empty", ""))
                .pressTab()
                .check(p -> {
                    p.inputTrackQuantity.parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    p.inputTrackQuantity.shouldHave(attribute("title", actualMessage));
                });
    }

    @Test
    @QaseId(189)
    @DisplayName("Белый фон при заполнении выпадающих списков \"Марка\" питающей линии")
    public void validColorOnSelectSupplyLineDropdowns() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .check(p -> element(p.dropdownMenuFeederMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue)))
                .selectFirstTrack()
                .check(p -> element(p.dropdownMenuTrackMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue)));
    }

    @Test
    @QaseId(190)
    @DisplayName("Белый фон при заполнении выпадающих списков \"Марка\" тяговой сети")
    public void validColorOnSelectTractionNetworkDropdowns() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .inputFeederWireQuantity(1)
                .inputContactWireQuantity(1)
                .inputPowerWireQuantity(1)
                .inputTrackQuantity(1)
                .selectFirstFeeder()
                .check(p -> element(p.dropdownMenuFeederMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue)))
                .selectFirstContactWire()
                .check(p -> element(p.dropdownMenuContactWireMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue)))
                .selectFirstPowerWire()
                .check(p -> element(p.dropdownMenuPowerWireMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue)))
                .selectFirstTrack()
                .check(p -> element(p.dropdownMenuTrackMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.VALID_INPUT.rgbaValue)));
    }

    @Test
    @QaseId(191)
    @DisplayName("Красный фон и правильные подсказки при пустых значениях в выпадающих списках \"Марка\" питающего провода")
    public void invalidColorOnEmptySupplyLineDropdowns() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .waitTime()
                .check(p -> {
                    element(p.dropdownMenuFeederMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    element(p.dropdownMenuFeederMark.getWrappedElement()).shouldHave(attribute("title", "Поле, обязательное для заполнения"));
                })
                .inputTrackQuantity(1)
                .pressTab()
                .check(p -> {
                    element(p.dropdownMenuTrackMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    element(p.dropdownMenuTrackMark.getWrappedElement()).shouldHave(attribute("title", "Поле, обязательное для заполнения"));
                });
    }

    @Test
    @QaseId(192)
    @DisplayName("Красный фон и правильные подсказки при пустых значениях в выпадающих списках \"Марка\" тяговой сети")
    public void invalidColorOnEmptyTractionNetworkDropdowns() {
        loginIfNeeded();
        open(PageDirectNetworkList.class)
                .clickCreate()
                .switchToTractionNetwork()
                .waitTime()
                .check(p -> {
                    element(p.dropdownMenuFeederMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    element(p.dropdownMenuFeederMark.getWrappedElement()).shouldHave(attribute("title", "Поле, обязательное для заполнения"));
                    element(p.dropdownMenuContactWireMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    element(p.dropdownMenuContactWireMark.getWrappedElement()).shouldHave(attribute("title", "Поле, обязательное для заполнения"));
                })
                .inputPowerWireQuantity(1)
                .pressTab()
                .waitTime()
                .check(p -> {
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    element(p.dropdownMenuPowerWireMark.getWrappedElement()).shouldHave(attribute("title", "Поле, обязательное для заполнения"));
                })
                .inputTrackQuantity(1)
                .pressTab()
                .check(p -> {
                    element(p.dropdownMenuTrackMark.getWrappedElement()).parent().shouldHave(cssValue("background-color", Color.INVALID_INPUT.rgbaValue));
                    element(p.dropdownMenuTrackMark.getWrappedElement()).shouldHave(attribute("title", "Поле, обязательное для заполнения"));
                });
    }

}
