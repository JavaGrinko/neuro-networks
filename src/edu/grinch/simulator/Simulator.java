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
    public static final int MAX_ANTS = 100;
    public static final int RESPAWN_TIME = 5;

    private GraphWays graphWays;
    private List<Ant> ants = new LinkedList<Ant>();
    private int steps = 0;
    public Simulator(){
        /*graphWays = new GraphWays(new Matrix(new double[][]{
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
        }));*/
        graphWays = new GraphWays(new Matrix(new double[][]{
                //0 1 2 3 4 5 6 7 8 910111213141516171819202122232425262728293031323334353637383940414243
                {0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//0
                {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//1
                {0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//2
                {1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//3
                {0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//4
                {0,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//5
                {0,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//6
                {0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//7
                {0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//8
                {0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//9
                {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//10
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//11
                {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//12
                {0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//13
                {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//14
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//15
                {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//16
                {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//17
                {0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//18
                {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//19
                {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},//20
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//21
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//22
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//23
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},//24
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0},//25
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},//26
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//27
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0},//28
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},//29
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},//30
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0},//31
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},//32
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},//33
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},//34
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0},//35
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0},//36
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},//37
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},//38
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0},//39
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},//40
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},//41
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1},//42
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,0},//43
        }));
        ants.add(new Ant(graphWays.getStartVertex()));
        initGameObjects();
    }

    private void initGameObjects() {
        Vertex home = graphWays.getStartVertex();
        home.setGameObject(ObjectBox.getObjectWithNoise(ObjectBox.IMAGE_HOME));
        Vertex food = graphWays.getEndVertex();
        food.setGameObject(ObjectBox.getObjectWithNoise(ObjectBox.IMAGE_FOOD));

        Vertex one = graphWays.getVertexes().get(38);
        one.setGameObject(ObjectBox.getObjectWithNoise(ObjectBox.IMAGE_SQUARE,0));
        Vertex two = graphWays.getVertexes().get(15);
        two.setGameObject(ObjectBox.getObjectWithNoise(ObjectBox.IMAGE_CROSS,1));
        Vertex three = graphWays.getVertexes().get(7);
        three.setGameObject(ObjectBox.getObjectWithNoise(ObjectBox.IMAGE_SQUARE,0));
        /*Vertex four = graphWays.getVertexes().get(20);
        four.setGameObject(ObjectBox.getObjectWithNoise(ObjectBox.IMAGE_CROSS,1.0));*/
    }

    public void move(){
        steps++;
        graphWays.decreaseAllPheromones();
        for (Ant a : ants){
            if (a.getStatus() == Ant.ALIVE){
                a.move();
            }else{
                //ants.remove(a);
            }
        }
        if (ants.size() < MAX_ANTS){
            if (steps % RESPAWN_TIME == 0){
                ants.add(new Ant(graphWays.getStartVertex()));
            }
        }
    }

    public Ant getFirstAnt(Vertex v){
        for (Ant a : ants){
            if (a.getCurrentPosition() == v)
                return a;
        }
        return null;
    }

    public int getAntCount(Vertex v){
        int c = 0;
        for (Ant a : ants){
            if (a.getStatus() == Ant.ALIVE && a.getCurrentPosition() == v) c++;
        }
        return c;
    }

    public int getAliveAnts(){
        int c = 0;
        for (Ant a : ants){
            if (a.getStatus() == Ant.ALIVE) c++;
        }
        return c;
    }

    public int getDeadAnts(){
        int c = 0;
        for (Ant a : ants){
            if (a.getStatus() == Ant.DEAD) c++;
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
