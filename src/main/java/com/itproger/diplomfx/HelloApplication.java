package com.itproger.diplomfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Проверка подлючения к БД
        DB db = new DB();
        try {
            db.isConnected();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Сокращение сылок");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}