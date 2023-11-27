package ru.vniizht.asuter.autotest.constants;

public enum WireMaterial {
    NONE(""),
    COPPER("медный"),
    ALUMINIUM("алюминиевый"),
    STEEL("стальной"),
    STEEL_COPPER("сталемедный"),
    STEEL_ALUMINIUM("сталеалюминиевый");

    public final String displayedText;

    WireMaterial(String value) {
        this.displayedText = value;
    }
}
