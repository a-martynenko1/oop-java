package calculator.commands;

import calculator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Define implements Command {
    private static final Logger logger = LogManager.getLogger(Define.class);

    @Override
    public void execute(Context context, String[] args) throws CalculatorException {
        logger.debug("Executing DEFINE with args: {}", (Object) args);

        if (args.length != 2) {
            logger.warn("Invalid number of arguments for DEFINE: expected 2, got {}", args.length);
            throw new CalculatorException("DEFINE requires name and value");
        }

        try {
            double value = Double.parseDouble(args[1]);
            context.setParam(args[0], value);
            logger.info("DEFINE {} = {}", args[0], value);
        } catch (NumberFormatException e) {
            logger.error("Invalid number format for DEFINE value: {}", args[1]);
            throw new CalculatorException("Invalid number: " + args[1]);
        }
    }
}