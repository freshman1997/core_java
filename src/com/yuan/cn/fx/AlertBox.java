package com.yuan.cn.fx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message)
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("close the window");
        closeButton.setOnAction(e -> window.close());

        VBox v = new VBox(10);
        v.getChildren().addAll(label, closeButton);
        v.setAlignment(Pos.CENTER);

        Scene scene = new Scene(v);
        window.setScene(scene);
        window.showAndWait();
    }
}
