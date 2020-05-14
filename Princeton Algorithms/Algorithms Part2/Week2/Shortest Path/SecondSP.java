import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.In;

/**
 * The {@code SecondSP} class represents a data type for computing
 * the <em>second shortest path</em> in an directed edge-weighted graph.
 * Assume that there is no negative weighted edge.
 * <p>
 * This code is for the Questions of Online course:
 * <i>Algorithms, Part II on Coursera, Shortest Path</i>,
 * and uses the code in <i>algs4.jar</i> provided by the official resources.
 * <p>
 * For detailed documentation,
 * see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a>
 *
 * @author Kathy Wang
 * 2020,5,12
 */

public class SecondSP {
    private int s, t;
    private double distTo[];
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;
    private DirectedEdge delEdge;

    /**
     * Computes the second shortest path from the source vertex {@code s} to
     * the sink vertex {@code t} in the edge-weighted digraph {@code G}.
     * Utilizes DijstraSP to compute the shortest path from s to v firstly.
     *
     * @param G the edge-weighted digraph
     * @param s the source vertex
     * @param t the sink vertex
     */
    public SecondSP(EdgeWeightedDigraph G, int s, int t) {
        this(G, (Stack<DirectedEdge>) new DijkstraSP(G, s).pathTo(t), s, t);
    }

    /**
     * This is an optimized version.
     * The loop break as soon as it reach the vertex t.
     *
     * @param  G the edge-weighted digraph
     * @param  s the source vertex
     * @param  t the sink vertex
     */
//    public SecondSP(EdgeWeightedDigraph G, int s, int t) {
//        this.s = s;
//        this.t = t;
//        int V = G.V();
//        edgeTo = new DirectedEdge[V];
//        distTo = new double[V];
//        for (int v = 0; v < V; v++) {
//            distTo[v] = Double.POSITIVE_INFINITY;
//        }
//        distTo[s] = 0.0;
//        pq = new IndexMinPQ<>(V);
//        pq.insert(s,0.0);
//        while (!pq.isEmpty()) {
//            int v = pq.delMin();
//            if (v == t) {
//                delEdge = edgeTo[v];
//                break;
//            }
//            relax(G, v);
//        }
//        while (!pq.isEmpty()) pq.delMin();
//        Arrays.fill(edgeTo, null);
//        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
//        distTo[s] = 0.0;
//        pq.insert(s, 0.0);
//        while(!pq.isEmpty()) {
//            int v = pq.delMin();
//            if (v == t) break;
//            relax(G, v);
//        }
//    }

    /**
     * If the shorest path has been given, no need to run DijkstraSP to compute it.
     *
     * @param G edge-weighted digraph
     * @param P the shortest path
     * @param s the source vertex
     * @param t the sink vertex
     */
    public SecondSP(EdgeWeightedDigraph G, Stack<DirectedEdge> P, int s, int t) {
        this.s = s;
        this.t = t;
        int V = G.V();
        edgeTo = new DirectedEdge[V];
        distTo = new double[V];
        for (int v = 0; v < V; v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        pq = new IndexMinPQ<>(V);
        while (!P.isEmpty()) {
            delEdge = P.pop();
        }
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            if (v == t) break;
            relax(G, v);
        }

    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            if (e == delEdge) continue;
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.changeKey(w, distTo[w]);
                else pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<DirectedEdge> pathTo() {
        if (!hasSecondPath()) return null;
        Stack<DirectedEdge> stack = new Stack<>();
        for (DirectedEdge e = edgeTo[t]; e != null; e = edgeTo[e.from()]) {
            stack.push(e);
        }
        return stack;
    }

    public boolean hasSecondPath() {
        return distTo[t] != Double.POSITIVE_INFINITY;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        SecondSP ssp = new SecondSP(G, 0, 2);
        for (DirectedEdge e : ssp.pathTo()) {
            StdOut.print(e + "   ");
        }
        StdOut.println();
    }
}
