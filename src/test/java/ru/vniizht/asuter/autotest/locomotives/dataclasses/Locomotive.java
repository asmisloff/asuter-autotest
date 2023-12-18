package ru.vniizht.asuter.autotest.locomotives.dataclasses;

import java.util.Map;

/**
 * Тестовые данные локомотива.
 * @param name наименование
 * @param length длина
 * @param weight масса учетная
 * @param maxSpeed конструкционная скорость
 * @param coefficientsOfResistance коэффициенты основного удельного сопротивления движению
 * @param tractionCharacteristics характеристики тягового режима
 * @param thermalCharacteristics тепловые характеристики двигателя
 */
public record Locomotive(
        String name,
        String length,
        String weight,
        String maxSpeed,
        String[] coefficientsOfResistance,
        Map<String, ElectricalCharacteristics[]> tractionCharacteristics,
        ThermalCharacteristics thermalCharacteristics
) {
}
