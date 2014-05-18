package edu.grinch.simulator;

import edu.grinch.graph.GraphWays;
import edu.grinch.graph.Vertex;
import edu.grinch.linearalgebra.Matrix;

import java.util.LinkedList;
import java.util.List;

/**
 * Author Grinch
 * Date: 18.05.14
 * Time: 12:12
 */
public class Simulator {
    public static final int MAX_ANTS = 10;
    private GraphWays graphWays;
    private List<Ant> ants = new LinkedList<Ant>();
    private int steps = 0;
    public Simulator(){
        graphWays = new GraphWays(new Matrix(new double[][]{
          //0 1 2 3 4 5 6 7 8 910111213
           {0,1,0,0,0,0,0,0,0,0,0,0,0,0},//0
           {1,0,1,0,1,0,0,0,0,0,0,0,0,0},//1
           {0,1,0,1,0,0,0,0,0,0,0,0,0,0},//2
           {0,0,1,0,0,1,0,0,0,0,0,0,0,0},//3
           {0,1,0,0,0,0,1,0,0,0,0,0,0,0},//4
           {0,0,0,1,0,0,0,0,0,0,0,0,0,0},//5
           {0,0,0,0,1,0,0,1,1,0,0,0,0,0},//6
           {0,0,0,0,0,0,1,0,0,0,0,0,0,1},//7
           {0,0,0,0,0,0,1,0,0,1,0,0,0,0},//8
           {0,0,0,0,0,0,0,0,1,0,1,0,0,0},//9
           {0,0,0,0,0,0,0,0,0,1,0,1,0,0},//10
           {0,0,0,0,0,0,0,0,0,0,1,0,1,0},//11
           {0,0,0,0,0,0,0,0,0,0,0,1,0,1},//12
           {0,0,0,0,0,0,0,1,0,0,0,0,1,0},//13
        }));
        ants.add(new Ant(graphWays));
    }

    public void move(){
        steps++;
        graphWays.decreaseAllPheromones();
        for (Ant a : ants){
            a.move();
        }
        if (ants.size() < MAX_ANTS){
            if (steps % 2 == 0){
                ants.add(new Ant(graphWays));
            }
        }
    }


    public int getAntCount(Vertex v){
        int c = 0;
        for (Ant a : ants){
            if (a.getCurrentPosition() == v) c++;
        }
        return c;
    }

    public GraphWays getGraphWays() {
        return graphWays;
    }

    public void setGraphWays(GraphWays graphWays) {
        this.graphWays = graphWays;
    }

    public List<Ant> getAnt() {
        return ants;
    }

    public void setAnt(List<Ant> ants) {
        this.ants = ants;
    }
}
