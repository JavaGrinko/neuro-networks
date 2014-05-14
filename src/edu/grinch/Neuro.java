package edu.grinch;

import edu.grinch.ins.HopfieldNeuralNetwork;
import edu.grinch.ins.KohonensNeuralNetwork;
import edu.grinch.ins.SingleLayerPerceptron;
import edu.grinch.linearalgebra.Matrix;
import edu.grinch.linearalgebra.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Author Grinch
 * Date: 03.05.14
 * Time: 14:48
 */
public class Neuro {

    public static void main(String[] args){
        //slp();
        //knn();
        hnn();
    }

    static void hnn(){
        HopfieldNeuralNetwork hnn = new HopfieldNeuralNetwork(9);

        Vector v = new Vector(9);
        v.setData(0,1);v.setData(1,-1);v.setData(2,1);
        v.setData(3,-1);v.setData(4,1);v.setData(5,-1);
        v.setData(6,1);v.setData(7,-1);v.setData(8,1);
        hnn.training(v);

        v.setData(0,1);v.setData(1,1);v.setData(2,1);
        v.setData(3,-1);v.setData(4,-1);v.setData(5,1);
        v.setData(6,-1);v.setData(7,-1);v.setData(8,1);
        hnn.training(v);

        v.setData(0,1);v.setData(1,-1);v.setData(2,-1);
        v.setData(3,-1);v.setData(4,1);v.setData(5,-1);
        v.setData(6,-1);v.setData(7,-1);v.setData(8,1);

        Vector R = hnn.combat(v);
        Matrix k = new Matrix(new double[][]{{R.getData(0),R.getData(1),R.getData(2)},
                                             {R.getData(3),R.getData(4),R.getData(5)},
                                             {R.getData(6),R.getData(7),R.getData(8)}});
        System.out.println(k);
    }

    static void knn(){
        KohonensNeuralNetwork knn = new KohonensNeuralNetwork(9,3);
        Vector v = new Vector(3);
        v.setData(0,0);
        v.setData(1,1);
        v.setData(2,0);
        knn.training(v);

        v = new Vector(3);
        v.setData(0,1);
        v.setData(1,0);
        v.setData(2,1);
        knn.training(v);

        v = new Vector(3);
        v.setData(0,1);
        v.setData(1,1);
        v.setData(2,1);
        knn.training(v);

        v = new Vector(3);
        v.setData(0,0);
        v.setData(1,1);
        v.setData(2,0);
        knn.training(v);
    }

    static void slp(){
        SingleLayerPerceptron slp = new SingleLayerPerceptron(3,2);
        Vector X = new Vector(2);
        double eps = 1;
        double delta;

        List<Vector> samples = new LinkedList<Vector>();
        List<Double> results = new LinkedList<Double>();
        Random r = new Random();

        for (int i = 0; i < 1500; i++){
            double arg1 = r.nextDouble()*5;
            double arg2 = r.nextDouble()*5;
            Vector v = new Vector(2);
            v.setData(0,arg1);
            v.setData(1,arg2);
            samples.add(v);
            results.add(f(arg1,arg2));
        }

        do{
            delta = 0;
            for (int i = 0; i < samples.size(); i++){
                //slp.training(samples.get(i),results.get(i));
                delta += Math.abs(results.get(i)-slp.getY().toDouble());
            }

            delta /= samples.size();
        }while (delta > eps);

        X.setData(0,3);
        X.setData(1,2);
        System.out.println(slp.combat(X).toDouble());
    }

    public static double f(double x, double y){
        return x;
    }
}
