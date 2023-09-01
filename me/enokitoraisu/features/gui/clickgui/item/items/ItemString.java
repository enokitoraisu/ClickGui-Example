package me.enokitoraisu.features.gui.clickgui.item.items;

import me.enokitoraisu.features.gui.clickgui.util.RenderUtil;
import me.enokitoraisu.features.gui.clickgui.item.Item;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class ItemString extends Item<StrSetting> {
    private boolean moved;
    private int input;
    private String value;
    private boolean typing;

    public ItemString(StrSetting strSetting, int x, int y, int width, int height) {
        super(strSetting, x, y, width, height);
        this.moved = false;
        this.value = getObject().getValue();
        this.input = this.value.length();
        this.typing = false;
    }

    @Override
    public int drawScreen(int mouseX, int mouseY, float partialTicks, int offset) {
        if (!getObject().isVisible()) return 0;

        this.offset = offset;
        float y = offset + this.y;

        RenderUtil.rect(x, y, width, height, 0x80000000);
        mc.fontRenderer.drawStringWithShadow(value, x + 5, y + height / 2F - mc.fontRenderer.FONT_HEIGHT / 2F, 0xFF2B71F3);
        if (typing) {
            int len = moved ? this.input : value.length();
            float len_width = mc.fontRenderer.getStringWidth(value.substring(0, this.input));
            RenderUtil.rect(x + 5 + len_width, y + height / 2F - mc.fontRenderer.FONT_HEIGHT / 2F, 1, mc.fontRenderer.FONT_HEIGHT, 0xFF2B71F3);
        }

        return height;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (bounding(mouseX, mouseY) && mouseButton == 0) {
            this.typing = !this.typing;
            if (typing) {
                input = value.length();
                moved = false;
                getObject().setValue(value);
            } else {
                value = getObject().getValue();
                input = value.length();
                moved = false;
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        switch (keyCode) {
            case Keyboard.KEY_LEFT:
                this.input = Math.max(this.input - 1, 0);
                if (this.input == value.length()) 
                    moved = false;
                break;
            case Keyboard.KEY_RIGHT:
                this.input = Math.min(this.input + 1, value.length());
                if (this.input == value.length()) 
                    moved = false;
                break;
            case Keyboard.KEY_DELETE:
                value = getDELETEString(value, moved, input);
                if (value.length() <= input) {
                    moved = false;
                    input = value.length();
                }
                break;
            case Keyboard.KEY_BACK:
                value = getBACKSPACEString(value, moved, input);
                if (value.length() <= input) {
                    moved = false;
                    input = value.length();
                }
                break;
            default:
                if (!ChatAllowedCharacters.isAllowedCharacter(typedChar)) break;
                value = append(typedChar, value, moved, input);
                if (!moved) {
                    input = value.length();
                }
                break;
        }
    }

    public String append(char typedChar, String value, boolean position, int inputPosition) {
        if (position) {
            String before = value.substring(0, inputPosition);
            String after = value.substring(inputPosition, value.length());
            return before + typedChar + after;
        } else {
            return value + typedChar;
        }
    }

    public String getBACKSPACEString(String value, boolean position, int inputPosition) {
        if (position) {
            String before = value.substring(0, inputPosition);
            String after = value.substring(inputPosition, value.length());
            int splitPos = before.length() <= 0 ? 0 : before.length() - 1;
            return before.substring(0, splitPos) + after;
        } else {
            int splitPos = value.length() <= 0 ? 0 : value.length() - 1;
            return value.substring(0, splitPos);
        }
    }

    public String getDELETEString(String value, boolean position, int inputPosition) {
        if (position) {
            String before = value.substring(0, inputPosition);
            String after = value.substring(inputPosition, value.length());
            int splitPos = after.length() > 1 ? 0 : 1;
            return before + after.substring(splitPos, before.length());
        } else {
            return value;
        }
    }
}
