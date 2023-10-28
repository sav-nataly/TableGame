package ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram;

// represents the beach line
// can either be a site that is the center of a parabola
// or can be a vertex that bisects two sites
public class Parabola {
    private ParabolaType type;
    private Point point; // if is focus
    private Edge edge; // if is vertex
    private VoronoiEvent event; // a parabola with a focus can disappear in a circle event

    private Parabola parent;
    private Parabola leftChild;
    private Parabola rightChild;

    public Parabola() {
        type = ParabolaType.VERTEX;
    }

    public Parabola(Point p) {
        point = p;
        type = ParabolaType.FOCUS;
    }

    // returns the closest left site (focus of parabola)
    public static Parabola getLeft(Parabola p) {
        return getLeftChild(getLeftParent(p));
    }

    // returns closest right site (focus of parabola)
    public static Parabola getRight(Parabola p) {
        return getRightChild(getRightParent(p));
    }

    // returns the closest parent on the left
    public static Parabola getLeftParent(Parabola p) {
        Parabola parent = p.parent;
        if (parent == null) return null;
        Parabola last = p;
        while (parent.leftChild == last) {
            if (parent.parent == null) return null;
            last = parent;
            parent = parent.parent;
        }
        return parent;
    }

    // returns the closest parent on the right
    public static Parabola getRightParent(Parabola p) {
        Parabola parent = p.parent;
        if (parent == null) return null;
        Parabola last = p;
        while (parent.rightChild == last) {
            if (parent.parent == null) return null;
            last = parent;
            parent = parent.parent;
        }
        return parent;
    }

    // returns the closest site (focus of another parabola) to the left
    public static Parabola getLeftChild(Parabola p) {
        if (p == null) return null;
        Parabola child = p.leftChild;
        while (child.type == ParabolaType.VERTEX) child = child.rightChild;
        return child;
    }

    // returns closest site (focus of another parabola) to the right
    public static Parabola getRightChild(Parabola p) {
        if (p == null) return null;
        Parabola child = p.rightChild;
        while (child.type == ParabolaType.VERTEX) child = child.leftChild;
        return child;
    }

    public ParabolaType getType() {
        return type;
    }

    public void setType(ParabolaType type) {
        this.type = type;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public VoronoiEvent getEvent() {
        return event;
    }

    public void setEvent(VoronoiEvent event) {
        this.event = event;
    }

    public Parabola getParent() {
        return parent;
    }

    public void setParent(Parabola parent) {
        this.parent = parent;
    }

    public Parabola getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Parabola p) {
        leftChild = p;
        p.parent = this;
    }

    public Parabola getRightChild() {
        return rightChild;
    }

    public void setRightChild(Parabola p) {
        rightChild = p;
        p.parent = this;
    }
}
