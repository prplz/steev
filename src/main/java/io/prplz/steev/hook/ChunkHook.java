package io.prplz.steev.hook;

import net.minecraft.world.chunk.Chunk;

public class ChunkHook {

    // returns true if this chunk should be filled, or false if the data is too small
    public static boolean fillChunkHook(Chunk chunk, byte[] data, int mask, boolean full) {
        boolean sky = !chunk.getWorld().provider.getHasNoSky();
        int size = 0;
        for (int i = 0; i < 16; i++) {
            if ((mask & 1 << i) != 0) {
                size += 8192; // blocks
                size += 2048; // block light
                if (sky) {
                    size += 2048; // sky light
                }
            }
        }
        if (full) {
            size += 256; // biomes
        }
        return data.length >= size; // data length should be equal to or greater than the expected size
    }
}
