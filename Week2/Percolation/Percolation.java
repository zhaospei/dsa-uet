import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation { 
    private WeightedQuickUnionUF mainUF;
    private WeightedQuickUnionUF secondUF;
    private int size;
    private boolean[][] opens;
    private int openSites;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.size = n;
        mainUF = new WeightedQuickUnionUF(size * size + 2);
        secondUF = new WeightedQuickUnionUF(size * size + 2);
        opens = new boolean[size][size];
        openSites = 0;
    }

    private int getIndex(int row, int col) {
        return (row - 1) * size + col;
    }
    
    private void checkValue(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) 
            throw new IllegalArgumentException();
    }

    public void open(int row, int col) {
        checkValue(row, col);
        if (isOpen(row, col)) return;
        opens[row - 1][col - 1] = true;
        openSites++;
        
        if (row == 1) {
            mainUF.union(0, getIndex(row, col));
            secondUF.union(0, getIndex(row, col));
        }

        if (row == size) {
            mainUF.union(size * size + 1, getIndex(row, col));
        }

        if (row > 1 && isOpen(row - 1, col)) {
            mainUF.union(getIndex(row, col), getIndex(row - 1, col));
            secondUF.union(getIndex(row, col), getIndex(row - 1, col));
        }

        if (row < size && isOpen(row + 1, col)) {
            mainUF.union(getIndex(row, col), getIndex(row + 1, col));
            secondUF.union(getIndex(row, col), getIndex(row + 1, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            mainUF.union(getIndex(row, col), getIndex(row, col - 1));
            secondUF.union(getIndex(row, col), getIndex(row, col - 1));
        }

        if (col < size && isOpen(row, col + 1)) {
            mainUF.union(getIndex(row, col), getIndex(row, col + 1));
            secondUF.union(getIndex(row, col), getIndex(row, col + 1));
        }
        
    }

    public boolean isOpen(int row, int col) {
        checkValue(row, col);
        return opens[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        checkValue(row, col);
        return secondUF.find(0) == secondUF.find(getIndex(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return mainUF.find(0) == mainUF.find(size * size + 1);
    }
/*   public static void main(String[] args) {
        Percolation mainPercolation = new Percolation(5);
        mainPercolation.open(1, 1);
        mainPercolation.open(1, 2);
        mainPercolation.open(1, 4);
        mainPercolation.open(2, 4);
        mainPercolation.open(3, 2);
        mainPercolation.open(3, 4);
        mainPercolation.open(3, 5);
        mainPercolation.open(4, 1);
        mainPercolation.open(4, 3);
        mainPercolation.open(5, 1);
        mainPercolation.open(5, 2);
        mainPercolation.open(5, 4);
        mainPercolation.open(5, 5);  
        mainPercolation.open(4, 4);   
        for (int i  = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) System.out.print(mainPercolation.isFull(i, j) + " ");  
            System.out.print("\n");
        } 
        System.out.println(mainPercolation.percolates());
    }*/
}