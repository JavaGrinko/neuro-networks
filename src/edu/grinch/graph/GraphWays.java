package edu.grinch.graph;

import edu.grinch.linearalgebra.Matrix;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

import java.util.LinkedList;
import java.util.List;

/**
 * Author Grinch
 * Date: 18.05.14
 * Time: 10:19
 */
public class GraphWays {

    private List<Vertex> vertexes = new LinkedList<Vertex>();
    private Vertex startVertex;
    private Vertex endVertex;
    private Matrix adjacencyMatrix;

    /**
     * Создание графа по матрице смежности
     * @param adjacencyMatrix - матрица смежности
     */
    public GraphWays(Matrix adjacencyMatrix){
        this.adjacencyMatrix = adjacencyMatrix;
        initVertexes();
        startVertex = vertexes.get(0);
        endVertex = vertexes.get(vertexes.size()-1);
    }

    private void initVertexes() {
        int count = adjacencyMatrix.getHeight();
        for (int i = 0; i < count; i++){
            vertexes.add(new Vertex(i));
        }
        for (int i = 0; i < count; i++){
            Vertex current = vertexes.get(i);
            //linking parents
            for (int j = 0; j < i; j++){
                double link = adjacencyMatrix.getData(i,j);
                boolean isEdge = link == 1. ? true : false;
                if (isEdge){
                    Vertex parent = vertexes.get(j);
                    current.getParents().add(parent);
                }
            }
            //linking children
            for (int j = (i+1); j < count; j++){
                double link = adjacencyMatrix.getData(i,j);
                boolean isEdge = link == 1. ? true : false;
                if (isEdge){
                    Vertex child = vertexes.get(j);
                    current.getChildren().add(child);
                }
            }
        }
    }

    public Graph<Vertex, String> getGraph(){
        Graph<Vertex, String> g = new SparseMultigraph<Vertex, String>();
        //вершины графа
        for (Vertex v : vertexes){
            g.addVertex(v);
        }

        int i = 0;
        //связи по дочерним вершинам
        for (Vertex v : vertexes){
            for (Vertex c : v.getChildren()){
                g.addEdge("Link"+i,v,c);
                i++;
            }
        }
        return g;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(Vertex startVertex) {
        this.startVertex = startVertex;
    }

    public Vertex getEndVertex() {
        return endVertex;
    }

    public void setEndVertex(Vertex endVertex) {
        this.endVertex = endVertex;
    }
}
