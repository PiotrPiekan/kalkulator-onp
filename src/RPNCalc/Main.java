package RPNCalc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent layout = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Calculator.fxml")));
            Scene scene = new Scene(layout);

            stage.setMinHeight(400);
            stage.setMinWidth(250);
            stage.setTitle("Projekt zaliczeniowy");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
