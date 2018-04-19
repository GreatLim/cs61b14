/* Kruskal.java */

package graphalg;

import com.sun.media.sound.DLSInfo;
import dict.HashTableChained;
import graph.*;
import set.*;
import list.*;
/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {
    protected static HashTableChained vertexHashtable = new HashTableChained();
    protected static LinkedQueue edgeQueue = new LinkedQueue();


    /**
     * minSpanTree() returns a WUGraph that represents the minimum spanning tree
     * of the WUGraph g.  The original WUGraph g is NOT changed.
     *
     * @param g The weighted, undirected graph whose MST we want to compute.
     * @return A newly constructed WUGraph representing the MST of g.
     */
    public static WUGraph minSpanTree(WUGraph g) {
        WUGraph T = createNewGraph(g);
        DisjointSets sets = new DisjointSets(g.vertexCount());
        getAllEdge(g);
        ListSorts.quickSort(edgeQueue);
        try {
            while (edgeQueue.size() > 0) {
                Edge e = (Edge) edgeQueue.dequeue();
                Vertex v1Vertex = (Vertex) vertexHashtable.find(e.vertex1).value();
                Vertex v2Vertex = (Vertex) vertexHashtable.find(e.vertex2).value();
                int index1 = v1Vertex.index;
                int index2 = v2Vertex.index;
                if(sets.find(index1) != sets.find(index2)) {
                    T.addEdge(v1Vertex.vertex, v2Vertex.vertex, e.weight);
                    sets.union(sets.find(index1), sets.find(index2));
                }
            }
        } catch(QueueEmptyException e) {
            System.out.println(e);
        }
        vertexHashtable.makeEmpty();
        edgeQueue.makeEmpty();
        return T;

    }


    private static WUGraph createNewGraph(WUGraph g) {
        WUGraph T = new WUGraph();
        Object[] vertices = g.getVertices();
        for (int i = 0; i < vertices.length; i++) {
            Object v = vertices[i];
            T.addVertex(v);
            vertexHashtable.insert(v,new Vertex(v, i));
        }
        return T;
    }

    private static void getAllEdge(WUGraph g) {
        Object[] vertices = g.getVertices();
        for (int i = 0; i < vertices.length; i++) {
            Object v = vertices[i];
            dfs(g, v);
        }
    }

    private static void dfs(WUGraph g, Object u) {
        Vertex uVertex = (Vertex) vertexHashtable.find(u).value();
        if(uVertex.visited) return;
        uVertex.visited = true;
        Neighbors neighbors = g.getNeighbors(u);
        for(int i = 0; i < neighbors.neighborList.length; i++) {
            Object v = neighbors.neighborList[i];
            int weight = neighbors.weightList[i];
            Vertex vVertex = (Vertex) vertexHashtable.find(v).value();
            if(uVertex.index >= vVertex.index) {
                Edge edge = new Edge(u, v, weight);
                edgeQueue.enqueue(edge);
            }
            if(!vVertex.visited || u == v) {
                dfs(g, v);
            }
        }
    }


//    private static void getAllEdge(WUGraph g) {
//        Object[] vertices = g.getVertices();
//        for (int i = 0; i < vertices.length; i++) {
//            for(int j = i; j < vertices.length; j++) {
//                if(g.isEdge(vertices[i], vertices[j])) {
//                    Edge e = new Edge(vertices[i], vertices[j], g.weight(vertices[i], vertices[j]));
//                    edgeQueue.enqueue(e);
////                    int u = ((Vertex) vertexHashtable.find(vertices[i]).value()).index;
////                    int v = ((Vertex) vertexHashtable.find(vertices[j]).value()).index;
//                    System.out.println("[ " + i + " " + j + " " + "]" );
//                }
//            }
//        }
//    }
}
