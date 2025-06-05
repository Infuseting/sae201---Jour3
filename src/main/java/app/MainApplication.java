package app;

import app.fx.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        MainController controller = fxmlLoader.getController();
        stage.setScene(scene);

        stage.show();

    }





    public static void main(String[] args) {
        launch();
    }
}