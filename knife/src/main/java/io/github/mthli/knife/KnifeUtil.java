package io.github.mthli.knife;

public class KnifeUtil {

    public static String intColorToHex(int intColor) {
        return String.format("#%06X", (0xFFFFFF & intColor));
    }
}
