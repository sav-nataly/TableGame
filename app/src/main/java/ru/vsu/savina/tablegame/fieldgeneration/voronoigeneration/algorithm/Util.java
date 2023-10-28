package ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.algorithm;

import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Point;

public class Util {

    private static double[] getCoefficients(Point a, Point b) {
        double A = a.getY() - b.getY();
        double B = b.getX() - a.getX();
        double C = a.getX() * b.getY() - b.getX() * a.getY();

        return new double[]{-A / B, -C / B};
    }

    public static double getXIntersection(Point a, Point b, double y) {
        double[] coef = getCoefficients(a, b);

        return (y - coef[1]) / coef[0];
    }

    public static double getYIntersection(Point a, Point b, double x) {
        double[] coef = getCoefficients(a, b);

        return coef[0] * x + coef[1];
    }

    public static double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    public static double getXDistance(Point p1, Point p2) {
        return Math.abs(p2.getX() - p1.getX());
    }

    public static double getYDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.getY() - p1.getY(), 2));
    }
}
