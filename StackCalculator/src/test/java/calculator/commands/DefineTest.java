package calculator.commands;

import calculator.CalculatorException;
import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DefineTest {
    private Context context;
    private Define defineCmd;

    @BeforeEach
    void setUp() {
        context = new Context();
        defineCmd = new Define();
    }

    @Test
    void testExecute() {
        defineCmd.execute(context, new String[]{"a", "4.5"});
        assertEquals(4.5, context.getParam("a"));
    }

    @Test
    void testInvalidArgsLength() {
        assertThrows(CalculatorException.class, () -> defineCmd.execute(context, new String[]{"a"}));
    }

    @Test
    void testInvalidFormat() {
        assertThrows(CalculatorException.class, () -> defineCmd.execute(context, new String[]{"a", "abc"}));
    }
}