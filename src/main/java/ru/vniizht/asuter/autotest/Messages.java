package ru.vniizht.asuter.autotest;

public class Messages {

    public static final String FieldIsRequired = "Поле, обязательное для заполнения";

    public static String numberOutOfRange(Number min, Number max, int precision) {
        return String.format(
                "Значение должно находиться в пределах [%s...%s] (до %d знаков после запятой)",
                min.toString(), max.toString(), precision
        );
    }
}
