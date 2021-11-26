package com.arithmetic;

import java.util.*;

public class Algo {

    private final MathOperation multiply  = (int a, int b) -> a * b;
    private final MathOperation divide    = (int a, int b) -> a / b;
    private final MathOperation add       = (int a, int b) -> a + b;
    private final MathOperation subtract  = (int a, int b) -> a - b;

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
        int operation(int a, int b);
    }

    public void algo(String expression) {
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
        // 1 3 4 2 * * 1 5 - / +
    }

    public Object evaluate() {

        Stack<Integer> results = new Stack();
        for(String token: this.out_stack) {
            System.out.println(token);
            if (ops.keySet().contains(String.valueOf(token))) {
                Integer arg2 = results.pop();
                Integer arg1 = results.pop();
                System.out.println(token + " " + arg1 + " " + arg2);
                Integer result = ops.get(token).operation(arg1, arg2);
                results.push(result);
            }
            else {
                results.push(Integer.valueOf(token));
            }
        }
        return results.pop();
    }

    public static void main(String[] args) {
        Algo algo = new Algo();
        algo.algo("1 + 3 * ( 4 * 2 ) / ( 99 - 6 )");
        System.out.println(algo.evaluate());
    }
}
