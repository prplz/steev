package io.prplz.steev.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class MinecraftPatch {

    public static void patchIsFramerateLimitBelowMax(MethodNode method) {
        /*
        if (!DisplayActiveListener.isActive) {
            return true;
        }
        */
        InsnList insnlist = new InsnList();
        insnlist.add(new FieldInsnNode(Opcodes.GETSTATIC, "io/prplz/steev/listener/DisplayActiveListener", "isActive", "Z"));
        LabelNode label = new LabelNode();
        insnlist.add(new JumpInsnNode(Opcodes.IFNE, label));
        insnlist.add(new InsnNode(Opcodes.ICONST_1));
        insnlist.add(new InsnNode(Opcodes.IRETURN));
        insnlist.add(label);
        method.instructions.insert(insnlist);
    }

    public static void patchGetLimitFramerate(MethodNode method) {
        /*
        if (!DisplayActiveListener.isActive) {
            return 30;
        }
        */
        InsnList insnlist = new InsnList();
        insnlist.add(new FieldInsnNode(Opcodes.GETSTATIC, "io/prplz/steev/listener/DisplayActiveListener", "isActive", "Z"));
        LabelNode label = new LabelNode();
        insnlist.add(new JumpInsnNode(Opcodes.IFNE, label));
        insnlist.add(new LdcInsnNode(30));
        insnlist.add(new InsnNode(Opcodes.IRETURN));
        insnlist.add(label);
        method.instructions.insert(insnlist);
    }
}
