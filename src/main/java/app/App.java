package app;

import db.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Database.init(); // ensure DB and tables exist
        var root = FXMLLoader.load(getClass().getResource("/ui/Login.fxml"));
        stage.setTitle("BirdSenger");
        stage.setResizable(false);
        stage.setScene(new Scene((Parent) root, 420, 520));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
