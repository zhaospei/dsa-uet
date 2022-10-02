import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Stack<Board> solveBoards;
    private boolean solvable;

    private class Node implements Comparable<Node> {
        private Board board;
        private Node preNode;
        private int manhattan;
        private int moves;

        public Node(Board board, Node preNode) {
            this.board = board;
            this.preNode = preNode;
            this.manhattan = this.board.manhattan();
            if (preNode == null) moves = 0;
            else moves = preNode.moves + 1;
        }

        @Override 
        public int compareTo(Node that) {
            int diff = (this.manhattan + this.moves) - (that.manhattan + that.moves);
            if (diff == 0) return this.manhattan - that.manhattan;
            else return diff;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        solveBoards = new Stack<>();
        solvable = false;
        MinPQ<Node> nodes = new MinPQ<>();

        nodes.insert(new Node(initial, null));
        nodes.insert(new Node(initial.twin(), null));
        while (!nodes.min().board.isGoal()) {
            Node node = nodes.delMin();
            for (Board board: node.board.neighbors()) {
                if (node.preNode != null && node.preNode.board.equals(board)) continue;
                nodes.insert(new Node(board, node));
            }
        }
        Node curNode = nodes.min();
        while (curNode.preNode != null) {
            solveBoards.push(curNode.board);
            curNode = curNode.preNode;
        } 
        solveBoards.push(curNode.board);

        if (curNode.board.equals(initial)) solvable = true;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solvable) return solveBoards.size() - 1;
        else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) return solveBoards;
        else return null;
    }

    // test client (see below) 
    public static void main(String[] args) {
        // create initial board from file
        In in = new In("puzzle04.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}