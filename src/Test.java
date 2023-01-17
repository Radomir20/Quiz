import java.util.spi.TimeZoneNameProvider;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Test extends Application{

     public static void main(String[] args) {
          launch(args);
     }

     @Override
     public void start(Stage primaryStage) throws Exception {
          
          primaryStage.setTitle("Player 1");

          HBox hb = new HBox(200);
          Label l1 = new Label("Player1: 5");
          l1.setStyle("-fx-font-size: 12; -fx-text-fill: white; -fx-font-family: 'Verdana'; -fx-font-weight: bold;");
          Label l2 = new Label("Player2: 9");
          l2.setStyle("-fx-font-size: 12; -fx-text-fill: white; -fx-font-family: 'Verdana'; -fx-font-weight: bold;");
          hb.getChildren().addAll(l1,l2);
          hb.setAlignment(Pos.CENTER);
          hb.setStyle("-fx-background-color: #073737; -fx-padding: 5 0 5 0");
          //hb.setPrefHeight(40);
          

          VBox vb = new VBox(20);
          vb.setAlignment(Pos.TOP_CENTER);
          vb.setStyle("-fx-padding: 50 0 0 0");
          
          
          Label ques = new Label("Koji je jedan od benefita OOP-a?");
          ques.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-family: 'Verdana'; -fx-text-alignment: center;");
          ques.setPrefWidth(300);
          ques.setWrapText(true);
          vb.getChildren().add(ques);


          Button b1 = new Button("A) Fleksibilnost");
          b1.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
               + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
          b1.setPrefWidth(300);
          //b1.setMinHeight(60);
          vb.getChildren().add(b1);

          Button b2 = new Button("B) Polimorfizam");
          b2.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
               + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
          b2.setPrefWidth(300);
          vb.getChildren().add(b2);

          Button b3 = new Button("C) Održivost");
          b3.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
               + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
          b3.setPrefWidth(300);
          vb.getChildren().add(b3);
          b3.setOnMouseEntered(new EventHandler<MouseEvent>() {
               @Override public void handle(MouseEvent mouseEvent){
                    b3.setStyle("-fx-text-fill: #0c8080; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                         + "-fx-background-color: #A9F2F2; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
               }
          });
          b3.setOnMouseExited(new EventHandler<MouseEvent>() {
               @Override public void handle(MouseEvent mouseEvent){
                    b3.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'Verdana';"
                         + "-fx-background-color: #0c8080; -fx-background-radius: 30px; -fx-padding: 15; -fx-cursor: hand;");
               }
          });


          BorderPane pane = new BorderPane();
          pane.setStyle("-fx-background-color: #74c1cd;");   //mzd za dva klijenta razl boje
          pane.setCenter(vb);
          pane.setTop(hb);
          

          Scene scene = new Scene(pane, 450, 500);
          primaryStage.setScene(scene);
          primaryStage.show();
     }
     
}
