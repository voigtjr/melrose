package com.melrose;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.melrose.dfs.*;

import java.util.List;
import java.util.Set;

/**
 * Print the graph with fancy ascii output.
 *
 * Created by voigtjr on 7/26/16.
 */
public class AsciiOut implements Visit {
    private final Set<String> seen = Sets.newHashSet();

    private static final String TOK_0 = "  ";
    private static final String TOK_1 = "| ";
    private static final String END_0 = "|_";
    private static final String END_1 = "\\_";
    private static final String CYCLE = "...";

    private String render(String node, List<Node> depth, boolean cycle) {
        StringBuilder sb = new StringBuilder();
        if (!depth.isEmpty()) {
            for (Node dn : depth.subList(0, depth.size() - 1)) {
                if (dn.hasDeps()) {
                    sb.append(TOK_1);
                } else {
                    sb.append(TOK_0);
                }
                sb.append(' ');
            }

            Node dn = depth.get(depth.size() - 1);
            if (dn.hasDeps()) {
                sb.append(END_0);
            } else {
                sb.append(END_1);
            }
            sb.append(' ');
        }

        sb.append(node);
        sb.append(' ');
        if (cycle) {
            sb.append("...");
        }

        return sb.toString();
    }

    @Override
    public VisitStop visit(Graph g, String node, List<Node> depth) {
        boolean visited = !seen.add(node);
        List<String> deps = g.getDeps(node);
        if (visited) {
            if (!deps.isEmpty()) {
                List<String> path = Lists.transform(depth, d -> d.getNode());
                if (path.contains(node)) {
                    // Cycle
                    System.out.println(render(node, depth, true));
                    return VisitStop.YES;
                }
            }
        }
        System.out.println(render(node, depth, false));
        return VisitStop.NO;
    }
}
