import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class FastCollinearPoints {
    private boolean isRepeatingSegments = false;
    private ArrayList<LineSegment> segments = new ArrayList<>();
    private ArrayList<Double> slopes = new ArrayList<>();
    private ArrayList<Point> endPoints = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        checkDup(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        for (Point startPoint : points) {
            ArrayList<Point> slopePoints = new ArrayList<>();
            double currentSlope = 0.0;
            double previousSlope = Double.NEGATIVE_INFINITY;
            Arrays.sort(pointsCopy, startPoint.slopeOrder());
            for (int i = 1; i < pointsCopy.length; i++) {
                currentSlope = pointsCopy[i].slopeTo(startPoint);
                if (currentSlope == previousSlope)
                    slopePoints.add(pointsCopy[i]);
                else {
                    if (slopePoints.size() >= 3) {
                        slopePoints.add(startPoint);
                        addSegmentIfNew(slopePoints, previousSlope);
                    }
                    slopePoints.clear();
                    slopePoints.add(pointsCopy[i]);
                }
                previousSlope = currentSlope;
            }
            if (slopePoints.size() >= 3) {
                slopePoints.add(startPoint);
                addSegmentIfNew(slopePoints, currentSlope);
            }
        }
    }

    private void addSegmentIfNew(ArrayList<Point> slopePoints, double slope) {
        Collections.sort(slopePoints);
        Point startPoint = slopePoints.get(0);
        Point endPoint = slopePoints.get(slopePoints.size() - 1);
        if (segments.isEmpty()) {
            LineSegment lineSegment = new LineSegment(startPoint, endPoint);
            slopes.add(slope);
            endPoints.add(endPoint);
            segments.add(lineSegment);
        }
        else {
            for (int i = 0; i < segments.size(); i++) {
                if (slope == slopes.get(i) && endPoint == endPoints.get(i))
                    isRepeatingSegments = true;
            }
            if (isRepeatingSegments == false) {
                LineSegment lineSegment = new LineSegment(startPoint, endPoint);
                slopes.add(slope);
                endPoints.add(endPoint);
                segments.add(lineSegment);
            }
            isRepeatingSegments = false;
        }
    }

    private void checkDup(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Duplicates input error");
            }
        }
    }

    public int numberOfSegments(){
        return segments.size();
    }

    public LineSegment[] segments(){
        return segments.toArray(new LineSegment[numberOfSegments()]);
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
        StdDraw.setPenRadius(0.005);
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
