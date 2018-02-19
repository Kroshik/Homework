package ru.sberbank.homework.checker;

import lombok.extern.slf4j.Slf4j;
import ru.sberbank.homework.common.Calculator;

@Slf4j
public class SolutionChecker {

    private final CommandSequence[] sequences = {
            cs(ex("2 + 2", "4"), ex("- 1", "3"),  ex("* 2", "6"),  ex("/ 4", "1.5"),  ex("1 - 1", "0")), //happy path all operations
            cs(ex("1+1", "error > wrong expression"), ex("1 - foo", "error > foo")), // неаккуратный пользователь.
            cs(ex("0b1000_0100 + 0", "132"), ex("20l - 0xf5", "-225"), ex("0b11111111_11111111_11111111_11111111 + 1", "0")), // common expressions
            cs(ex("0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111L + 1", "0")), // negative long
            cs(ex("1_1 + 2_2", "33"), ex("_1 + 2","error > _1"), ex("123__34 + 1", "error > 123__34"), ex("123_ + 85", "error > 123_")),
            cs(ex("123__ + 2", "error > 123__")), // underscore before/after
            cs(ex("035 + 095", "error > 095"), ex("010 + 1", "9"), ex("01.3 + 01.4", "2.7"), ex("00035L + 0009.2", "38.2")), // invalid octal
            cs(ex("123lL + 5","error > 123lL")), // double with suffix L
            cs(ex("1.3l + 0.7", "error > 1.3l")) // double with suffix l

            // Остальные тесты пишите сами, своими не поделюсь ;)
    };

    public void startTesting(Calculator calculator) {
        for (CommandSequence cs : sequences) {
            cs.getExpressions().forEach(expression -> calculateAndCheck(calculator, expression));
        }
    }

    private void calculateAndCheck(Calculator calculator, Expression expression) {
        log.debug("executing {} ", expression.getInput());
        String result = calculator.calculate(expression.getInput());
        if (expression.getExpectedOutput().equalsIgnoreCase(result)) {
            log.debug("OK: {} ", result);
        } else {
            log.error("FAIL: {}; expecting: {}", result, expression.getExpectedOutput());
        }
    }

    private CommandSequence cs(Expression... expressions) {
        return CommandSequence.create().addAllExpressions(expressions);
    }

    private Expression ex(String input, String expectedResult) {
        return new Expression(input, expectedResult);
    }
}
