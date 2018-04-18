package graphalg;

import list.DList;
import list.DListNode;

public class Vertex {
    protected Object vertex;
    int index;
    protected boolean visited;

    protected Vertex(Object vertex, int index) {
        this.vertex = vertex;
        this.index = index;
        visited = false;
    }
}
