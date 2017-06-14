package io.prplz.steev.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class WorldPatch {

    public static void patchCheckLightFor(MethodNode method) {
        // return true
        method.instructions.clear();
        method.instructions.add(new InsnNode(Opcodes.ICONST_1));
        method.instructions.add(new InsnNode(Opcodes.IRETURN));
    }

    public static void patchGetLight(MethodNode method) {
        // return 15 (full brightness)
        method.instructions.clear();
        method.instructions.add(new LdcInsnNode(15));
        method.instructions.add(new InsnNode(Opcodes.IRETURN));
    }
}
