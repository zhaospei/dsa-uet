import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    // constructor takes a WordNet object
    private final WordNet wordNet;
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }       

    // given an array of WordNet nouns, return an outcast 
    public String outcast(String[] nouns) {
        int pos = -1;
        int maxLen = -1;
        int[] len = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) 
            for (int j = 0; j < nouns.length; j++) {
                len[i] += wordNet.distance(nouns[i], nouns[j]);
            }
   /*      for (int i = 0; i < nouns.length; i++) 
            StdOut.print(len[i] + " ");
        StdOut.print("\n"); */
        for (int i = 0; i < nouns.length; i++) {
            if (len[i] > maxLen) {
                maxLen = len[i];
                pos = i;
            }
        }
        return nouns[pos];
    }   

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}