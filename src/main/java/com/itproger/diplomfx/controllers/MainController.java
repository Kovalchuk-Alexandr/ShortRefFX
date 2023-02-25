/**
 * Sample Skeleton for 'main.fxml' Controller Class
 */

package com.itproger.diplomfx.controllers;

import com.itproger.diplomfx.DB;
import com.itproger.diplomfx.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="add_btn"
    private Button add_btn; // Value injected by FXMLLoader

    @FXML // fx:id="full_ref"
    private TextField full_ref; // Value injected by FXMLLoader

    @FXML // fx:id="msg_label"
    private Label msg_label; // Value injected by FXMLLoader

    @FXML // fx:id="panelVBox"
    private VBox panelVBox; // Value injected by FXMLLoader

    @FXML // fx:id="short_ref"
    private TextField short_ref; // Value injected by FXMLLoader

    private DB db = new DB();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, SQLException {
        add_btn.setOnAction(event -> {
            try {
                setData();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        setRef();
    }

    private void setRef() throws SQLException, IOException {
        ResultSet resultSet = db.getRef();
        while (resultSet.next()) {

            //resultSet.updateObject();
            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("ref_list.fxml")));

            // Находим объекты по заголовкам
            Label lbl_short = (Label) node.lookup("#label_short");
            lbl_short.setText(resultSet.getString("shortref"));

            Label lbl_URL = (Label) node.lookup("#label_full");
            lbl_URL.setText(resultSet.getString("fullref"));

            // Дополнительные украшения/эффекты
            // При наведении мыши
            node.setOnMouseEntered(event -> {
                node.setStyle("-fx-background-color: #707173");
            });

            // При убирании мыши
            node.setOnMouseExited(event -> {
                node.setStyle("-fx-background-color:  #4f4545");
            });

            panelVBox.getChildren().add(node);
            panelVBox.setSpacing(0);   // Отступы между объектами по 10 пикселей
        }
    }

    private void setData() throws SQLException, IOException {
        String fullRef =  full_ref.getCharacters().toString();
        String shortRef =  short_ref.getCharacters().toString();

        // При повторном нажатии устанавливаем исходный цвет обводки
        full_ref.setStyle("-fx-border-color: #14ba5a");
        short_ref.setStyle("-fx-border-color: #14ba5a");


        // Проверяем, все ли поля заполнены корректно, если нет, делаем красную обводку
        if(shortRef.length() <= 1)
            short_ref.setStyle("-fx-border-color: #e06249");
//        else if (fullRef.length() <= 9)
//            full_ref.setStyle("-fx-border-color: #e06249");
        else if (!fullRef.contains("https://")) {
            full_ref.setStyle("-fx-border-color: #e06249");
            msg_label.setStyle("-fx-text-fill: #e06249");
            msg_label.setText("URL должен содержать https://");
        } else if (fullRef.length() > 250) {
            full_ref.setStyle("-fx-border-color: #e06249");
            msg_label.setStyle("-fx-text-fill: #e06249");
            msg_label.setText("URL должен меньше 250 символов");
        } else if (db.isExistRef(shortRef)) {
            msg_label.setStyle("-fx-text-fill: #e06249");
            msg_label.setText("Укажите другое сокращение ");
      } else {
            db.addRef(shortRef, fullRef);
            panelVBox.getChildren().clear();
            setRef();
            full_ref.setText("");  // Если запись существует
            short_ref.setText("");  // очищаем поля
            msg_label.setStyle("-fx-text-fill: #14ba5a");
            msg_label.setText("Все готово :)");
        }
    }
}
