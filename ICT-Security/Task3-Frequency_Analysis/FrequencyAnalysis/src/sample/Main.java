package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double xOffset;
    private double yOffset;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        /** Methods to move application **/
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getScreenY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });


        // Hide top bar
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setTitle("Frenquency Analysis - Java Application");

        try{
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/icon.png")));
        }catch (Exception e){
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        //primaryStage.setScene(new Scene(root));

        primaryStage.setResizable(false);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
