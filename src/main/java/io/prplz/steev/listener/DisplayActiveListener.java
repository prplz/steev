package io.prplz.steev.listener;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.Display;

public class DisplayActiveListener {

    public static boolean isActive = true;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        isActive = Display.isActive();
    }
}
