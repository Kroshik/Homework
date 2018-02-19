package ru.sberbank.homework.dergun;

import ru.sberbank.homework.common.Calculator;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ScannerCalculator {
    private final Calculator calculator;
    private final Scanner input;
    private final PrintStream output;

    public ScannerCalculator(Calculator calculator, InputStream input, PrintStream output) {
        this.calculator = calculator;
        this.input = new Scanner(input);
        this.output = output;
    }

    public void start() {
        while (true) {
            final String line = input.nextLine();
            final String result = calculator.calculate(line);
            output.println(result);
        }
    }
}
