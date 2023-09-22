package RPNCalc;

public class Operator {
    private char operator;
    private int precedence;
    private boolean leftAssoc;
    private boolean unary;

    public Operator(String o) {
        setOperator(o.charAt(0));
    }

    public void setOperator(char o) {
        switch (o) {
            case '+', '-' -> {
                precedence = 2;
                leftAssoc = true;
                unary = false;
            }
            case '*', '/' -> {
                precedence = 3;
                leftAssoc = true;
                unary = false;
            }
            case 'n' -> { // pojedynczy minus
                precedence = 4;
                leftAssoc = false;
                unary = true;
            }
            case '(', ')' -> {
                precedence = 999;
                leftAssoc = false;
                unary = false;
            }
        }
        operator = o;
    }

    public char getOperator() {
        return operator;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean isLeftAssoc() {
        return leftAssoc;
    }

    public boolean isUnary() {
        return unary;
    }
}
