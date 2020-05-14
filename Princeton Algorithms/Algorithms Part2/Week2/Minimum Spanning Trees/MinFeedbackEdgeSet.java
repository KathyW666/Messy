import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code MinFeedbackEdgeSet} class represents a data type for computing
 * the <em>minimum-weight feedback edge set</em> in an edge-weighted graph.
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

public class MinFeedbackEdgeSet {
    private HashSet<Edge> minFeedbackEdgeSet;
    public MinFeedbackEdgeSet(EdgeWeightedGraph G) {
        minFeedbackEdgeSet = new HashSet<>();
        for (Edge e : G.edges()) {
            minFeedbackEdgeSet.add(e);
        }
        PrimMaxST maxST = new PrimMaxST(G);
        for (Edge e : maxST.edges()) {
            minFeedbackEdgeSet.remove(e);
        }
    }

    public Set<Edge> getMinFeedbackEdgeSet() {
        return minFeedbackEdgeSet;
    }

    /**
     * UT
     * @param args
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        MinFeedbackEdgeSet minFbES = new MinFeedbackEdgeSet(G);
        for (Edge e : minFbES.getMinFeedbackEdgeSet()) {
            System.out.println(e);
        }
    }
}
