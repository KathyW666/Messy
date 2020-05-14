import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;

/**
 * The {@code IsEInMST} class represents a data type for judging if an
 * given edge e is in the minimum spanning tree of an edge-weighted graph.
 *
 * This code is for the Questions of Online course:
 * <i>Algorithms, Part II on Coursera, Minimum Spanning Trees</i>,
 * and uses the code in <i>algs4.jar </i>provided by the official resources.
 *
 * For detailed documentation,
 *  see <a href="https://algs4.cs.princeton.edu/43mst">Section 4.3</a>
 *
 * @author K. Wang
 * 2020,3,5
 */
public class IsEInMST {
    private boolean[] marked;
    private double weightE;
    private int v, w; // vertexs of the given edge e;
    public IsEInMST(EdgeWeightedGraph G, Edge e) {
        marked = new boolean[G.V()];
        weightE = e.weight();
        v = e.either();
        w = e.other(v);
        dfs(G, v);
    }

    public boolean inMST() {
        return !marked[w];
    }

    private void dfs(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        if (marked[w]) return;
        for (Edge e : G.adj(v)) {
            if(e.weight() < weightE && !marked[e.other(v)]) {
                dfs(G, e.other(v));
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        for (Edge e : G.edges()) {
            IsEInMST isEInMST = new IsEInMST(G, e);
            System.out.println(e.toString().substring(0,4) + isEInMST.inMST());
        }
    }
}
