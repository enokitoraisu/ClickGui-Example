package me.enokitoraisu.features.gui.clickgui;

import me.enokitoraisu.features.module.Module;
import net.minecraft.client.Minecraft;

public class ModuleButton {
    private final Module module;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Minecraft mc;
    private int offset;

    public ModuleButton(Module module, int x, int y, int width, int height, Minecraft mc) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mc = mc;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
        this.offset = offset;
        RenderUtil.rect(x, y + offset, width, height, 0x80000000);
        mc.fontRenderer.drawStringWithShadow(module.getName(), x + 3, y + offset + (height / 2f - mc.fontRenderer.FONT_HEIGHT / 2f), this.module.istoggle ? 0xFF2B71F3 : -1);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (bounding(mouseX, mouseY) && mouseButton == 0) this.module.toggle();
    }

    public void keyTyped(char typedChar, int keyCode) {
    }

    public boolean bounding(int mouseX, int mouseY) {
        if (mouseX < this.x) return false;
        if (mouseX > this.x + this.width) return false;
        if (mouseY < this.y + this.offset) return false;
        return mouseY <= this.y + this.offset + this.height;
    }
}
