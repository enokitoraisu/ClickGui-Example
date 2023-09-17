package me.enokitoraisu.features.gui.clickgui;

import me.enokitoraisu.features.gui.clickgui.item.Item;
import me.enokitoraisu.features.gui.clickgui.item.items.*;
import me.enokitoraisu.features.gui.clickgui.utils.MouseUtil;
import me.enokitoraisu.features.gui.clickgui.utils.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class ModuleButton {
    private final Module module;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final MinecraftClient mc;
    private int offset;
    private boolean open;
    private final List<Item<?>> items = new ArrayList<>();

    public ModuleButton(Module module, int x, int y, int width, int height, MinecraftClient mc) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mc = mc;

        module.settings.forEach(setting -> {
            if (setting instanceof BoolSetting boolSetting)
                items.add(new ItemBoolean(boolSetting, x, y, width, height));
            else if (setting instanceof IntSetting intSetting)
                items.add(new ItemInteger(intSetting, x, y, width, height));
            else if (setting instanceof FloatSetting floatSetting)
                items.add(new ItemFloat(floatSetting, x, y, width, height));
            else if (setting instanceof ColorSetting colorSetting)
                items.add(new ItemColor(colorSetting, x, y, width, height));
            else if (setting instanceof StrSetting strSetting)
                items.add(new ItemString(strSetting, x, y, width, height));
        });

        items.add(new ItemKeyBind(module, x, y, width, height));
    }

    public int render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks, int offset) {
        this.offset = offset;
        int y = this.y + offset;
        RenderUtil.rect(matrices, x, y, width, height, 0x80000000);
        mc.textRenderer.drawWithShadow(matrices, module.getName(), x + 3, y + (height / 2f - mc.textRenderer.fontHeight / 2f), this.module.istoggle ? 0xFF2B71F3 : -1);

        int offsets = height;
        if (open) {
            for (Item<?> item : items) {
                offsets += item.drawScreen(matrices, mouseX, mouseY, partialTicks, offsets + offset);
            }
        }

        return offsets;
    }

    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
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

    public void mouseReleased(double mouseX, double mouseY, int state) {
        if (open)
            items.forEach(item -> item.mouseReleased(mouseX, mouseY, state));
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (open)
            items.forEach(item -> item.keyPressed(keyCode, scanCode, modifiers));
    }

    public void charTyped(char chr, int modifiers) {
        if (open)
            items.forEach(item -> item.charTyped(chr, modifiers));
    }

    public boolean bounding(int mouseX, int mouseY) {
        return MouseUtil.bounding(mouseX, mouseY, this.x, this.y + this.offset, this.width, this.height);
    }
}
