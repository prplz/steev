package io.prplz.steev.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class LayerArmorBasePatch {

    public static void patchShouldCombineTextures(MethodNode method) {
        // patch to return true, makes armor red when taking damage like in 1.7
        method.instructions.clear();
        method.instructions.add(new InsnNode(Opcodes.ICONST_1));
        method.instructions.add(new InsnNode(Opcodes.IRETURN));
    }
}
