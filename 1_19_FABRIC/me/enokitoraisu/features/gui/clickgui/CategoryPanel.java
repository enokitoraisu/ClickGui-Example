package me.enokitoraisu.features.gui.clickgui;

import me.enokitoraisu.features.gui.clickgui.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class CategoryPanel {
    private final Category category;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final MinecraftClient mc;
    private final List<ModuleButton> moduleButtons = new ArrayList<>();
    private boolean open = true;

    public CategoryPanel(Category category, int x, int y, int width, int height, MinecraftClient mc) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mc = mc;

        for (Module module : ClientName.moduleManager.getModules()) {
            if (module.category == this.category) {
                moduleButtons.add(new ModuleButton(module, x, y, width, height, mc));
            }
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderUtil.rect(matrices, x, y, width, height, 0xFF2B71F3);
        mc.textRenderer.drawWithShadow(matrices, category.name(),
                x + width / 2f - mc.textRenderer.getWidth(category.name()) / 2f,
                y + height / 2f - mc.textRenderer.fontHeight / 2f,
                -1);

        if (this.open) {
            int offset = height;
            for (ModuleButton moduleButton : this.moduleButtons) {
                offset += moduleButton.render(matrices, mouseX, mouseY, delta, offset);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (this.open) moduleButtons.forEach(moduleButton -> moduleButton.mouseClicked(mouseX, mouseY, mouseButton));

        if (bounding(mouseX, mouseY) && mouseButton == 1) this.open = !this.open;
    }

    public void mouseReleased(double mouseX, double mouseY, int state) {
        if (this.open)
            moduleButtons.forEach(moduleButton -> moduleButton.mouseReleased(mouseX, mouseY, state));
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.open) moduleButtons.forEach(moduleButton -> moduleButton.keyPressed(keyCode, scanCode, modifiers));
    }

    public void charTyped(char chr, int modifiers) {
        if (this.open)
            moduleButtons.forEach(moduleButton -> moduleButton.charTyped(chr, modifiers));
    }

    public boolean bounding(double mouseX, double mouseY) {
        if (mouseX < this.x) return false;
        if (mouseX > this.x + this.width) return false;
        if (mouseY < this.y) return false;
        return mouseY <= this.y + this.height;
    }
}
