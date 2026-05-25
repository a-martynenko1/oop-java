package calculator.commands;

import calculator.CalculatorException;
import calculator.Context;

public interface Command {
    void execute(Context context, String[] args) throws CalculatorException;
}