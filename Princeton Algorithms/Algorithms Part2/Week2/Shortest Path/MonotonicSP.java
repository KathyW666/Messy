import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.In;

/**
 * The {@code MonotonicSP} class represents a data type for computing
 * the <em>monotonic shortest path</em> in an directed edge-weighted graph.
 * The monotonic shortest path can be either ascending or decreasing.
 *
 * This code is for the Questions of Online course:
 * <i>Algorithms, Part II on Coursera, Shortest Path</i>,
 * and uses the code in <i>algs4.jar</i> provided by the official resources.
 *
 * For detailed documentation,
 *  see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a>
 *
 * @author Kathy Wang
 * 2020,5,11
 */

public class MonotonicSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;

    public MonotonicSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        pq = new IndexMinPQ<>(G.V());

        // find in ascending order
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            relax(G, v,1);
        }
        // find in decreasing order
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            relax(G, v,-1);
        }
    }


    // The path is definitely the shortest but many vertex maybe unreachable in this way.
    private void relax(EdgeWeightedDigraph G, int v, int ascend) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            DirectedEdge lastEdge = edgeTo[v];
            if (lastEdge == null || (e.weight() * ascend > lastEdge.weight() * ascend) && distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.changeKey(w, distTo[w]);
                else pq.insert(w, distTo[w]);
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public Stack<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public boolean hasPathTo(int v) {
        return distTo[v] != Double.POSITIVE_INFINITY;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        MonotonicSP sp = new MonotonicSP(G, s);
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (DirectedEdge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
    }

}
