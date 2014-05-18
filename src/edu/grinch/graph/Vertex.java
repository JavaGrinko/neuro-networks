package edu.grinch.graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Author Grinch
 * Date: 18.05.14
 * Time: 10:14
 */
public class Vertex {
    public static final double DEFAULT_WEIGHT = 1.;
    public static final double EPS = 0.01;
    private int id;
    private List<Vertex> parents = new LinkedList<Vertex>();
    private List<Vertex> children = new LinkedList<Vertex>();
    private double pheromone;

    private double weight;

    public Vertex(int id){
        this.id = id;
        weight = Vertex.DEFAULT_WEIGHT;
    }

    public void decreasePheromone(){
        pheromone = pheromone <= 0 ? 0:(1-Vertex.EPS)*pheromone;
    }

    public List<Vertex> getParents() {
        return parents;
    }

    public void setParents(List<Vertex> parents) {
        this.parents = parents;
    }

    public List<Vertex> getChildren() {
        return children;
    }

    public void setChildren(List<Vertex> children) {
        this.children = children;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPheromone() {
        return pheromone;
    }

    public void setPheromone(double pheromone) {
        this.pheromone = pheromone;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String toString(){
        return String.valueOf("ID: "+getId());
    }
}