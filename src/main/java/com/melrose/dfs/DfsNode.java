package com.melrose.dfs;

import java.util.List;

/**
 * Created by voigtjr on 7/25/16.
 */
public class DfsNode implements Node {
    static DfsNode make(String node, List<String> deps) {
        return new DfsNode(node, deps);
    }

    static DfsNode popFirst(DfsNode dn) {
        return new DfsNode(dn.node, dn.deps.subList(1, dn.deps.size()));
    }

    private final String node;
    private final List<String> deps;

    private DfsNode(String node, List<String> deps) {
        this.node = node;
        this.deps = deps;
    }

    @Override
    public String getNode() {
        return node;
    }

    @Override
    public boolean hasDeps() {
        return !deps.isEmpty();
    }

    String firstDep() {
        return deps.get(0);
    }

    @Override
    public String toString() {
        return node + "(" + String.join(", ", deps) + ")";
    }
}
