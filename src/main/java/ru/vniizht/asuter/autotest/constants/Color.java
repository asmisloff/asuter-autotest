package ru.vniizht.asuter.autotest.constants;

public class Color {

    public final String name;
    public final String rgbaValue;

    private Color(String name, String rgbaValue) {
        this.name = name;
        this.rgbaValue = rgbaValue;
    }

    public final static Color WHITE = new Color("white", "rgba(255, 255, 255, 1)");
    public final static Color PINK = new Color("pink", "rgba(255, 192, 203, 1)");


    public final static Color VALID_INPUT = WHITE;
    public final static Color INVALID_INPUT = PINK;


}
