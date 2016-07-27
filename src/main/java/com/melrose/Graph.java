package com.melrose;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by voigtjr on 7/22/16.
 */
public class Graph {
    static final Pattern inp = Pattern.compile("(\\w+)->(\\w+)");

    private static class Edge {
        private final String l;
        private final String r;

        private Edge(String l, String r) {
            this.l = l;
            this.r = r;
        }

        @Override
        public String toString() {
            return l + "->" + r;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return Objects.equal(l, edge.l) &&
                    Objects.equal(r, edge.r);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(l, r);
        }
    }

    public static Graph loadFromFile(String filename) throws FileNotFoundException, IOException {
        BufferedReader r = new BufferedReader(new FileReader(filename));
        List<String> nin = Lists.newLinkedList();
        List<Edge> ein = Lists.newLinkedList();

        String line;
        while ((line = r.readLine()) != null) {
            Matcher m = inp.matcher(line);
            if (m.matches()) {
                String a = m.group(1);
                String b = m.group(2);
                nin.add(a);
                nin.add(b);
                ein.add(new Edge(a, b));
            }
        }

        return new Graph(nin, ein);
    }

    private final Set<String> nodes;
    private final Set<Edge> edges;

    private Graph(Iterable<String> nodes, Iterable<Edge> edges) {
        this.nodes = Sets.newHashSet(nodes);
        this.edges = Sets.newHashSet(edges);
    }

    public Set<String> getNodes() {
        return ImmutableSet.copyOf(nodes);
    }

    public Set<Edge> getEdges() {
        return ImmutableSet.copyOf(edges);
    }

    public List<String> getDeps(String node) {
        // TODO use a better representation
        List<String> deps = Lists.newArrayList();
        for (Edge e : edges) {
            if (e.l.equals(node)) {
                deps.add(e.r);
            }
        }
        return deps;
    }

}
