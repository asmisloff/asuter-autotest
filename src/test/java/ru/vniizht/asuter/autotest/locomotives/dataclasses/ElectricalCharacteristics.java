package ru.vniizht.asuter.autotest.locomotives.dataclasses;

/**
 * Характеристики позиции тягового режима.
 * @param speed скорость движения
 * @param force сила тяги касательная
 * @param motorAmperage ток тягового электродвигателя
 * @param activeCurrentAmperage ток полный потребляемый на тягу
 */
public record ElectricalCharacteristics(
        String speed,
        String force,
        String motorAmperage,
        String activeCurrentAmperage
) {
}
