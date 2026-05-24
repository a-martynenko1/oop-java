package calculator.commands;

import calculator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sqrt implements Command {
    private static final Logger logger = LogManager.getLogger(Sqrt.class);

    @Override
    public void execute(Context context, String[] args) throws CalculatorException {
        logger.debug("Executing SQRT");

        if (args.length != 0) {
            logger.warn("SQRT takes no arguments, but got {}", args.length);
            throw new CalculatorException("SQRT takes no arguments");
        }

        double a = context.pop();
        if (a < 0) {
            logger.error("SQRT of negative number: {}", a);
            throw new CalculatorException("SQRT of negative number: " + a);
        }
        double result = Math.sqrt(a);
        context.push(result);
        logger.info("SQRT: sqrt({}) = {}", a, result);
    }
}