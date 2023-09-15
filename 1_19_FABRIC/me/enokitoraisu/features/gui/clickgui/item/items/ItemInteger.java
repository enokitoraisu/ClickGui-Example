package me.enokitoraisu.features.gui.clickgui.item.items;

import me.enokitoraisu.features.gui.clickgui.utils.RenderUtil;
import me.enokitoraisu.features.gui.clickgui.item.Item;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ItemInteger extends Item<IntSetting> {
    private boolean isSlide;
  
    public ItemInteger(IntSetting intSetting, int x, int y, int width, int height) {
        super(intSetting, x, y, width, height);
    }
  
    @Override
    public int drawScreen(MatrixStack matrices, int mouseX, int mouseY, float partialTicks, int offset) {
        if (!getObject().isVisible()) return 0;
      
        this.offset = offset;
        float y = offset + this.y;
      
        RenderUtil.rect(matrices, x, y, width, height, 0x80000000);
        float drawWidth = (getObject().getValue().floatValue() - getObject().getMin()) / (getObject().getMax() - getObject().getMin());
        RenderUtil.rect(matrices, x, y, width * drawWidth, height, 0xFF2B71F3);
        String text = getObject().getName() + " " + getObject().getValue();
        mc.textRenderer.drawWithShadow(matrices, text, x + 5, y + height / 2F - mc.textRenderer.fontHeight / 2F, -1);
    
        if (this.isSlide) {
            float value = ((float) mouseX - x) / width;
            value = MathHelper.clamp(value, 0.0F, 1.0F);
            getObject().setValue((int) (getObject().getMin() + (getObject().getMax() - getObject().getMin()) * value));
        }
      
        return height;
    }
  
    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!getObject().isVisible()) return;
        if (bounding(mouseX, mouseY)) {
            this.isSlide = true;
        }
    }
  
    @Override
    public void mouseReleased(double mouseX, double mouseY, int state) {
        this.isSlide = false;
    }
}
