package ru.vniizht.asuter.autotest.car.validate;

import com.codeborne.selenide.Condition;
import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.constants.Color;
import ru.vniizht.asuter.autotest.pages.transport.cars.PageCarsList;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssValue;
import static ru.vniizht.asuter.autotest.Messages.*;

public class CarValidateRedBackgroundTest extends BaseTest {

    private static final Condition redBackground = cssValue("background-color", Color.PINK.rgbaValue);
    private static final Condition grayBackground = cssValue("background-color", Color.GRAY_BG_IN_AUTO_FIELDS.rgbaValue);
    private static final Condition messageFieldIsRequired = attribute("title", FieldIsRequired);

    @QaseId(312)
    @Test
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поле ввода Наименование")
    public void testNameInputInvalidCase() {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .check(p -> {
                    p.nameInput.shouldHave(redBackground);
                    p.nameInput.shouldHave(messageFieldIsRequired);
                })
                .inputName("Вагон с длинным наименованием из пятидесяти1 символ")
                .pressTab()
                .check(p -> {
                    p.nameInput.shouldHave(redBackground);
                    p.nameInput.shouldHave(attribute("title", numberOfCharactersBetween(1, 50)));
                });
    }

    @QaseId(313)
    @ParameterizedTest
    @ValueSource(strings = {"0", "0,999", "10000,001"})
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поля ввода Масса тары и Масса нетто")
    public void testContainerMassNetMassInputInvalidCases(String v) {
        final Condition messageExpectedValue = attribute("title", numberOutOfRangeV2(1, 10_000, 3));
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .check(p -> {
                    p.containerMassInput.shouldHave(redBackground);
                    p.containerMassInput.shouldHave(messageFieldIsRequired);
                    p.netMassInput.shouldHave(redBackground);
                    p.netMassInput.shouldHave(messageFieldIsRequired);
                })
                .inputContainerMass(v)
                .pressTab()
                .check(p -> {
                    p.containerMassInput.shouldHave(redBackground);
                    p.containerMassInput.shouldHave(messageExpectedValue);
                })
                .inputNetMass(v)
                .pressTab()
                .check(p -> {
                    p.netMassInput.shouldHave(redBackground);
                    p.netMassInput.shouldHave(messageExpectedValue);
                });
    }

    @QaseId(314)
    @ParameterizedTest
    @ValueSource(strings = {"0", "0,999", "1000,001"})
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поле ввода Длина по осям сцепления")
    public void testLengthInputInvalidCases(String v) {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .check(p -> {
                    p.lengthInput.shouldHave(redBackground);
                    p.lengthInput.shouldHave(messageFieldIsRequired);
                })
                .inputLength(v)
                .pressTab()
                .check(p -> {
                    p.lengthInput.shouldHave(redBackground);
                    p.lengthInput.shouldHave(attribute("title", numberOutOfRangeV2(1, 1_000, 3)));
                });
    }

    @QaseId(315)
    @ParameterizedTest
    @ValueSource(strings = {"0", "0,097", "98,001"})
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поля коэффициентов А")
    public void testCoefficientsAInvalidCases(String v) {
        final Condition expectedMessage = attribute("title", numberOutOfRangeV2(0.098, 98, 3));
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .inputComponentRailA(v)
                .pressTab()
                .check(p -> {
                    p.componentRailAInput.shouldHave(redBackground);
                    p.componentRailAInput.shouldHave(expectedMessage);
                })
                .inputContinuousRailA(v)
                .pressTab()
                .check(p -> {
                    p.continuousRailAInput.shouldHave(redBackground);
                    p.continuousRailAInput.shouldHave(expectedMessage);
                });
    }

