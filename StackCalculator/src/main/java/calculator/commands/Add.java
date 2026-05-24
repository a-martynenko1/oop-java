package calculator.commands;

import calculator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Add implements Command {
    private static final Logger logger = LogManager.getLogger(Add.class);

    @Override
    public void execute(Context context, String[] args) throws CalculatorException {
        logger.debug("Executing ADD");

        if (args.length != 0) {
            logger.warn("ADD takes no arguments, but got {}", args.length);
            throw new CalculatorException("ADD takes no arguments");
        }

        double b = context.pop();
        double a = context.pop();
        double result = a + b;
        context.push(result);
        logger.info("ADD: {} + {} = {}", a, b, result);
    }
}