import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private Point[] points;
    private List<LineSegment> segments;
    
    public FastCollinearPoints(Point[] points) {
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
        if (this.points.length < 4) return;
        
        for (int i = 0; i < this.points.length; i++) {
            Point[] parr = Arrays.copyOf(this.points, this.points.length);
            Arrays.sort(parr, this.points[i].slopeOrder());
            int start = 1;
            int end = 1;
            double curValue = this.points[i].slopeTo(parr[1]);
            for (int j = 2; j < parr.length; j++) {
                if (this.points[i].slopeTo(parr[j]) == curValue) {
                    end = j;
                } else {
                    if (end - start + 1 >= 3) {
                        if (this.points[i].compareTo(parr[start]) < 0) {
                            segments.add(new LineSegment(this.points[i], parr[end]));
                        }
                    }
                    curValue = this.points[i].slopeTo(parr[j]);
                    start = j;
                    end = j;
                }
            }
            if (end - start + 1 >= 3) {
                if (this.points[i].compareTo(parr[start]) < 0) {
                    segments.add(new LineSegment(this.points[i], parr[end]));
                }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }   

}
