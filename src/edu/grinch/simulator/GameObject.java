package edu.grinch.simulator;

import edu.grinch.linearalgebra.Matrix;
import edu.grinch.linearalgebra.Vector;

/**
 * Author Grinch
 * Date: 19.05.14
 * Time: 10:00
 */
public class GameObject extends Matrix{
    public static final double DEC_STEP = 0.05;

    private int clusterID = -1;
    private double dangerous;
    private double currentDangerous;

    public GameObject(double[][] data) {
        super(data);
    }

    public GameObject(double[][] data, double dangerous) {
        super(data);
        setDangerous(dangerous);
    }

    public String toString(){
        return clusterID <= -1 ? "Unknown" : ("Object "+clusterID);
    }

    public String toPrint(){
        return super.toString();
    }

    public Vector collectToVector(){
        Vector v = new Vector(getWidth()*getHeight());
        for (int i=0; i < getHeight(); i++){
            for (int j=0; j < getWidth(); j++)
                v.setData(j + i * getWidth(), getData(i, j));
        }
        return v;
    }

    public int getClusterID() {
        return clusterID;
    }

    public void setClusterID(int clusterID) {
        this.clusterID = clusterID;
    }

    public double getDangerous() {
        return dangerous;
    }

    public void setDangerous(double dangerous) {
        this.dangerous = dangerous;
    }

    public void setCurrentDangerous(double currentDangerous) {
        this.currentDangerous = currentDangerous;
    }

    public double getCurrentDangerous() {
        return currentDangerous;
    }
}
