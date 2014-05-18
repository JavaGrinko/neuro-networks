package edu.grinch.simulator;

import edu.grinch.graph.GraphWays;
import edu.grinch.linearalgebra.Matrix;

/**
 * Author Grinch
 * Date: 18.05.14
 * Time: 12:12
 */
public class Simulator {
    private GraphWays graphWays;
    private Ant ant;
    private int time;
    public Simulator(){
        graphWays = new GraphWays(new Matrix(new double[][]{{0,1,1,0,0},
                                                    {1,0,0,1,0},
                                                    {1,0,0,1,0},
                                                    {0,1,1,0,1},
                                                    {0,0,0,1,0}}));
        ant = new Ant(graphWays);
    }

    public void simulate(){
        for (time = 0; time < 100; time++){
            ant.move();
        }
    }

    public GraphWays getGraphWays() {
        return graphWays;
    }

    public void setGraphWays(GraphWays graphWays) {
        this.graphWays = graphWays;
    }

    public Ant getAnt() {
        return ant;
    }

    public void setAnt(Ant ant) {
        this.ant = ant;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
