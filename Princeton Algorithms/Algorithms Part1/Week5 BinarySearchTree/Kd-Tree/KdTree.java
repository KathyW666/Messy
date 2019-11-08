import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

/******************************************************************************
 *
 *  test:89
 *  在contains中有bug，根据提示直接将粗糙地将x相同的点归到右子树中，这样会出现一些问题
 *  如同envean的博客中再在相等情况下，重新判断划分一次，可以解决这个问题
 *
 ******************************************************************************/

public class KdTree {
    private Node root;

    private static class Node {
        private final Point2D p;
        private final RectHV rect;
        private Node left, right;
        private int size;
        private boolean vertical;

        public Node(Point2D p, RectHV rect, int size, boolean vertical) {
            this.p = p;
            this.rect = rect;
            this.vertical = vertical;
            this.size = size;
            this.left = null;
            this.right = null;
        }
    }

    public KdTree() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node rt) {
        if (rt == null) return 0;
        else return rt.size;
    }

//    public int compareTo(Point2D p, Point2D q, int line) {
//        if (p.equals(q)) return 0;
//        else {
//            if (line == 0) {    //垂直
//                if (p.x() < q.x()) return -1;
//                else return 1;
//            } else {
//                if (p.y() < q.y()) return -1;
//                else return 1;
//            }
//        }
//    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null!");
        if (root == null)
            root = new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0), 1, true);
        else insert(root, p);
    }

    private void insert(Node rt, Point2D point) {
        if (rt.p.equals(point)) return;
        if (rt.vertical) {
            int cmp = Double.compare(point.x(), rt.p.x());
            if (cmp == -1) {
                if (rt.left != null) insert(rt.left, point);
                else { // 将root对应的矩形空间切分的左侧部分
                    RectHV parent = rt.rect;
                    double newXmin = parent.xmin();
                    double newYmin = parent.ymin();
                    double newXmax = rt.p.x();
                    double newYmax = parent.ymax();
                    rt.left = new Node(point, new RectHV(newXmin, newYmin, newXmax, newYmax), 1, !rt.vertical);
                }
            } else {
                if (rt.right != null) insert(rt.right, point);
                else { // 右侧
                    RectHV parent = rt.rect;
                    double newXmin = rt.p.x();
                    double newYmin = parent.ymin();
                    double newXmax = parent.xmax();
                    double newYmax = parent.ymax();
                    rt.right = new Node(point, new RectHV(newXmin, newYmin, newXmax, newYmax), 1, !rt.vertical);
                }
            }
        } else {
            int cmp = Double.compare(point.y(), rt.p.y());
            if (cmp == -1) {
                if (rt.left != null) insert(rt.left, point);
                else {
                    RectHV parent = rt.rect;
                    double newXmin = parent.xmin();
                    double newYmin = parent.ymin();
                    double newXmax = parent.xmax();
                    double newYmax = rt.p.y();
                    rt.left = new Node(point, new RectHV(newXmin, newYmin, newXmax, newYmax), 1, !rt.vertical);
                }
            } else {
                if (rt.right != null) insert(rt.right, point);
                else {
                    RectHV parent = rt.rect;
                    double newXmin = parent.xmin();
                    double newYmin = rt.p.y();
                    double newXmax = parent.xmax();
                    double newYmax = parent.ymax();
                    rt.right = new Node(point, new RectHV(newXmin, newYmin, newXmax, newYmax), 1, !rt.vertical);
                }
            }
        }
        rt.size = 1 + size(rt.left) + size(rt.right);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null!");
        else return contains(root, p);
    }

    private boolean contains(Node rt, Point2D p) {
        if (rt == null) return false;
        if (rt.p.equals(p)) return true;
        if (rt.vertical) {
            int cmp = Double.compare(p.x(), rt.p.x());
            if (cmp == -1) return contains(rt.left, p);
            else if (cmp == 1) return contains(rt.right, p);
            else {
                int cmp2 = Double.compare(p.y(), rt.p.y());
                if (cmp2 == -1) return contains(rt.left, p);
                else return contains(rt.right, p);
            }
        } else {
            int cmp = Double.compare(p.y(), rt.p.y());
            if (cmp == -1) return contains(rt.left, p);
            else if (cmp == 1) return contains(rt.right, p);
            else {
                int cmp2 = Double.compare(p.x(), rt.p.x());
                if (cmp2 == -1) return contains(rt.left, p);
                else return contains(rt.right, p);
            }
        }
    }

    public void draw() {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        draw(root);
    }

    private void draw(Node rt) {
        if (rt == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        rt.p.draw();
        if (rt.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            Point2D start = new Point2D(rt.p.x(), rt.rect.ymin());
            Point2D end = new Point2D(rt.p.x(), rt.rect.ymax());
            start.drawTo(end);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            Point2D start = new Point2D(rt.rect.xmin(), rt.p.y());
            Point2D end = new Point2D(rt.rect.xmax(), rt.p.y());
            start.drawTo(end);
        }
        draw(rt.left);
        draw(rt.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("rectangle is null!");
        ArrayList<Point2D> points = new ArrayList<>();
        if (root != null) return range(root, rect, points);
        else return new ArrayList<Point2D>();
    }

    private ArrayList<Point2D> range(Node rt, RectHV rect, ArrayList<Point2D> points) {
        if (rt != null && rt.rect.intersects(rect)) {
            if (rect.contains(rt.p)) points.add(rt.p);
            if (rt.left != null) range(rt.left, rect, points);
            if (rt.right != null) range(rt.right, rect, points);
        }
        return new ArrayList<>(points);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null!");
        if (root == null) return null;
        else return nearest(root, p, root.p);
    }

    private Point2D nearest(Node rt, Point2D p, Point2D res) {
        if (rt.p.equals(p)) return rt.p;
        double min = res.distanceSquaredTo(p);
        if (rt.rect.distanceSquaredTo(p) >= min) return res;
        else {
            double dis = rt.p.distanceSquaredTo(p);
            if (dis < min) {
                res = rt.p;
                min = dis;
            }
            if (rt.left != null) res = nearest(rt.left, p, res);
            if (rt.right != null) res = nearest(rt.right, p, res);
        }
        return res;
    }

//    public static void main(String[] args) {}
}


