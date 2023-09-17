package me.enokitoraisu.features.gui.clickgui.utils;

public class MouseUtil {
    public static boolean bounding(float mouseX, float mouseY, float x, float y, float width, float height) {
        return mouseX > x && mouseX <= x + width && mouseY > y && mouseY <= y + height;
    }

    public static boolean bounding(double mouseX, double mouseY, float x, float y, float width, float height) {
        return mouseX > x && mouseX <= x + width && mouseY > y && mouseY <= y + height;
    }
}
