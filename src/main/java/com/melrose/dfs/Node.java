package com.melrose.dfs;

/**
 * Created by voigtjr on 7/26/16.
 */
public interface Node {
    /**
     * @return Node identifier
     */
    String getNode();

    /**
     * @return Dependencies of node
     */
    boolean hasDeps();
}
