import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Quiz extends Application {
    Stage primarStage;
    private Scene scene;
    private Label ques;
    private Label res;
    private Button b1;
    private Button b2;
    private Button b3;

    private Client client;
    private String result;
    private String username;
    private String opponent;
    private boolean opponentConnected = false;
    private int bodovi = 0;

    @Override
    public void start(Stage primaryStage) {

        client = new Client(this);
        client.start();

        this.primarStage = primaryStage;

        VBox start = new VBox(10);
        start.setPrefWidth(500);
        start.setPrefHeight(350);
        start.setAlignment(Pos.CENTER);
        Label input = new Label("Enter username:");
        input.setStyle(
                "-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #0c8080; -fx-font-family: 'Verdana'; -fx-text-alignment: center;");

        TextField userName = new TextField();
        userName.setMaxWidth(200);
        userName.setMaxHeight(30);
        userName.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                + "-fx-background-color: #0c8080; -fx-padding: 15;");

        Button play = new Button("Play");
        play.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
        play.setPrefWidth(70);
        play.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                play.setStyle(
                        "-fx-text-fill: #0c8080; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                                + "-fx-background-color: #A9F2F2; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
            }
        });
        play.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                play.setStyle(
                        "-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                                + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
            }
        });

        Label error = new Label();
        error.setStyle(" -fx-text-fill: red;");
        error.setManaged(false);
        error.setVisible(false);
        start.getChildren().addAll(input, userName, play, error);
        start.setStyle("-fx-background-color: #74c1cd;");

        scene = new Scene(start, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quiz");
        primaryStage.setResizable(false);
        primaryStage.show();

        play.setOnAction(e -> {
            if (userName.getText().isEmpty()) {
                error.setText("Enter username!");
                error.setManaged(true);
                error.setVisible(true);
                return;
            }
            error.setText("");
            username = userName.getText();
            client.setUsername(userName.getText());
            client.sendUsername();
            primaryStage.setScene(makeScene());
            primaryStage.centerOnScreen();
            primaryStage.show();
            res.setText("Protivnik nije konektovan"); 
        });
    }



    // ----------------------------------- GUI
    private Scene makeScene() {
        HBox hb = new HBox(200);
        Label l1 = new Label(username + ": " + bodovi);// Dodati ime i bodove
        l1.setStyle("-fx-font-size: 12; -fx-text-fill: white; -fx-font-family: 'Verdana'; -fx-font-weight: bold;");
        Label l2 = new Label("Player2: 9");// Dodati protivnika i bodove
        l2.setStyle("-fx-font-size: 12; -fx-text-fill: white; -fx-font-family: 'Verdana'; -fx-font-weight: bold;");
        hb.getChildren().addAll(l1, l2);
        hb.setAlignment(Pos.CENTER);
        hb.setStyle("-fx-background-color: #073737; -fx-padding: 5 0 5 0");
        // hb.setPrefHeight(40);

        VBox vb = new VBox(20);
        vb.setAlignment(Pos.TOP_CENTER);
        vb.setStyle("-fx-padding: 50 0 0 0");

        ques = new Label();// pitanje
        ques.setStyle(
                "-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-family: 'Verdana'; -fx-text-alignment: center;");
        ques.setWrapText(true);
        vb.getChildren().add(ques);

        b1 = new Button();// odgovor
        b1.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
        b1.setPrefWidth(300);
        // b1.setMinHeight(60);
        vb.getChildren().add(b1);
        b1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b1.setStyle(
                        "-fx-text-fill: #0c8080; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                                + "-fx-background-color: #A9F2F2; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
            }
        });
        b1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b1.setStyle(
                        "-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                                + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
            }
        });

        b2 = new Button();// odgovor
        b2.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
        b2.setPrefWidth(300);
        vb.getChildren().add(b2);
        b2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b2.setStyle(
                        "-fx-text-fill: #0c8080; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                                + "-fx-background-color: #A9F2F2; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
            }
        });
        b2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b2.setStyle(
                        "-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                                + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
            }
        });

        b3 = new Button();// odgovor
        b3.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
        b3.setPrefWidth(300);
        vb.getChildren().add(b3);
        b3.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b3.setStyle(
                        "-fx-text-fill: #0c8080; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                                + "-fx-background-color: #A9F2F2; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
            }
        });
        b3.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b3.setStyle(
                        "-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                                + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
            }
        });

        res = new Label();
        res.setStyle(
                "-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: green; -fx-font-family: 'Verdana'; -fx-text-alignment: center;");
        res.setWrapText(true);
        vb.getChildren().add(res);

        b1.setOnAction(e -> {
            client.sendMessage("A");
            b1.setDisable(true);
            b2.setDisable(true);
            b3.setDisable(true);
        });

        b2.setOnAction(e -> {
            client.sendMessage("B");
            b1.setDisable(true);
            b2.setDisable(true);
            b3.setDisable(true);
        });
        b3.setOnAction(e -> {
            client.sendMessage("C");
            b1.setDisable(true);
            b2.setDisable(true);
            b3.setDisable(true);
        });

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #74c1cd;");
        pane.setCenter(vb);
        pane.setTop(hb);

        Scene scene = new Scene(pane, 450, 500);
        return scene;

        // ---------------------------------------
    }

    public void setQuestion(String q_a) {
        String question = q_a.split("; ")[0];
        String[] answers = Arrays.copyOfRange(q_a.split("; "), 1, q_a.split("; ").length);
        this.ques.setText(question);
        this.b1.setText(answers[0]);
        this.b2.setText(answers[1]);
        this.b3.setText(answers[2]);
        timer();
    }

    public void setResult(String result) {
        this.result = result;
    }

    private void timer() {
        TimerTask task = new TimerTask() {
            int seconds = 10;
            int i = 0;

            @Override
            public void run() {
                if (seconds - i == 0) {
                    Platform.runLater(() -> res.setText(result));
                    Platform.runLater(() -> b1.setDisable(false));
                    Platform.runLater(() -> b2.setDisable(false));
                    Platform.runLater(() -> b3.setDisable(false));
                    client.sendMessage("Pitanje");
                    this.cancel();
                } else
                    Platform.runLater(() -> res.setText("" + (seconds - i)));
                i++;
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setOponentName(String opponent) {
        this.opponent = opponent;
        //System.out.println("Poslao protivnika");
        client.sendMessage("Pitanje");
    }


}