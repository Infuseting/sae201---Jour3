package app;

import app.fx.controller.MainController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
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
        stage.titleProperty().bind(Bindings.concat(
                "GraphEditor - ",
                Bindings.when(controller.contentController.isModifiedProperty)
                        .then(" *")
                        .otherwise(""),
                controller.contentController.currentFile()

        ));
        stage.setScene(scene);

        stage.show();
        closeWindow(stage, controller);

    }

    public void closeWindow(Stage stage, MainController controller) {
        stage.setOnCloseRequest(event -> {
            if (controller.contentController.isModifiedProperty.get()) {
                if (!controller.onQuitter(controller)) {
                    event.consume();
                }
            }

        });
    }



    public static void main(String[] args) {
        launch();
    }
}