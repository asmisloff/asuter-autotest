package ru.vniizht.asuter.autotest.constants;

public class Color {

    public final String name;
    public final String rgbaValue;
    public final String hexValue;

    private Color(String name, String rgbaValue, String hexValue) {
        this.name = name;
        this.rgbaValue = rgbaValue;
        this.hexValue = hexValue;
    }

    public final static Color WHITE = new Color("white", "rgba(255, 255, 255, 1)", "#fff");
    public final static Color PINK = new Color("pink", "rgba(255, 192, 203, 1)", "#ffc0cb");

    /** Серый фон полей заполняемых автоматически */
    public final static Color GRAY_BG_IN_AUTO_FIELDS = new Color("gray", "rgba(233, 236, 239, 1)", "#e9ecef");

    public final static Color VALID_INPUT = WHITE;
    public final static Color INVALID_INPUT = PINK;


}
