package edu.grinch.linearalgebra;

public class Matrix{
    private Vector[] data;
    private int height;
    private int width;

    public Vector getVector(int pos){
        return data[pos];
    }

    public void setVector(int pos, Vector v){
        data[pos] = v;
    }

    public void setData(Vector[] data) {
        this.data = data;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Matrix(int height, int width){
        this.height = height;
        this.width = width;
        data = new Vector[height];
        for (int i = 0; i < height; i++){
            data[i] = new Vector(width);
        }
    }

    public Matrix (double[][] data){
        this.height = data.length;
        this.width = data[0].length;
        this.data = new Vector[height];
        for (int i = 0; i < height; i++){
            this.data[i] = new Vector(data[i]);
        }
    }

    public void setData(int i, int j, double value){
        Vector v = data[i];
        v.setData(j,value);
    }

    public double getData(int i, int j){
        Vector v = data[i];
        return v.getData(j);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (int i=0; i < height; i++){
            for (int j=0; j < width; j++)
                s.append(getData(i,j)).append(" ");
            s.append("\n");
        }
        return s.toString();
    }

    public Vector toVector(){
        return data[0];
    }

    public void normalize(){
        for (Vector v : data){
            v.normalize();
        }
    }
}