package me.enokitoraisu.features.gui.clickgui.item;

import me.enokitoraisu.features.gui.clickgui.utils.MouseUtil;
import net.minecraft.client.Minecraft;

public class Item<T> {
    private final T object;
    public final int x, y, width, height;
    public int offset;
    public Minecraft mc = Minecraft.getMinecraft();

    public Item(T object, int x, int y, int width, int height) {
        this.object = object;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int drawScreen(int mouseX, int mouseY, float partialTicks, int offset) { return 0; }
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) { }
    public void mouseReleased(int mouseX, int mouseY, int state) { }
    public void keyTyped(char typedChar, int keyCode) { }

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
