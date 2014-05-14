package edu.grinch.ins;

/**
 * Author Grinch
 * Date: 04.05.14
 * Time: 15:36
 */
public class Neuron {
    private ActivateFunction function;
    private double result;

    public ActivateFunction getFunction() {
        return function;
    }

    public void setFunction(ActivateFunction function) {
        this.function = function;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double calc(double s){
        setResult(function.f(s));
        return getResult();
    }
}
