import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.In;

/**
 * The {@code EdgeSkippableSP} class represents a data type for computing
 * the <em>shortest path with one skippable edge</>
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

public class EdgeSkippableSP {
    private DijkstraSP[] sps;
    private double minPathWeight;
    private Queue<DirectedEdge> skippableSP;

    public EdgeSkippableSP(EdgeWeightedDigraph G, int s, int t) {
        sps = new DijkstraSP[G.V()];
        skippableSP = new Queue<>();
        for (int v = 0; v < G.V(); v++) {
            sps[v] = new DijkstraSP(G, v);
        }
        minPathWeight = Double.POSITIVE_INFINITY;
        DirectedEdge minE = null;
        for (DirectedEdge e : G.edges()) {
            int v = e.from(), w = e.to();
            if (sps[s].distTo(v) + sps[w].distTo(t) < minPathWeight) {
                minPathWeight = sps[s].distTo(v) + sps[w].distTo(t);
                minE = e;
            }
        }
        for (DirectedEdge e : sps[s].pathTo(minE.from())) {
            skippableSP.enqueue(e);
        }
        skippableSP.enqueue(minE);
        for (DirectedEdge e : sps[minE.to()].pathTo(t)) {
            skippableSP.enqueue(e);
        }
        minPathWeight += minE.weight();
    }

    public Iterable<DirectedEdge> getPath() {
        return skippableSP;
    }

    public double getMinPathWeight() {
        return minPathWeight;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = 0, t = 2;
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        EdgeSkippableSP sp = new EdgeSkippableSP(G, s, t);
        if (sp.getPath() != null) {
            StdOut.printf("%d to %d (%.2f)  ", s, t, sp.getMinPathWeight());
            for (DirectedEdge e : sp.getPath()) {
                StdOut.print(e + "   ");
            }
            StdOut.println();
        } else {
            StdOut.printf("%d to %d         no path\n", s, t);
        }

    }
}
