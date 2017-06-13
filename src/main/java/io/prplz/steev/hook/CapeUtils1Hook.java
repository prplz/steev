package io.prplz.steev.hook;

import io.prplz.steev.OptifineAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class CapeUtils1Hook {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void skinAvailable(String name, ResourceLocation resource) {
        mc.addScheduledTask(() -> {
            if (mc.theWorld != null) {
                for (EntityPlayer player : mc.theWorld.playerEntities) {
                    if (player.getName().equals(name)) {
                        OptifineAdapter.setLocationOfCape((AbstractClientPlayer) player, resource);
                    }
                }
            }
        });
    }

}
