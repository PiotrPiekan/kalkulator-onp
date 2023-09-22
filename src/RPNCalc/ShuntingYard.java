package RPNCalc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ShuntingYard {
    // algorytm "stacji rozrządowej" autorstwa E. Dijkstry
    // https://en.wikipedia.org/wiki/Shunting_yard_algorithm/
    public static Queue<Object> getRPNQueue(Queue<Object> infixQueue) {
        Queue<Object> output = new LinkedList<>();
        Stack<Operator> stack = new Stack<>();
        while (!infixQueue.isEmpty()) {
            Object token = infixQueue.remove();
            if (token instanceof Double)
                output.add(token);
            else {
                Operator op = (Operator) token;
                switch (op.getOperator()) {
                    case '(' -> stack.push(op);
                    case ')' -> deparenthesize(output, stack);
                    default -> handleOperator(output, stack, op);
                }
            }
        }
        while (!stack.empty()) {
            Operator o = stack.pop();
            if (o.getOperator() == '(')
                throw new IllegalArgumentException("Złe ułożenie nawiasów.");
            output.add(o);
        }
        return output;
    }

    private static void deparenthesize(Queue<Object> output, Stack<Operator> stack) {
        while (!stack.empty() && stack.peek().getOperator() != '(')
            output.add(stack.pop());
        if (stack.empty())
            throw new IllegalArgumentException("Złe ułożenie nawiasów.");
        stack.pop();
    }

    private static void handleOperator(Queue<Object> output, Stack<Operator> stack, Operator op) {
        while (complicatedOperatorCheckIsTrue(stack, op))
            output.add(stack.pop());
        stack.push(op);
    }

    private static boolean complicatedOperatorCheckIsTrue(Stack<Operator> stack, Operator op1) {
        if (stack.empty())
            return false;
        Operator op2 = stack.peek();
        if (op2.getOperator() == '(')
            return false;
        if (op2.getPrecedence() > op1.getPrecedence())
            return true;
        return op2.getPrecedence() == op1.getPrecedence() && op1.isLeftAssoc();
    }
}
