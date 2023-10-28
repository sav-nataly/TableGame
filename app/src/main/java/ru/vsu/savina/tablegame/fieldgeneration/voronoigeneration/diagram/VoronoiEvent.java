package ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram;

// an event is either a site or circle event for the sweep line to process
public class VoronoiEvent implements Comparable<VoronoiEvent> {
    private Point site;
    private VoronoiEventType type;
    private Parabola arc; // only if circle event

    public VoronoiEvent(Point p, VoronoiEventType type) {
        this.site = p;
        this.type = type;
        arc = null;
    }

    public VoronoiEvent(Point p, VoronoiEventType type, Parabola arc) {
        this.site = p;
        this.type = type;
        this.arc = arc;
    }

    public Point getSite() {
        return site;
    }

    public void setSite(Point site) {
        this.site = site;
    }

    public VoronoiEventType getType() {
        return type;
    }

    public void setType(VoronoiEventType type) {
        this.type = type;
    }

    public Parabola getArc() {
        return arc;
    }

    public void setArc(Parabola arc) {
        this.arc = arc;
    }

    public int compareTo(VoronoiEvent other) {
        if (Math.abs(this.site.getY() - other.site.getY()) < 0.00001) {
            if (Math.abs(this.site.getX() - other.site.getX()) < 0.00001) {
                return 0;
            } else if (this.site.getX() > other.site.getX())
                return 1;
        }
        if (this.site.getY() > other.site.getY())
            return 1;
        return -1;
    }
}
