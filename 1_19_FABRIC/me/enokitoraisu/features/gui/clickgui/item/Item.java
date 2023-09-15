package me.enokitoraisu.features.gui.clickgui.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class Item<T> {
    private final T object;
    public final int x, y, width, height;
    public int offset;
    public MinecraftClient mc = MinecraftClient.getInstance();

    public Item(T object, int x, int y, int width, int height) {
        this.object = object;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int drawScreen(MatrixStack matrices, int mouseX, int mouseY, float partialTicks, int offset) { return 0; }
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) { }
    public void mouseReleased(double mouseX, double mouseY, int state) { }
    public void keyPressed(int keyCode, int scanCode, int modifiers) { }
    public void charTyped(char chr, int modifiers) { }

    public boolean bounding(double mouseX, double mouseY) {
        if (mouseX < this.x) return false;
        if (mouseX > this.x + this.width) return false;
        if (mouseY < this.y + this.offset) return false;
        return mouseY <= this.y + this.offset + this.height;
    }

    public boolean bounding(double mouseX, double mouseY, int x, int y, int width, int height) {
        if (mouseX < x) return false;
        if (mouseX > x + width) return false;
        if (mouseY < y + this.offset) return false;
        return mouseY <= y + this.offset + height;
    }

    public T getObject() {
        return object;
    }
}
