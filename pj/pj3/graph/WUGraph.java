/* WUGraph.java */
package graph;

import list.*;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {

    protected DList vertexList;
    protected HashTableChained vertexHashtable;
    protected HashTableChained edgeHashtable;


    /**
     * WUGraph() constructs a graph having no vertices or edges.
     * <p>
     * Running time:  O(1).
     */
    public WUGraph() {
        vertexList = new DList();
        vertexHashtable = new HashTableChained();
        edgeHashtable = new HashTableChained();

    }

    /**
     * vertexCount() returns the number of vertices in the graph.
     * <p>
     * Running time:  O(1).
     */
    public int vertexCount() {
        return vertexHashtable.size();
    }

    /**
     * edgeCount() returns the total number of edges in the graph.
     * <p>
     * Running time:  O(1).
     */
    public int edgeCount() {
        return edgeHashtable.size();
    }

    /**
     * getVertices() returns an array containing all the objects that serve
     * as vertices of the graph.  The array's length is exactly equal to the
     * number of vertices.  If the graph has no vertices, the array has length
     * zero.
     * <p>
     * (NOTE:  Do not return any internal data structure you use to represent
     * vertices!  Return only the same objects that were provided by the
     * calling application in calls to addVertex().)
     * <p>
     * Running time:  O(|V|).
     */
    public Object[] getVertices() {
        Object[] res = new Object[vertexCount()];
        int i = 0;
        ListNode n = vertexList.front();
        try {
            while (n.isValidNode()) {
                res[i] = ((Vertex) n.item()).getVertex();
                n = n.next();
                i++;
            }
        } catch (InvalidNodeException e) {
            System.out.println(e);
        }
        return res;
    }

    /**
     * addVertex() adds a vertex (with no incident edges) to the graph.
     * The vertex's "name" is the object provided as the parameter "vertex".
     * If this object is already a vertex of the graph, the graph is unchanged.
     * <p>
     * Running time:  O(1).
     */
    public void addVertex(Object vertex) {
        if(isVertex(vertex)) return;
        Vertex v = new Vertex(vertex);
        vertexList.insertBack(v);
        ListNode n = vertexList.back();
        v.setNode((DListNode) n);
        vertexHashtable.insert(vertex,v);
    }

    /**
     * removeVertex() removes a vertex from the graph.  All edges incident on the
     * deleted vertex are removed as well.  If the parameter "vertex" does not
     * represent a vertex of the graph, the graph is unchanged.
     * <p>
     * Running time:  O(d), where d is the degree of "vertex".
     */
    public void removeVertex(Object vertex) {
        if(!isVertex(vertex)) return;
        try {
            //internal vertex
            Vertex v = (Vertex) vertexHashtable.find(vertex).value();
            //the node of v
            ListNode vertexNode = v.node;
            //the first of edge nodes of v
            ListNode edgeNode = v.edgeList.front();
            while (edgeNode.isValidNode()) {
                Edge e = (Edge) edgeNode.item();
                edgeNode = edgeNode.next();
                removeEdge(e.vertex1,e.vertex2);
            }
            //remove the node in edgeList
            v.node.remove();
            //remove vertex in vertexHashtable
            vertexHashtable.remove(vertex);
        } catch(InvalidNodeException e) {
            System.out.println(e);
        }

    }

    /**
     * isVertex() returns true if the parameter "vertex" represents a vertex of
     * the graph.
     * <p>
     * Running time:  O(1).
     */
    public boolean isVertex(Object vertex) {
        return vertexHashtable.find(vertex) != null;
    }

    /**
     * degree() returns the degree of a vertex.  Self-edges add only one to the
     * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
     * of the graph, zero is returned.
     * <p>
     * Running time:  O(1).
     */
    public int degree(Object vertex) {
        if(!isVertex(vertex)) return 0;
        return ((Vertex) vertexHashtable.find(vertex).value()).edgeList.length();
    }

    /**
     * getNeighbors() returns a new Neighbors object referencing two arrays.  The
     * Neighbors.neighborList array contains each object that is connected to the
     * input object by an edge.  The Neighbors.weightList array contains the
     * weights of the corresponding edges.  The length of both arrays is equal to
     * the number of edges incident on the input vertex.  If the vertex has
     * degree zero, or if the parameter "vertex" does not represent a vertex of
     * the graph, null is returned (instead of a Neighbors object).
     * <p>
     * The returned Neighbors object, and the two arrays, are both newly created.
     * No previously existing Neighbors object or array is changed.
     * <p>
     * (NOTE:  In the neighborList array, do not return any internal data
     * structure you use to represent vertices!  Return only the same objects
     * that were provided by the calling application in calls to addVertex().)
     * <p>
     * Running time:  O(d), where d is the degree of "vertex".
     */
    public Neighbors getNeighbors(Object vertex) {
        if(!isVertex(vertex) || degree(vertex) == 0) return null;
        Vertex v = (Vertex) vertexHashtable.find(vertex).value();
        Neighbors res = new Neighbors();
        res.neighborList = new Object[degree(vertex)];
        res.weightList = new int[degree(vertex)];
        ListNode edgeNode = v.edgeList.front();
        int i = 0;

        try {
            while (edgeNode.isValidNode()) {
                Edge e = (Edge) edgeNode.item();
                if(vertex == e.vertex1) {
                    res.neighborList[i] = e.vertex2;
                } else {
                    res.neighborList[i] = e.vertex1;
                }
                res.weightList[i] = e.weight;
                edgeNode = edgeNode.next();
                i++;
            }
        } catch(InvalidNodeException e) {
            System.out.println(e);
        }
        return res;

    }

    /**
     * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
     * u and v does not represent a vertex of the graph, the graph is unchanged.
     * The edge is assigned a weight of "weight".  If the graph already contains
     * edge (u, v), the weight is updated to reflect the new value.  Self-edges
     * (where u.equals(v)) are allowed.
     * <p>
     * Running time:  O(1).
     */
    public void addEdge(Object u, Object v, int weight) {
        if(!isVertex(u) || !isVertex(v)) return;
        if(isEdge(u, v)) {
            Edge e = (Edge) edgeHashtable.find(new VertexPair(u, v)).value();
            e.weight = weight;
            e.partner.weight = weight;
        } else {
            Edge e = new Edge(u, v, weight);
            Vertex uVertex = (Vertex) vertexHashtable.find(u).value();
            uVertex.edgeList.insertBack(e);
            ListNode n1 = uVertex.edgeList.back();
            e.setNode((DListNode) n1);
            if(u != v) {
                Vertex vVertex = (Vertex) vertexHashtable.find(v).value();
                vVertex.edgeList.insertBack(e.partner);
                ListNode n2 = vVertex.edgeList.back();
                e.partner.setNode((DListNode) n2);
            }
            edgeHashtable.insert(new VertexPair(u, v), e);
        }
    }

    /**
     * removeEdge() removes an edge (u, v) from the graph.  If either of the
     * parameters u and v does not represent a vertex of the graph, the graph
     * is unchanged.  If (u, v) is not an edge of the graph, the graph is
     * unchanged.
     * <p>
     * Running time:  O(1).
     */
    public void removeEdge(Object u, Object v) {
        if(!isVertex(u) || !isVertex(v)) return;
        if(!isEdge(u, v)) return;
        try {
            Edge e = (Edge) edgeHashtable.find(new VertexPair(u, v)).value();
            //remove nodes in edgeList
            e.node.remove();
            edgeHashtable.remove(new VertexPair(e.vertex1, e.vertex2));
            if(u != v) {
                //remove the node of edge's partner
                e.partner.node.remove();
                //remove the vertex pair in edgeHashtable
            }
        } catch(InvalidNodeException e) {
            System.out.println(e);
        }
    }

    /**
     * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
     * if (u, v) is not an edge (including the case where either of the
     * parameters u and v does not represent a vertex of the graph).
     * <p>
     * Running time:  O(1).
     */
    public boolean isEdge(Object u, Object v) {
        return isVertex(u) && isVertex(v) && edgeHashtable.find(new VertexPair(u, v)) != null;
    }

    /**
     * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
     * an edge (including the case where either of the parameters u and v does
     * not represent a vertex of the graph).
     * <p>
     * (NOTE:  A well-behaved application should try to avoid calling this
     * method for an edge that is not in the graph, and should certainly not
     * treat the result as if it actually represents an edge with weight zero.
     * However, some sort of default response is necessary for missing edges,
     * so we return zero.  An exception would be more appropriate, but also more
     * annoying.)
     * <p>
     * Running time:  O(1).
     */
    public int weight(Object u, Object v) {
        if(!isEdge(u, v)) return 0;
        return ((Edge) edgeHashtable.find(new VertexPair(u, v)).value()).weight;
    }

}
