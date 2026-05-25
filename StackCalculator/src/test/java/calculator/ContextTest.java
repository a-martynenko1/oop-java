package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContextTest {
    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
    }

    @Test
    void testPushAndPop() {
        context.push(5.0);
        context.push(10.0);
        assertEquals(10.0, context.pop());
        assertEquals(5.0, context.pop());
    }

    @Test
    void testPopEmptyStack() {
        assertThrows(CalculatorException.class, () -> context.pop());
    }

    @Test
    void testPeek() {
        context.push(7.0);
        assertEquals(7.0, context.peek());
        assertEquals(7.0, context.pop());
    }

    @Test
    void testParams() {
        context.setParam("a", 42.0);
        assertTrue(context.isParamDefined("a"));
        assertEquals(42.0, context.getParam("a"));
    }

    @Test
    void testUndefinedParam() {
        assertThrows(CalculatorException.class, () -> context.getParam("b"));
    }
}