    @QaseId(316)
    @ParameterizedTest
    @ValueSource(strings = {"0", "0,097", "980,001"})
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поля коэффициентов B")
    public void testCoefficientsBInvalidCases(String v) {
        final Condition expectedMessage = attribute("title", numberOutOfRangeV2(0.098, 980, 3));
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .inputComponentRailB(v)
                .pressTab()
                .check(p -> {
                    p.componentRailBInput.shouldHave(redBackground);
                    p.componentRailBInput.shouldHave(expectedMessage);
                })
                .inputContinuousRailB(v)
                .pressTab()
                .check(p -> {
                    p.continuousRailBInput.shouldHave(redBackground);
                    p.continuousRailBInput.shouldHave(expectedMessage);
                });
    }

    @QaseId(317)
    @ParameterizedTest
    @ValueSource(strings = {"0", "0,0097", "9,8001"})
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поля коэффициентов C")
    public void testCoefficientsCInvalidCases(String v) {
        final Condition expectedMessage = attribute("title", numberOutOfRangeV2(0.0098, 9.8, 4));
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .inputComponentRailC(v)
                .pressTab()
                .check(p -> {
                    p.componentRailCInput.shouldHave(redBackground);
                    p.componentRailCInput.shouldHave(expectedMessage);
                })
                .inputContinuousRailC(v)
                .pressTab()
                .check(p -> {
                    p.continuousRailCInput.shouldHave(redBackground);
                    p.continuousRailCInput.shouldHave(expectedMessage);
                });
    }

    @QaseId(318)
    @ParameterizedTest
    @ValueSource(strings = {"0", "0,0000097", "9,8001"})
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в поля коэффициентов D")
    public void testCoefficientsDInvalidCases(String v) {
        final Condition expectedMessage = attribute("title", numberOutOfRangeV2(0.0000098, 9.8, 7));
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .inputComponentRailD(v)
                .pressTab()
                .check(p -> {
                    p.componentRailDInput.shouldHave(redBackground);
                    p.componentRailDInput.shouldHave(expectedMessage);
                })
                .inputContinuousRailD(v)
                .pressTab()
                .check(p -> {
                    p.continuousRailDInput.shouldHave(redBackground);
                    p.continuousRailDInput.shouldHave(expectedMessage);
                });
    }

    @QaseId(319)
    @ParameterizedTest
    @ValueSource(strings = {"0", "0,999", "10000,001"})
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в заблокированом поле Масса брутто")
    public void testFullMassInvalidCases(String v) {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .check(p -> {
                    p.fullMassInput.shouldHave(grayBackground);
                    p.fullMassInput.shouldHave(attribute("title", FieldIsFilledInAutomatically));
                })
                .inputContainerMass(v)
                .pressTab()
                .check(p -> {
                    p.fullMassInput.shouldHave(redBackground);
                    String expectedTitle = FieldIsFilledInAutomatically + "\n" + numberOutOfRangeV2(1, 10_000, 3);
                    p.fullMassInput.shouldHave(attribute("title", expectedTitle));
                });
    }

    @QaseId(320)
    @ParameterizedTest
    @ValueSource(strings = {"0", "3,997", "400,002"})
    @DisplayName("Красный фон и правильные подсказки при вводе невалидных значений в заблокированном поле Масса на ось")
    public void testMassPerAxleInvalidCases(String v) {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .check(p -> {
                    p.fullMassInput.shouldHave(grayBackground);
                    p.fullMassInput.shouldHave(attribute("title", FieldIsFilledInAutomatically));
                })
                .inputContainerMass(v)
                .pressTab()
                .check(p -> {
                    p.massPerAxleInput.shouldHave(redBackground);
                    String expectedTitle = FieldIsFilledInAutomatically + "\n" + numberOutOfRangeV2(1, 100, 3);
                    p.massPerAxleInput.shouldHave(attribute("title", expectedTitle));
                });
    }

    @QaseId(346)
    @Test
    @DisplayName("Правильные подсказки по умолчанию в заблокированных полях")
    public void testTitlesInDisabledFields() {
        loginIfNeeded();
        open(PageCarsList.class).clickCreate()
                .check(p -> {
                    p.fullMassInput.shouldHave(attribute("title", FieldIsFilledInAutomatically));
                    p.massPerAxleInput.shouldHave(attribute("title", FieldIsFilledInAutomatically));
                });
    }
}
