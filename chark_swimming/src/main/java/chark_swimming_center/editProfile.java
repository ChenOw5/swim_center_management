package chark_swimming_center;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class editProfile extends Application {
    private String user_email;
    private String user_name;
    private VBox main_vbox;
    private Label error_label;
    private TextField new_name;
    private PasswordField new_password;
    private PasswordField confirm_new_password;
    private Stage profileStage;
    private UserMenu userMenu;
    private AdminMenu adminMenu;

    public editProfile(String user_name, String user_email, UserMenu userMenu) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.userMenu = userMenu;
    }

    public editProfile(String user_name, String user_email, AdminMenu adminMenu) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.adminMenu = adminMenu;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    public VBox createEditProfile() {
        error_label = new Label("Account Settings");
        main_vbox = new VBox();
        new_name = new TextField(user_name);
        new_password = new PasswordField();
        confirm_new_password = new PasswordField();

        new_password.setPromptText("Password");
        confirm_new_password.setPromptText("Confirm Password");

        Button discard = new Button("Reset Changes");
        Button confirm = new Button("Apply Changes");

        discard.setOnAction(event -> clickedDiscard());
        confirm.setOnAction(event -> clickedConfirm());

        HBox hBox0 = new HBox();
        hBox0.setSpacing(5);
        hBox0.setAlignment(Pos.CENTER);
        HBox hBox1 = new HBox();
        hBox1.setSpacing(5);
        hBox1.setAlignment(Pos.CENTER);
        HBox hBox2 = new HBox();
        hBox2.setSpacing(5);
        hBox2.setAlignment(Pos.CENTER);
        HBox hBox3 = new HBox();
        hBox3.setSpacing(5);
        hBox3.setAlignment(Pos.CENTER);
        HBox hBox4 = new HBox();
        hBox4.setSpacing(5);
        hBox4.setAlignment(Pos.CENTER);

        Label label0 = new Label("Email: ");
        Label emailLabel = new Label(user_email);
        Label label1 = new Label("Username: ");
        Label label2 = new Label("New Password: ");
        Label label3 = new Label("Confirm New Password: ");

        String labelstyle = "-fx-text-fill: darkblue;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;";
        error_label.setStyle("-fx-font-size: 15px;" +
                "-fx-text-alignment: center;" +
                "-fx-font-weight: bold;");
        emailLabel.setStyle("-fx-font-size: 15px;" +
                "-fx-text-alignment: center;" +
                "-fx-font-weight: bold;");

        Label[] labels = {label0, label1, label2, label3};
        for (Label lbl : labels) {
            lbl.setStyle(labelstyle);
        }

        hBox0.getChildren().addAll(label0, emailLabel);
        hBox1.getChildren().addAll(label1, new_name);
        hBox2.getChildren().addAll(label2, new_password);
        hBox3.getChildren().addAll(label3, confirm_new_password);
        hBox4.getChildren().addAll(discard, confirm);

        String buttonStyle = "-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;";

        discard.setStyle(buttonStyle);
        confirm.setStyle(buttonStyle);
        main_vbox.getChildren().addAll(error_label, hBox0, hBox1, hBox2, hBox3, hBox4);

        main_vbox.setAlignment(Pos.CENTER);
        main_vbox.setSpacing(10);
        return main_vbox;
    }

    private void clickedDiscard() {
        new_name.setText(user_name);
        new_password.setText(null);
        confirm_new_password.setText(null);
    }

    private void clickedConfirm() {
        String newNameText = new_name.getText().trim();
        String newPasswordText = new_password.getText().trim();
        String confirmPasswordText = confirm_new_password.getText().trim();

        boolean nameChanged = !newNameText.equals(user_name);
        boolean passwordEntered = !newPasswordText.isEmpty();
        boolean passwordsMatch = newPasswordText.equals(confirmPasswordText);

        if (nameChanged || (passwordEntered && passwordsMatch)) {
            String updatedName = nameChanged ? newNameText : "";
            if (nameChanged && SQLConnection.UsernameExist(newNameText)) {
                error_label.setText("Username Already In Use");
                error_label.setTextFill(Color.RED);
            } else {
                editProfile_stage editProfileStage;
                if (userMenu != null) {
                    editProfileStage = new editProfile_stage(
                            user_email, updatedName, newPasswordText, this, userMenu
                    );
                } else {
                    editProfileStage = new editProfile_stage(
                            user_email, updatedName, newPasswordText, this, adminMenu
                    );
                }
                Stage currentStage = (Stage) main_vbox.getScene().getWindow();
                editProfileStage.createEditProfileStage(currentStage);
            }
        } else if (!passwordsMatch) {
            error_label.setText("Passwords Don't Match");
            error_label.setTextFill(Color.RED);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
