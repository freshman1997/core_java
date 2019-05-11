package com.yuan.cn.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application implements EventHandler<ActionEvent>{

    private Stage window;
    private Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Label label = new Label("Welcome to the first scene.");
        Button button = new Button("Go to scene 2.");
        button.setOnAction(event -> {
            window.setScene(scene2);
        });
        //
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label, button);
        scene1 = new Scene(layout1, 200, 200);

        Button button1 = new Button("go back scene 1.");
        button1.setOnAction(e -> window.setScene(scene1));

        StackPane layout2 = new StackPane();
        Button button2 = new Button("click me.");
        button2.setOnAction(e ->{
            AlertBox.display("这是标题", "这是提示的消息。");
        });

        layout2.getChildren().addAll(button1, button2);
        scene2 = new Scene(layout2, 600, 300);

        window.setScene(scene1);
        window.setTitle("title here.");
        window.show();
    }

    @Override
    public void handle(ActionEvent event) {
    }
}
