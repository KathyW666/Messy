import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private Digraph G;
    private int V;
    private SAP SAP;
    private Map<String, List<Integer>> nouns;
    private Map<Integer, String> synsetsMap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new NullPointerException();
        this.nouns = new HashMap<>();
        this.synsetsMap = new HashMap<>();
        initSynsets(synsets);
        initHypernyms(hypernyms);
        validateDigraph(G);
        SAP = new SAP(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validateNoun(nounA);
        validateNoun(nounB);
        return SAP.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        validateNoun(nounA);
        validateNoun(nounB);
        return synsetsMap.get(SAP.ancestor(nouns.get(nounA), nouns.get(nounB)));
    }

    private void validateNoun(String noun) {
        if (!isNoun(noun))
            throw new IllegalArgumentException(noun + " is not a WordNet noun!");
    }

    // The input to the constructor corresponds to a rooted DAG
    private void validateDigraph(Digraph G) {
        DirectedCycle directedCycle = new DirectedCycle(G);
        if (directedCycle.hasCycle()) throw new IllegalArgumentException("Digraph has cycle!");
    }

    // Initial Synsets, store in 2 maps
    // 1) id - synset, <1, "C-reactive_protein CRP">
    // 2) word - id list, <"wind", [81193, 81194, 81195, 81196]>
    private void initSynsets(String synsets) {
        In in = new In(synsets);
        int V = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] realms = line.split(",");
            int id = Integer.valueOf(realms[0]);
            synsetsMap.put(id, realms[1]);
            String[] words = realms[1].split(" ");
            for (int i = 0; i < words.length; i++) {
                List<Integer> list;
                if (nouns.containsKey(words[i])) list = nouns.get(words[i]);
                else list = new ArrayList<>();
                list.add(id);
                nouns.put(words[i], list);
            }
            V++;
        }
        this.V = V;
    }

    // construct the Digraph
    private void initHypernyms(String hypernyms) {
        In in = new In(hypernyms);
        G = new Digraph(this.V);
        while (in.hasNextLine()) {
            String[] vIds = in.readLine().split(",");
            int v = Integer.valueOf(vIds[0]);
            for (int i = 1; i < vIds.length; i++) {
                int w = Integer.valueOf(vIds[i]);
                G.addEdge(v, w);
            }
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String synsets = args[0];
        String hypernyms = args[1];
        WordNet wordNet = new WordNet(synsets, hypernyms);
        while (!StdIn.isEmpty()) {
            String nounA = StdIn.readString();
            String nounB = StdIn.readString();
            int distance = wordNet.distance(nounA, nounB);
            String ancestorSynset = wordNet.sap(nounA, nounB);
            StdOut.printf("distance = %d, ancestor synset = %s\n", distance, ancestorSynset);
        }
    }
}