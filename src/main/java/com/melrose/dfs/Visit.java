package com.melrose.dfs;

import com.melrose.Graph;

import java.util.List;

/**
 * Created by voigtjr on 7/25/16.
 */
public interface Visit {
    VisitStop visit(Graph g, String node, List<Node> depth);
}
