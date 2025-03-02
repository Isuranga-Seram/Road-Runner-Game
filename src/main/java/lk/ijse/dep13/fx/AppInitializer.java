package lk.ijse.dep13.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/scene/MainScene.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        root.setFocusTraversable(true);
        root.requestFocus();

        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Gravity");
        primaryStage.centerOnScreen();

    }
}
