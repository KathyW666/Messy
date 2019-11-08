import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class SAP {
    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new NullPointerException();
        this.G = G;
    }

    private void validateVertex(int v) {
        if (v < 0 || v > G.V())
            throw new IllegalArgumentException("Vertex " + v + " is outside its prescribed range!");
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) throw new IllegalArgumentException("argument is null");
        for (int v : vertices) {
            if (v < 0 || v >= G.V()) {
                throw new IllegalArgumentException("vertex " + v + "is outside its prescribed range!");
            }
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return bfsSearch(v, w)[0];
    }


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return bfsSearch(v, w)[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return bfsSearch(v, w)[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return bfsSearch(v, w)[1];
    }

    private int[] bfsSearch(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        if (v == w) return new int[]{0, v};
        BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(G, w);
        int[] res = new int[2];
        res[0] = Integer.MAX_VALUE;
        res[1] = -1;
        List<Integer> ancestorList = new ArrayList<>();
        for (int i = 0; i < G.V(); i++) {
            if (pathV.hasPathTo(i) && pathW.hasPathTo(i)) {
                ancestorList.add(i);
            }
        }
        if (ancestorList.isEmpty()) {
            res[0] = -1;
            return res;
        }
        for (Integer a : ancestorList) {
            int currentLen = pathV.distTo(a) + pathW.distTo(a);
            if (currentLen < res[0]) {
                res[0] = currentLen;
                res[1] = a;
            }
        }
        return res;
    }

    private int[] bfsSearch(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);
        BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(G, w);
        int[] res = new int[2];
        res[0] = Integer.MAX_VALUE;
        res[1] = -1;
        List<Integer> ancestorList = new ArrayList<>();
        for (int i = 0; i < G.V(); i++) {
            if (pathV.hasPathTo(i) && pathW.hasPathTo(i)) {
                ancestorList.add(i);
            }
        }
        if (ancestorList.isEmpty()) {
            res[0] = -1;
            return res;
        }
        for (Integer a : ancestorList) {
            int currentLen = pathV.distTo(a) + pathW.distTo(a);
            if (currentLen < res[0]) {
                res[0] = currentLen;
                res[1] = a;
            }
        }
        return res;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
