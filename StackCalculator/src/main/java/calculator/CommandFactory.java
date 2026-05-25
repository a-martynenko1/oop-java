package calculator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import calculator.commands.Command;

public class CommandFactory {
    private static final Logger logger = LogManager.getLogger(CommandFactory.class);
    private final Properties commands = new Properties();

    public CommandFactory() {
        try (InputStream input = new FileInputStream("src/main/resources/calculator/commands.config")) {
            commands.load(input);
            logger.info("Config loaded from: src/main/resources/calculator/commands.config");
            logger.debug("Loaded commands: {}", commands.keySet());
        } catch (IOException e) {
            logger.error("Failed to load config: {}", e.getMessage());
            throw new RuntimeException("Failed to load command config", e);
        }
    }

    public Command createCommand(String name) throws CalculatorException {
        logger.debug("Creating command: {}", name);
        String className = commands.getProperty(name);
        if (className == null) {
            logger.warn("Unknown command: {}", name);
            throw new CalculatorException("Unknown command: " + name);
        }

        try {
            Class<?> clazz = Class.forName(className);
            Command cmd = (Command) clazz.getDeclaredConstructor().newInstance();
            logger.info("Command created successfully: {} -> {}", name, className);
            return cmd;
        } catch (Exception e) {
            logger.error("Failed to create command: {}", name, e);
            throw new CalculatorException("Command creation failed: " + name, e);
        }
    }
}