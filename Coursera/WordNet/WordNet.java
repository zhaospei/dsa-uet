import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;

public class WordNet {
    private HashMap<, Int> 
    private Digraph worldNet;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        In in = new In(synsets);
        while (!in.hasNextLine()) {
            in.readAllStrings()
        }
    }
 
    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return null;
    }
 
    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return false;
    }
 
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (isNoun(nounA) == false || isNoun(nounB) == false) 
            throw new IllegalArgumentException();
        return 0;
    }
 
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (isNoun(nounA) == false || isNoun(nounB) == false) 
            throw new IllegalArgumentException();
        return null;
    }
 
    // do unit testing of this class
    public static void main(String[] args) {

    }
}