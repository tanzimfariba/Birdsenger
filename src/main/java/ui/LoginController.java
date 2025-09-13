package ui;

import db.Database;
import db.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField loginKeyField;   // username or email
    @FXML private PasswordField loginPasswordField;
    @FXML private Label statusLabel;

    private final UserDao userDao = new UserDao();

    @FXML
    public void initialize() {
        Database.init(); // safe to call multiple times
    }

    @FXML
    public void onLogin(ActionEvent e) {
        String key = loginKeyField.getText();
        String pass = loginPasswordField.getText();

        if (key.isBlank() || pass.isBlank()) {
            statusLabel.setText("Please fill all fields.");
            return;
        }

        boolean ok = userDao.login(key, pass);
        if (ok) {
            statusLabel.setText("Login success");
            // TODO: navigate to your main/chat screen
        } else {
            statusLabel.setText("Invalid username/email or password");
        }
    }

    @FXML
    public void goToRegister(ActionEvent e) {
        switchScene("/ui/Register.fxml");
    }

    private void switchScene(String fxmlPath) {
        try {
            Stage stage = (Stage) loginKeyField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root, 420, 520));
        } catch (Exception ex) {
            ex.printStackTrace();
            statusLabel.setText("Could not switch screen.");
        }
    }
}
