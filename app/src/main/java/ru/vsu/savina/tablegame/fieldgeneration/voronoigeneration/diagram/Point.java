package ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram;

public class Point implements Comparable<Point> {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Point other) {
        if (Math.abs(this.y - other.y) <= 0.000001) {
            if (Math.abs(this.x - other.x) <= 0.000001) return 0;
            else if (this.x > other.x) return 1;
            else return -1;
        } else if (this.y > other.y) {
            return 1;
        } else {
            return -1;
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) return false;
        return Math.abs(x - ((Point) obj).x) <= 0.000001 && Math.abs(y - ((Point) obj).y) <= 0.000001;
    }
}
