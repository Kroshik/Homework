package ru.sberbank.homework.dergun;

import ru.sberbank.homework.common.Calculator;

import java.io.IOException;
import java.util.Locale;

public class ProxyCalculator implements Calculator {
    private static final double DBL_EPSILON = 1e-10;
    private static final String QUIT = "quit";
    private final EngineCalculator calculator = new EngineCalculatorImpl();
    private boolean firstScan = true;

    @Override
    public String calculate(String line) {
        final String[] split = line.split(" ");
        if (split.length == 1 && split[0].equals(QUIT)) {
            System.exit(0);
        }
        if (firstScan && split.length != 3) {
            return "error > wrong expression";
        }
        if (split.length == 2) {
            return oneArgument(calculator.getValue(), split[0], split[1]);
        }
        if (split.length == 3) {
            firstScan = false;
            return twoArgument(split[0], split[1], split[2]);
        }
        return "error > wrong expression";
    }

    private String twoArgument(String A, String operator, String B) {
        double doubleA;
        try {
            doubleA = parseDouble(A);
        } catch (Exception e) {
            return "error > " + A;
        }
        return oneArgument(doubleA, operator, B);
    }

    private String oneArgument(double doubleA, String operator, String B) {
        Operation operation;
        try {
            operation = Operation.parse(operator.charAt(0));
        } catch (IllegalArgumentException e) {
            return "error > wrong expression";
        }

        double doubleB;
        try {
            doubleB = parseDouble(B);
        } catch (Exception e) {
            return "error > " + B;
        }
        calculator.calculate(doubleA, operation, doubleB);
        return getValue();
    }

    private String getValue() {
        final double result = calculator.getValue();
        if (Math.abs(result - (long) result) <= DBL_EPSILON) {
            return Long.toString((long) result);
        }
        String res = String.format(Locale.ROOT, "%.2f", result);
        if (res.charAt(res.length() - 1) == '0') {
            res = res.substring(0, res.length() - 1);
            if (res.charAt(res.length() - 1) == '0') {
                res = res.substring(0, res.length() - 2);
            }
        }
        return res;
    }

    private double parseDouble(String s) throws IOException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        if (isValidNumber(s)) {
            return Parser.parseValue(s);
        }
        throw new IllegalAccessException();
    }

    private boolean isValidNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && (s.charAt(0) == '-' || s.charAt(0) == '+')) {
                continue;
            }
            if (i > 0 && isExponent(s.charAt(i - 1), s.charAt(i))) {
                ++i;
                continue;
            }
            if (!isValidSymbol(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isExponent(char one, char two) {
        return (one == 'e' || one == 'E') && (two == '+' || two == '-');
    }

    private boolean isValidSymbol(char symbol) {
        char[] alphabet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D',
                'E', 'F', 'l', 'L', 'x', 'X', '_', '#', ' ', '.'};
        for (char ch : alphabet) {
            if (symbol == ch || Character.isDigit(symbol)) {
                return true;
            }
        }
        return false;
    }
}