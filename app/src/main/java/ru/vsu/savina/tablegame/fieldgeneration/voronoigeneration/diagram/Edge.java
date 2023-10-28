package ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram;

import java.util.ArrayList;
import java.util.List;

public class Edge {
    private Point start;
    private Point end;
    private Point siteLeft;
    private Point siteRight;
    private Point direction; // edge is really a vector normal to left and right points

    private Edge neighbor; // the same edge, but pointing in the opposite direction

    private double slope;
    private double yint;

    public Edge(Point first, Point left, Point right) {
        start = first;
        siteLeft = left;
        siteRight = right;
        direction = new Point(right.getY() - left.getY(), -(right.getX() - left.getX()));
        end = null;
        slope = (right.getX() - left.getX()) / (left.getY() - right.getY());
        Point mid = new Point((right.getX() + left.getX()) / 2, (left.getY() + right.getY()) / 2);
        yint = mid.getY() - slope * mid.getX();
    }

    public Edge(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Point getSiteLeft() {
        return siteLeft;
    }

    public void setSiteLeft(Point siteLeft) {
        this.siteLeft = siteLeft;
    }

    public Point getSiteRight() {
        return siteRight;
    }

    public void setSiteRight(Point siteRight) {
        this.siteRight = siteRight;
    }

    public List<Point> getPoints() {
        return new ArrayList<>() {{
            add(start);
            add(end);
        }};
    }

    public Point getDirection() {
        return direction;
    }

    public void setDirection(Point direction) {
        this.direction = direction;
    }

    public Edge getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(Edge neighbor) {
        this.neighbor = neighbor;
    }

    public double getSlope() {
        return slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public double getYint() {
        return yint;
    }

    public void setYint(double yint) {
        this.yint = yint;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge)) return false;
        return (start.equals(((Edge) obj).getStart()) && end.equals(((Edge) obj).getEnd()))
                || (start.equals(((Edge) obj).getEnd()) && end.equals(((Edge) obj).getStart()));
    }
}
