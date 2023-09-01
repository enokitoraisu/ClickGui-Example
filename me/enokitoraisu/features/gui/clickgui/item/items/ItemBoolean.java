package me.enokitoraisu.features.gui.clickgui.item.items;

import me.enokitoraisu.features.gui.clickgui.util.RenderUtil;
import me.enokitoraisu.features.gui.clickgui.item.Item;

public class ItemBoolean extends Item<BoolSetting> {
    public ItemBoolean(BoolSetting boolSetting, int x, int y, int width, int height) {
        super(boolSetting, x, y, width, height);
    }

    @Override
    public int drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
        if (!getObject().isVisible()) return 0;
        this.offset = offset;
        float y = this.y + offset;

        RenderUtil.rect(x, y, width, height, 0x80000000);
        mc.fontRenderer.drawStringWithShadow(getObject().getName(), x + 5, y + height / 2f - mc.fontRenderer.FONT_HEIGHT / 2f, getObject().getValue() ? 0xFF2B71F3 : -1);

        return height;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!getObject().isVisible()) return;
        if (bounding(mouseX, mouseY)) {
            if (mouseButton == 0)
                getObject().setValue(!getObject().getValue());
        }
    }
}
