package me.enokitoraisu.features.gui.clickgui.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix4f;

public class RenderUtil {
    public static void rect(MatrixStack matrices, float x, float y, float width, float height, int color) {
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float a = (float) ColorHelper.Argb.getAlpha(color) / 255.0f;
        float r = (float) ColorHelper.Argb.getRed(color) / 255.0f;
        float g = (float) ColorHelper.Argb.getGreen(color) / 255.0f;
        float b = (float) ColorHelper.Argb.getBlue(color) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix4f, x, y, 0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix4f, x, y + height, 0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix4f, x + width, y + height, 0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix4f, x + width, y, 0f).color(r, g, b, a).next();
        tessellator.draw();
        RenderSystem.disableBlend();
    }

    public static void minusrect(MatrixStack matrices, float x, float y, float width, float height, int color) {
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float a = (float) ColorHelper.Argb.getAlpha(color) / 255.0f;
        float r = (float) ColorHelper.Argb.getRed(color) / 255.0f;
        float g = (float) ColorHelper.Argb.getGreen(color) / 255.0f;
        float b = (float) ColorHelper.Argb.getBlue(color) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix4f, x, y, 0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix4f, x, y + height, 0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix4f, x + width, y + height, 0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix4f, x + width, y, 0f).color(r, g, b, a).next();
        tessellator.draw();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }
}
