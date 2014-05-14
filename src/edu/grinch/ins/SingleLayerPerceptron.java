package edu.grinch.ins;

import edu.grinch.linearalgebra.Matrix;
import edu.grinch.linearalgebra.Operations;
import edu.grinch.linearalgebra.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author Grinch
 * @Date: 04.05.14
 * @Time 15:44
 */
public class SingleLayerPerceptron {
    final double defaultW = 0.01;
    final double eta = 0.01;
    public Matrix W;
    private List<Neuron> neurons;
    private int neuronCount;
    private int inputCount;
    public Vector Y;

    public SingleLayerPerceptron(int neuronCount, int inputCount){
        this.neuronCount = neuronCount;
        this.inputCount = inputCount;
        initNeurons();
        initW();
        Y = new Vector(neuronCount);
    }

    private void initW(){
        W = new Matrix(neuronCount,inputCount);
        for (int i = 0; i < W.getHeight(); i++){
            for (int j = 0; j < W.getWidth(); j++){
                W.setData(i,j,defaultW);
            }
        }
    }

    private void initNeurons(){
        neurons = new LinkedList<Neuron>();
        for (int i = 0; i < neuronCount; i++){
            Neuron neuron = new Neuron();
            neuron.setFunction(new ActivateFunction() {
                @Override
                public double f(double s) {
                    return s;
                }
            });
            neurons.add(neuron);
        }
    }

    private void training(Vector X, Vector R){
        Vector S = Operations.transpose(Operations.multiplication(W,Operations.transpose(X.toMatrix()))).toVector();
        Y = new Vector(S.getCount());
        for (int i = 0; i < S.getCount(); i++){
            Y.setData(i,neurons.get(i).calc(S.getData(i)));
        }
        for (int i = 0; i < W.getHeight(); i++){
            for (int j = 0; j < W.getWidth(); j++){
                double sigma = Y.getData(i) - R.getData(i);
                W.setData(i,j,W.getData(i,j)-eta*X.getData(j)*sigma);
            }
        }
    }

    public Vector combat(Vector X){
        Vector S = Operations.transpose(Operations.multiplication(W,Operations.transpose(X.toMatrix()))).toVector();
        Y = new Vector(S.getCount());
        for (int i = 0; i < S.getCount(); i++){
            Y.setData(i,neurons.get(i).calc(S.getData(i)));
        }
        return Y;
    }

    public Matrix getW() {
        return W;
    }

    public void setW(Matrix w) {
        W = w;
    }

    public Vector getY() {
        return Y;
    }

    public Neuron getNeurons(int pos) {
        return neurons.get(pos);
    }

    public int getInputCount() {
        return inputCount;
    }

    public int getNeuronCount() {
        return neuronCount;
    }
}
