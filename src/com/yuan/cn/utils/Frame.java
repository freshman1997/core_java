package com.yuan.cn.utils;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class Frame extends Application {
     static boolean isStop = false;
    private static ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private static PrintStream printStream = new PrintStream(byteArrayOutputStream);
     static ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    private static TextArea textArea = new TextArea();
    private static PrintThread printThread = new PrintThread();
//     BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
//     BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream));

    public static void main(String[] args) throws InterruptedException {
        System.setOut(printStream);
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setHeight(800);
        primaryStage.setWidth(800);

        System.out.println("Hello World!!!!");
        System.out.println("你好世界！！！");

        VBox box = new VBox();
        Scene scene = new Scene(box);
        scene.setFill(Paint.valueOf("#0066FF"));
        box.setBackground(new Background(new BackgroundFill(Paint.valueOf("#0066FF"),new CornerRadii(10), new Insets(0))));
        printThread.start();

        textArea.setPrefHeight(700);
        textArea.setPrefWidth(700);
        textArea.setWrapText(true);

        textArea.setBackground(new Background(
                new BackgroundFill(Paint.valueOf("#0066FF"),
                        new CornerRadii(10), new Insets(0))));

        Button button = new Button("call");
        Button clear = new Button("clear");
        clear.setOnAction(event -> {
            textArea.setText("");
        });
        //18362097
        button.setOnAction(event -> {
            System.out.println("你好，世界！！！");
            System.out.println("size = " + byteArrayOutputStream.size());
            System.out.println("Hello World!!");
            printThread = new PrintThread();
            printThread.start();
        });
        box.getChildren().addAll(button, clear, textArea);

        primaryStage.setScene(scene);
        primaryStage.show();
        Platform.runLater(() ->{
            ScheduledService service = new ScheduledService() {
                @Override
                protected Task createTask() {

                    return new Task<String>() {

                        @Override
                        protected String call() throws Exception {

                            //String s = new String(byteArrayOutputStream.toByteArray());
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
                            byteArrayOutputStream.reset();
                            String str;
                            while ( (str = reader.readLine()) != null)
                            {
                                if (! str.equals("") && ! str.equals("\n"))
                                {
                                    if (! str.contains("\n"))
                                        str = str + "\n";
                                    textArea.appendText(str);
                                    Thread.sleep(100);
                                }
                            }
                            reader.close();

                            return null;
                        }
                    };
                }
            };
            service.setPeriod(Duration.millis(0));
            service.setDelay(Duration.millis(0));
            service.start();
        });

        primaryStage.setOnCloseRequest(event -> {
            isStop = true;
            Stage stage = new Stage();
            Scene scene1 = new Scene(new VBox());
            scene1.setFill(Paint.valueOf("#ff00ff"));
            stage.setWidth(100);
            stage.setHeight(100);
            //stage.initModality(Modality.WINDOW_MODAL);
            //stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(primaryStage);
            stage.setScene(scene1);
            stage.show();

            printStream.close();
        });
    }


}
