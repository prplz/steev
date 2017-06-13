package io.prplz.steev;

import org.objectweb.asm.tree.FieldNode;

import java.util.function.Consumer;

public class FieldTransformer {

    private final String name;
    private final Consumer<FieldNode> consumer;

    public FieldTransformer(String name, Consumer<FieldNode> consumer) {
        this.name = name;
        this.consumer = consumer;
    }

    public boolean transform(FieldNode node) {
        if (node.name.equals(name)) {
            consumer.accept(node);
            return true;
        }
        return false;
    }
}
