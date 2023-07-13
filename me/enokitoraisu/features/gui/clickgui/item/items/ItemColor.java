package me.enokitoraisu.features.gui.clickgui.item.items;

import me.enokitoraisu.features.gui.clickgui.RenderUtil;
import me.enokitoraisu.features.gui.clickgui.item.Item;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

public class ItemColor extends Item<ColorSetting> {
  private float settingHue, settingSaturation, settingBrightness;
  private boolean movingPicker;
  private boolean isSlideHue;
  
  public ItemColor(ColorSetting colorSetting, int x, int y, int width, int height) {
    super(colorSetting, x, y, width, height);
    float[] hsb = Color.RGBtoHSB(getObject().getValue().getRed(), getObject().getValue().getGreen(), getObject().getValue().getBlue(), new float[3]);
    this.settingHue = hsb[0];
    this.settingSaturation = hsb[1];
    this.settingBrightness = hsb[2];
  }

  @Override
  public int drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
    if (!getObject().isVisible()) return 0;
    this.offset = offset;
    int y = this.y + offset;

    RenderUtil.rect(x + 2, y + 2, height - 4, height - 4, getIntValue());
    mc.fontRenderer.drawStringWithShadow(x + height + 5, y + height / 2 - mc.fontRenderer.FONT_HEIGHT / 2, -1);
    drawColorPicker(x + 3, y + height + 3, width - 6, width - 18, Color.HSBtoRGB(settingHue, settingSaturation, settingBirhgness));

    //current saturation brightness
    RenderUtil.rect(x + 3 + (width - 6) * settingSaturation, y + 3 + (width - 18) * settingBrightness, 1, 1, 0x80000000);
    for (int hueX = 0; hueX < width - 6; hueX++) {
      RenderUtil.rect(x + 3 + hueX, y + 3 + width - 15, 1, 12, Color.HSBtoRGB((float) hueX / (width - 6), 1F, 1F));
    }
    //current hue
    RenderUtil.rect(x + 3 + (width - 6) * settingHue, y + 3 + width - 15, 1, 12, 0x80000000);

    if (movingPicker) {
      settingSaturation = MathHelper.clamp(((float) mouseX - (x + 3)) / (width - 6), 0F, 1F);
      settingBrightness = MathHelper.clamp(((float) mouseY - (y + height + 3)) / (width - 18), 0F, 1F);
      getObject().setValue(Color.getHSBColor(settingHue, settingSaturation, settingBrightness));
    } else if (isSlideHue) {
      settingHue = MathHelper.clamp(((float) mouseX - (x + 3)) / (width - 6), 0F, 1F);
      getObject().setValue(Color.getHSBColor(settingHue, settingSaturation, settingBrightness));
    }
    
    return width + height;
  }

  @Override
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    if (!getObject().isVisible()) return;
    if (bounding(mouseX, mouseY, x + 3, y + height + 3, width - 6, width - 18))
      movingPicker = true;
    else if (bounding(mouseX, mouseY, x + 3, y + 3 + width - 15, width - 6, 12))
      isSlideHue = true;
  }

  @Override
  public void mouseReleased(int mouseX, int mouseY, int state) {
    movingPicker = false;
    isSlideHue = false;
  }

  public void drawColorPickerShade(int x, int y, int width, int height, int color) {
    color = fixHue(color);
    float red = (float) (color >> 16 & 0xFF) / 255.0f;
    float green = (float) (color >> 8 & 0xFF) / 255.0f;
    float blue = (float) (color & 0xFF) / 255.0f;

    GlStateManager.disableTexture2D();
    GlStateManager.enableBlend();
    GlStateManager.disableAlpha();
    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    GlStateManager.shadeModel(GL11.GL_SMOOTH);
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferBuilder = tessellator.getBuffer();
    bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
    bufferBuilder.pos(x, y, 0.0).color(1F, 1F, 1F, 1F).endVertex();
    bufferBuilder.pos(x, y + height, 0.0).color(0F, 0F, 0F, 1F).endVertex();
    bufferBuilder.pos(x + width, y + height, 0.0).color(0F, 0F, 0F, 1F).endVertex();
    bufferBuilder.pos(x + width, y, 0.0).color(red, green, blue, 1F).endVertex();
    tessellator.draw();
    GlStateManager.shadeModel(GL11.GL_FLAT);
    GlStateManager.disableBlend();
    GlStateManager.enableAlpha();
    GlStateManager.enableTexture2D();
  }

  //lagggg
  public void drawColorPicker(int x, int y, int width, int height, int color) {
    int red = color >> 16 & 0xFF;
    int green = color >> 8 & 0xFF;
    int blue = color & 0xFF;
    float hue = Color.RGBtoHSB(red, green, blue, new float[3])[0];
    for (int colorX = 0; colorX < width; colorX++) {
      for (int colorY = 0; colorY < height; colorX++) {
        float saturation = (float) colorX / width;
        float brightness = 1F - (float) colorY / height;
        RenderUtil.rect(x + colorX, y + colorY, 1, 1, Color.HSBtoRGB(hue, saturation, brightness));
      }
    }
  }

  public int fixHue(int color) {
    int red = color >> 16 & 0xFF;
    int green = color >> 8 & 0xFF;
    int blue = color & 0xFF;
    float hue = Color.RGBtoHSB(red, green, blue, new float[3])[0];
    return Color.HSBtoRGB(hue, 1F, 1F);
  }
}
