package com.melrose.dfs;

import com.google.common.collect.ImmutableList;
import com.melrose.Graph;

import java.util.*;

/**
 * Created by voigtjr on 7/25/16.
 */
public class Dfs {
    public static Dfs makeDfs(Graph g) {
        return new Dfs(g);
    }

    private final Graph g;
    private final Deque<DfsNode> stack = new ArrayDeque<>();

    private Dfs(Graph g) {
        this.g = g;
    }

    private void pushNext(DfsNode node) {
        stack.push(DfsNode.popFirst(node));

        String child = node.firstDep();
        stack.push(DfsNode.make(child, g.getDeps(child)));
    }

    /**
     * Traverse the graph starting at source.
     *
     * If there are cycles, the visit callback must detect them using the state that it is given and stop the search
     * with VisitStop.YES
     *
     * @param source Starting point (node id)
     * @param visit Callback on each node visited
     */
    public void traverseForeverFrom(String source, Visit visit) {
        stack.push(DfsNode.make(source, g.getDeps(source)));

        while (!stack.isEmpty()) {
            DfsNode node = stack.pop();
            VisitStop vs = visit.visit(g, node.getNode(), ImmutableList.copyOf(stack.descendingIterator()));
            if (vs == VisitStop.NO && node.hasDeps()) {
                pushNext(node);
            } else {
                while (!stack.isEmpty()) {
                    DfsNode top = stack.pop();
                    if (top.hasDeps()) {
                        pushNext(top);
                        break;
                    }
                }
            }
        }
    }
}
