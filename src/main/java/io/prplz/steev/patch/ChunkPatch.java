package io.prplz.steev.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ChunkPatch {

    public static void patchFillChunk(MethodNode method) {
        /*
        if (ChunkHook.fillChunkHook(this, data, mask, full)) {
            return;
        }
        */
        InsnList insnlist = new InsnList();
        insnlist.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnlist.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insnlist.add(new VarInsnNode(Opcodes.ILOAD, 2));
        insnlist.add(new VarInsnNode(Opcodes.ILOAD, 3));
        insnlist.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "io/prplz/steev/hook/ChunkHook", "fillChunkHook", "(Lnet/minecraft/world/chunk/Chunk;[BIZ)Z"));
        LabelNode label = new LabelNode();
        insnlist.add(new JumpInsnNode(Opcodes.IFNE, label));
        insnlist.add(new InsnNode(Opcodes.RETURN));
        insnlist.add(label);
        method.instructions.insert(insnlist);
    }
}
