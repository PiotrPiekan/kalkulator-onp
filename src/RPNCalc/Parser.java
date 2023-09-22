package RPNCalc;

import java.util.LinkedList;
import java.util.Queue;

public class Parser {
    public static Queue<Object> parse(String expression) {
        expression = replaceUnaryMinuses(expression);
        expression = insertMultiplicationSigns(expression);
        return createQueueOfTokens(expression);
    }

    // minus może być pojedynczym operatorem, trzeba odróżnić te minusy od pozostałych
    private static String replaceUnaryMinuses(String expression) {
        return expression.replaceAll("(?<=[+\\-*/])-|^-", "n");
    }

    // użytkownik może zapisać (1-2)(3+4), 2(3+4), (2+3)4, co jest poprawne, ale algorytm ONP miałby z tym problem
    private static String insertMultiplicationSigns(String expression) {
        return expression.replaceAll("(?<=\\))((?=\\()|(?=\\d))|(?<=\\d)(?<=\\()", "*");
    }

    private static Queue<Object> createQueueOfTokens(String expression) {
        String[] strings = getArrayOfStrings(expression);
        Queue<Object> infixQueue = new LinkedList<>();
        for (String s : strings) {
            infixQueue.add(getToken(s));
        }
        return infixQueue;
    }

    //
    private static String[] getArrayOfStrings(String expression) {
        return expression.split("(?<=[-+*/()n])|(?=[-+*/()n])");
    }

    private static Object getToken(String item) {
        if (isNumber(item))
            return Double.parseDouble(item);
        if (isOperator(item))
            return new Operator(item);
        throw new IllegalArgumentException("Błąd w działaniu.");
    }

    private static boolean isNumber(String x) {
        return x.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isOperator(String x) {
        return x.matches("[+\\-*/()n]");
    }
}
