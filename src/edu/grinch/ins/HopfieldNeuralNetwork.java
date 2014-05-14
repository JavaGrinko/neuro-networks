package edu.grinch.ins;

import edu.grinch.linearalgebra.Matrix;
import edu.grinch.linearalgebra.Operations;
import edu.grinch.linearalgebra.Vector;

import java.util.Random;

/**
 * Author Grinch
 * Date: 11.05.14
 * Time: 17:43
 */
public class HopfieldNeuralNetwork extends SingleLayerPerceptron {

    Random random = new Random();

    public HopfieldNeuralNetwork(int inputCount){
        super(inputCount, inputCount);
        for (int i = 0; i < getNeuronCount(); i++){
            getNeurons(i).setFunction(new ActivateFunction() {
                @Override
                public double f(double s) {
                    if (s >= 0){
                        return 1.;
                    }else{
                        return -1.;
                    }
                }
            });
        }
    }

    public void training(Vector X){
        Matrix deltaX = Operations.multiplication(Operations.transpose(X.toMatrix()),X.toMatrix());
        deltaX = Operations.multiplication(deltaX, 1./getInputCount());
        deltaX = Operations.transpose(deltaX);
        setW(Operations.addition(getW(),deltaX));
        for (int i = 0; i < getW().getHeight(); i++){
            getW().setData(i, i, 0);
        }
    }

    @Override
    public Vector combat(Vector X){
        final int MAX_REPEATS = 100;
        Vector I = new Vector(X);
        int count = 0;
        do{
            int pos = random.nextInt(getNeuronCount());
            super.combat(I);
            if (I.getData(pos) == Y.getData(pos)) count++;
            I.setData(pos,Y.getData(pos));
        }while (count < MAX_REPEATS);
        Y.copy(I);
        return Y;
    }
}
