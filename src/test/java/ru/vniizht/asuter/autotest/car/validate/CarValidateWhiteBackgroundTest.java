package ru.vniizht.asuter.autotest.car.validate;

import com.codeborne.selenide.Condition;
import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.constants.Color;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import static com.codeborne.selenide.Condition.*;

public class CarValidateWhiteBackgroundTest extends BaseTest {

    private static final Condition whiteBackground = cssValue("background-color", Color.WHITE.rgbaValue);

    @QaseId(305)
    @ParameterizedTest
    @ValueSource(strings = {"0", "12", "=", "Wagon", "Вагон грузовой", "Вагон с длинным наименованием из пятидесяти символ"})
    @DisplayName("Белый фон при вводе валидных значений в поле ввода Наименование")
    public void testNameInputValidCases(String v) {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .inputName(v)
                .pressTab()
                .check(p -> p.nameInput.shouldHave(whiteBackground));
    }

    @QaseId(306)
    @ParameterizedTest
    @ValueSource(strings = {"1", "1,001", "9999,999", "10000", "1234"})
    @DisplayName("Белый фон при вводе валидных значений в поля ввода Масса тары и Масса нетто")
    public void testContainerMassNetMassInputValidCases(String v) {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .inputContainerMass(v)
                .pressTab()
                .check(p -> p.containerMassInput.shouldHave(whiteBackground))
                .inputNetMass(v)
                .pressTab()
                .check(p -> p.netMassInput.shouldHave(whiteBackground));

    }

    @QaseId(307)
    @ParameterizedTest
    @ValueSource(strings = {"1", "1,001", "999,999", "1000", "123"})
    @DisplayName("Белый фон при вводе валидных значений в поле ввода Длина по осям сцепления")
    public void testLengthInputValidCases(String v) {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .inputLength(v)
                .pressTab()
                .check(p -> p.lengthInput.shouldHave(whiteBackground));

    }

    @QaseId(308)
    @ParameterizedTest
    @ValueSource(strings = {"0,098", "0,099", "12", "97,999", "98"})
    @DisplayName("Белый фон при вводе валидных значений в поля коэффициентов А")
    public void testCoefficientAInputValidCases(String v) {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .inputComponentRailA(v)
                .pressTab()
                .check(p -> p.componentRailAInput.shouldHave(whiteBackground))
                .inputContinuousRailA(v)
                .pressTab()
                .check(p -> p.continuousRailAInput.shouldHave(whiteBackground));

    }

    @QaseId(309)
    @ParameterizedTest
    @ValueSource(strings = {"0,098", "0,099", "123", "979,999", "980"})
    @DisplayName("Белый фон при вводе валидных значений в поля коэффициентов B")
    public void testCoefficientBInputValidCases(String v) {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .inputComponentRailB(v)
                .pressTab()
                .check(p -> p.componentRailBInput.shouldHave(whiteBackground))
                .inputContinuousRailB(v)
                .pressTab()
                .check(p -> p.continuousRailBInput.shouldHave(whiteBackground));

    }

    @QaseId(310)
    @ParameterizedTest
    @ValueSource(strings = {"0,0098", "0,0099", "1", "9,799", "9,8"})
    @DisplayName("Белый фон при вводе валидных значений в поля коэффициентов C")
    public void testCoefficientCInputValidCases(String v) {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .inputComponentRailC(v)
                .pressTab()
                .check(p -> p.componentRailCInput.shouldHave(whiteBackground))
                .inputContinuousRailC(v)
                .pressTab()
                .check(p -> p.continuousRailCInput.shouldHave(whiteBackground));

    }

    @QaseId(311)
    @ParameterizedTest
    @ValueSource(strings = {"0,0000098", "0,0000099", "1", "9,7999999", "9,8"})
    @DisplayName("Белый фон при вводе валидных значений в поля коэффициентов D")
    public void testCoefficientDInputValidCases(String v) {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .inputComponentRailD(v)
                .pressTab()
                .check(p -> p.componentRailDInput.shouldHave(whiteBackground))
                .inputContinuousRailD(v)
                .pressTab()
                .check(p -> p.continuousRailDInput.shouldHave(whiteBackground));

    }
}
