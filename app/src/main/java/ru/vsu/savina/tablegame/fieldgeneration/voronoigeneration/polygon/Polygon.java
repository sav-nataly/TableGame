package ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.polygon;

import java.util.ArrayList;
import java.util.List;

import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Edge;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Point;

public class Polygon {
    private List<Edge> edges;
    private List<Point> points;
    private PolygonType type;

    private Point center;

    private List<Point> neighbors;

    private Point leftPoint = null;

    public Polygon() {
        edges = new ArrayList<>();
        points = new ArrayList<>();
        neighbors = new ArrayList<>();
    }

    public Polygon(List<Edge> edges, List<Point> points, PolygonType type) {
        this.edges = edges;
        this.points = points;
        this.type = type;
    }

    public List<Point> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Point> neighbors) {
        this.neighbors = neighbors;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public PolygonType getType() {
        return type;
    }

    public void setType(PolygonType type) {
        this.type = type;
    }

    public double getWidth() {
        return getMaxPoint().getX() - getMinPoint().getX();
    }

    public double getHeight() {
        return getMaxPoint().getY() - getMinPoint().getY();
    }

    public Point getMinPoint() {
        double minX = Integer.MAX_VALUE;
        double minY = Integer.MAX_VALUE;
        for (Point p : points) {
            if (p.getX() < minX)
                minX = p.getX();

            if (p.getY() < minY)
                minY = p.getY();
        }

        return new Point(minX, minY);
    }

    public Point getMaxPoint() {
        double maxX = Integer.MIN_VALUE;
        double maxY = Integer.MIN_VALUE;
        for (Point p : points) {
            if (p.getX() > maxX)
                maxX = p.getX();

            if (p.getY() > maxY)
                maxY = p.getY();
        }

        return new Point(maxX, maxY);
    }

    public Point getLeftPoint() {
        if (leftPoint == null) {
            Point found = points.get(0);
            for (Point p : points) {
                if (p.getX() < found.getX()) {
                    found = p;
                }
                if (p.getX() == found.getX()) {
                    if (p.getY() > found.getY())
                        found = p;
                }
            }
            leftPoint = found;
        }
        return leftPoint;
    }

    public double getLeftX() {
        return getLeftPoint().getX();
    }

    public double getLeftY() {
        return getLeftPoint().getY();
    }
}
