import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Java solution to the simple integer arithmetic problem.
 */
public class Algo {

    // Test file name
    private final static String TEST_FILE = "input.csv";

    // Parenthesis local variable definisions
    private final String LH = "(";
    private final String RH = ")";

    /**
     * Simple operation interface
     */
    interface MathOperation {
        double operation(double a, double b);
    }

    // State the four operators to be supported
    private final MathOperation multiply  = (double a, double b) -> a * b;
    private final MathOperation divide    = (double a, double b) -> a / b;
    private final MathOperation add       = (double a, double b) -> a + b;
    private final MathOperation subtract  = (double a, double b) -> a - b;

    // Maps to hold calculators and precedence
    private final Map<String, MathOperation> ops  = new HashMap();
    private final Map<String, Integer> prec = new HashMap();

    /**
     * C'tor that adds the math operations to a map
     * and also adds the operator precedence to a separate map
     */
    private Algo() {
        ops.put("*", multiply);
        ops.put("/", divide);
        ops.put("+", add);
        ops.put("-", subtract);

        prec.put("*", 2);
        prec.put("/", 2);
        prec.put("+", 1);
        prec.put("-", 1);
        prec.put("(", 0);
    }
    // Create two stacks to hold operators and operands
    private final Stack<String> out_stack = new Stack();
    private final Stack<String> ops_stack = new Stack();

    /**
     * Basic test class to check the calculated
     * vs expected values.
     */
    static class AlgoTest {

        private Algo algo = new Algo();
        private String expression;
        private String expected;

        /**
         * C'tor to hold the expression and expected result
         * @param s the comma separated string from the input file
         */
        public AlgoTest(String s) {
            this.expression = s.split(",")[0];
            this.expected = s.split(",")[1];
        }

        /**
         * Simply compare the calculated to the expected
         * @return test result as a String
         */
        public String test() {
            int calculated = algo.algo(this.expression);
            boolean result = calculated == Integer.valueOf(this.expected);
            if (result) {
                return "PASS: " + calculated + " == " + expected;
            } else {
                return "FAIL: " + calculated + " != " + expected;
            }
        }
    }

    /**
     * There are better libraries like Apache Commons
     * to use for this check to avoid library dependecy
     * I've implemented a rudimentary methid
     * @param token the string value
     * @return true if it's an integer value
     */
    private boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            // ignore...
        }
        return false;
    }

    /**
     * My shunting-yard algo implementation.
     *
     * (see Python notebook for a description of this algo)
     * @param expression the infix expression "1 + 2 * 3"
     * @return the evaluated calculation.
     */
    public Integer algo(final String expression) {

        // While there are expression tokens to be read
        for(String token : expression.split(" ")) {

            // If it's a left bracket
            if(token.equals(LH)) {
                ops_stack.push(String.valueOf(token));
            }
            // If it's a number add it to the output stack
            else if (isNumber(token)) {
                out_stack.push(String.valueOf(token));
            }
            // If it's an operator
            else if (ops.keySet().contains(String.valueOf(token))) {

                // While there's an operator on the top of the operator stack with greater precedence
                while(!ops_stack.isEmpty() && (prec.get(ops_stack.peek()) >= prec.get(String.valueOf(token)))) {
                    // Pop operators from the operator stack and push onto the output stack
                    out_stack.push(ops_stack.pop());
                }
                // Push the current operator onto the stack
                ops_stack.push(String.valueOf(token));
            }
            // If it's a right bracket
            else if(token.equals(RH)) {
                // While there's not a left bracket at the top of the stack
                while(!ops_stack.isEmpty() && !ops_stack.peek().equals(LH)) {
                    // Pop operators from the operator stack and push onto the output stack
                    out_stack.push(ops_stack.pop());
                }
                // Pop the left bracket from the operator stack and discard it
                ops_stack.pop();
            }
        }
        // While there are operators on the operator stack, pop them to the output stack
        while(!ops_stack.isEmpty()) {
            out_stack.push(ops_stack.pop());
        }
        return evaluate();
    }

    /**
     * Evaluate the postfix expression
     * @return the evaluated calculation
     */
    private Integer evaluate() {

        // Make a result stack
        Stack<Double> results = new Stack();

        // While there are operators/operands on the output stack
        for(String token: this.out_stack) {

            // If the token is an operator
            if (ops.keySet().contains(String.valueOf(token))) {

                // Get the last two values from the stack
                double arg2 = results.pop();
                double arg1 = results.pop();

                // Calculate the operator and value output
                Double result = ops.get(token).operation(arg1, arg2);

                // Push the result back to the result stack
                results.push(result);
            }
            else {
                // It's a number, so add it back to the result stack
                // in anticipation of an operator appearing
                // in the above loop. This is key to how the postfix
                // expression is parsed. This is somewhat abstract,
                // as essentially this loop is calculating small sections
                // of the infix expression, in the correct order after the
                // shunting yard algo.
                results.push(Double.valueOf(token));
            }
        }
        return results.pop().intValue();
    }

    /**
     * Run tests against each line in the input test file and output results as pass or fail.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Files.lines(Paths.get(TEST_FILE)).forEach(s -> System.out.println(new AlgoTest(s).test()));
    }
}