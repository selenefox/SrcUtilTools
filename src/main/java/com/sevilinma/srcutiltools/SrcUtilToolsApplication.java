package com.sevilinma.srcutiltools;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SrcUtilToolsApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SrcUtilToolsApplication.class.getResource("util-tools-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //stage.initStyle(StageStyle.UTILITY);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.setOpacity(0.9);
        stage.setTitle("Cleaning tools - v1.0");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}