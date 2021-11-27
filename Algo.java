import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Java solution to the simple integer arithmetic problem.
 */
public class Algo {

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
     * Simple operation interface
     */
    interface MathOperation {
        double operation(double a, double b);
    }

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
                return "PASS";
            } else {
                return "FAIL: " + calculated + " != " + expected;
            }
        }
    }

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
     * Shunting Yard Algo implementation
     * (see Python notebook for a description of this algo)
     * @param expression the infix expression "1 + 2 * 3"
     * @return the evaluated calculation.
     */
    public Integer algo(final String expression) {

        for(String token : expression.split(" ")) {
            if(token.equals("(")) {
                ops_stack.push(String.valueOf(token));
            }
            else if (isNumber(token)) {
                out_stack.push(String.valueOf(token));
            }
            else if (ops.keySet().contains(String.valueOf(token))) {
                while(!ops_stack.isEmpty() && (prec.get(ops_stack.peek()) >= prec.get(String.valueOf(token)))) {
                    out_stack.push(ops_stack.pop());
                }
                ops_stack.push(String.valueOf(token));
            }
            else if(token.equals(")")) {
                while(!ops_stack.isEmpty() && !ops_stack.peek().equals("(")) {
                    out_stack.push(ops_stack.pop());
                }
                ops_stack.pop();
            }
        }

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

        Stack<Double> results = new Stack();
        for(String token: this.out_stack) {
            if (ops.keySet().contains(String.valueOf(token))) {
                double arg2 = results.pop();
                double arg1 = results.pop();
                Double result = ops.get(token).operation(arg1, arg2);
                results.push(result);
            }
            else {
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
        Files.lines(Paths.get("input.csv")).forEach(s -> System.out.println(new AlgoTest(s).test()));
    }
}