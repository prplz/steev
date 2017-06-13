package io.prplz.steev.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class CapeUtils1Patch {

    public static void patchConstructor(MethodNode method) {
        AbstractInsnNode insn = method.instructions.getFirst();
        while (insn != null) {
            if (insn.getOpcode() == Opcodes.PUTFIELD) {
                FieldInsnNode fieldInsn = (FieldInsnNode) insn;
                if (fieldInsn.name.equals("val$thePlayer")) {
                    method.instructions.insertBefore(insn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "wn", "e_", "()Ljava/lang/String;")); // EntityPlayer.getName
                    fieldInsn.desc = "Ljava/lang/String;";
                    return;
                }
            }
            insn = insn.getNext();
        }
    }

    public static void patchSkinAvailable(MethodNode method) {
        method.instructions.clear();
        method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        method.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, "CapeUtils$1", "val$thePlayer", "Ljava/lang/String;"));
        method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        method.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, "CapeUtils$1", "val$rl", "Ljy;"));
        method.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "io/prplz/steev/hook/CapeUtils1Hook", "skinAvailable", "(Ljava/lang/String;Ljy;)V"));
        method.instructions.add(new InsnNode(Opcodes.RETURN));
    }

}
