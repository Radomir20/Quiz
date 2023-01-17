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
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;

public class Quiz extends Application {
    Stage primarStage;
    private Scene scene;
    private Client client;
    private String question;
    private String[] answers;
    private String result;
    private String username;

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
            // client.setUsername(userName.getText());
            // client.sendUsername();
            client.sendMessage("Pitanje");
            primaryStage.setScene(makeScene());
            primaryStage.centerOnScreen();
            primaryStage.show();
            // refresh();
        });
    }

    // ----------------------------------- GUI
    private Scene makeScene() {
        HBox hb = new HBox(200);
        Label l1 = new Label("Player1: 5");// Dodati ime i bodove
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

        Label ques = new Label(this.getQuestion());// pitanje
        ques.setStyle(
                "-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-family: 'Verdana'; -fx-text-alignment: center;");
        ques.setWrapText(true);
        vb.getChildren().add(ques);

        Button b1 = new Button(this.getAnswers()[0]);// odgovor
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

        Button b2 = new Button(this.getAnswers()[1]);// odgovor
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

        Button b3 = new Button(this.getAnswers()[2]);// odgovor
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

        Label res = new Label();
        res.setStyle(
                "-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-family: 'Verdana'; -fx-text-alignment: center;");
        res.setPrefWidth(300);
        res.setWrapText(true);
        vb.getChildren().add(res);

        b1.setOnAction(e -> {
            client.sendMessage("A");
            res.setText(this.getResult());
        });

        b2.setOnAction(e -> {
            client.sendMessage("B");
            res.setText(this.getResult());
        });
        b3.setOnAction(e -> {
            client.sendMessage("C");
            res.setText(this.getResult());
        });

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #74c1cd;"); // mzd za dva klijenta razl boje
        pane.setCenter(vb);
        pane.setTop(hb);

        Scene scene = new Scene(pane, 450, 500);
        return scene;

        // ---------------------------------------
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static void main(String[] args) {
        launch(args);
    }

}