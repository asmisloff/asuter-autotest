package ru.vniizht.asuter.autotest.locomotives.dataclasses;

/**
 * Тестовые данные тепловых характеристик двигателя.
 * @param overheatTolerance допустимое превышение температуры обмоток тягового электродвигателя
 * @param thermalTimeConstant тепловая постоянная времени
 * @param amperages массив значений тока тягового электродвигателя
 * @param overheats массива значений превышений температуры обмоток
 */
public record ThermalCharacteristics(
        String overheatTolerance,
        String thermalTimeConstant,
        String[] amperages,
        String[] overheats
) {
}
