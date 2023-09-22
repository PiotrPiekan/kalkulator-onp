package RPNCalc;

import java.util.Queue;
import java.util.Stack;

public class ReversePolishNotation {
    public static double handleExpression(String expression) {
        Queue<Object> infixQueue = Parser.parse(expression);
        Queue<Object> rpnQueue = ShuntingYard.getRPNQueue(infixQueue);
        return rpnSolve(rpnQueue);
    }

    private static double rpnSolve(Queue<Object> rpnQueue) {
        Stack<Double> stack = new Stack<>();
        while (!rpnQueue.isEmpty()) {
            Object token = rpnQueue.remove();
            if (token instanceof Double) {
                stack.push((Double) token);
                continue;
            }
            stack.push(calculation((Operator) token, stack));
        }
        if (stack.size() > 1)
            throw new IllegalArgumentException("Błąd w działaniu.");
        return stack.peek();
    }

    private static double calculation(Operator op, Stack<Double> stack) {
        if (op.isUnary()) {
            if (stack.isEmpty())
                throw new ArithmeticException("Błąd w działaniu.");
            double a = stack.pop();
            return -a;
        }

        if (stack.size() < 2)
            throw new ArithmeticException("Błąd w działaniu.");
        double a = stack.pop();
        double b = stack.pop();
        return switch (op.getOperator()) {
            case '+' -> b + a;
            case '-' -> b - a;
            case '*' -> b * a;
            case '/' -> {
                if (b == 0)
                    throw new ArithmeticException("Dzielenie przez zero.");
                yield b / a;
            }
            default -> throw new ArithmeticException("Nieznany znak.");
        };
    }
}
