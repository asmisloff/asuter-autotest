package ru.vniizht.asuter.autotest.utils;

public class NameResolver {

    private NameResolver() {}

    public static String resolveNameForNetworkDC(
            int feederCount,
            String feederName,
            int contactWireCount,
            String contactWireName,
            int powerWireCount,
            String powerWireName,
            int railCount,
            String railName
    ) {
        feederName = (feederCount <= 1 ? "" : feederCount) + feederName;
        contactWireName = contactWireCount <= 0 ? "" : ("+" + (contactWireCount == 1 ? "" : contactWireCount)) + contactWireName;
        powerWireName = powerWireCount <= 0 ? "" : ("+" + (powerWireCount == 1 ? "" : powerWireCount)) + powerWireName;
        railName = railCount <= 0 ? "" : ("+" + railCount * 2 + railName);
        return (feederName + contactWireName + powerWireName + railName)
                .replaceFirst("^\\++", "");
    }
}
