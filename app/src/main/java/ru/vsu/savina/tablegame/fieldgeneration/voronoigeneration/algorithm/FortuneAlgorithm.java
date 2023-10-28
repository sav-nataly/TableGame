package ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.algorithm;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Edge;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Parabola;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.ParabolaType;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Point;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.VoronoiDiagram;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.VoronoiEvent;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.VoronoiEventType;

public class FortuneAlgorithm {
    public static VoronoiDiagram generateDiagram(List<Point> pointList, double width, double height) {
        VoronoiDiagram diagram = new VoronoiDiagram(pointList);
        generateVoronoi(diagram, width, height);

        return diagram;
    }

    private static void generateVoronoi(VoronoiDiagram diagram, double width, double height) {
        Queue<VoronoiEvent> events = new PriorityQueue<>(); // priority queue represents sweep line
        Parabola root = null; // binary search tree represents beach line
        double yCurr; // current y-coord of sweep line

        for (Point p : diagram.getSites()) {
            events.add(new VoronoiEvent(p, VoronoiEventType.SITE_EVENT));
        }

        // process events (sweep line)
        while (!events.isEmpty()) {
            VoronoiEvent e = events.poll();
            yCurr = e.getSite().getY();

            if (root == null)
                root = new Parabola(e.getSite());

            if (e.getType() == VoronoiEventType.SITE_EVENT) {
                handleSite(e.getSite(), events, root, yCurr);
            } else {
                handleCircle(e, root, events, diagram, yCurr);
            }
        }
        yCurr = width + height;

        endEdges(root, diagram, yCurr); // close off any dangling edges

        //place to close polygons
        // get rid of those crazy inifinte lines
        for (Edge e : diagram.getEdges()) {
            if (e.getNeighbor() != null) {
                e.setStart(e.getNeighbor().getEnd());
                e.setNeighbor(null);
            }
        }
    }

    // processes site event
    private static void handleSite(Point p, Queue<VoronoiEvent> events, Parabola root, double yCurr) {
        // find parabola on beach line right above p
        Parabola par = getParabolaByX(p.getX(), root, yCurr);

        if (par.getEvent() != null) {
            events.remove(par.getEvent());
            par.setEvent(null);
        }

        // create new dangling edge; bisects parabola focus and p
        Point start = new Point(p.getX(), getY(par.getPoint(), p.getX(), yCurr));
        Edge el = new Edge(start, par.getPoint(), p);
        Edge er = new Edge(start, p, par.getPoint());

        el.setNeighbor(er);
        er.setNeighbor(el);
        par.setEdge(el);
        par.setType(ParabolaType.VERTEX);

        // replace original parabola par with p0, p1, p2
        Parabola p0 = new Parabola(par.getPoint());
        Parabola p1 = new Parabola(p);
        Parabola p2 = new Parabola(par.getPoint());

        par.setLeftChild(p0);
        par.setRightChild(new Parabola());
        par.getRightChild().setEdge(er);
        par.getRightChild().setLeftChild(p1);
        par.getRightChild().setRightChild(p2);

        checkCircleEvent(p0, yCurr, events);
        checkCircleEvent(p2, yCurr, events);
    }

    // process circle event
    private static void handleCircle(VoronoiEvent e, Parabola root, Queue<VoronoiEvent> events, VoronoiDiagram diagram, double yCurr) {
        // find p0, p1, p2 that generate this event from left to right
        Parabola p1 = e.getArc();
        Parabola xl = Parabola.getLeftParent(p1);
        Parabola xr = Parabola.getRightParent(p1);
        Parabola p0 = Parabola.getLeftChild(xl);
        Parabola p2 = Parabola.getRightChild(xr);

        // remove associated events since the points will be altered
        if (p0.getEvent() != null) {
            events.remove(p0.getEvent());
            p0.setEvent(null);
        }
        if (p2.getEvent() != null) {
            events.remove(p2.getEvent());
            p2.setEvent(null);
        }

        Point p = new Point(e.getSite().getX(), getY(p1.getPoint(), e.getSite().getX(), yCurr)); // new vertex

        // end edges!
        xl.getEdge().setEnd(p);
        xr.getEdge().setEnd(p);
        diagram.getEdges().add(xl.getEdge());
        diagram.getEdges().add(xr.getEdge());

        // start new bisector (edge) from this vertex on which ever original edge is higher in tree
        Parabola higher = new Parabola();
        Parabola par = p1;
        while (par != root) {
            par = par.getParent();
            if (par == xl) higher = xl;
            if (par == xr) higher = xr;
        }
        higher.setEdge(new Edge(p, p0.getPoint(), p2.getPoint()));

        // delete p1 and parent (boundary edge) from beach line
        Parabola gparent = p1.getParent().getParent();
        if (p1.getParent().getLeftChild() == p1) {
            if (gparent.getLeftChild() == p1.getParent())
                gparent.setLeftChild(p1.getParent().getRightChild());
            if (gparent.getRightChild() == p1.getParent())
                gparent.setRightChild(p1.getParent().getRightChild());
        } else {
            if (gparent.getLeftChild() == p1.getParent())
                gparent.setLeftChild(p1.getParent().getLeftChild());
            if (gparent.getRightChild() == p1.getParent())
                gparent.setRightChild(p1.getParent().getLeftChild());
        }

        Point op = p1.getPoint();
        p1.setParent(null);
        p1 = null;

        checkCircleEvent(p0, yCurr, events);
        checkCircleEvent(p2, yCurr, events);
    }

