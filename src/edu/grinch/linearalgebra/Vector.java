package edu.grinch.linearalgebra;

/**
 * Author Grinch
 * Date: 03.05.14
 * Time: 15:05
 */
public class Vector {
    private double[] data;
    private int count;

    public int getCount() {
        return count;
    }

    public Vector(int count){
        this.count = count;
        data = new double[count];
    }

    public Vector(Vector X){
        copy(X);
    }

    public Vector(double[] data){
        this.count = data.length;
        this.data = new double[count];
        for (int i = 0; i < count; i++){
            this.data[i] = data[i];
        }
    }

    @Override
    public boolean equals(Object X){
        for (int i = 0; i < data.length; i++){
            if (data[i] != ((Vector)X).getData(i)) return false;
        }
        return true;
    }

    public void copy(Vector X){
        this.count = X.getCount();
        data = new double[count];
        for (int i = 0; i < count; i++){
            data[i] = X.getData(i);
        }
    }

    public double getData(int pos){
        return data[pos];
    }

    public void setData(int pos, double value){
        data[pos] = value;
    }

    public double toDouble(){
        double d = 0;
        for (int i=0; i < count; i++) d += getData(i);
        return d;
    }

    public Matrix toMatrix(){
        Matrix m = new Matrix(1,getCount());
        m.setData(new Vector[]{this});
        return m;
    }

    public double length(){
        double l = 0;
        for (double d : data){
            l += Math.pow(d,2);
        }
        return Math.sqrt(l);
    }

    public void normalize(){
        double length = length();
        for (int i = 0; i < data.length; i++){
            data[i] /= length;
        }
    }

    public double getMaxItem(){
        double max = Double.NEGATIVE_INFINITY;
        for (double d : data){
            if (d > max){
                max = d;
            }
        }
        return max;
    }

    public int getMaxItemIndex(){
        int index = 0;
        double max = getMaxItem();
        for (int i = 0; i < data.length; i++){
            double d = data[i];
            if (d == max){
                index = i;
                break;
            }
        }
        return index;
    }

    public void zeros(){
        data = new double[data.length];
    }
}
