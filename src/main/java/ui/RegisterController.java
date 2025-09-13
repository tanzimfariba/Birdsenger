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

public class RegisterController {

    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private Label statusLabel;

    private final UserDao userDao = new UserDao();

    @FXML
    public void initialize() {
        Database.init();
    }

    @FXML
    public void onRegister(ActionEvent e) {
        String email = emailField.getText();
        String username = usernameField.getText();
        String pass = passwordField.getText();
        String confirm = confirmField.getText();

        if (email.isBlank() || username.isBlank() || pass.isBlank() || confirm.isBlank()) {
            statusLabel.setText("Please fill all fields.");
            return;
        }
        if (!pass.equals(confirm)) {
            statusLabel.setText("Passwords do not match.");
            return;
        }
        if (pass.length() < 6) {
            statusLabel.setText("Use at least 6 characters.");
            return;
        }

        boolean created = userDao.register(email, username, pass);
        if (created) {
            statusLabel.setText("Account created ✅ You can login now.");
            // Optionally jump to login automatically:
            // goToLogin(null);
        } else {
            statusLabel.setText("Email or username already exists.");
        }
    }

    @FXML
    public void goToLogin(ActionEvent e) {
        switchScene("/ui/Login.fxml");
    }

    private void switchScene(String fxmlPath) {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root, 420, 520));
        } catch (Exception ex) {
            ex.printStackTrace();
            statusLabel.setText("Could not switch screen.");
        }
    }
}
