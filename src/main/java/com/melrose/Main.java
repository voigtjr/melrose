package com.melrose;

import com.melrose.dfs.Dfs;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: melrose input_file");
            return;
        }

        AsciiOut ao = new AsciiOut();
        try {
            Graph g = Graph.loadFromFile(args[0]);
            Dfs dfs = Dfs.makeDfs(g);
            dfs.traverseForeverFrom("A", ao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
