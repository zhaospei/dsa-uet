import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    private final Digraph G;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.G = new Digraph(G);
    }
 
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return temp(v, w)[0];
    }
 
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return temp(v, w)[1];
    }

    private int[] temp(int v, int w) {
        int[] ans = new int[2];
        int minLen = Integer.MAX_VALUE;
        int pos = -1;
        BreadthFirstDirectedPaths graphA = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths graphB = new BreadthFirstDirectedPaths(G, w);
        for (int i = 0; i < G.V(); i++) {
            if (graphA.hasPathTo(i) && graphB.hasPathTo(i)) {
                int curLen = graphA.distTo(i) + graphB.distTo(i);
                if (curLen < minLen) {
                    minLen = curLen;
                    pos = i;
                }
            }
        }
        if (minLen == Integer.MAX_VALUE) minLen = -1;
        ans[0] = minLen;
        ans[1] = pos;
        return ans;
    }
 
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return temp(v, w)[0];
    }
  
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return temp(v, w)[1];
    }

    private int[] temp(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        int cnt = 0;
        for (Integer x: v) cnt++;
        if (cnt == 0) throw new IllegalArgumentException("zero vertices");
        cnt = 0;
        for (Integer x: w) cnt++;
        if (cnt == 0) throw new IllegalArgumentException("zero vertices");
        int[] ans = new int[2];
        int minLen = Integer.MAX_VALUE;
        int pos = -1;
        BreadthFirstDirectedPaths graphA = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths graphB = new BreadthFirstDirectedPaths(G, w);
        for (int i = 0; i < G.V(); i++) {
            if ((graphA.hasPathTo(i) && graphB.hasPathTo(i))) {
                int curLen = graphA.distTo(i) + graphB.distTo(i);
                if (curLen < minLen) {
                    minLen = curLen;
                    pos = i;
                }
            }
        }
        if (minLen == Integer.MAX_VALUE) minLen = -1;
        ans[0] = minLen;
        ans[1] = pos;
        return ans;
    }
 
    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
 }
 