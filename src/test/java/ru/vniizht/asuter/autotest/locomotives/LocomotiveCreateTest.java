package ru.vniizht.asuter.autotest.locomotives;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vniizht.asuter.autotest.BaseTest;
import ru.vniizht.asuter.autotest.locomotives.dataclasses.ElectricalCharacteristics;
import ru.vniizht.asuter.autotest.locomotives.dataclasses.Locomotive;
import ru.vniizht.asuter.autotest.locomotives.dataclasses.ThermalCharacteristics;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns.LocomotiveElectricalCharacteristicRow;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.rowsandcolumns.LocomotiveElectricalPositionRow;
import ru.vniizht.asuter.autotest.pages.transport.locomotives.PageLocomotivesList;

import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static ru.vniizht.asuter.autotest.CustomConditions.invalidInput;
import static ru.vniizht.asuter.autotest.CustomConditions.validInput;

/**
 * Тест для проверки работы Контроллера страницы Локомотивов.
 * Соответствует QaseId(59).
 */
@DisplayName("Локомотивы - Создание")
public class LocomotiveCreateTest extends BaseTest {

    private final static String LOCOMOTIVE_2EC4K_DONCHACK = "2ЭС4К «Дончак» ПМИ-23_autotest";
    private final static String[] coefficientsOfResistance = new String[] {
            "10", "0,01", "0,003",
            "17", "0,05", "0,004",
            "10", "0,01", "0,003",
            "17", "0,05", "0,004"
    };
    private final static ElectricalCharacteristics[] electricalCharacteristicsPos1 = new ElectricalCharacteristics[] {
            new ElectricalCharacteristics("0", "640", "800", "800"),
            new ElectricalCharacteristics("5", "580", "750", "750"),
            new ElectricalCharacteristics("10", "550", "700", "1400"),
            new ElectricalCharacteristics("20", "520", "660", "1320"),
            new ElectricalCharacteristics("30", "500", "640", "2560"),
            new ElectricalCharacteristics("40", "480", "620", "2480"),
            new ElectricalCharacteristics("50", "460", "610", "2440"),
            new ElectricalCharacteristics("60", "440", "670", "2680"),
            new ElectricalCharacteristics("70", "420", "740", "2960")
    };
    private final static ElectricalCharacteristics[] electricalCharacteristicsPos2 = new ElectricalCharacteristics[] {
            new ElectricalCharacteristics("66", "450", "720", "2880"),
            new ElectricalCharacteristics("70", "390", "670", "2680"),
            new ElectricalCharacteristics("74", "340", "620", "2480"),
            new ElectricalCharacteristics("78", "300", "570", "2280"),
            new ElectricalCharacteristics("82", "260", "530", "2120"),
            new ElectricalCharacteristics("86", "230", "500", "2000"),
            new ElectricalCharacteristics("90", "210", "470", "1880"),
            new ElectricalCharacteristics("94", "190", "450", "1800"),
            new ElectricalCharacteristics("98", "175", "430", "1720"),
            new ElectricalCharacteristics("102", "160", "410", "1640"),
            new ElectricalCharacteristics("106", "150", "400", "1600"),
            new ElectricalCharacteristics("110", "140", "380", "1520"),
            new ElectricalCharacteristics("114", "130", "370", "1480"),
            new ElectricalCharacteristics("118", "120", "360", "1440")
    };
    private final static ThermalCharacteristics thermalCharacteristics = new ThermalCharacteristics(
            "160",
            "25",
            new String[] {"100", "200", "300", "400", "500", "600", "700", "800", "900", "1000"},
            new String[] {"10", "25", "40", "50", "65", "80", "100", "125", "165", "225"}
    );
    private final static String POSITION_NAME_1 = "-";
    private final static String POSITION_NAME_2 = "ПП – ОП4";
    private final static Locomotive locomotive = new Locomotive(
            LOCOMOTIVE_2EC4K_DONCHACK,
            "40",
            "200",
            "120",
            coefficientsOfResistance,
            Map.of(
                    POSITION_NAME_1, electricalCharacteristicsPos1,
                    POSITION_NAME_2, electricalCharacteristicsPos2
            ),
            thermalCharacteristics
    );

