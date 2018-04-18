package graphalg;


public class Edge implements Comparable<Edge> {
    protected Object vertex1;
    protected Object vertex2;
    protected int weight;


    public Edge(Object vertex1, Object vertex2, int weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    public int compareTo(Edge e) {
        return this.weight - e.weight;
    }

}
