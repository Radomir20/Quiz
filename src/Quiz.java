import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Quiz extends Application {
    private Client client;
    private String question;
    private String result;

    @Override
    public void start(Stage primaryStage) {
        client = new Client(this);
        client.start();
        client.sendMessage("Konektovan");

        primaryStage.setTitle("Test Question 1");

        Label labelfirst = new Label(this.getQuestion());
        Label labelresponse = new Label();

        Button button = new Button("Submit");

        RadioButton radio1, radio2, radio3, radio4;
        radio1 = new RadioButton("10");
        radio2 = new RadioButton("20");
        radio3 = new RadioButton("30");
        radio4 = new RadioButton("40");

        ToggleGroup question1 = new ToggleGroup();

        radio1.setToggleGroup(question1);
        radio2.setToggleGroup(question1);
        radio3.setToggleGroup(question1);
        radio4.setToggleGroup(question1);

        button.setDisable(true);

        radio1.setOnAction(e -> button.setDisable(false));
        radio2.setOnAction(e -> button.setDisable(false));
        radio3.setOnAction(e -> button.setDisable(false));
        radio4.setOnAction(e -> button.setDisable(false));

        button.setOnAction(e -> {
            if (radio1.isSelected()) {
                client.sendMessage(radio1.getText());
                button.setDisable(true);
            } else if (radio2.isSelected()) {
                client.sendMessage(radio2.getText());
                button.setDisable(true);
            } else if (radio3.isSelected()) {
                client.sendMessage(radio3.getText());
                button.setDisable(true);
            } else {
                client.sendMessage(radio4.getText());
                button.setDisable(true);
            }

        });
        labelresponse.setText(this.getResult());

        VBox layout = new VBox(5);

        layout.getChildren().addAll(labelfirst, radio1, radio2, radio3, radio4, button, labelresponse);

        Scene scene1 = new Scene(layout, 400, 250);
        primaryStage.setScene(scene1);

        primaryStage.show();
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getQuestion() {
        return question;
    }

    public String getResult() {
        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }

}