package calculator.commands;

import calculator.CalculatorException;
import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DivTest {
    private Context context;
    private Div divCmd;

    @BeforeEach
    void setUp() {
        context = new Context();
        divCmd = new Div();
    }

    @Test
    void testExecute() {
        context.push(10.0);
        context.push(2.0);
        divCmd.execute(context, new String[0]);
        assertEquals(5.0, context.pop());
    }

    @Test
    void testDivisionByZero() {
        context.push(10.0);
        context.push(0.0);
        assertThrows(CalculatorException.class, () -> divCmd.execute(context, new String[0]));
    }
}