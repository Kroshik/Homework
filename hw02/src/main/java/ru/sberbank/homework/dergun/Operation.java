package ru.sberbank.homework.dergun;

import java.util.function.BiFunction;

public enum Operation {
    PLUS('+', (l, r) -> l + r),
    MINUS('-', (l, r) -> l - r),
    MULTIPLY('*', (l, r) -> l * r),
    DIVIDE('/', (l, r) -> l / r);

    private char symbol;
    private BiFunction<Double, Double, Double> operator;

    Operation(char symbol, BiFunction<Double, Double, Double> operator) {
        this.symbol = symbol;
        this.operator = operator;
    }

    public static Operation parse(char s) {
        for (Operation operation : values()) {
            if (operation.symbol == s) {
                return operation;
            }
        }

        throw new IllegalArgumentException("Invalid operator");
    }

    public double apply(double a, double b) {
        return operator.apply(a, b);
    }
}
