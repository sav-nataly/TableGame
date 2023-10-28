package ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.algorithm;

import java.util.ArrayList;
import java.util.List;

import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Point;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.polygon.Polygon;

public class LloydRelaxation {
    private static double getA(Polygon polygon) {
        double A = 0;
        for (int i = 0; i < polygon.getPoints().size() - 1; i++) {
            A += polygon.getPoints().get(i).getX() * polygon.getPoints().get(i + 1).getY()
                    - polygon.getPoints().get(i + 1).getX() * polygon.getPoints().get(i).getY();
        }

        return A / 2;
    }

    private static double getCy(Polygon polygon) {
        double Cy = 0;

        for (int i = 0; i < polygon.getPoints().size(); i++) {
            Cy += polygon.getPoints().get(i).getY();
        }

        return Cy / polygon.getPoints().size();
    }

    private static double getCx(Polygon polygon) {
        double Cx = 0;

        for (int i = 0; i < polygon.getPoints().size(); i++) {
            Cx += polygon.getPoints().get(i).getX();
        }

        return Cx / polygon.getPoints().size();
    }

    public static List<Point> getRelaxation(List<Polygon> polygons) {
        List<Point> centroids = new ArrayList<>();

        for (Polygon p : polygons) {
            double A = getA(p);
            double Cx = getCx(p);
            double Cy = getCy(p);

            centroids.add(new Point(Cx, Cy));
        }

        return centroids;
    }
}
