package me.enokitoraisu.features.gui.clickgui.item.items;

import me.enokitoraisu.features.gui.clickgui.RenderUtil;
import me.enokitoraisu.features.gui.clickgui.item.Item;
import net.minecraft.util.math.MathHelper;

public class ItemInteger extends Item<IntSetting> {
    private boolean isSlide;
  
    public ItemInteger(IntSetting intSetting, int x, int y, int width, int height) {
        super(intSetting, x, y, width, height);
    }
  
    @Override
    public int drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
        if (!getObject().isVisible()) return 0;
      
        this.offset = offset;
        float y = offset + this.y;
      
        RenderUtil.rect(x, y, width, height, 0x80000000);
        float drawWidth = (getObject().getValue().floatValue() - getObject().getMin()) / (getObject().getMax() - getObject().getMin());
        RenderUtil.rect(x, y, width * drawWidth, height, 0xFF2B71F3);
        String text = getObject().getName() + " " + getObject().getValue();
        mc.fontRenderer.drawStringWithShadow(text, x + 5, y + height / 2F - mc.fontRenderer.FONT_HEIGHT / 2F, -1);
    
        if (this.isSlide) {
            float value = ((float) mouseX - x) / width;
            value = MathHelper.clamp(value, 0.0F, 1.0F);
            getObject().setValue((int) (getObject().getMin() + (getObject().getMax() - getObject().getMin()) * value));
        }
      
        return height;
    }
  
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!getObject().isVisible()) return;
        if (bounding(mouseX, mouseY)) {
            this.isSlide = true;
        }
    }
  
    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.isSlide = false;
    }
}
