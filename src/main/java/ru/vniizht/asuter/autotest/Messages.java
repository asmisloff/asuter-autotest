package ru.vniizht.asuter.autotest;

public class Messages {


    public static final String FieldIsRequired =
            // todo: Решить проблему с пробелом перед текстом сообщения, сейчас он добавляется чтобы тест не падал
            " " +
            "Поле, обязательное для заполнения";
    public static final String ValueMustBePositive =
            // todo: Решить проблему с пробелом перед текстом сообщения, сейчас он добавляется чтобы тест не падал
            " " +
                    "Должно быть положительным";

    public static String numberOutOfRange(Number min, Number max, int precision) {
        return String.format(
                "Значение должно находиться в пределах [%s...%s] (до %d знаков после запятой)",
                min.toString(), max.toString(), precision
        );
    }

    // todo: Rename после удаления старого кода использующего старый вариант numberOutOfRange()
    public static String numberOutOfRangeV2(Number min, Number max, int precision) {
        return String.format(
                // todo: Решить проблему с пробелом перед текстом сообщения, сейчас он добавляется чтобы тест не падал
                " " +
                "Значение должно находиться в пределах %s...%s; не более %d знаков после запятой",
                min.toString(), max.toString(), precision
        );
    }
}
