package io.prplz.steev;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import io.prplz.steev.patch.CapeUtils1Patch;
import io.prplz.steev.patch.ChunkPatch;
import io.prplz.steev.patch.LayerArmorBasePatch;
import io.prplz.steev.patch.MinecraftPatch;
import io.prplz.steev.patch.OptifineAdapterPatch;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassTransformer implements IClassTransformer, Opcodes {

    private final ListMultimap<String, FieldTransformer> fieldTransformers = ArrayListMultimap.create();
    private final ListMultimap<String, MethodTransformer> methodTransformers = ArrayListMultimap.create();

    public ClassTransformer() {
        methodTransformers.put("net.minecraft.world.chunk.Chunk", new MethodTransformer("a", "([BIZ)V", ChunkPatch::patchFillChunk));

        fieldTransformers.put("CapeUtils$1", new FieldTransformer("val$thePlayer", f -> f.desc = "Ljava/lang/String;"));
        methodTransformers.put("CapeUtils$1", new MethodTransformer("<init>", "(Lbet;Ljy;)V", CapeUtils1Patch::patchConstructor));
        methodTransformers.put("CapeUtils$1", new MethodTransformer("a", "()V", CapeUtils1Patch::patchSkinAvailable));

        methodTransformers.put("io.prplz.steev.OptifineAdapter", new MethodTransformer("setLocationOfCape", "(Lnet/minecraft/client/entity/AbstractClientPlayer;Lnet/minecraft/util/ResourceLocation;)V", OptifineAdapterPatch::patchSetLocationOfCape));

        methodTransformers.put("net.minecraft.client.Minecraft", new MethodTransformer("k", "()Z", MinecraftPatch::patchIsFramerateLimitBelowMax));
        methodTransformers.put("net.minecraft.client.Minecraft", new MethodTransformer("j", "()I", MinecraftPatch::patchGetLimitFramerate));

        methodTransformers.put("net.minecraft.client.renderer.entity.layers.LayerArmorBase", new MethodTransformer("b", "()Z", LayerArmorBasePatch::patchShouldCombineTextures));
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
