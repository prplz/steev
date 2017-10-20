package io.prplz.steev;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import io.prplz.steev.patch.ChunkPatch;
import io.prplz.steev.patch.GuiIngameForgePatch;
import io.prplz.steev.patch.LayerArmorBasePatch;
import io.prplz.steev.patch.MinecraftPatch;
import io.prplz.steev.patch.WorldPatch;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassTransformer implements IClassTransformer {

    private final ListMultimap<String, FieldTransformer> fieldTransformers = ArrayListMultimap.create();
    private final ListMultimap<String, MethodTransformer> methodTransformers = ArrayListMultimap.create();

    public ClassTransformer() {
        // chunks packet exception spam fix (hypixel)
        methodTransformers.put("net.minecraft.world.chunk.Chunk", new MethodTransformer("a", "([BIZ)V", ChunkPatch::patchFillChunk));

        // limit fps while mc is unfocused
        methodTransformers.put("net.minecraft.client.Minecraft", new MethodTransformer("k", "()Z", MinecraftPatch::patchIsFramerateLimitBelowMax));
        methodTransformers.put("net.minecraft.client.Minecraft", new MethodTransformer("j", "()I", MinecraftPatch::patchGetLimitFramerate));

        // make armor red while taking damage, as in 1.7
        methodTransformers.put("net.minecraft.client.renderer.entity.layers.LayerArmorBase", new MethodTransformer("b", "()Z", LayerArmorBasePatch::patchShouldCombineTextures));
        methodTransformers.put("net.minecraft.client.renderer.entity.layers.LayerArmorBase", new MethodTransformer("a", "(Lpr;Lbbo;FFFFFFF)V", LayerArmorBasePatch::patchRenderGlint));

        // fullbright + no light updates
        methodTransformers.put("net.minecraft.world.World", new MethodTransformer("c", "(Lads;Lcj;)Z", WorldPatch::patchCheckLightFor));
        methodTransformers.put("net.minecraft.world.World", new MethodTransformer("k", "(Lcj;)I", WorldPatch::patchGetLight)); // getLight
        methodTransformers.put("net.minecraft.world.World", new MethodTransformer("c", "(Lcj;Z)I", WorldPatch::patchGetLight)); // getLight
        methodTransformers.put("net.minecraft.world.World", new MethodTransformer("b", "(Lads;Lcj;)I", WorldPatch::patchGetLight)); // getLightFor
        methodTransformers.put("net.minecraft.world.World", new MethodTransformer("a", "(Lads;Lcj;)I", WorldPatch::patchGetLight)); // getLightFromNeighborsFor
        methodTransformers.put("net.minecraft.world.chunk.Chunk", new MethodTransformer("a", "(Lads;Lcj;)I", WorldPatch::patchGetLight)); // getLightFor
        methodTransformers.put("net.minecraft.world.chunk.Chunk", new MethodTransformer("a", "(Lcj;I)I", WorldPatch::patchGetLight)); // getLightSubtracted

        // flashing hearts fix
        methodTransformers.put("net.minecraftforge.client.GuiIngameForge", new MethodTransformer("renderHealth", "(II)V", GuiIngameForgePatch::patchRenderHealth));
    }


    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (fieldTransformers.containsKey(transformedName) || methodTransformers.containsKey(transformedName)) {
            ClassReader classReader = new ClassReader(bytes);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 0);

            for (FieldTransformer transformer : fieldTransformers.get(transformedName)) {
                for (FieldNode field : classNode.fields) {
                    if (transformer.transform(field)) {
                        System.out.println("Transforming field " + transformedName + "." + field.name + " " + field.desc);
                    }
                }
            }

            for (MethodTransformer transformer : methodTransformers.get(transformedName)) {
                for (MethodNode method : classNode.methods) {
                    if (transformer.transform(method)) {
                        System.out.println("Transforming method " + transformedName + "." + method.name + method.desc);
                    }
                }
            }

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        return bytes;
    }
}
