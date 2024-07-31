package infinituum.labellingcontainers.utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public final class ContainerSet {
    private final Set<String> set;

    public ContainerSet() {
        this.set = new HashSet<>();
    }

    public Set<String> toSet() {
        return set;
    }

    public ContainerSet add(String namespace, @NotNull String path) {
        if (namespace == null) {
            namespace = "minecraft";
        }

        this.set.add(namespace + ":" + path);

        return this;
    }
}
