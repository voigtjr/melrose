package com.melrose;

import com.google.common.base.Objects;
import com.google.common.collect.*;

import java.io.*;
import java.util.List;
import java.util.Map;
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
        List<Edge> ein = Lists.newLinkedList();

        String line;
        while ((line = r.readLine()) != null) {
            Matcher m = inp.matcher(line);
            if (m.matches()) {
                String a = m.group(1);
                String b = m.group(2);
                ein.add(new Edge(a, b));
            }
        }

        return new Graph(ein);
    }

    private final Map<String, List<String>> edges;
    private final List<String> EMPTY_LIST = Lists.newArrayListWithCapacity(0);

    private Graph(Iterable<Edge> edges) {
        // collect edges to assemble dependency mapping
        // make everything immutable so it can be returned directly

        Map<String, ImmutableList.Builder<String>> collected = Maps.newHashMap();

        for (Edge e : edges) {
            ImmutableList.Builder<String> b = collected.computeIfAbsent(e.l, key -> ImmutableList.builder());
            b.add(e.r);
        }

        ImmutableMap.Builder<String, List<String>> mb = ImmutableMap.builder();
        for (Map.Entry<String, ImmutableList.Builder<String>> e : collected.entrySet()) {
            mb.put(e.getKey(), e.getValue().build());
        }

        this.edges = mb.build();
    }

    public List<String> getDeps(String node) {
        List<String> ret = edges.get(node);
        if (ret == null) {
            return EMPTY_LIST;
        }
        return ret;
    }

}
