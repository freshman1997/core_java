package com.yuan.cn.cut_image;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main extends Application {
    private ImageView iv;
    private Stage stage;
    private Stage stage1;
    private boolean is_done =false;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root);
        iv= new ImageView();
        iv.setFitHeight(400);

        iv.setPreserveRatio(true);
        Button button = new Button("开始截图");
        Button button1 = new Button("保存到硬盘");
        Button button2 = new Button("copy到剪切板");


        root.getChildren().add(button);
        root.getChildren().add(button1);
        root.getChildren().add(button2);
        AnchorPane.setTopAnchor(button, 50.0);
        AnchorPane.setLeftAnchor(button, 50.0);
        AnchorPane.setTopAnchor(button1, 50.0);
        AnchorPane.setLeftAnchor(button1, 150.0);
        AnchorPane.setTopAnchor(button2, 50.0);
        AnchorPane.setLeftAnchor(button2, 250.0);
        root.getChildren().add(iv);

        AnchorPane.setTopAnchor(iv, 100.0);
        AnchorPane.setLeftAnchor(iv, 50.0);
        primaryStage.setTitle("jack_yuan_截图");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(false);
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();
        button.setOnAction(event -> show());
        button1.setOnAction(event -> {
            if(!is_done){
                Alert alert = new Alert(Alert.AlertType.ERROR,"你还没有开始截图哦！",new ButtonType("取消", ButtonBar.ButtonData.NO),
                        new ButtonType("确定", ButtonBar.ButtonData.YES));
                alert.showAndWait();
            }else {
                saveToDisk();
            }
        });
        button2.setOnAction(event -> {
            if(!is_done){
                Alert alert = new Alert(Alert.AlertType.ERROR,"你还没有开始截图哦！",new ButtonType("取消", ButtonBar.ButtonData.NO),
                        new ButtonType("确定", ButtonBar.ButtonData.YES));
                alert.showAndWait();
            }else
                addToClipboard();
        });
        KeyCombination key = KeyCombination.valueOf("ctrl+alt+p");
        Mnemonic mc = new Mnemonic(button, key);
        scene.addMnemonic(mc);
    }

    private void show(){
        // 让按钮点击即进入最小化
        stage.setIconified(true);

        AnchorPane pane = new AnchorPane();
        pane.setStyle("-fx-background-color: #cccccccc");
        Scene scene = new Scene(pane);
        stage1 = new Stage();
        scene.setFill(Paint.valueOf("#FFFFFF00"));
        stage1.setFullScreenExitHint("选择区域开始截图");
        stage1.initStyle(StageStyle.TRANSPARENT);
        stage1.setScene(scene);
        stage1.setFullScreen(true);
        stage1.show();

        drag(pane);

        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                stage1.close();
                stage.setIconified(false);
            }
        });
    }
    double scenex_start;
    double sceney_start;
    double scenex_end;
    double sceney_end;
    HBox box;
    private void drag(AnchorPane anchorPane){
        anchorPane.setOnMousePressed(event -> {
            anchorPane.getChildren().clear();
            box = new HBox();
            box.setBackground(null);
            box.setBorder(new Border(new BorderStroke(Paint.valueOf("#CD3700"), BorderStrokeStyle.SOLID, null, new BorderWidths((2)))));

            anchorPane.getChildren().add(box);
            scenex_start = event.getSceneX();
            sceney_start = event.getSceneY();

            AnchorPane.setTopAnchor(box, sceney_start);
            AnchorPane.setLeftAnchor(box, scenex_start);

        });
        anchorPane.setOnDragDetected(event -> {
            anchorPane.startFullDrag();
        });
        anchorPane.setOnMouseDragOver(event -> {
            Label label = new Label();
            label.setAlignment(Pos.CENTER);
            label.setPrefWidth(200);
            label.setPrefHeight(30);
            anchorPane.getChildren().add(label);
            AnchorPane.setLeftAnchor(label, scenex_start);
            AnchorPane.setTopAnchor(label, sceney_start - label.getPrefHeight());
            label.setTextFill(Paint.valueOf("#ffffff"));
            label.setStyle("-fx-background-color: #000000");
            double sceneX = event.getSceneX();
            double sceneY = event.getSceneY();

            double width = sceneX - scenex_start;
            double height = sceneY - sceney_start;
            box.setPrefWidth(width);
            box.setPrefHeight(height);
            label.setText("宽度："+width+" 高度："+height);
        });
        anchorPane.setOnMouseDragExited(event -> {
            scenex_end = event.getSceneX();
            sceney_end = event.getSceneY();

            Button button = new Button("选择完成");
            box.getChildren().add(button);
            box.setAlignment(Pos.BOTTOM_RIGHT);
            button.setOnAction(event1 -> {
                try {
                    getScreenImage();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            });
        });
    }
    BufferedImage bufferedImage;
    WritableImage wi;
    // 生成图像并放入剪切板
    private void getScreenImage() throws AWTException {
        stage1.close();
        double w = scenex_end - scenex_start;
        double h = sceney_end - sceney_start;

        Robot robot = new Robot();
        Rectangle rectangle = new Rectangle((int)scenex_start, (int)sceney_start, (int)w, (int) h);
        bufferedImage = robot.createScreenCapture(rectangle);
        wi = SwingFXUtils.toFXImage(bufferedImage, null);
        iv.setImage(wi);
        stage.setIconified(false);
        addToClipboard();
        is_done = true;
    }
    private void addToClipboard(){
        Clipboard cb = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putImage(wi);
        cb.setContent(content);
    }
    private void saveToDisk(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("");
        fileChooser.setInitialFileName("");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG","*.jpg"),
                                                 new FileChooser.ExtensionFilter("PNG","*.png"));
        File file = fileChooser.showSaveDialog(stage);
        if(file != null){
            List<String> extensions = fileChooser.getSelectedExtensionFilter().getExtensions();
            for(String format :extensions){
                if(format.equalsIgnoreCase(".jpg")){
                    try {
                        ImageIO.write(bufferedImage,"jpg", file.getAbsoluteFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        ImageIO.write(bufferedImage,"png", file.getAbsoluteFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
