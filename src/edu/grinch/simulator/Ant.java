package edu.grinch.simulator;

import edu.grinch.graph.GraphWays;
import edu.grinch.graph.Vertex;
import edu.grinch.linearalgebra.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Author Grinch
 * Date: 18.05.14
 * Time: 11:29
 */
public class Ant {
    public static final double KOEF_K = 10.;
    public static final double ALPHA = 2.;
    public static final double BETA = 0;
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
                Vector P = getP(children);
                double s = 0;
                for (int i = 0; i < P.getCount(); i++){
                    s += P.getData(i);
                }
                for (int i = 0; i < P.getCount(); i++){
                    P.setData(i,P.getData(i)/s);
                    if (i != 0) P.setData(i,P.getData(i)+P.getData(i-1));
                }
                Random r = new Random();
                double x = r.nextDouble();
                int winIndex = 0;
                for (int i = 0; i < P.getCount(); i++){
                    if (i == 0){
                        if (x >= 0 & x < P.getData(i)){
                            winIndex = i;
                            break;
                        }
                    }else{
                        if (x >= P.getData(i-1) & x < P.getData(i)){
                            winIndex = i;
                            break;
                        }
                    }
                }
                Vertex nextVertex = availableChildren.get(winIndex); //КОСТЫЛЬ, ПЕРЕПИСАТЬ ПО ФОРМУЛЕ!!!!!!!!!!!!
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

    private Vector getP(List<Vertex> children){
        Vector p = new Vector(children.size());
        double s = 0;
        for (Vertex v : children){
            s += 1./Math.pow(v.getWeight(),Ant.BETA) + Math.pow(v.getPheromone(),Ant.ALPHA);
        }
        for (int i = 0; i < p.getCount(); i++){
            double c = 1./Math.pow(children.get(i).getWeight(),Ant.BETA) + Math.pow(children.get(i).getPheromone(),Ant.ALPHA);
            p.setData(i,c/s);
        }
        return p;
    }

    private void increasePheromone(){
        double pheromone = currentPosition.getPheromone();
        pheromone += Ant.KOEF_K/tabuList.size();
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
