package me.enokitoraisu.features.gui.clickgui.item.items;

import com.mojang.blaze3d.systems.RenderSystem;
import me.enokitoraisu.features.gui.clickgui.utils.RenderUtil;
import me.enokitoraisu.features.gui.clickgui.item.Item;
import net.minecraft.client.render.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;

import java.awt.Color;

public class ItemColor extends Item<ColorSetting> {
    private float settingHue, settingSaturation, settingBrightness;
    private boolean movingPicker;
    private boolean isSlideHue;
    private boolean open;

    public ItemColor(ColorSetting colorSetting, int x, int y, int width, int height) {
        super(colorSetting, x, y, width, height);
        float[] hsb = Color.RGBtoHSB(getObject().getValue().getRed(), getObject().getValue().getGreen(), getObject().getValue().getBlue(), new float[3]);
        this.settingHue = hsb[0];
        this.settingSaturation = hsb[1];
        this.settingBrightness = hsb[2];
        this.open = false;
    }

    @Override
    public int drawScreen(MatrixStack matrices, int mouseX, int mouseY, float partialTicks, int offset) {
        if (!getObject().isVisible()) return 0;
        this.offset = offset;
        int y = this.y + offset;

        RenderUtil.rect(x + 2, y + 2, height - 4, height - 4, getObject().getValue().hashCode());
        mc.textRenderer.drawWithShadow(matrices, getObject().getName(), x + height + 5, y + height / 2F - mc.textRenderer.fontHeight / 2F, -1);
        if(open){
            drawColorPicker(matrices, x + 3, y + height + 3, width - 6, width - 18, Color.HSBtoRGB(settingHue, settingSaturation, settingBrightness));
            //current saturation brightness
            RenderUtil.rect(matrices, x + 3 + (width - 6) * settingSaturation, y + height + 3 + (width - 18) * (1 - settingBrightness), 1, 1, 0x80000000);
            for (int hueX = 0; hueX < width - 6; hueX++) {
                RenderUtil.rect(matrices, x + 3 + hueX, y + height + 3 + width - 15, 1, 12, Color.HSBtoRGB((float) hueX / (width - 6), 1F, 1F));
            }
            //current hue
            RenderUtil.rect(matrices, x + 3 + (width - 6) * settingHue, y + height + 3 + width - 15, 1, 12, 0x80000000);
    
            if (movingPicker) {
                settingSaturation = MathHelper.clamp(((float) mouseX - (x + 3)) / (width - 6), 0F, 1F);
                settingBrightness = 1 - MathHelper.clamp(((float) mouseY - (y + height + 3)) / (width - 18), 0F, 1F);
                getObject().setValue(Color.getHSBColor(settingHue, settingSaturation, settingBrightness));
            } else if (isSlideHue) {
                settingHue = MathHelper.clamp(((float) mouseX - (x + 3)) / (width - 6), 0F, 1F);
                getObject().setValue(Color.getHSBColor(settingHue, settingSaturation, settingBrightness));
            }
        }
        return width + height;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!getObject().isVisible()) return;
        if (bounding(mouseX, mouseY, x + 3, y + height + 3, width - 6, width - 18))
            movingPicker = true;
        else if (bounding(mouseX, mouseY, x + 3, y + height + 3 + width - 15, width - 6, 12))
            isSlideHue = true;
        if(bounding(mouseX,mouseY) && mouseButton == 1){
            open = !open;
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int state) {
        movingPicker = false;
        isSlideHue = false;
    }

    public void drawColorPickerShade(MatrixStack matrices, int x, int y, int width, int height, int color) {
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float r = (float) ColorHelper.Argb.getRed(color) / 255.0f;
        float g = (float) ColorHelper.Argb.getGreen(color) / 255.0f;
        float b = (float) ColorHelper.Argb.getBlue(color) / 255.0f;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix4f, x, y, 0f).color(1F, 1F, 1F, 1F).next();
        bufferBuilder.vertex(matrix4f, x, y + height, 0f).color(0F, 0F, 0F, 1F).next();
        bufferBuilder.vertex(matrix4f, x + width, y + height, 0f).color(0F, 0F, 0F, 1F).next();
        bufferBuilder.vertex(matrix4f, x + width, y, 0f).color(r, g, b, 1F).next();
        tessellator.draw();
        RenderSystem.disableBlend();
    }

    //lagggg
    public void drawColorPicker(MatrixStack matrices, int x, int y, int width, int height, int color) {
        int red = color >> 16 & 0xFF;
        int green = color >> 8 & 0xFF;
        int blue = color & 0xFF;
        float hue = Color.RGBtoHSB(red, green, blue, new float[3])[0];
        for (int colorX = 0; colorX < width; colorX++) {
            for (int colorY = 0; colorY < height; colorY++) {
                float saturation = (float) colorX / width;
                float brightness = 1F - (float) colorY / height;
                RenderUtil.rect(matrices, x + colorX, y + colorY, 1, 1, Color.HSBtoRGB(hue, saturation, brightness));
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