    @Test
    @QaseId(59)
    @DisplayName("Создание локомотива (ПМИ-23)")
    public void createLocomotive() {
        loginIfNeeded();
        var page = open(PageLocomotivesList.class).clickCreate()

                // Основные параметры
                .inputName(locomotive.name())
                .pressTab()
                .check(p -> p.nameInput.shouldHave(validInput(locomotive.name())))
                .inputLength(locomotive.length())
                .pressTab()
                .check(p -> p.lengthInput.shouldHave(validInput(locomotive.length())))
                .inputWeight(locomotive.weight())
                .pressTab()
                .check(p -> p.weightInput.shouldHave(validInput(locomotive.weight())))
                .inputMaxSpeed(locomotive.maxSpeed())
                .pressTab()
                .check(p -> p.maxSpeedInput.shouldHave(validInput(locomotive.maxSpeed())))

                // Коэффициенты основного удельного сопротивления движению
                .clickCoefficientsOfResistanceHeader()
                .inputCoefficientsOfResistance(locomotive.coefficientsOfResistance())
                .pressTab()
                .check(p -> {
                    p.coefficientComponentRailWoB.shouldHave(validInput(locomotive.coefficientsOfResistance()[0]));
                    p.coefficientComponentRailWoC.shouldHave(validInput(locomotive.coefficientsOfResistance()[1]));
                    p.coefficientComponentRailWoD.shouldHave(validInput(locomotive.coefficientsOfResistance()[2]));
                    p.coefficientComponentRailWxB.shouldHave(validInput(locomotive.coefficientsOfResistance()[3]));
                    p.coefficientComponentRailWxC.shouldHave(validInput(locomotive.coefficientsOfResistance()[4]));
                    p.coefficientComponentRailWxD.shouldHave(validInput(locomotive.coefficientsOfResistance()[5]));
                    p.coefficientContinuousRailWoB.shouldHave(validInput(locomotive.coefficientsOfResistance()[6]));
                    p.coefficientContinuousRailWoC.shouldHave(validInput(locomotive.coefficientsOfResistance()[7]));
                    p.coefficientContinuousRailWoD.shouldHave(validInput(locomotive.coefficientsOfResistance()[8]));
                    p.coefficientContinuousRailWxB.shouldHave(validInput(locomotive.coefficientsOfResistance()[9]));
                    p.coefficientContinuousRailWxC.shouldHave(validInput(locomotive.coefficientsOfResistance()[10]));
                    p.coefficientContinuousRailWxD.shouldHave(validInput(locomotive.coefficientsOfResistance()[11]));
                });

        // Характеристики тягового режима - позиция 1
        page.clickTractionCharacteristicsHeader()
                .getElectricalPositionRow(1)
                .inputName(POSITION_NAME_1)
                .pressTab()
                .check(p -> p.nameInput.shouldHave(validInput(POSITION_NAME_1)))
                .getElectricalCharacteristicRow(1)
                .also(p -> {
                    int numRows = LocomotiveElectricalCharacteristicRow.totalCount();
                    int numRowsToAdd = electricalCharacteristicsPos1.length - numRows;
                    for (int i = 0; i < numRowsToAdd; i++) {
                        p.clickAdd();
                    }
                })
                .also(p -> {
                    var electricalCharacteristics1 = locomotive.tractionCharacteristics().get(POSITION_NAME_1);
                    var row = p;
                    int n = LocomotiveElectricalCharacteristicRow.totalCount();
                    for (int i = 0; i < n; i++) {
                        row.speedInput.shouldHave(invalidInput(
                                "0",
                                "У точек на одной кривой характеристики не должно быть одинаковых скоростей"
                        ));
                        row.efficiencyOutputField.shouldHave(value("0,000"));
                        row.inputSpeed(electricalCharacteristics1[i].speed());
                        row.inputForce(electricalCharacteristics1[i].force());
                        row.inputMotorAmperage(electricalCharacteristics1[i].motorAmperage());
                        row.inputActiveCurrentAmperage(electricalCharacteristics1[i].activeCurrentAmperage());
                        row = row.nextRow();
                    }
                    row.pressTab();
                    row = p;
                    for (int i = 0; i < n; i++) {
                        row.speedInput.shouldHave(validInput(electricalCharacteristics1[i].speed()));
                        row.forceInput.shouldHave(validInput(electricalCharacteristics1[i].force()));
                        row.motorAmperageInput.shouldHave(validInput(electricalCharacteristics1[i].motorAmperage()));
                        row.activeCurrentAmperageInput.shouldHave(validInput(electricalCharacteristics1[i].activeCurrentAmperage()));
                        if (i != 0) {
                            row.efficiencyOutputField.shouldHave(not(value("0,000")));
                        }
                        row = row.nextRow();
                    }
                    // Построение графика не тестируется
                });

        // Характеристики тягового режима - позиция 2
        int numPositions = LocomotiveElectricalPositionRow.totalCount();
        if (numPositions < 2) {
            page.getLastElectricalPositionRow().clickAdd();
        }
        var electricalCharacteristics2 = locomotive.tractionCharacteristics().get(POSITION_NAME_2);
        page.getElectricalPositionRow(2)
                .inputName(POSITION_NAME_2)
                .pressTab()
                .check(p -> p.nameInput.shouldHave(validInput(POSITION_NAME_2)))
                .getElectricalCharacteristicRow(1)
                .also(p -> {
                    int numRows = LocomotiveElectricalCharacteristicRow.totalCount();
                    int numRowsToAdd = electricalCharacteristics2.length - numRows;
                    for (int i = 0; i < numRowsToAdd; i++) {
                        p.clickAdd();
                    }
                }).also(p -> {
                    int numRows = LocomotiveElectricalCharacteristicRow.totalCount();
                    var row = p;
                    for (int i = 0; i < numRows; i++) {
                        row.efficiencyOutputField.shouldHave(value("0,000"));
                        row.inputSpeed(electricalCharacteristics2[i].speed());
                        row.inputForce(electricalCharacteristics2[i].force());
                        row.inputMotorAmperage(electricalCharacteristics2[i].motorAmperage());
                        row.inputActiveCurrentAmperage(electricalCharacteristics2[i].activeCurrentAmperage());
                        row = row.nextRow();
                    }
                    row.pressTab();
                    row = p;
                    for (int i = 0; i < numRows; i++) {
                        row.speedInput.shouldHave(validInput(electricalCharacteristics2[i].speed()));
                        row.forceInput.shouldHave(validInput(electricalCharacteristics2[i].force()));
                        row.motorAmperageInput.shouldHave(validInput(electricalCharacteristics2[i].motorAmperage()));
                        row.activeCurrentAmperageInput.shouldHave(validInput(electricalCharacteristics2[i].activeCurrentAmperage()));
                        if (i != 0) {
                            row.efficiencyOutputField.shouldHave(not(value("0,000")));
                        }
                        row = row.nextRow();
                    }
                    // Построение графика не тестируется
                });

        // Тепловые характеристики двигателя
        int numColumns = locomotive.thermalCharacteristics().amperages().length;
        page.clickEngineThermalCharacteristicsHeader()
                .inputOverheatToleranceInput(locomotive.thermalCharacteristics().overheatTolerance())
                .check(p -> p.overheatToleranceInput.shouldHave(validInput(locomotive.thermalCharacteristics().overheatTolerance())))
                .inputThermalTimeConstantInput(locomotive.thermalCharacteristics().thermalTimeConstant())
                .check(p -> p.thermalTimeConstantInput.shouldHave(validInput(locomotive.thermalCharacteristics().thermalTimeConstant())))
                .also(p -> {
                    for (int i = 1; i <= numColumns; i++) {
                        p.clickToAddThermalCharacteristicsColumn();
                    }
                })
                .also(p -> {
                    for (int i = 0; i < numColumns; i++) {
                        String amperage = locomotive.thermalCharacteristics().amperages()[i];
                        String overheat = locomotive.thermalCharacteristics().overheats()[i];
                        p.getThermalCharacteristicsColumn(i + 1)
                                .inputMotorAmperage(amperage)
                                .pressTab()
                                .check(c -> c.motorAmperage.shouldHave(validInput(amperage)))
                                .inputBalancingOverheat(overheat)
                                .pressTab()
                                .check(c -> c.balancingOverheat.shouldHave(validInput(overheat)));
                    }
                });
        page.clickSave();

        // Проверка результата сохранения
        var listPage = open(PageLocomotivesList.class);
        var found = listPage.findLocomotiveRowByName(locomotive.name(), true);
        var locomotivePage = found.openLocomotive();
        locomotivePage.check(p -> {
            // Основные параметры
            p.nameInput.shouldHave(validInput(locomotive.name()));
            p.lengthInput.shouldHave(validInput(locomotive.length()));
            p.weightInput.shouldHave(validInput(locomotive.weight()));
            p.maxSpeedInput.shouldHave(validInput(locomotive.maxSpeed()));
            // Коэффициенты основного удельного сопротивления движению
            p.clickCoefficientsOfResistanceHeader();
            p.coefficientComponentRailWoB.shouldHave(validInput(locomotive.coefficientsOfResistance()[0]));
            p.coefficientComponentRailWoC.shouldHave(validInput(locomotive.coefficientsOfResistance()[1]));
            p.coefficientComponentRailWoD.shouldHave(validInput(locomotive.coefficientsOfResistance()[2]));
            p.coefficientComponentRailWxB.shouldHave(validInput(locomotive.coefficientsOfResistance()[3]));
            p.coefficientComponentRailWxC.shouldHave(validInput(locomotive.coefficientsOfResistance()[4]));
            p.coefficientComponentRailWxD.shouldHave(validInput(locomotive.coefficientsOfResistance()[5]));
            p.coefficientContinuousRailWoB.shouldHave(validInput(locomotive.coefficientsOfResistance()[6]));
            p.coefficientContinuousRailWoC.shouldHave(validInput(locomotive.coefficientsOfResistance()[7]));
            p.coefficientContinuousRailWoD.shouldHave(validInput(locomotive.coefficientsOfResistance()[8]));
            p.coefficientContinuousRailWxB.shouldHave(validInput(locomotive.coefficientsOfResistance()[9]));
            p.coefficientContinuousRailWxC.shouldHave(validInput(locomotive.coefficientsOfResistance()[10]));
            p.coefficientContinuousRailWxD.shouldHave(validInput(locomotive.coefficientsOfResistance()[11]));
            // Характеристики тягового режима - позиция 1
            var tractionCharacteristics1 = locomotive.tractionCharacteristics().get(POSITION_NAME_1);
            p.clickTractionCharacteristicsHeader()
                    .getElectricalPositionRow(1)
                    .check(pos -> {
                        pos.nameInput.shouldHave(validInput(POSITION_NAME_1));
                        var characteristics = pos.getElectricalCharacteristicRow(1);
                        for (ElectricalCharacteristics tc : tractionCharacteristics1) {
                            characteristics.speedInput.shouldHave(validInput(tc.speed()));
                            characteristics.forceInput.shouldHave(validInput(tc.force()));
                            characteristics.motorAmperageInput.shouldHave(validInput(tc.motorAmperage()));
                            characteristics.activeCurrentAmperageInput.shouldHave(validInput(tc.activeCurrentAmperage()));
                            characteristics = characteristics.nextRow();
                        }
                    });
            // Характеристики тягового режима - позиция 2
            var tractionCharacteristics2 = locomotive.tractionCharacteristics().get(POSITION_NAME_2);
            p.getElectricalPositionRow(2)
                    .activatePositionRow()
                    .check(pos -> {
                        pos.nameInput.shouldHave(validInput(POSITION_NAME_2));
                        var characteristics = pos.getElectricalCharacteristicRow(1);
                        for (ElectricalCharacteristics tc : tractionCharacteristics2) {
                            characteristics.speedInput.shouldHave(validInput(tc.speed()));
                            characteristics.forceInput.shouldHave(validInput(tc.force()));
                            characteristics.motorAmperageInput.shouldHave(validInput(tc.motorAmperage()));
                            characteristics.activeCurrentAmperageInput.shouldHave(validInput(tc.activeCurrentAmperage()));
                            characteristics = characteristics.nextRow();
                        }
                    });
            // Тепловые характеристики двигателя
            var thermalCharacteristics = locomotive.thermalCharacteristics();
            p.clickEngineThermalCharacteristicsHeader();
            p.overheatToleranceInput.shouldHave(validInput(thermalCharacteristics.overheatTolerance()));
            p.thermalTimeConstantInput.shouldHave(validInput(thermalCharacteristics.thermalTimeConstant()));
            for (int i = 0; i < locomotive.thermalCharacteristics().amperages().length; i++) {
                var col = p.getThermalCharacteristicsColumn(i + 1);
                col.motorAmperage.shouldHave(validInput(thermalCharacteristics.amperages()[i]));
                col.balancingOverheat.shouldHave(validInput(thermalCharacteristics.overheats()[i]));
            }
        });

        // Удалить созданный локомотив
        open(PageLocomotivesList.class)
                .findLocomotiveRowByName(locomotive.name(), true)
                .delete();
    }

}
