package calculator.commands;

import calculator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pop implements Command {
    private static final Logger logger = LogManager.getLogger(Pop.class);

    @Override
    public void execute(Context context, String[] args) throws CalculatorException {
        logger.debug("Executing POP");

        if (args.length != 0) {
            logger.warn("POP takes no arguments, but got {}", args.length);
            throw new CalculatorException("POP takes no arguments");
        }

        double value = context.pop();
        logger.info("POP removed: {}", value);
    }
}