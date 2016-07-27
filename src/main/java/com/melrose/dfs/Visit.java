package com.melrose.dfs;

import com.melrose.Graph;

import java.util.List;

/**
 * Created by voigtjr on 7/25/16.
 */
public interface Visit {
    /**
     * Called on each node during search.
     *
     * Cycles or other search abbreviations can be stopped by returning YES (please stop visiting dependencies).
     *
     * @param g Graph representation
     * @param node The currently visited node
     * @param depth The current path from root (not including node)
     * @return YES to stop the search
     */
    VisitStop visit(Graph g, String node, List<Node> depth);
}
