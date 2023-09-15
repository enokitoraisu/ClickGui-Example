package me.enokitoraisu.features.gui.clickgui.item.items;

import me.enokitoraisu.features.gui.clickgui.utils.RenderUtil;
import me.enokitoraisu.features.gui.clickgui.item.Item;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ItemFloat extends Item<FloatSetting> {
    private final int places;
    private boolean isSlide;
  
    public ItemFloat(FloatSetting floatSetting, int x, int y, int width, int height) {
        super(floatSetting, x, y, width, height);
        this.places = decimalPlaces(getObject().getStep());
    }
  
    @Override
    public int drawScreen(MatrixStack matrices, int mouseX, int mouseY, float partialTicks, int offset) {
        if (!getObject().isVisible()) return 0;
      
        this.offset = offset;
        float y = offset + this.y;
      
        RenderUtil.rect(matrices, x, y, width, height, 0x80000000);
        float drawWidth = (getObject().getValue() - getObject().getMin()) / (getObject().getMax() - getObject().getMin());
        RenderUtil.rect(matrices, x, y, width * drawWidth, height, 0xFF2B71F3);
        String text = getObject().getName() + " " + getObject().getValue();
        mc.textRenderer.drawWithShadow(matrices, text, x + 5, y + height / 2F - mc.textRenderer.fontHeight / 2F, -1);
    
        if (this.isSlide) {
            float value = ((float) mouseX - x) / width;
            value = MathHelper.clamp(value, 0.0F, 1.0F);
            float rounded = (float) round(Math.round((value * (getObject().getMax() - getObject().getMin()) + getObject().getMin()) / getObject().getStep()) * getObject().getStep(), places);
            this.getObject().setValue(rounded);
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

    /**
     * <a href="https://github.com/kami-blue/commons/blob/master/org/kamiblue/commons/utils/MathUtils.kt#L23">KamiBlue MathUtils.round</a>
     */
    public static double round(float value, int places) {
        double scale = Math.pow(10.0, places);
        return Math.round(value * scale) / scale;
    }

    /**
     * <a href="https://github.com/kami-blue/commons/blob/master/org/kamiblue/commons/utils/MathUtils.kt#L35">KamiBlue MathUtils.decimalPlaces</a>
     */
    public static int decimalPlaces(float value) {
        return Float.toString(value).split("\\.")[1].length();
    }
}
