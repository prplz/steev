package io.prplz.steev.hook;

import net.minecraftforge.client.GuiIngameForge;

public class GuiIngameForgeHook {

    public static void drawTexturedModalRectHook(GuiIngameForge gui, int x, int y, int u, int v, int width, int height) {
        // cancel if drawing flashing heart texture
        if (v == 0 || v == 45) {
            if (u == 70 || u == 79 || u == 106 || u == 115 || u == 142 || u == 151) {
                return;
            }
        }
        gui.drawTexturedModalRect(x, y, u, v, width, height);
    }
}
