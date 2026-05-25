package calculator.commands;

import calculator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Push implements Command {
    private static final Logger logger = LogManager.getLogger(Push.class);

    @Override
    public void execute(Context context, String[] args) throws CalculatorException {
        logger.debug("Executing PUSH with args: {}", (Object) args);

        if (args.length != 1) {
            logger.warn("Invalid number of arguments for PUSH: expected 1, got {}", args.length);
            throw new CalculatorException("PUSH requires 1 argument");
        }

        double value;
        String arg = args[0];

        if (context.isParamDefined(arg)) {
            value = context.getParam(arg);
            logger.debug("PUSH using parameter: {} = {}", arg, value);
        } else {
            try {
                value = Double.parseDouble(arg);
                logger.debug("PUSH using literal: {}", value);
            } catch (NumberFormatException e) {
                logger.error("Invalid argument for PUSH: {}", arg);
                throw new CalculatorException("Invalid number or parameter: " + arg);
            }
        }
        context.push(value);
    }
}