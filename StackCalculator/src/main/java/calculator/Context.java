package calculator;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Context {
    private static final Logger logger = LogManager.getLogger(Context.class);
    private final Deque<Double> stack = new ArrayDeque<>();
    private final Map<String, Double> params = new HashMap<>();

    public void push(double value) {
        stack.push(value);
        logger.debug("Pushed {} onto stack. Stack size: {}", value, stack.size());
    }

    public double pop() {
        if (stack.isEmpty()) {
            logger.error("Stack underflow attempted");
            throw new CalculatorException("Stack underflow");
        }
        double value = stack.pop();
        logger.debug("Popped {} from stack. Stack size: {}", value, stack.size());
        return value;
    }

    public double peek() {
        if (stack.isEmpty()) {
            logger.error("Peek on empty stack");
            throw new CalculatorException("Stack is empty");
        }
        double value = stack.peek();
        logger.debug("Peeked at {}. Stack size: {}", value, stack.size());
        return value;
    }

    public void setParam(String name, double value) {
        params.put(name, value);
        logger.info("Parameter defined: {} = {}", name, value);
    }

    public double getParam(String name) {
        if (!params.containsKey(name)) {
            logger.warn("Undefined parameter requested: {}", name);
            throw new CalculatorException("Undefined parameter: " + name);
        }
        double value = params.get(name);
        logger.debug("Retrieved parameter: {} = {}", name, value);
        return value;
    }

    public boolean isParamDefined(String name) {
        boolean defined = params.containsKey(name);
        logger.debug("Parameter '{}' defined: {}", name, defined);
        return defined;
    }
}