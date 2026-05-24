package calculator.commands;

import calculator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Print implements Command {
    private static final Logger logger = LogManager.getLogger(Print.class);

    @Override
    public void execute(Context context, String[] args) throws CalculatorException {
        logger.debug("Executing PRINT");

        if (args.length != 0) {
            logger.warn("PRINT takes no arguments, but got {}", args.length);
            throw new CalculatorException("PRINT takes no arguments");
        }

        double value = context.peek();
        System.out.println(value);
        logger.info("PRINT: {}", value);
    }
}