import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * week5: d-Tree Assignment
 * 暴力求解最邻近点，Treeset保存所有点，遍历O(n)
 */
public class PointSET {
    private TreeSet<Point2D> set;

    public PointSET() {
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return (set.isEmpty());
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null!");
        if (!set.contains(p)) set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null!");
        return set.contains(p);
    }          // does the set contain point p?

    public void draw() {
        for (Point2D point : set)
            point.draw();
        StdDraw.show();
    }                         // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle is null!");
        ArrayList<Point2D> inRect = new ArrayList<>();
        for (Point2D point : set)
            if (rect.contains(point)) inRect.add(point);
        return inRect;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null!");
        if (set.isEmpty()) return null;
        double nrstDis = Double.MAX_VALUE;
        Point2D nrst = null;
        for (Point2D q : set) {
            double disSqr = p.distanceSquaredTo(q);
            if (disSqr < nrstDis) {
                nrst = q;
                nrstDis = disSqr;
            }
        }
        return nrst;
    }

    public static void main(String[] args) {

    }
}
