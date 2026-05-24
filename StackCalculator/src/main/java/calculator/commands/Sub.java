package calculator.commands;

import calculator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sub implements Command {
    private static final Logger logger = LogManager.getLogger(Sub.class);

    @Override
    public void execute(Context context, String[] args) throws CalculatorException {
        logger.debug("Executing SUB");

        if (args.length != 0) {
            logger.warn("SUB takes no arguments, but got {}", args.length);
            throw new CalculatorException("SUB takes no arguments");
        }

        double b = context.pop();
        double a = context.pop();
        double result = a - b;
        context.push(result);
        logger.info("SUB: {} - {} = {}", a, b, result);
    }
}