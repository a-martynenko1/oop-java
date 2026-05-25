package calculator;

import java.io.*;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import calculator.commands.Command;

public class CalculatorApp {
    private static final Logger logger = LogManager.getLogger(CalculatorApp.class);

    private final Context context = new Context();
    private final CommandFactory factory = new CommandFactory();
    private boolean shouldExit = false;

    public void run(String[] args) {
        Scanner scanner = createScanner(args);
        if (scanner == null) {
            return;
        }

        processCommands(scanner);
        scanner.close();
    }

    private Scanner createScanner(String[] args) {
        if (args.length > 0) {
            try {
                logger.info("Reading commands from file: {}", args[0]);
                return new Scanner(new File(args[0]));
            } catch (FileNotFoundException e) {
                logger.error("File not found: {}", args[0]);
                System.err.println("Error: file not found");
                return null;
            }
        } else {
            logger.info("Reading commands from console");
            System.out.println("Calculator started. Type 'EXIT' to quit.");
            return new Scanner(System.in);
        }
    }

    private void processCommands(Scanner scanner) {
        while (!shouldExit && scanner.hasNextLine()) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();

            if (shouldSkip(line)) {
                continue;
            }

            String[] parts = line.split("\\s+");
            String cmdName = parts[0].toUpperCase();
            String[] cmdArgs = extractArgs(parts);

            executeCommand(cmdName, cmdArgs);
        }

        logger.info("Calculator finished");
    }

    private boolean shouldSkip(String line) {
        return line.isEmpty() || line.startsWith("#");
    }

    private String[] extractArgs(String[] parts) {
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, parts.length - 1);
        return args;
    }

    private void executeCommand(String cmdName, String[] cmdArgs) {
        if (cmdName.equals("EXIT")) {
            shouldExit = true;
            logger.info("Exiting calculator");
            System.out.println("Goodbye!");
            return;
        }

        try {
            Command cmd = factory.createCommand(cmdName);
            cmd.execute(context, cmdArgs);
            logger.debug("Executed command: {}", cmdName);
        } catch (CalculatorException e) {
            logger.error("Error executing {}: {}", cmdName, e.getMessage());
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.fatal("Unexpected error: ", e);
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}