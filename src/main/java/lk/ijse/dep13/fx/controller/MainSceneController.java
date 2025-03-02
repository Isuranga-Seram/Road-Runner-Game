package lk.ijse.dep13.fx.controller;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class MainSceneController {
    public AnchorPane root;
    public Rectangle ground;
    public ImageView character;
    public ImageView imgCactus1;
    public ImageView imgBirds;
    public ImageView imgCar;
    public Label lblGameOver;

    int t = 0;
    int imageIndex = 1;
    int dx = 0;
    boolean jump = false;
    boolean gameOver = false;
    Timeline animationLoop;

    public void initialize() throws InterruptedException {

        double ds = 10;
        final double gravity = 5;
            var keFrame1 = new KeyFrame(Duration.millis(1000/27.), (e)->{
            double dy = gravity * t++;
            if((character.getLayoutY() + dy < root.getHeight() - ground.getHeight() - character.getFitHeight())){
                character.setLayoutY(character.getLayoutY() + dy);
            }else{
                t = 0;
                character.setLayoutY(root.getPrefHeight() - ground.getHeight() - character.getFitHeight());
            }

            if (imgCar.getLayoutX() >= root.getLayoutX() - imgCar.getFitWidth()){
                imgCar.setLayoutX(imgCar.getLayoutX() - ds);
            }else {
                imgCar.setLayoutX(root.getPrefWidth());
            }

            if (imgCar.getLayoutX() == character.getLayoutX() && (character.getLayoutY()+character.getFitHeight()) >= imgCar.getLayoutY()){
                gameOver = true;
                ScaleTransition st = new ScaleTransition(Duration.millis(500), lblGameOver);
                st.setFromX(0);
                st.setToX(1.5);
                st.setFromY(0);
                st.setToY(1.5);
                st.playFromStart();
            }else {
                lblGameOver.setScaleX(0);
            }

            if (gameOver){
                character.setImage(new Image("/image/dead/Dead (%d).png".formatted(imageIndex++)));
                if (imageIndex > 10) imageIndex = 1;
                animationLoop.stop();
            }else {
                if (dx != 0) {
                    character.setLayoutX(character.getLayoutX() + dx);
                    character.setImage(new Image("/image/run/Run (%d).png".formatted(imageIndex++)));
                    if (imageIndex > 8) imageIndex = 1;

                } else {
                    character.setImage(new Image("/image/idle/Idle (%d).png".formatted(imageIndex++)));
                    if (imageIndex > 10) imageIndex = 1;
                }
                if (jump) {
                    character.setImage(new Image("/image/jump/Jump (%d).png".formatted(imageIndex++)));
                    if (imageIndex > 8) imageIndex = 1;
                }
            }
        });
        animationLoop = new Timeline(keFrame1);
        animationLoop.setDelay(Duration.millis(500));
        animationLoop.setCycleCount(-1);
        animationLoop.playFromStart();

        TranslateTransition tt2 = new TranslateTransition(Duration.millis(4000), imgBirds);
        tt2.setNode(imgBirds);
        tt2.setCycleCount(TranslateTransition.INDEFINITE);
        tt2.setFromX(imgBirds.getFitWidth());
        tt2.setToX(-root.getPrefWidth());
        tt2.playFromStart();
    }

    public void rootOnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE){
            character.setLayoutY(character.getLayoutY() - 175);
            jump = true;
        }else if(keyEvent.getCode() == KeyCode.LEFT){
            character.setTranslateZ(character.getBoundsInLocal().getWidth() / 2.0);
            character.setRotationAxis(Rotate.Y_AXIS);
            character.setRotate(180);
            dx = -10;
        }else if(keyEvent.getCode() == KeyCode.RIGHT){
            character.setTranslateZ(character.getBoundsInLocal().getWidth() / 2.0);
            character.setRotationAxis(Rotate.Y_AXIS);
            character.setRotate(0);
            dx = 10;
        }
    }

    public void rootOnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.RIGHT){
            dx = 0;
            imageIndex = 1;
        }else if (keyEvent.getCode() ==KeyCode.SPACE) {
            jump = false;
        }
    }
}
