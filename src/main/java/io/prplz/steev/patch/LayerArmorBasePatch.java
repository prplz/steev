package io.prplz.steev.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class LayerArmorBasePatch {

    public static void patchShouldCombineTextures(MethodNode method) {
        // patch to return true, makes armor red when taking damage like in 1.7
        method.instructions.clear();
        method.instructions.add(new InsnNode(Opcodes.ICONST_1));
        method.instructions.add(new InsnNode(Opcodes.IRETURN));
    }

    public static void patchRenderGlint(MethodNode method) {
        /*
        if (LayerArmorBaseHook.renderGlintHook(entity)) {
            return;
        }
        */
        InsnList insnlist = new InsnList();
        insnlist.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insnlist.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "io/prplz/steev/hook/LayerArmorBaseHook", "renderGlintHook", "(Lnet/minecraft/entity/EntityLivingBase;)Z"));
        LabelNode label = new LabelNode();
        insnlist.add(new JumpInsnNode(Opcodes.IFEQ, label));
        insnlist.add(new InsnNode(Opcodes.RETURN));
        insnlist.add(label);
        method.instructions.insert(insnlist);
    }
}
