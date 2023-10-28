package ru.vsu.savina.tablegame.fieldgeneration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.polygon.DiagramCutter;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Edge;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.algorithm.FortuneAlgorithm;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.algorithm.LloydRelaxation;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Point;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.polygon.Polygon;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.polygon.PolygonType;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.algorithm.Util;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.VoronoiDiagram;
import ru.vsu.savina.tablegame.game.engine.field.Field;
import ru.vsu.savina.tablegame.game.engine.field.Place;
import ru.vsu.savina.tablegame.game.engine.field.Transition;
import ru.vsu.savina.tablegame.game.impl.field.CampPlace;
import ru.vsu.savina.tablegame.view.wrapper.FieldWrapper;
import ru.vsu.savina.tablegame.view.wrapper.PlaceWrapper;
import ru.vsu.savina.tablegame.view.wrapper.TransitionWrapper;

public class FieldGeneration {
    public static FieldWrapper generateField(int numOfCells, int relaxationSteps, double width, double height) {
        VoronoiDiagram voronoi = FortuneAlgorithm.generateDiagram(generateSites(numOfCells, width, height), width, height);
        List<Polygon> polygons = createPolygons(voronoi, width, height);

        for (int i = 0; i < relaxationSteps; i++) {
            List<Point> pointList = LloydRelaxation.getRelaxation(polygons);
            voronoi = FortuneAlgorithm.generateDiagram(pointList, width, height);
            polygons = createPolygons(voronoi, width, height);
        }

        Point centerPoint = new Point(width / 2, height / 2);
        Polygon dPolygon = chooseDPolygon(polygons, centerPoint);

        assignTypes(polygons, dPolygon, centerPoint, width, height);

        FieldWrapper wrapper = joinVertices(polygons, dPolygon, width, height);

        return wrapper;
    }

    private static Polygon chooseDPolygon(List<Polygon> polygons, Point centerPoint) {
        int dNum = ThreadLocalRandom.current().nextInt(0, polygons.size());
        Polygon dPolygon = polygons.get(dNum);

        double dist = Util.getDistance(dPolygon.getCenter(), centerPoint);
        while (dist > centerPoint.getX() * 0.75 && dist < centerPoint.getX() * 0.35) {
            dNum = ThreadLocalRandom.current().nextInt(0, polygons.size());
            dPolygon = polygons.get(dNum);
            dist = Util.getDistance(dPolygon.getCenter(), centerPoint);
        }

        return dPolygon;
    }

    private static List<Point> generateSites(int numOfCells, double width, double height) {
        ArrayList<Point> sites = new ArrayList<>();

        Random gen = new Random(12345);

        for (int i = 0; i < numOfCells; i++) {
            double x = gen.nextDouble() * width;
            double y = gen.nextDouble() * height;
            sites.add(new Point(x, y));
        }

        return sites;
    }

    public static List<Polygon> createPolygons(VoronoiDiagram diagram, double width, double height) {
        List<Polygon> polygons = new ArrayList<>();

        diagram.getEdges().removeIf(e -> Double.isNaN(e.getStart().getX()) || Double.isNaN(e.getStart().getY())
                || Double.isNaN(e.getEnd().getX()) || Double.isNaN(e.getEnd().getY()));

        deleteDuplicates(diagram);

        for (Edge e : diagram.getEdges()) {
            DiagramCutter.cutEdges(e, width, height);
        }

        deleteEdges(diagram, width, height);

        for (Point p : diagram.getSites()) {
            Polygon polygon = new Polygon();
            polygon.setCenter(p);
            for (Edge e : diagram.getEdges()) {
                if (p.compareTo(e.getSiteLeft()) == 0 || p.compareTo(e.getSiteRight()) == 0) {
                    if (e.getEnd().equals(e.getStart()))
                        continue;
                    if (laysOnBorder(e, width, height)) {
                        continue;
                    }

                    if (!polygon.getPoints().contains(e.getEnd()))
                        polygon.getPoints().add(e.getEnd());
                    if (!polygon.getPoints().contains(e.getStart()))
                        polygon.getPoints().add(e.getStart());
                    if (!polygon.getEdges().contains(e))
                        polygon.getEdges().add(e);

                    if (p.compareTo(e.getSiteLeft()) == 0)
                        if (!polygon.getNeighbors().contains(e.getSiteRight()))
                            polygon.getNeighbors().add(e.getSiteRight());

                    if (p.compareTo(e.getSiteRight()) == 0)
                        if (!polygon.getNeighbors().contains(e.getSiteLeft()))
                            polygon.getNeighbors().add(e.getSiteLeft());
                }
            }
            if (polygon.getPoints().size() > 0) {
                polygons.add(polygon);
            }
        }
        return DiagramCutter.closePolygons(polygons, width, height);
    }

    private static void assignTypes(List<Polygon> polygons, Polygon dPolygon, Point centerPoint, double width, double height) {
        assignOceanType(polygons, centerPoint, width, height);
        assignJangaType(polygons, dPolygon, centerPoint);
        assignRiverType(polygons, centerPoint, dPolygon);
        assignPlainsTypes(polygons);
    }

    private static void assignPlainsTypes(List<Polygon> polygons) {
        for (Polygon polygon : polygons) {
            if (polygon.getType() == null) {

                double randomNum = ThreadLocalRandom.current().nextDouble();

                if (randomNum <= 0.3) {
                    polygon.setType(PolygonType.SWAMP);
                } else
                    polygon.setType(PolygonType.PLAIN);
            }
        }
    }

