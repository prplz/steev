package io.prplz.steev;

import org.objectweb.asm.tree.MethodNode;

import java.util.function.Consumer;

class MethodTransformer {

    private final String name;
    private final String desc;
    private final Consumer<MethodNode> consumer;

    MethodTransformer(String name, String desc, Consumer<MethodNode> consumer) {
        this.name = name;
        this.desc = desc;
        this.consumer = consumer;
    }

    public boolean transform(MethodNode node) {
        if (node.name.equals(name) && node.desc.equals(desc)) {
            consumer.accept(node);
            return true;
        }
        return false;
    }
}
