package chark_swimming_center;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class manageUsers_stage {
    private final User user;
    private VBox mainVbox;
    private Stage manageUsersStage;
    private Label error_label;
    private TextField new_name;
    private PasswordField new_password;
    private ComboBox<String> new_privilege;
    private final manageUsers session;

    public manageUsers_stage(User user, manageUsers session) {
        this.user = user;
        this.session = session;
    }

    public Scene createManageUsersScene() {
        mainVbox = new VBox();
        mainVbox.setSpacing(10);
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setStyle("-fx-background-color: #FFFFFF;");

        error_label = new Label("Update User Details");
        new_name = new TextField(user.getUser_name());
        new_password = new PasswordField();
        new_privilege = new ComboBox<>();
        new_privilege.getItems().addAll("User", "Admin");
        new_privilege.setValue("User");

        new_password.setPromptText("Password");

        Button discard = new Button("Cancel Changes");
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
        Label emailLabel = new Label(user.getUser_email());
        Label label1 = new Label("Username: ");
        Label label2 = new Label("New Password: ");
        Label label3 = new Label("New User Privilege: ");

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
        hBox3.getChildren().addAll(label3, new_privilege);
        hBox4.getChildren().addAll(discard, confirm);

        String buttonStyle = "-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;";

        discard.setStyle(buttonStyle);
        confirm.setStyle(buttonStyle);
        mainVbox.getChildren().addAll(error_label, hBox0, hBox1, hBox2, hBox3, hBox4);

        return new Scene(mainVbox, 400, 300);
    }

    public void createManageUsersStage(Stage ownerStage) {
        manageUsersStage = new Stage();
        manageUsersStage.initModality(Modality.WINDOW_MODAL);
        manageUsersStage.initOwner(ownerStage);
        manageUsersStage.setScene(createManageUsersScene());
        manageUsersStage.setWidth(400);
        manageUsersStage.setHeight(300);
        manageUsersStage.setMinWidth(400);
        manageUsersStage.setMinHeight(300);
        manageUsersStage.setTitle("Chark Swimming Center - Update User Details");
        manageUsersStage.show();
    }

    private void clickedDiscard() {
        manageUsersStage.close();
    }

    private void clickedConfirm() {
        String newName = new_name.getText();
        String newPassword = new_password.getText();
        String newPrivilege = new_privilege.getValue().toLowerCase();

        boolean usernameChanged = !user.getUser_name().matches(newName);
        boolean passwordChanged = !user.getUser_password().matches(newPassword);
        boolean privilegeChanged = !user.getUser_privilege().matches(newPrivilege);

        if (usernameChanged) {
            if (SQLConnection.UsernameExist(newName)) {
                error_label.setText("Username Already In Use");
                error_label.setTextFill(Color.RED);
                return;
            } else {
                SQLConnection.updateUsername(user.getUser_email(), newName);
            }
        }
        if (passwordChanged && !newPassword.isEmpty()) {
            SQLConnection.updatePassword(user.getUser_email(), newPassword);
        }
        if (privilegeChanged) {
            SQLConnection.updatePrivilege(user.getUser_email(), newPrivilege);
        }
        manageUsersStage.close();
        session.refreshSessions();
    }
}