    // adds circle event if foci a, b, c lie on the same circle
    private static void checkCircleEvent(Parabola b, double yCurr, Queue<VoronoiEvent> events) {
        Parabola lp = Parabola.getLeftParent(b);
        Parabola rp = Parabola.getRightParent(b);

        if (lp == null || rp == null) return;

        Parabola a = Parabola.getLeftChild(lp);
        Parabola c = Parabola.getRightChild(rp);

        if (a == null || c == null || a.getPoint() == c.getPoint()) return;

        if (ccw(a.getPoint(), b.getPoint(), c.getPoint()) != 1) return;

        // edges will intersect to form a vertex for a circle event
        Point start = getEdgeIntersection(lp.getEdge(), rp.getEdge());
        if (start == null) return;

        // compute radius
        double dx = b.getPoint().getX() - start.getX();
        double dy = b.getPoint().getY() - start.getY();
        double d = Math.sqrt((dx * dx) + (dy * dy));
        if (start.getY() + d < yCurr) return; // must be after sweep line

        Point ep = new Point(start.getX(), start.getY() + d);

        // add circle event
        VoronoiEvent e = new VoronoiEvent(ep, VoronoiEventType.CIRCLE_EVENT);
        e.setArc(b);
        b.setEvent(e);
        events.add(e);
    }

    public static int ccw(Point a, Point b, Point c) {
        double area2 = (b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX());
        if (area2 < 0) return -1;
        else if (area2 > 0) return 1;
        else return 0;
    }


    // end all unfinished edges
    private static void endEdges(Parabola p, VoronoiDiagram diagram, double yCurr) {
        if (p.getType() == ParabolaType.FOCUS) {
            return;
        }

        double x = getXofEdge(p, yCurr);
        p.getEdge().setEnd(new Point(x, p.getEdge().getSlope() * x + p.getEdge().getYint()));
        diagram.getEdges().add(p.getEdge());

        endEdges(p.getLeftChild(), diagram, yCurr);
        endEdges(p.getRightChild(), diagram, yCurr);
    }

    // returns intersection of the lines of with vectors a and b
    private static Point getEdgeIntersection(Edge a, Edge b) {

        if (b.getSlope() == a.getSlope() && b.getYint() != a.getYint()) return null;

        double x = (b.getYint() - a.getYint()) / (a.getSlope() - b.getSlope());
        double y = a.getSlope() * x + a.getYint();

        return new Point(x, y);
    }

    // returns current x-coordinate of an unfinished edge
    private static double getXofEdge(Parabola par, double yCurr) {
        //find intersection of two parabolas
        Parabola left = Parabola.getLeftChild(par);
        Parabola right = Parabola.getRightChild(par);

        Point p = left.getPoint();
        Point r = right.getPoint();

        double[] coeff1 = getParabolaCoefficients(p.getX(), p.getY(), yCurr);
        double[] coeff2 = getParabolaCoefficients(r.getX(), r.getY(), yCurr);
        //нахождение коэффициентов парабол

        double a = coeff1[0] - coeff2[0];
        double b = coeff1[1] - coeff2[1];
        double c = coeff1[2] - coeff2[2];

        //нахождение точки пересечения
        double disc = b * b - 4 * a * c;
        double x1 = (-b + Math.sqrt(disc)) / (2 * a);
        double x2 = (-b - Math.sqrt(disc)) / (2 * a);

        double ry;
        if (p.getY() > r.getY()) ry = Math.max(x1, x2);
        else ry = Math.min(x1, x2);

        return ry;
    }

    // returns parabola above this x coordinate in the beach line
    private static Parabola getParabolaByX(double xx, Parabola root, double yCurr) {
        Parabola par = root;
        while (par.getType() == ParabolaType.VERTEX) {
            double x = getXofEdge(par, yCurr);
            if (x > xx) par = par.getLeftChild();
            else par = par.getRightChild();
        }
        return par;
    }

    // find corresponding y-coordinate to x on parabola with focus p
    private static double getY(Point p, double x, double yCurr) {
        // determine equation for parabola around focus p
        double[] coeff1 = getParabolaCoefficients(p.getX(), p.getY(), yCurr);
        return (coeff1[0] * x * x + coeff1[1] * x + coeff1[2]);
    }


    private static double[] getParabolaCoefficients(double x, double y, double yCurr) {
        double dp = 2 * (y - yCurr);
        double a = 1 / dp;
        double b = -2 * x / dp;
        double c = (x * x + y * y - yCurr * yCurr) / dp;

        return new double[]{a, b, c};
    }
}
