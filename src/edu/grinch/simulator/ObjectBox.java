package edu.grinch.simulator;

import java.util.Random;

/**
 * Author Grinch
 * Date: 19.05.14
 * Time: 10:33
 */
public class ObjectBox {
    public static final int NOISE_LEVEL = 0;
    public static final double[][] IMAGE_HOME = new double[][]{
            {0,0,1,1,0,0},
            {0,1,0,0,1,0},
            {1,1,1,1,1,1},
            {1,0,0,0,0,1},
            {1,0,0,0,0,1},
            {1,1,1,1,1,1}
    };
    public static final double[][] IMAGE_FOOD = new double[][]{
            {0,0,0,0,0,1},
            {0,0,1,1,1,0},
            {0,1,1,1,1,0},
            {1,1,1,1,1,0},
            {1,1,1,1,0,0},
            {1,1,1,0,0,0}
    };
    public static final double[][] IMAGE_SQUARE = new double[][]{
            {1,1,1,1,1,1},
            {1,0,0,0,0,1},
            {1,0,0,0,0,1},
            {1,0,0,0,0,1},
            {1,0,0,0,0,1},
            {1,1,1,1,1,1}
    };
    public static final double[][] IMAGE_CROSS = new double[][]{
            {1,0,0,0,0,1},
            {0,1,0,0,1,0},
            {0,0,1,1,0,0},
            {0,0,1,1,0,0},
            {0,1,0,0,1,0},
            {1,0,0,0,0,1}
    };

    public static GameObject getObjectWithNoise(double[][] data){
        return getObjectWithNoise(data,0);
    }

    public static GameObject getObject(double[][] data){
        return getObject(data,0);
    }

    public static GameObject getObjectWithNoise(double[][] data, double dangerous){
        double[][] d = new double[data.length][];
        for(int i = 0; i < data.length; i++)
            d[i] = data[i].clone();
        for (int i = 0; i < NOISE_LEVEL; i++){
            Random r = new Random();
            int x = r.nextInt(d.length);
            int y = r.nextInt(d[0].length);
            d[x][y] = d[x][y] == 0 ? 1 : 0;
        }
        GameObject obj = new GameObject(d, dangerous);
        return obj;
    }

    public static GameObject getObject(double[][] data, double dangerous){
        GameObject obj = new GameObject(data, dangerous);
        return obj;
    }
}
