package edu.grinch.simulator;

import edu.grinch.graph.GraphWays;
import edu.grinch.graph.Vertex;

import java.util.LinkedList;
import java.util.List;

/**
 * Author Grinch
 * Date: 18.05.14
 * Time: 11:29
 */
public class Ant {
    public static final double PHEROMONE_INTENSIVE = 0.1;
    private GraphWays graphWays;
    private List<Vertex> tabuList = new LinkedList<Vertex>();
    private Vertex currentPosition;
    private boolean isTailDown;
    private boolean isBack;

    public Ant(GraphWays graphWays){
        this.graphWays = graphWays;
        currentPosition = graphWays.getStartVertex();
    }

    public void move(){
        //если хвост поднят - увеличиваем объем феромонов на этой вершине
        if (isTailDown()){
            increasePheromone();
        }

        //если идем не назад, а к пище
        if (!isBack){
            //ищем дочерние вершины
            List<Vertex> children = currentPosition.getChildren();
            List<Vertex> availableChildren = new LinkedList<Vertex>();
            for (Vertex v : children){
                if (!isInTabuList(v)){
                    availableChildren.add(v);
                }
            }

            if (availableChildren.size() == 0){
                //зашли в тупик, идем обратно
                isBack = true;
            }else{
                //ищим наилучший вариант хода в вероятностном смысле
                Vertex nextVertex = availableChildren.get(0); //КОСТЫЛЬ, ПЕРЕПИСАТЬ ПО ФОРМУЛЕ!!!!!!!!!!!!
                tabuList.add(currentPosition);
                currentPosition = nextVertex;

                /* если мы перешли на финишную вершину - радуемся и опускаем хвост,
                   посыпая дорожку феромонами
                 */
                if (currentPosition == graphWays.getEndVertex()){
                    isBack = true;
                    isTailDown = true;
                }
            }
        }else{
            //идем назад по тому же пути
            currentPosition = tabuList.get(tabuList.size()-1);
            tabuList.remove(currentPosition);

            //если вернулись на исходную - поднимаем хвост и идем опять за едой
            if (currentPosition == graphWays.getStartVertex()){
                isBack = false;
                isTailDown = false;
            }
        }
    }

    private void increasePheromone(){
        double pheromone = currentPosition.getPheromone();
        pheromone += Ant.PHEROMONE_INTENSIVE;
        currentPosition.setPheromone(pheromone);
    }

    private boolean isInTabuList(Vertex vertex){
        for (Vertex v : tabuList){
            if (v == vertex){
                return true;
            }
        }
        return false;
    }

    public Vertex getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Vertex currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isTailDown() {
        return isTailDown;
    }

    public void setTailDown(boolean isTailDown) {
        this.isTailDown = isTailDown;
    }

    public boolean isBack() {
        return isBack;
    }

    public void setBack(boolean isBack) {
        this.isBack = isBack;
    }
}
