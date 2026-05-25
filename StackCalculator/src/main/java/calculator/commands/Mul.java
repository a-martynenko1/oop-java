package calculator.commands;

import calculator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mul implements Command {
    private static final Logger logger = LogManager.getLogger(Mul.class);

    @Override
    public void execute(Context context, String[] args) throws CalculatorException {
        logger.debug("Executing MUL");

        if (args.length != 0) {
            logger.warn("MUL takes no arguments, but got {}", args.length);
            throw new CalculatorException("MUL takes no arguments");
        }

        double b = context.pop();
        double a = context.pop();
        double result = a * b;
        context.push(result);
        logger.info("MUL: {} * {} = {}", a, b, result);
    }
}