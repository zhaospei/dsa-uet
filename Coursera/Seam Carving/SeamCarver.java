import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) 
            throw new IllegalArgumentException();
        this.picture = new Picture(picture);
    }
    // current picture
    public Picture picture() {
        return new Picture(picture);
    }
 
    // width of current picture
    public int width() {
        return picture.width();
    }
 
    // height of current picture
    public int height() {
        return picture.height();
    }
 
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= picture.width() || y < 0 || y >= picture.height()) 
            throw new IllegalArgumentException();
        if (x == 0 || x == picture.width() - 1 || y == 0 || y == picture.height() - 1) 
            return 1000;
        int rgbUp = picture.getRGB(x, y - 1);
        int rgbDown = picture.getRGB(x, y + 1);
        int rgbLeft = picture.getRGB(x - 1, y);
        int rgbRight = picture.getRGB(x + 1, y);

        double rx = ((rgbLeft >> 16) & 0xFF) - ((rgbRight >> 16) & 0xFF); 
        double gx = ((rgbLeft >> 8) & 0xFF) - ((rgbRight >> 8) & 0xFF); 
        double bx = (rgbLeft & 0xFF) - (rgbRight & 0xFF); 
        double ry = ((rgbUp >> 16) & 0xFF) - ((rgbDown >> 16) & 0xFF); 
        double gy = ((rgbUp >> 8) & 0xFF) - ((rgbDown >> 8) & 0xFF); 
        double by = (rgbUp & 0xFF) - (rgbDown & 0xFF); 
        return Math.sqrt(rx * rx + gx * gx + bx * bx + ry * ry + gy * gy + by * by);
    }
 
    // sequence of indices for horizontal seam

    private class Pair {
        public double val;
        public int prev;

        public Pair(double val, int prev) {
            this.val = val;
            this.prev = prev;
        }
    }

    public int[] findHorizontalSeam() {
        Pair[][] dp = new Pair[width()][height()];
        for (int i = 0; i < height(); i++) {
            dp[0][i] = new Pair(1000, -1);
        }
        for (int i = 1; i < width(); i++) { 
            for (int j = 0; j < height(); j++) {
                double cur = dp[i - 1][j].val;
                int prev = j;
                if (j > 0 && dp[i - 1][j - 1].val < cur) {
                    cur = dp[i - 1][j - 1].val;
                    prev = j - 1;
                } 
                if (j < height() - 1 && dp[i - 1][j + 1].val < cur) {
                    cur = dp[i - 1][j + 1].val;
                    prev = j + 1;
                }
                dp[i][j] = new Pair(cur + energy(i, j), prev); 
            }
        }
        int index = 0;
        double res = dp[width() - 1][0].val;
        for (int j = 1; j < height(); j++) {
            if (dp[width() - 1][j].val < res) {
                res = dp[width() - 1][j].val;
                index = j;
            }
        }
        int[] ans = new int[width()];
        for (int i = width() - 1; i >= 0; i--) {
            ans[i] = index;
            index = dp[i][index].prev;
        }
        return ans;
    }
 
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        Pair[][] dp = new Pair[width()][height()];
        for (int i = 0; i < width(); i++) {
            dp[i][0] = new Pair(1000, -1);
        }
        for (int j = 1; j < height(); j++) { 
            for (int i = 0; i < width(); i++) {
                double cur = dp[i][j - 1].val;
                int prev = i;
                if (i > 0 && dp[i - 1][j - 1].val < cur) {
                    cur = dp[i - 1][j - 1].val;
                    prev = i - 1;
                } 
                if (i < width() - 1 && dp[i + 1][j - 1].val < cur) {
                    cur = dp[i + 1][j - 1].val;
                    prev = i + 1;
                }
                dp[i][j] = new Pair(cur + energy(i, j), prev); 
            }
        }
        int index = 0;
        double res = dp[0][height() - 1].val;
        for (int i = 1; i < width(); i++) {
            if (dp[i][height() - 1].val < res) {
                res = dp[i][height() - 1].val;
                index = i;
            }
        }
        int[] ans = new int[height()];
        for (int j = height() - 1; j >= 0; j--) {
            ans[j] = index;
            index = dp[index][j].prev;
        }
        return ans;
    }
 
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width()) 
            throw new IllegalArgumentException();
        for (int i = 1; i < picture.width(); i++) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) 
                throw new IllegalArgumentException();
        }    
        Picture tmp = new Picture(width(), height() - 1);
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < seam[i]; j++)
                tmp.set(i, j, picture.get(i, j));
            
            for (int j = seam[i]; j < height() - 1; j++)
                tmp.set(i, j, picture.get(i, j + 1));
        }
        this.picture = tmp;
    }
 
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height()) 
            throw new IllegalArgumentException();
        for (int i = 1; i < picture.height(); i++) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) 
                throw new IllegalArgumentException();
        }    
        Picture tmp = new Picture(width() - 1, height());
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < seam[j]; i++)
                tmp.set(i, j, picture.get(i, j));
            
            for (int i = seam[j]; i < width() - 1; i++)
                tmp.set(i, j, picture.get(i + 1, j));
        }
        this.picture = tmp;
    }
 
}