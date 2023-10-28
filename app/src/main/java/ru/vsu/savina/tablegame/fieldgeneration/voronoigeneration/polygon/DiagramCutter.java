package ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.polygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.algorithm.Util;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Edge;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Point;

public class DiagramCutter {

    private static void cutEdgeByX(Edge e, Point p, double x) {
        double y = Util.getYIntersection(e.getStart(), e.getEnd(), x);
        p.setX(x);
        p.setY(y);
    }

    private static void cutEdgeByY(Edge e, Point p, double y) {
        double x = Util.getXIntersection(e.getStart(), e.getEnd(), y);
        p.setX(x);
        p.setY(y);
    }

    public static void cutEdges(Edge e, double width, double height) {
        e.getPoints().stream().filter(p -> p.getX() > width).forEach(p1 -> cutEdgeByX(e, p1, width));
        e.getPoints().stream().filter(p -> p.getX() < 0).forEach(p1 -> cutEdgeByX(e, p1, 0));
        e.getPoints().stream().filter(p -> p.getY() < 0).forEach(p1 -> cutEdgeByY(e, p1, 0));
        e.getPoints().stream().filter(p -> p.getY() > height).forEach(p1 -> cutEdgeByY(e, p1, height));
    }
    public static List<Polygon> closePolygons(List<Polygon> polygons, double width, double height) {
        for (Polygon polygon : polygons) {
            Map<Point, Border> map = getBorderPoints(polygon.getEdges(), width, height);
            if (map.size() <= 1)
                continue;

            List<Point> points = new ArrayList<>(map.keySet());

            if (map.values().stream().distinct().count() == 1) {
                polygon.getEdges().add(new Edge(points.get(0), points.get(1)));
            } else {
                List<Border> borders = new ArrayList<>(map.values());
                Point point = getNewPoint(borders.get(0), borders.get(1), width, height);

                polygon.getEdges().add(new Edge(points.get(0), point));
                polygon.getEdges().add(new Edge(points.get(1), point));

                polygon.getPoints().add(points.get(0));
                polygon.getPoints().add(points.get(1));
            }
        }
        return polygons;
    }

    private static Map<Point, Border> getBorderPoints(List<Edge> edges, double width, double height) {
        Map<Point, Border> map = new HashMap<>();

        for (Edge e : edges) {
            e.getPoints().stream().filter(p -> p.getX() == width).forEach(p1 -> map.put(p1, Border.RIGHT));
            e.getPoints().stream().filter(p -> p.getX() == 0).forEach(p1 -> map.put(p1, Border.LEFT));
            e.getPoints().stream().filter(p -> p.getY() == 0).forEach(p1 -> map.put(p1, Border.BOTTOM));
            e.getPoints().stream().filter(p -> p.getY() == height).forEach(p1 -> map.put(p1, Border.TOP));
        }

        return map;
    }

    private static Point getNewPoint(Border first, Border second, double width, double height) {
        double x = 0, y = 0;
        if (first == Border.BOTTOM || second == Border.BOTTOM)
            y = 0;
        if (first == Border.TOP || second == Border.TOP)
            y = height;
        if (first == Border.LEFT || second == Border.LEFT)
            x = 0;
        if (first == Border.RIGHT || second == Border.RIGHT)
            x = width;

        return new Point(x, y);
    }



}
