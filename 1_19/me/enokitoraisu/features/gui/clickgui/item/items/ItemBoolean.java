package me.enokitoraisu.features.gui.clickgui.item.items;

import me.enokitoraisu.features.gui.clickgui.utils.RenderUtil;
import me.enokitoraisu.features.gui.clickgui.item.Item;
import net.minecraft.client.util.math.MatrixStack;

public class ItemBoolean extends Item<BoolSetting> {
    public ItemBoolean(BoolSetting boolSetting, int x, int y, int width, int height) {
        super(boolSetting, x, y, width, height);
    }

    @Override
    public int drawScreen(MatrixStack matrices, int mouseX, int mouseY, float partialTicks, int offset) {
        if (!getObject().isVisible()) return 0;
        this.offset = offset;
        float y = this.y + offset;

        RenderUtil.rect(matrices, x, y, width, height, 0x80000000);
        mc.textRenderer.drawWithShadow(matrices, getObject().getName(), x + 5, y + height / 2f - mc.textRenderer.fontHeight / 2f, getObject().getValue() ? 0xFF2B71F3 : -1);

        return height;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!getObject().isVisible()) return;
        if (bounding(mouseX, mouseY)) {
            if (mouseButton == 0)
                getObject().setValue(!getObject().getValue());
        }
    }
}
