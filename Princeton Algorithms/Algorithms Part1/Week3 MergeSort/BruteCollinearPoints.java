import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] pointSet;
    private ArrayList<LineSegment> lineSgmts = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Null Point Array!");
        int len = points.length;
        pointSet = Arrays.copyOf(points, len);
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    if (points[i].slopeTo(pointSet[j])
                            == pointSet[i].slopeTo(pointSet[k])) {
                        for (int l = k + 1; l < len; l++) {
                            if (pointSet[i].slopeTo(pointSet[k])
                                    == pointSet[i].slopeTo(pointSet[l])) {
                                Point[] subLine = new Point[]{pointSet[i],
                                        pointSet[j], pointSet[k], pointSet[l]};
                                Insertion.sort(subLine);
                                LineSegment newAdd = new LineSegment(subLine[0], subLine[3]);
                                lineSgmts.add(newAdd);
                            }
                        }
                    }

                }
            }

        }
    }

    public int numberOfSegments(){
        return lineSgmts.size();
    }

    public LineSegment[] segments() {
        return lineSgmts.toArray(new LineSegment[numberOfSegments()]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++)
        {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.setCanvasSize(800, 600);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.enableDoubleBuffering();
        for (Point p : points) { p.draw(); }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments())
        {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
