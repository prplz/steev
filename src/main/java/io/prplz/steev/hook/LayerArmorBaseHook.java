package io.prplz.steev.hook;

import net.minecraft.entity.EntityLivingBase;

public class LayerArmorBaseHook {

    // return true to skip rendering glint
    public static boolean renderGlintHook(EntityLivingBase entity) {
        return entity.hurtTime > 0 || entity.deathTime > 0;
    }
}
