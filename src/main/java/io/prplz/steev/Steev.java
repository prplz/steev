package io.prplz.steev;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import io.prplz.steev.listener.DisplayActiveListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.Collections;

public class Steev extends DummyModContainer {

    public static final String MOD_ID = "steev";
    public static final String MOD_NAME = "Steev";
    public static final String VERSION = "1.0-SNAPSHOT";

    public Steev() {
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = MOD_ID;
        meta.name = MOD_NAME;
        meta.version = VERSION;
        meta.authorList = Collections.singletonList("prplz");
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Subscribe
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new DisplayActiveListener());
    }
}
