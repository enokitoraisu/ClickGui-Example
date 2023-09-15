package me.enokitoraisu.features.gui.clickgui.item.items;

import me.enokitoraisu.features.gui.clickgui.utils.RenderUtil;
import me.enokitoraisu.features.gui.clickgui.item.Item;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.input.Keyboard;

public class ItemKeyBind extends Item<Module> {
    private boolean pendingKey;

    public ItemKeyBind(Module module, int x, int y, int width, int height) {
        super(module, x, y, width, height);
    }

    @Override
    public int drawScreen(MatrixStack matrices, int mouseX, int mouseY, float partialTicks, int offset) {
        this.offset = offset;
        float y = this.y + offset;

        RenderUtil.rect(matrices, x, y, width, height, 0x80000000);
        mc.textRenderer.drawWithShadow(matrices, pendingKey ? "PressKey..." : "Bind [" + GLFW.glfwGetKeyName(getObject().keybind, GLFW.glfwGetKeyScancode(getObject().keybind)) + "]",
                x + 5,
                y + height / 2f - mc.textRenderer.fontHeight / 2f,
                -1);

        return height;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (bounding(mouseX, mouseY)) {
            if (mouseButton == 0)
                pendingKey = !pendingKey;
        } else
            pendingKey = false;
    }

    @Override
    public void keyPressed(int typedChar, int keyCode, int aaa) {
        if (pendingKey) {
            if (keyCode == Keyboard.KEY_DELETE)
                getObject().keybind = Keyboard.KEY_NONE;
            else
                getObject().keybind = keyCode;
            pendingKey = false;
        }
    }
}
