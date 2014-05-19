package edu.grinch.simulator;

import edu.grinch.graph.Vertex;
import edu.grinch.ins.KohonensNeuralNetwork;
import edu.grinch.ins.SingleLayerPerceptron;
import edu.grinch.linearalgebra.Vector;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Author Grinch
 * Date: 18.05.14
 * Time: 11:29
 */
public class Ant {
    public static final double KOEF_K = 1.;
    public static final double ALPHA = 1.;
    public static final double BETA = 0.;
    public static final double ALIVE = 0;
    public static final double DEAD = 1;
    public static int home;
    public static int food;
    private static KohonensNeuralNetwork knn;
    private static SingleLayerPerceptron slp;
    private List<Vertex> tabuList = new LinkedList<Vertex>();
    private Vertex currentPosition;
    private int lastCount;
    private boolean isTailDown;
    private boolean isBack;
    private Color color;
    private double status = ALIVE;
    private Random random = new Random();

    static {
        setKnn(new KohonensNeuralNetwork(36,10));
        setSlp(new SingleLayerPerceptron(1,10));
        initialTraining();
    }

    private static void initialTraining() {
        getKnn().training(ObjectBox.getObject(ObjectBox.IMAGE_HOME).collectToVector()); //обучаем дому
        home = getKnn().getY().getMaxItemIndex();
        getKnn().training(ObjectBox.getObject(ObjectBox.IMAGE_FOOD).collectToVector()); //обучаем еде
        food = getKnn().getY().getMaxItemIndex();
    }

    public Ant(Vertex location){
        currentPosition = location;
        Random r = new Random();
        color = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256),r.nextInt(256));
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
                    P.setData(i, P.getData(i) / s);
                    if (i != 0) P.setData(i, P.getData(i) + P.getData(i-1));
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
                Vertex nextVertex = availableChildren.get(winIndex);
                tabuList.add(currentPosition);
                currentPosition = nextVertex;

                //есть ли на клетке образ?
                if (currentPosition.getGameObject() != null){
                    /*
                        Распознаем образ с помощью сети Коханена
                     */
                    knn.training(currentPosition.getGameObject().collectToVector());
                    Vector Y = knn.getY();
                    int cluster = Y.getMaxItemIndex();
                    currentPosition.getGameObject().setClusterID(cluster);
                    currentPosition.getGameObject().setCurrentDangerous(getSlp().combat(Y).getData(0));
                    if (cluster == Ant.food){
                        isBack = true;
                        isTailDown = true;
                        lastCount = tabuList.size();
                    }else{
                        /*
                            опрашиваем нейросеть, принимаем решение:
                            рисковать пройтись по объекту или развернуться
                         */
                        if (isMustToRisk(Y)){
                            /*
                                Оправдался ли наш риск исходя из реальной природы объекта?
                             */
                            Vector R = new Vector(1);
                            if (isDead(currentPosition.getGameObject())){
                                R.setData(0,Ant.DEAD);
                                setStatus(DEAD);
                            }else{
                                R.setData(0,Ant.ALIVE);
                            }

                            //нейросеть получает опыт от события
                            getSlp().training(Y,R);
                        }else{
                            //разворачиваемся назад
                            isBack = true;
                        }
                    }
                }
            }
        }else{
            //идем назад по тому же пути
            currentPosition = tabuList.get(tabuList.size()-1);
            tabuList.remove(currentPosition);


            if (currentPosition.getGameObject() != null){
                    /*
                        Распознаем образ с помощью сети Коханена
                     */
                knn.training(currentPosition.getGameObject().collectToVector());
                int cluster = knn.getY().getMaxItemIndex();
                currentPosition.getGameObject().setClusterID(cluster);
                currentPosition.getGameObject().setCurrentDangerous(getSlp().combat(knn.getY()).getData(0));
                //если вернулись на исходную - поднимаем хвост и идем опять за едой
                if (cluster == Ant.home){
                    isBack = false;
                    isTailDown = false;
                }else{
                    if (isDead(currentPosition.getGameObject())){
                        setStatus(DEAD);
                    }else{
                        //currentPosition.getGameObject().decreaseDangerous();
                    }
                    //нейросеть не получает опыт от события, действие было вынужденным
                }
            }
        }
    }

    private boolean isMustToRisk(Vector X){
        Vector Y = getSlp().combat(X);
        double dangerous = Y.getData(0);
        dangerous = dangerous > 0.99 ? 1 : dangerous;
        double r = random.nextDouble();
        if (r <= dangerous){
            return false;
        }else{
            return true;
        }
    }

    private boolean isDead(GameObject object){
        double dangerous = object.getDangerous();
        double r = random.nextDouble();
        if (r <= dangerous){
            return true;
        }else{
            return false;
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
        pheromone += Ant.KOEF_K/lastCount;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static KohonensNeuralNetwork getKnn() {
        return knn;
    }

    public static void setKnn(KohonensNeuralNetwork knn) {
        Ant.knn = knn;
    }

    public static SingleLayerPerceptron getSlp() {
        return slp;
    }

    public static void setSlp(SingleLayerPerceptron slp) {
        Ant.slp = slp;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }
}
