package graph;

import list.*;

public class Vertex {
    protected DList edgeList;
    protected Object vertex;
    protected DListNode node;

    protected Vertex() {
        vertex = null;
        edgeList = new DList();
    }

    protected Vertex(Object vertex) {
        this.vertex = vertex;
        edgeList = new DList();
    }

    public void setNode(DListNode node) {
        this.node = node;
    }

    public Object getVertex() {
        return vertex;
    }
}
