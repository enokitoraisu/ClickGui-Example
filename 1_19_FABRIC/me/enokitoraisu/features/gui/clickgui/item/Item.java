package me.enokitoraisu.features.gui.clickgui.item;

import me.enokitoraisu.features.gui.clickgui.utils.MouseUtil;
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

    public boolean bounding(int mouseX, int mouseY) {
        return MouseUtil.bounding(mouseX, mouseY, this.x, this.y + this.offset, this.width, this.height);;
    }

    public boolean bounding(int mouseX, int mouseY, int x, int y, int width, int height) {
        return MouseUtil.bounding(mouseX, mouseY, x, y, width, height);
    }

    public T getObject() {
        return object;
    }
}
