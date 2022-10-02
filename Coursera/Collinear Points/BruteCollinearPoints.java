import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private Point[] points;
    private List<LineSegment> segments;
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (Point point: points) {
            if (point == null) throw new IllegalArgumentException();
        }

        this.points = Arrays.copyOf(points, points.length);
        
        Arrays.sort(this.points);

        for (int i = 1; i < this.points.length; i++)
            if (this.points[i].slopeTo(this.points[i - 1]) == Double.NEGATIVE_INFINITY) 
                throw new IllegalArgumentException();

        segments = new ArrayList<LineSegment>();
        
        int sz = this.points.length;
        for (int i = 0; i < sz; i++) 
            for (int j = i + 1; j < sz; j++)
                for (int k = j + 1; k < sz; k++)
                    for (int z = k + 1; z < sz; z++) {
                        Point p = this.points[i];
                        Point q = this.points[j];
                        Point r = this.points[k];
                        Point s = this.points[z];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
                            segments.add(new LineSegment(p, s));
                        }
                    }
    }

    public int numberOfSegments() {
        return segments.size();           
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }   

}
