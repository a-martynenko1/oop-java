package calculator.commands;

import calculator.CalculatorException;
import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MulTest {
    private Context context;
    private Mul mulCmd;

    @BeforeEach
    void setUp() {
        context = new Context();
        mulCmd = new Mul();
    }

    @Test
    void testExecute() {
        context.push(4.0);
        context.push(3.0);
        mulCmd.execute(context, new String[0]);
        assertEquals(12.0, context.pop());
    }
}