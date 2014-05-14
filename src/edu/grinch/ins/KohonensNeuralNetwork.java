package edu.grinch.ins;

import edu.grinch.linearalgebra.Operations;
import edu.grinch.linearalgebra.Vector;

/**
 * Author Grinch
 * Date: 10.05.14
 * Time: 17:20
 */
public class KohonensNeuralNetwork extends SingleLayerPerceptron{

    public KohonensNeuralNetwork(int neuronCount, int inputCount) {
        super(neuronCount, inputCount);
        W.normalize();
    }

    public void training(Vector X){
        combat(X);
        Vector Wv = W.getVector(Y.getMaxItemIndex());
        Vector k = Operations.multiplication(Wv,-1.);
        k = Operations.addition(k,X);
        k = Operations.multiplication(k,eta);
        k = Operations.addition(Wv,k);
        k.normalize();
        W.setVector(Y.getMaxItemIndex(),k);
    }

    @Override
    public Vector combat(Vector X){
        X.normalize();
        super.combat(X);
        int index = Y.getMaxItemIndex();
        Y.zeros();
        Y.setData(index,1);
        return Y;
    }
}
