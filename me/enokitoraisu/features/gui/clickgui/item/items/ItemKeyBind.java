package me.enokitoraisu.features.gui.clickgui.item.items;

import me.enokitoraisu.features.gui.clickgui.util.RenderUtil;
import me.enokitoraisu.features.gui.clickgui.item.Item;
import org.lwjgl.input.Keyboard;

public class ItemKeyBind extends Item<Module> {
    private boolean pendingKey;

    public ItemKeyBind(Module module, int x, int y, int width, int height) {
        super(module, x, y, width, height);
    }

    @Override
    public int drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
        this.offset = offset;
        float y = this.y + offset;

        RenderUtil.rect(x, y, width, height, 0x80000000);
        mc.fontRenderer.drawStringWithShadow(pendingKey ? "PressKey..." : "Bind [" + Keyboard.getKeyName(getObject().keybind) + "]",
                x + 5,
                y + height / 2f - mc.fontRenderer.FONT_HEIGHT / 2f,
                -1);

        return height;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (bounding(mouseX, mouseY)) {
            if (mouseButton == 0)
                pendingKey = !pendingKey;
        } else
            pendingKey = false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (pendingKey) {
            if (keyCode == Keyboard.KEY_DELETE)
                getObject().keybind = Keyboard.KEY_NONE;
            else
                getObject().keybind = keyCode;
            pendingKey = false;
        }
    }
}
