package ru.vniizht.asuter.autotest.equipment.electrical.wiresandrails;

import java.util.*;

import com.codeborne.selenide.Selenide;
import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.Alert;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.CustomConditions;
import ru.vniizht.asuter.autotest.Messages;
import ru.vniizht.asuter.autotest.pages.equipment.electrical.EditableRowOfPageWiresAndRails;
import ru.vniizht.asuter.autotest.pages.equipment.electrical.PageWiresAndRails;

import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.vniizht.asuter.autotest.CustomConditions.*;
import static ru.vniizht.asuter.autotest.pages.equipment.electrical.EditableRowOfPageWiresAndRails.*;

@DisplayName("Добавление новых проводов и рельс (кейс ASUTER-62) ")
public class WireAndRailCreateTest extends BaseTest {

    private record Wire(
            String name, String dcResistance, String acResistance, String radius,
            String limitAmp, String limitTemp, String thermalCapacity
    ) {}

    private final List<Wire> wires = List.of(
            new Wire("А-185 ПМИ-23_автотест", "0.16", "0.16", "0.9", "600", "90", "450"),
            new Wire("М-95 ПМИ-23_автотест", "0.2", "0.2", "0.65", "600", "100", "330"),
            new Wire("МФ-100 ПМИ-23_автотест", "0.175", "0.175", "0.6", "600", "95", "350"),
            new Wire("Р65 ПМИ-23_автотест", "0.02", "0.02", "11.5", "", "", "1000")
    );
    private final Map<String, EditableRowOfPageWiresAndRails> addedRows = new HashMap<>();

    @Test
    @QaseId(62)
    @DisplayName("Добавление новых проводов и рельс (ПМИ-23)")
    public void createThenDeleteWires() {
        loginIfNeeded();
        PageWiresAndRails page = open(PageWiresAndRails.class);
        var lastRow = page.clickEdit().getLastEditableRow();

        for (Wire wire : wires) {
            var row = lastRow.clickAddAndReturnCreatedRow();
            addedRows.put(wire.name, row);
            row.inputName.shouldHave(invalidInput("", Messages.FieldIsRequired));
            lastRow = row;
        }
        wires.forEach(w -> addedRows.get(w.name)
                .inputName(w.name)
                .pressTab()
                .inputName.shouldHave(validInput(w.name)));
        lastRow.inputLimitAmperage.shouldHave(validInput(""));
        lastRow.inputLimitTemperature.shouldHave(validInput(""));
        page.clickSave();
        Alert.shouldDisplayNotAllEnteredCorrectly();

        wires.forEach(w -> addedRows.get(w.name)
                .inputDcResistance(w.dcResistance)
                .pressTab()
                .inputDcResistance.shouldHave(validInput(w.dcResistance)));
        page.clickSave();
        Alert.shouldDisplayNotAllEnteredCorrectly();

        wires.forEach(w -> addedRows.get(w.name)
                .inputAcResistance(w.acResistance)
                .pressTab()
                .inputAcResistance.shouldHave(validInput(w.acResistance)));
        page.clickSave();
        Alert.shouldDisplayNotAllEnteredCorrectly();

        wires.forEach(w -> addedRows.get(w.name)
                .inputRadius(w.radius)
                .pressTab()
                .inputRadius.shouldHave(validInput(w.radius)));
        page.clickSave();
        Alert.shouldDisplayNotAllEnteredCorrectly();

        wires.forEach(w -> addedRows.get(w.name)
                .inputLimitAmperage(w.limitAmp)
                .pressTab()
                .inputLimitAmperage.shouldHave(validInput(w.limitAmp)));
        page.clickSave();
        Alert.shouldDisplayNotAllEnteredCorrectly();

        wires.forEach(w -> addedRows.get(w.name)
                .inputLimitTemperature(w.limitTemp)
                .pressTab()
                .inputLimitTemperature.shouldHave(validInput(w.limitTemp)));
        page.clickSave();
        Alert.shouldDisplayNotAllEnteredCorrectly();

        wires.forEach(w -> addedRows.get(w.name)
                .inputThermalCapacity(w.thermalCapacity)
                .pressTab()
                .inputThermalCapacity.shouldHave(validInput(w.thermalCapacity)));
        page.clickSave();
        CustomConditions.notificationShouldAppear("Сохранено.");
        wires.forEach(w -> findInputNameByValue(w.name).should(exist));

        Selenide.refresh(); // теперь добавленные провода наверху, иначе кнопка "сохранить" перекрывает кнопки удаления
        page.clickEdit();
        wires.forEach(w -> {
            // При удалении проводов структура страницы меняется поэтому каждый раз ищем заново
            var row = page.findEditableRowByWireName(w.name);
            assertEquals(row.inputName.getValue(), w.name);
            row.clickRemove();
        });
        page.clickSave();
        CustomConditions.notificationShouldAppear("Сохранено.");
        wires.forEach(wire -> findInputNameByValue(wire.name).shouldNot(exist));
    }

}
