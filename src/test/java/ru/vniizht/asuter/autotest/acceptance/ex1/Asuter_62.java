package ru.vniizht.asuter.autotest.acceptance.ex1;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vniizht.asuter.autotest.Alert;
import ru.vniizht.asuter.autotest.AllureSelenideTest;
import ru.vniizht.asuter.autotest.CommonOps;
import ru.vniizht.asuter.autotest.CustomConditions;
import ru.vniizht.asuter.autotest.wire.and.rail.WireAndRailController;
import ru.vniizht.asuter.autotest.wire.and.rail.WireAndRailRow;

import java.util.List;

import static com.codeborne.selenide.Condition.exist;
import static ru.vniizht.asuter.autotest.CustomConditions.invalidInput;
import static ru.vniizht.asuter.autotest.CustomConditions.validInput;
import static ru.vniizht.asuter.autotest.Messages.FieldIsRequired;

public class Asuter_62 extends AllureSelenideTest {

    private final Logger logger = LoggerFactory.getLogger(Asuter_62.class);

    private static final WireAndRailController c = new WireAndRailController();
    private final List<String> testWireNames = List.of(
        "А-185 ПМИ-23_автотест", "М-95 ПМИ-23_автотест", "МФ-100 ПМИ-23_автотест", "Р65 ПМИ-23_автотест"
    );

    @BeforeAll
    public static void init() {
        c.navigate();
    }

    @Test
    @DisplayName("Создание и удаление четырех новых проводов")
    public void test() {
        c.unlockInterface();
        addNewRows();
        fillNames();
        fillDcResistances();
        fillAcResistances();
        fillRadia();
        fillLimitAmp();
        fillLimitTemp();
        fillThermalCapacities();
        Selenide.refresh(); // иначе кнопка "сохранить" будет перекрывать кнопки удаления
        c.unlockInterface();
        deleteCreatedWires();
    }

    private void addNewRows() {
        WireAndRailRow[] rows = new WireAndRailRow[4];
        for (int i = 0; i < 4; i++) {
            rows[i] = c.insertRow(-1);
        }
        for (int i = 0; i < 4; i++) {
            var elements = rows[i].getElements();
            for (int j = 0; j < 7; j++) {
                elements.get(j).shouldBe(invalidInput("", FieldIsRequired));
            }
        }
    }

    private void fillNames() {
        for (int i = -4; i < 0; ++i) {
            SelenideElement input = c.getNameInput(i);
            String name = testWireNames.get(i + 4);
            CommonOps.setValueAndPressTab(input, name);
            input.shouldBe(validInput(name));
        }
        c.getLimitAmpInput(-1).shouldBe(validInput(""));
        c.getLimitTempInput(-1).shouldBe(validInput(""));
        c.pushSaveButton();
        Alert.find()
            .shouldBeVisible()
            .shouldHaveText("Не все данные введены корректно.")
            .closeAndCheckIfClosed();
    }

    private void fillDcResistances() {
        var values = List.of(0.16, 0.2, 0.175, 0.025);
        for (int i = -4; i < 0; i++) {
            var input = c.getDcResistanceInput(i);
            var r = values.get(i + 4);
            CommonOps.setValueAndPressTab(input, CommonOps.tr(r, 3));
            input.shouldBe(validInput(r.toString()));
        }
        c.pushSaveButton();
        Alert.find()
            .shouldBeVisible()
            .shouldHaveText("Не все данные введены корректно.")
            .closeAndCheckIfClosed();
    }

    private void fillAcResistances() {
        var values = List.of(0.16, 0.2, 0.175, 0.02);
        for (int i = -4; i < 0; i++) {
            var input = c.getAcResistanceInput(i);
            var r = values.get(i + 4);
            CommonOps.setValueAndPressTab(input, CommonOps.tr(r, 3));
            input.shouldBe(validInput(r.toString()));
        }
        c.pushSaveButton();
        Alert.find()
            .shouldBeVisible()
            .shouldHaveText("Не все данные введены корректно.")
            .closeAndCheckIfClosed();
    }

    private void fillRadia() {
        var values = List.of(0.9, 0.65, 0.6, 11.5);
        for (int i = -4; i < 0; i++) {
            var input = c.getRadiusInput(i);
            var r = values.get(i + 4);
            CommonOps.setValueAndPressTab(input, CommonOps.tr(r, 3));
            input.shouldBe(validInput(r.toString()));
        }
        c.pushSaveButton();
        Alert.find()
            .shouldBeVisible()
            .shouldHaveText("Не все данные введены корректно.")
            .closeAndCheckIfClosed();
    }

    private void fillLimitAmp() {
        var values = List.of(600, 600, 600);
        for (int i = -4; i < -1; i++) {
            var input = c.getLimitAmpInput(i);
            var r = values.get(i + 4);
            CommonOps.setValueAndPressTab(input, CommonOps.tr(r, 3));
            input.shouldBe(validInput(r.toString()));
        }
        c.pushSaveButton();
        Alert.find()
            .shouldBeVisible()
            .shouldHaveText("Не все данные введены корректно.")
            .closeAndCheckIfClosed();
    }

    private void fillLimitTemp() {
        var values = List.of(90, 100, 95);
        for (int i = -4; i < -1; i++) {
            var input = c.getLimitTempInput(i);
            var r = values.get(i + 4);
            CommonOps.setValueAndPressTab(input, CommonOps.tr(r, 3));
            input.shouldBe(validInput(r.toString()));
        }
        c.pushSaveButton();
        Alert.find()
            .shouldBeVisible()
            .shouldHaveText("Не все данные введены корректно.")
            .closeAndCheckIfClosed();
    }

    private void fillThermalCapacities() {
        var values = List.of(450, 330, 350, 1000);
        for (int i = -4; i < 0; i++) {
            var input = c.getThermalCapacityInput(i);
            var r = values.get(i + 4);
            CommonOps.setValueAndPressTab(input, CommonOps.tr(r, 3));
            input.shouldBe(validInput(r.toString()));
        }
        c.pushSaveButton();
        CustomConditions.notificationShouldAppear("Сохранено.");
        for (String name : testWireNames) {
            c.findNameInputByValue(name).should(exist);
        }
    }

    private void deleteCreatedWires() {
        for (String name : testWireNames) {
            c.findRowByWireName(name).delButton().click();
        }
        c.pushSaveButton();
        CustomConditions.notificationShouldAppear("Сохранено.");
        for (String name : testWireNames) {
            c.findNameInputByValue(name).shouldNot(exist);
            logger.info("{} отсутствует в таблице", name);
        }
    }
}