    private static void assignOceanType(List<Polygon> polygons, Point centerPoint, double width, double height) {
        for (Polygon polygon : polygons) {
            if (polygon.getPoints().stream().anyMatch(p -> p.getY() == 0 || p.getY() == height || p.getX() == 0 || p.getX() == width)) {
                polygon.setType(PolygonType.OCEAN);
                continue;
            }
            double dist = Util.getDistance(polygon.getCenter(), centerPoint);
            if (dist > centerPoint.getX() * 0.9) {
                polygon.setType(PolygonType.OCEAN);
            }
        }
    }

    private static void assignJangaType(List<Polygon> polygons, Polygon dPolygon, Point centerPoint) {
        for (Polygon polygon : polygons) {
            if (polygon.getType() == null) {
                double dDist = Util.getDistance(polygon.getCenter(), dPolygon.getCenter());

                if (dDist <= centerPoint.getX() / 4) {
                    polygon.setType(PolygonType.JANGA);
                }
            }
        }
    }

    private static void assignRiverType(List<Polygon> polygons, Point centerPoint, Polygon dPolygon) {
        int dNum = ThreadLocalRandom.current().nextInt(0, polygons.size());
        Polygon rPolygon = polygons.get(dNum);
        Point leftBorderPoint = new Point(0, 0);
        Point rightBorderPoint = new Point(centerPoint.getX() * 2, 0);

        while (rPolygon.getType() == null &&
                Util.getXDistance(rPolygon.getCenter(), leftBorderPoint) < centerPoint.getX()
                && Util.getXDistance(rPolygon.getCenter(), rightBorderPoint) < centerPoint.getX()
                && Util.getDistance(rPolygon.getCenter(), dPolygon.getCenter()) < centerPoint.getX() / 6
        ) {
            dNum = ThreadLocalRandom.current().nextInt(0, polygons.size());
            rPolygon = polygons.get(dNum);
        }

        for (Polygon polygon : polygons) {
            if (polygon.getType() == null) {

                double xDist = Util.getXDistance(rPolygon.getCenter(), polygon.getCenter());
                double yDist = Util.getYDistance(rPolygon.getCenter(), polygon.getCenter());

                if (xDist <= centerPoint.getX() / 8 && yDist <= centerPoint.getY() * 2) {
                    polygon.setType(PolygonType.RIVER);
                }
            }
        }

    }

    private static FieldWrapper joinVertices(List<Polygon> polygons, Polygon dPolygon, double width, double height) {
        Polygon startPolygon = polygons.stream().filter(p -> p.getType() == PolygonType.PLAIN).
                max(Comparator.comparingInt(p -> (int) Util.getDistance(p.getCenter(), dPolygon.getCenter()))).get();

        FieldWrapper wrapper = new FieldWrapper();
        wrapper.setWidth(width);
        wrapper.setWidth(height);
        Field field = new Field();

        wrapper.setPolygons(polygons);

        wrapper.setField(field);

        Set<Point> used = new HashSet<>();

        PlaceWrapper placeWrapper = new PlaceWrapper(startPolygon.getCenter().getX(), startPolygon.getCenter().getY());
        wrapper.addPlace(placeWrapper);

        Place startPlace = new CampPlace();
        field.addPlace(startPlace);
        field.setStartPlace(startPlace);
        placeWrapper.setPlace(startPlace);

        used.add(startPolygon.getCenter());
        addTransitions(polygons, wrapper, field, startPolygon, dPolygon, placeWrapper, used);

        //add vertex for doplygon

        return wrapper;
    }


    private static void addTransitions(List<Polygon> polygons, FieldWrapper wrapper, Field field, Polygon p, Polygon dPolygon, PlaceWrapper u, Set<Point> used) {
        for (Point point : p.getNeighbors()) {
            Polygon next = polygons.stream().filter(p1 -> p1.getCenter().equals(point)).findFirst().get();

            if (next.getType() != PolygonType.OCEAN) {

                if (!used.contains(point)) {
                    PlaceWrapper v = new PlaceWrapper(point.getX(), point.getY());
                    wrapper.addPlace(v);
                    TransitionWrapper tr = new TransitionWrapper(u, v);
                    wrapper.addTransition(tr);

                    Place v1 = GraphGeneration.createPlace(u, next, p.equals(dPolygon));
                    Transition tr1 = new Transition(u.getPlace(), v1);

                    field.addPlace(v1);
                    field.addTransition(tr1);

                    v.setPlace(v1);
                    tr.setTransition(tr1);

                    used.add(point);
                    addTransitions(polygons, wrapper, field, next, dPolygon, v, used);
                }
            }
        }
    }


    private static boolean laysOnBorder(Edge e, double width, double height) {
        return e.getPoints().stream().allMatch(p -> p.getX() == 0 || p.getX() == width || p.getY() == height || p.getY() == 0);
    }


    private static void deleteDuplicates(VoronoiDiagram diagram) {
        for (int i = 0; i < diagram.getEdges().size(); i++) {
            Edge e = diagram.getEdges().get(i);

            diagram.getEdges().removeIf(e1 -> (e1 != e) && (e.getStart() == e1.getStart() && e.getEnd() == e1.getEnd())
                    || (e.getStart() == e1.getEnd() && e.getEnd() == e1.getStart()));
        }
    }

    private static void deleteEdges(VoronoiDiagram diagram, double width, double height) {
        diagram.getEdges().removeIf(e -> (e.getStart().getX() == 0 && e.getEnd().getX() == 0)
                || (e.getStart().getX() == width && e.getEnd().getX() == width));

        diagram.getEdges().removeIf(e -> (e.getStart().getY() == 0 && e.getEnd().getY() == 0)
                || (e.getStart().getY() == width && e.getEnd().getY() == height));
    }
}
