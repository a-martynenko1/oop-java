package calculator.commands;

import calculator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Div implements Command {
    private static final Logger logger = LogManager.getLogger(Div.class);

    @Override
    public void execute(Context context, String[] args) throws CalculatorException {
        logger.debug("Executing DIV");

        if (args.length != 0) {
            logger.warn("DIV takes no arguments, but got {}", args.length);
            throw new CalculatorException("DIV takes no arguments");
        }

        double b = context.pop();
        if (b == 0) {
            logger.error("Division by zero attempted");
            throw new CalculatorException("Division by zero");
        }
        double a = context.pop();
        double result = a / b;
        context.push(result);
        logger.info("DIV: {} / {} = {}", a, b, result);
    }
}