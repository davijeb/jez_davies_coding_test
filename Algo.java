import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Algo {

    private final MathOperation multiply  = (double a, double b) -> a * b;
    private final MathOperation divide    = (double a, double b) -> a / b;
    private final MathOperation add       = (double a, double b) -> a + b;
    private final MathOperation subtract  = (double a, double b) -> a - b;

    private final Map<String, MathOperation> ops  = new HashMap();
    private final Map<String, Integer> prec = new HashMap();

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

    private final Stack<String> out_stack = new Stack();
    private final Stack<String> ops_stack = new Stack();

    interface MathOperation {
        double operation(double a, double b);
    }

    static class AlgoTest {

        private Algo algo = new Algo();
        private String expression;
        private String expected;

        public AlgoTest(String s) {
            this.expression = s.split(",")[0];
            this.expected = s.split(",")[1];
        }

        public boolean test() {
            return algo.algo(this.expression) == Integer.valueOf(this.expected);
        }
    }

    public Integer algo(final String expression) {
        for(char token : expression.toCharArray()) {
            if(token == '(') {
                ops_stack.push(String.valueOf(token));
            }
            else if (Character.isDigit(token)) {
                out_stack.push(String.valueOf(token));
            }
            else if (ops.keySet().contains(String.valueOf(token))) {
                while(!ops_stack.isEmpty() && (prec.get(ops_stack.peek()) >= prec.get(String.valueOf(token)))) {
                    out_stack.push(ops_stack.pop());
                }
                ops_stack.push(String.valueOf(token));
            }
            else if(token == ')') {
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

    public static void main(String[] args) {
        Algo algo = new Algo();
        algo.algo("1 + 3 * ( 4 * 2 ) / ( 99 - 6 )");
        System.out.println(algo.evaluate());

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get("input.csv"))) {
            stream.forEach(s -> new AlgoTest(s).test());
        } catch (IOException e) {
            e.printStackTrace();
        }

        double x = 1.23;

    }
}
