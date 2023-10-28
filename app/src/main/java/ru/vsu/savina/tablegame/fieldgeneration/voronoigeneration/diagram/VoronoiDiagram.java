package ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram;

import java.util.ArrayList;
import java.util.List;

public class VoronoiDiagram {
    private List<Point> sites;
    private List<Edge> edges;

    public VoronoiDiagram(List<Point> sites) {
        this.sites = sites;

        edges = new ArrayList<>();
    }

    public List<Point> getSites() {
        return sites;
    }

    public void setSites(List<Point> sites) {
        this.sites = sites;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
