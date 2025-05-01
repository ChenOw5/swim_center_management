package chark_swimming_center;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class editProfile_stage {
    private String user_email;
    private String new_name;
    private String new_password;
    private editProfile session;
    private Stage profile_stage;
    private VBox profile_vbox;
    private Scene profile_scene;
    private UserMenu userMenu;
    private AdminMenu adminMenu;

    public editProfile_stage(String user_email, String new_name, String new_password, editProfile session, UserMenu userMenu) {
        this.user_email = user_email;
        this.new_name = new_name;
        this.new_password = new_password;
        this.session = session;
        this.userMenu = userMenu;
    }

    public editProfile_stage(String user_email, String new_name, String new_password, editProfile session, AdminMenu adminMenu) {
        this.user_email = user_email;
        this.new_name = new_name;
        this.new_password = new_password;
        this.session = session;
        this.adminMenu = adminMenu;
    }

    private Scene createProfileScene() {
        profile_vbox = new VBox();
        profile_vbox.setSpacing(10);
        profile_vbox.setAlignment(Pos.CENTER);
        profile_scene = new Scene(profile_vbox, 400, 300);
        Button cancel = new Button("Cancel Changes");
        Button confirm = new Button("Confirm Changes");

        String buttonStyle = "-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;";

        cancel.setStyle(buttonStyle);
        confirm.setStyle(buttonStyle);

        HBox hBox0 = new HBox();
        hBox0.setAlignment(Pos.CENTER);
        hBox0.setSpacing(5);
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(5);
        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(5);

        Label label0 = new Label("New Username: ");
        Label label1 = new Label(new_name);
        Label label2 = new Label("New Password: ");
        Label label3 = new Label("*".repeat(new_password.length()));

        String labelstyle = "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;" +
                "-fx-font-weight: bold;";

        Label[] labels0 = {label0, label2};
        Label[] labels1 = {label1, label3};
        for (Label lbl : labels0) {
            lbl.setStyle(labelstyle + "-fx-text-fill: darkblue;");
        }
        for (Label lbl : labels1) {
            lbl.setStyle(labelstyle + "-fx-text-fill: black;");
        }

        hBox0.getChildren().addAll(label0, label1);
        hBox1.getChildren().addAll(label2, label3);
        hBox2.getChildren().addAll(cancel, confirm);

        if (!new_name.isEmpty()) {
            profile_vbox.getChildren().add(hBox0);
        }
        if (!new_password.isEmpty()) {
            profile_vbox.getChildren().add(hBox1);
        }

        profile_vbox.getChildren().addAll(hBox2);
        cancel.setOnAction(event -> clickedCancel());
        confirm.setOnAction(event -> clickedConfirm());

        profile_vbox.setStyle("-fx-background-color: white;");
        return profile_scene;
    }

    private void clickedCancel() {
        profile_stage.close();
    }

    private void clickedConfirm() {
        if (!new_name.isEmpty()) {
            SQLConnection.updateUsername(user_email, new_name);
        }
        if (!new_password.isEmpty()) {
            SQLConnection.updatePassword(user_email, new_password);
        }

        if (userMenu != null) {
            userMenu.createHeader();
            userMenu.clickedUser(user_email);
        } else {
            adminMenu.createHeader();
            adminMenu.clickedUser();
        }

        profile_stage.close();
    }

    public void createEditProfileStage(Stage ownerStage) {
        profile_stage = new Stage();
        profile_stage.initModality(Modality.WINDOW_MODAL);
        profile_stage.initOwner(ownerStage);
        profile_stage.setScene(createProfileScene());
        profile_stage.setWidth(400);
        profile_stage.setHeight(300);
        profile_stage.setMinWidth(400);
        profile_stage.setMinHeight(300);
        profile_stage.setTitle("Chark Swimming Center - Confirm Account Changes");
        profile_stage.show();
    }
}
