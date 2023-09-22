package RPNCalc;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CalculatorController {
    @FXML
    private Label equationText;
    @FXML
    private Label errorText;
    @FXML
    private GridPane buttonGrid;

    private boolean equationIsFinished;

    @FXML
    private void initialize() {
        clear();

        for (Node child : buttonGrid.getChildren()) {
            Button button = (Button) child;
            String buttonText = button.getText();

            switch (buttonText) {
                case "C" -> button.setOnAction(e -> clear());
                case "<" -> button.setOnAction(e -> backspace());
                case "=" -> button.setOnAction(e -> finishEquation());
                default -> button.setOnAction(e -> insertCharacter(buttonText));
            }
        }
    }

    private void insertCharacter(String character) {
        if (equationIsFinished)
            clear();

        equationText.setText(equationText.getText().concat(character));
    }

    private void clear() {
        equationIsFinished = false;
        errorText.setText("");
        equationText.setText("");
    }

    private void backspace() {
        if (equationIsFinished)
            clear();

        String oldText = equationText.getText();
        if (oldText.isEmpty())
            return;
        String newText = oldText.substring(0, oldText.length() - 1);
        equationText.setText(newText);
    }

    private void finishEquation() {
        if (equationIsFinished)
            return;

        String expression = equationText.getText();
        try {
            double solution = ReversePolishNotation.handleExpression(expression);
            equationIsFinished = true;
            equationText.setText(expression.concat(" = ").concat(Double.toString(solution)));
        } catch (Exception e) {
            errorText.setText(e.getMessage());
        }
    }
}
