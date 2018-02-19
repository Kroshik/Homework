package ru.sberbank.homework.dergun;

public class EngineCalculatorImpl implements EngineCalculator {
    private double result = 0;

    @Override
    public double getValue() {
        return result;
    }

    @Override
    public void calculate(double a, Operation operation, double b) {
        result = operation.apply(a, b);
    }
}
