package io.prplz.steev.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class GuiIngameForgePatch {

    public static void patchRenderHealth(MethodNode method) {
        AbstractInsnNode insn = method.instructions.getFirst();
        while (insn != null) {
            if (insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                MethodInsnNode methodInsn = (MethodInsnNode) insn;
                if (methodInsn.name.equals("b") && methodInsn.desc.equals("(IIIIII)V")) { // drawTexturedModalRect
                    methodInsn.setOpcode(Opcodes.INVOKESTATIC);
                    methodInsn.owner = "io/prplz/steev/hook/GuiIngameForgeHook";
                    methodInsn.name = "drawTexturedModalRectHook";
                    methodInsn.desc = "(Lnet/minecraftforge/client/GuiIngameForge;IIIIII)V";
                }
            }
            insn = insn.getNext();
        }
    }
}
