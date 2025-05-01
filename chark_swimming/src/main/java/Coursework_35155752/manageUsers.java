package Coursework_35155752;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;

public class manageUsers {
    private VBox main_vbox;
    private TableView<User> userTable;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Label errorLabel;

    public VBox createUserManager() {
        main_vbox = new VBox(15);
        main_vbox.setPadding(new Insets(20));
        main_vbox.setAlignment(Pos.CENTER);
        main_vbox.setStyle("-fx-background-color: #FFFFFF;");

        errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: black;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;");

        userTable = new TableView<>();
        userTable.setStyle("-fx-font-size: 14px; -fx-text-fill: darkblue;");
        createUserTable();

        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);

        addButton = createStyledButton("Add");
        updateButton = createStyledButton("Update");
        deleteButton = createStyledButton("Delete");

        inputBox.getChildren().addAll(updateButton, deleteButton);


        updateButton.setOnAction(e -> updateUser());
        deleteButton.setOnAction(e -> deleteSession());

        refreshSessions();

        main_vbox.getChildren().addAll(userTable, errorLabel, inputBox);
        return main_vbox;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #0092c7;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;"));
        return button;
    }

    private void createUserTable() {
        userTable.getColumns().clear();

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));

        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));

        TableColumn<User, String> passCol = new TableColumn<>("Password");
        passCol.setCellValueFactory(new PropertyValueFactory<>("user_password"));

        TableColumn<User, String> privCol = new TableColumn<>("Privilege");
        privCol.setCellValueFactory(new PropertyValueFactory<>("user_privilege"));

        userTable.getColumns().addAll(emailCol, nameCol, passCol, privCol);
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userTable.getColumns().forEach(col -> col.setReorderable(false));
    }

    public void refreshSessions() {
        List<User> users = SQLConnection.getAllUsers();
        ObservableList<User> observableList = FXCollections.observableArrayList(users);
        userTable.setItems(observableList);
    }

    private void updateUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            manageUsers_stage manageUsersStage = new manageUsers_stage(selected, this);
            manageUsersStage.createManageUsersStage((Stage) main_vbox.getScene().getWindow());
        } else {
            errorLabel.setText("Please Select a User");
            errorLabel.setTextFill(Color.RED);
        }
    }

    private void deleteSession() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected.getUser_privilege().equals("admin")) {
            errorLabel.setText("Admins Cannot Be Deleted");
            errorLabel.setTextFill(Color.RED);
        }else{
            if (selected != null) {
                int deletecheck = SQLConnection.deleteUser(selected.getUser_email());
                if (deletecheck == 1) {
                    refreshSessions();
                } else {
                    errorLabel.setText("User Still Has Existing Sessions");
                    errorLabel.setTextFill(Color.RED);
                }
            } else {
                errorLabel.setText("Please Select a User To Delete");
                errorLabel.setTextFill(Color.RED);
            }
        }
    }
}