import edu.princeton.cs.algs4.Stack;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] tiles;
    private int n;

    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = copy(tiles);
    }

    private int[][] copy(int[][] tilescopy) {
        int[][] ccopy = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                ccopy[i][j] = tilescopy[i][j];
        return ccopy;

    }

    private int[][] swap(int[][] tilescopy, int x1, int y1, int x2, int y2) {
        int[][] sswap = copy(tilescopy);
        sswap[x1][y1] = sswap[x1][y1] + sswap[x2][y2];
        sswap[x2][y2] = sswap[x1][y1] - sswap[x2][y2];
        sswap[x1][y1] = sswap[x1][y1] - sswap[x2][y2];
        return sswap;
    }

    // string representation of this board
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str.append(" ");
                str.append(tiles[i][j]);                
            }
            str.append("\n");
        }
        String ans = str.toString();
        return ans;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int cnt = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != i * n + j + 1) cnt++;
            }
        return cnt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                int dx = (tiles[i][j] - 1) / n;
                int dy = (tiles[i][j] - 1) % n;
                cnt += Math.abs(dx - i) + Math.abs(dy - j);
            }
        }
        return cnt;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] - 1 != i * n + j  && tiles[i][j] != 0) return false;
        return true;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board other = (Board) y;
        if (this.n != other.n) return false;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) 
                if (this.tiles[i][j] != other.tiles[i][j]) return false;
        return true;
    }

    private int getBlank() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] == 0) return i * n + j;
        return -1;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> nneighbors = new Stack<Board>();
        int blank = getBlank();
        if (blank == -1) return null;
        int dx = blank / n;
        int dy = blank % n;
        if (dx > 0) nneighbors.push(new Board(swap(tiles, dx, dy, dx - 1, dy)));
        if (dx + 1 < n) nneighbors.push(new Board(swap(tiles, dx, dy, dx + 1, dy)));
        if (dy > 0) nneighbors.push(new Board(swap(tiles, dx, dy, dx, dy - 1)));
        if (dy + 1 < n) nneighbors.push(new Board(swap(tiles, dx, dy, dx, dy + 1)));
        return nneighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (tiles[0][0] != 0 && tiles[0][1] != 0)
            return new Board(swap(tiles, 0, 0, 0, 1));
        else
            return new Board(swap(tiles, 1, 0, 1, 1));
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        Board board = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        System.out.println(board.isGoal());
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        for (Board bboard : board.neighbors()) {
            System.out.println(bboard);
        }
    }

}