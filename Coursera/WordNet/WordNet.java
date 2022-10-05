import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashMap;

public class WordNet {
    private final HashMap<Integer, String> mapA; 
    private final HashMap<String, Bag<Integer>> mapB; 
    private final Digraph wordNet;
    private final SAP sapGraph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        mapA = new HashMap<Integer, String>();
        mapB = new HashMap<String, Bag<Integer>>();
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String[] string = in.readLine().split(",");
            int id = Integer.parseInt(string[0]);
            String[] words = string[1].split("\\s+");
            mapA.put(id, string[1]);
           // StdOut.print(id);
            for (String word: words) {
               // StdOut.print(word);
                if (!mapB.containsKey(word)) {
                    mapB.put(word, new Bag<Integer>());
                }
                mapB.get(word).add(id);
            }
            // StdOut.print("\n");
        }
        
        wordNet = new Digraph(mapA.size());
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] ver = in.readLine().split(",");
            if (ver.length < 2) continue;
            int u = Integer.parseInt(ver[0]);
            for (int i = 1; i < ver.length; i++) {
                int v = Integer.parseInt(ver[i]);
                wordNet.addEdge(u, v);
            }
        }
        sapGraph = new SAP(wordNet);
        if (!checkWordNet()) throw new IllegalArgumentException();
    }
 
    private boolean checkWordNet() {
        int cnt = 0;
        for (int i = 0; i < wordNet.V(); i++) {
            if (!wordNet.adj(i).iterator().hasNext()) cnt++;
        }
        if (cnt != 1) return false;
        DirectedCycle cycleMap = new DirectedCycle(wordNet);
        if (cycleMap.hasCycle()) return false;
        return true;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return mapB.keySet();
    }
 
    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return mapB.containsKey(word);
    }
 
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) 
            throw new IllegalArgumentException();
        
        return sapGraph.length(mapB.get(nounA), mapB.get(nounB));
    }
 
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) 
            throw new IllegalArgumentException();
        
        return mapA.get(sapGraph.ancestor(mapB.get(nounA), mapB.get(nounB)));
    }
 
    // do unit testing of this class
    public static void main(String[] args) {    
        WordNet word = new WordNet("synsets.txt", "hypernyms.txt");
        StdOut.println(word.isNoun("a"));
        StdOut.println(word.isNoun("z"));
        StdOut.println(word.distance("a", "b"));
        StdOut.println(word.sap("a", "b"));
        StdOut.println(word.distance("a", "f"));
        StdOut.println(word.sap("a", "f"));
    }
}