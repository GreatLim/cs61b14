package graph;

import list.DListNode;

public class Edge {
    protected Object vertex1;
    protected Object vertex2;
    protected int weight;
    protected Edge partner;
    protected DListNode node;

    public Edge() {

    }

    public Edge(Object vertex1, Object vertex2, int weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
        this.partner = new Edge();
        this.partner.vertex1 = vertex1;
        this.partner.vertex2 = vertex2;
        this.partner.weight = weight;
        this.partner.partner = this;
    }

    public int getWeight() {
        return weight;
    }

    public void setNode(DListNode node) {
        this.node = node;
    }


    public Edge getPartner() {
        return partner;
    }
}
