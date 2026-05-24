package calculator.commands;

import calculator.CalculatorException;
import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddTest {
    private Context context;
    private Add addCmd;

    @BeforeEach
    void setUp() {
        context = new Context();
        addCmd = new Add();
    }

    @Test
    void testExecute() {
        context.push(2.0);
        context.push(3.0);
        addCmd.execute(context, new String[0]);
        assertEquals(5.0, context.pop());
    }

    @Test
    void testArgsException() {
        assertThrows(CalculatorException.class, () -> addCmd.execute(context, new String[]{"1"}));
    }
}