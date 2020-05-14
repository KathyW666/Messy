import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.In;

/**
 * The {@code KruskalMaxST} class represents a data type, which extends from
 * {@code KruskalMST} in <i>algs4.jar</i>, for computing
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
public class KruskalMaxST {
    private double weight; // weight of MaxST
    private Queue<Edge> maxST = new Queue<>(); // edges in MaxST

    public KruskalMaxST(EdgeWeightedGraph G) {
        MaxPQ<Edge> pq = new MaxPQ<>();
        for (Edge e : G.edges()) {
            pq.insert(e);
        }

        // greedy algorithm, need to use union-find to union all the forests
        UF uf = new UF(G.V());
        while (!pq.isEmpty() && maxST.size() < G.V() - 1) {
            Edge e = pq.delMax();
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                maxST.enqueue(e);
                weight += e.weight();
            }
        }
    }

    public Iterable<Edge> edges() {return maxST;}

    public double getWeight() { return weight;};

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        KruskalMaxST maxST = new KruskalMaxST(G);
        for (Edge e : maxST.edges()) {
            System.out.println(e);
        }
        System.out.println(maxST.getWeight());
    }
}
