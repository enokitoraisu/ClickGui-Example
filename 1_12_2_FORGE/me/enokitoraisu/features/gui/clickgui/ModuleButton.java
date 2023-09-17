package me.enokitoraisu.features.gui.clickgui;

import me.enokitoraisu.features.gui.clickgui.utils.MouseUtil;
import me.enokitoraisu.features.gui.clickgui.utils.RenderUtil;
import me.enokitoraisu.features.gui.clickgui.item.Item;
import me.enokitoraisu.features.gui.clickgui.item.items.*;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class ModuleButton {
    private final Module module;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Minecraft mc;
    private int offset;
    private boolean open;
    private final List<Item<?>> items = new ArrayList<>();

    public ModuleButton(Module module, int x, int y, int width, int height, Minecraft mc) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mc = mc;

        for (Setting<?> setting : module.settings) {
            if (setting instanceof BoolSetting)
                items.add(new ItemBoolean((BoolSetting) setting, x, y, width, height));
            else if (setting instanceof IntSetting)
                items.add(new ItemInteger((IntSetting) setting, x, y, width, height));
            else if (setting instanceof FloatSetting)
                items.add(new ItemFloat((FloatSetting) setting, x, y, width, height));
            else if (setting instanceof ColorSetting)
                items.add(new ItemColor((ColorSetting) setting, x, y, width, height));
            else if (setting instanceof StrSetting)
                items.add(new ItemString((StrSetting) setting, x, y, width, height));
        }

        items.add(new ItemKeyBind(module, x, y, width, height));
    }

    public int drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
        this.offset = offset;
        int y = this.y + offset;
        RenderUtil.rect(x, y, width, height, 0x80000000);
        mc.fontRenderer.drawStringWithShadow(module.getName(), x + 3, y + (height / 2f - mc.fontRenderer.FONT_HEIGHT / 2f), this.module.istoggle ? 0xFF2B71F3 : -1);

        int offsets = height;
        if (open) {
            for (Item<?> item : items) {
                offsets += item.drawScreen(mouseX, mouseY, partialTicks, offsets + offset);
            }
        }

        return offsets;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (bounding(mouseX, mouseY)){
            if (mouseButton == 0) {
                this.module.toggle();
            } else if (mouseButton == 1) {
                this.open = !this.open;
            }
        }

        if (open)
            items.forEach(item -> item.mouseClicked(mouseX, mouseY, mouseButton));
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (open)
            items.forEach(item -> item.keyTyped(typedChar, keyCode));
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (open)
            items.forEach(item -> item.mouseReleased(mouseX, mouseY, state));
    }

    public boolean bounding(int mouseX, int mouseY) {
        return MouseUtil.bounding(mouseX, mouseY, this.x, this.y + this.offset, this.width, this.height);
    }
}
