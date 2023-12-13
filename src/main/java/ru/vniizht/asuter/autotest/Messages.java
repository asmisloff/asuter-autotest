package ru.vniizht.asuter.autotest;

public class Messages {


    public static final String FieldIsRequired = "Поле, обязательное для заполнения";

    public final static String MUST_BE_FROM_1_TO_10000 = numberOutOfRangeV2(1, 10_000, 3);
    public final static String MUST_BE_FROM_1_TO_100 = numberOutOfRangeV2(1, 100, 3);

    public static String numberOutOfRange(Number min, Number max, int precision) {
        return String.format(
                "Значение должно находиться в пределах [%s...%s] (до %d знаков после запятой)",
                min.toString(), max.toString(), precision
        );
    }

    // todo: Rename после удаления старого кода использующего старый вариант numberOutOfRange()
    public static String numberOutOfRangeV2(Number min, Number max, int precision) {
        return String.format(
                "Значение должно находиться в пределах %s...%s; не более %d знаков после запятой",
                min.toString(), max.toString(), precision
        );
    }
}
