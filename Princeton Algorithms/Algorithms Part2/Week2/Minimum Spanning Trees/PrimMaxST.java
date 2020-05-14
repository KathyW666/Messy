import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMaxPQ;
import edu.princeton.cs.algs4.Queue;

/**
 * The {@code PrimMaxST} class represents a data type, which extends from
 * {@code PrimMST} in <i>algs4.jar</i>, for computing
 * the <em>max spanning tree</em> in an edge-weighted graph.
 *
 * This code is for the Questions of Online course:
 * <i>Algorithms, Part II on Coursera, Minimum Spanning Trees</i>,
 * and uses the code in <i>algs4.jar</i> provided by the official resources.
 *
 * For detailed documentation,
 *  see <a href="https://algs4.cs.princeton.edu/43mst">Section 4.3</a>
 *
 * @author K. Wang
 * 2020,3,6
 */
public class PrimMaxST {
    private Edge[] edgeTo; // edgeTo[v] = largest edge from tree vertex to non-tree vertex
    private double[] distTo; // distTo[v] = weight of largest such edge
    private boolean[] marked; // mark[v] = true if v on tree
    private IndexMaxPQ<Double> pq;

    public PrimMaxST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMaxPQ<>(G.V());
        for (int v = 0 ; v < G.V(); v++) {
            distTo[v] = Double.NEGATIVE_INFINITY;
        }

        for (int v= 0; v < G.V(); v++) {
            if (!marked[v]) prim(G, v);
        }
    }

    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while(!pq.isEmpty()) {
            int v = pq.delMax();
            scan(G, v);
        }
    }

    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight() > distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.increaseKey(w, distTo[w]);
                else pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> maxST = new Queue<>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                maxST.enqueue(e);
            }
        }
        return maxST;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }

    /**
     * UT
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        PrimMaxST maxST = new PrimMaxST(G);
        for (Edge e : maxST.edges()) {
            System.out.println(e);
        }
        System.out.println(maxST.weight());
    }
}
