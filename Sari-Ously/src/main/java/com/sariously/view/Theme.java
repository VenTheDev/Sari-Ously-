package com.sariously.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class Theme {

    private Theme() {}

    public static final Color NAVY = new Color(11, 72, 144);
    public static final Color NAVY_DARK = new Color(7, 52, 112);
    public static final Color BLUE = new Color(20, 92, 204);
    public static final Color AWNING_BLUE = new Color(20, 84, 190);
    public static final Color RED = new Color(225, 58, 48);
    public static final Color YELLOW = new Color(254, 203, 76);
    public static final Color CREAM = new Color(255, 252, 244);
    public static final Color RIGHT_TOP = new Color(255, 246, 214);
    public static final Color RIGHT_BOTTOM = new Color(224, 238, 253);
    public static final Color INK = new Color(33, 37, 41);
    public static final Color MUTED = new Color(120, 126, 140);
    public static final Color LINE = new Color(232, 226, 208);
    public static final Color GREEN = new Color(34, 150, 98);
    public static final Color ORANGE = new Color(240, 130, 40);

    public static final Color[] PALETTE = {
            new Color(225, 58, 48), new Color(245, 176, 30), new Color(34, 160, 110),
            new Color(30, 100, 210), new Color(130, 80, 200), new Color(240, 130, 40),
            new Color(0, 160, 170)
    };

    private static final String FAMILY = pickFamily(
            "Segoe UI", "Poppins", "Helvetica Neue", "Arial", "SansSerif");

    private static String pickFamily(String... wanted) {
        Set<String> have = new HashSet<>(Arrays.asList(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        for (String f : wanted) {
            // only use a font that can really draw the peso sign and the arrow
            if (have.contains(f) && new Font(f, Font.PLAIN, 12).canDisplay('\u20B1')
                    && new Font(f, Font.PLAIN, 12).canDisplay('\u2192')) return f;
        }
        return "SansSerif";
    }

    public static Font font(int style, float size) {
        return new Font(FAMILY, style, 1).deriveFont(style, size);
    }

    public static String peso(double value) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        boolean whole = Math.abs(value - Math.rint(value)) < 0.005;
        nf.setMinimumFractionDigits(whole ? 0 : 2);
        nf.setMaximumFractionDigits(whole ? 0 : 2);
        return (value < 0 ? "-" : "") + "\u20B1" + nf.format(Math.abs(value));
    }

    public static String num(double value) {
        boolean whole = Math.abs(value - Math.rint(value)) < 0.005;
        return whole ? String.valueOf((long) Math.rint(value)) : String.format(Locale.US, "%.2f", value);
    }
}
