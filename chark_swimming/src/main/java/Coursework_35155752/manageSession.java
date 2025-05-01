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

public class manageSession {
    private TableView<Session> sessionTable;
    private Button updateButton;
    private Button deleteButton;
    private Label errorLabel;
    private VBox main_vbox;
    public VBox createSessionManager() {
        main_vbox = new VBox(15);
        main_vbox.setPadding(new Insets(20));
        main_vbox.setAlignment(Pos.CENTER);
        errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: black;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;");

        sessionTable = new TableView<>();
        sessionTable.setStyle("-fx-font-size: 14px; -fx-text-fill: darkblue;");
        createSessionTable();

        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);

        updateButton = createStyledButton("Update");
        deleteButton = createStyledButton("Delete");

        inputBox.getChildren().addAll(updateButton, deleteButton);

        updateButton.setOnAction(e -> updateSession());
        deleteButton.setOnAction(e -> deleteSession());

        refreshSessions();

        main_vbox.getChildren().addAll(sessionTable, errorLabel, inputBox);
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

    private void createSessionTable() {
        sessionTable.getColumns().clear();

        TableColumn<Session, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("sessionId"));

        TableColumn<Session, Integer> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));

        TableColumn<Session, Date> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));

        TableColumn<Session, String> startCol = new TableColumn<>("Start Time");
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<Session, String> endCol = new TableColumn<>("End Time");
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<Session, Integer> paxCol = new TableColumn<>("Pax");
        paxCol.setCellValueFactory(new PropertyValueFactory<>("sessionPax"));

        TableColumn<Session, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Session, String> methodCol = new TableColumn<>("Payment Method");
        methodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        TableColumn<Session, Integer> deletedCol = new TableColumn<>("Is Deleted");
        deletedCol.setCellValueFactory(new PropertyValueFactory<>("is_deleted"));

        sessionTable.getColumns().addAll(idCol, emailCol, dateCol, startCol, endCol, paxCol, amountCol, methodCol, deletedCol);
        sessionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        sessionTable.getColumns().forEach(col -> col.setReorderable(false));
    }

    public void refreshSessions() {
        List<Session> sessions = SQLConnection.getBookedSessions_admin();
        ObservableList<Session> observableList = FXCollections.observableArrayList(sessions);
        sessionTable.setItems(observableList);
    }

    private void updateSession() {
        Session selected = sessionTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            manageSession_stage manageSessionStage =new manageSession_stage(selected,this);
            manageSessionStage.createManageSessionStage((Stage) main_vbox.getScene().getWindow());
        } else {
            errorLabel.setText("Please Select a Session");
            errorLabel.setTextFill(Color.RED);
        }
    }

    private void deleteSession() {
        Session selected = sessionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (SQLConnection.hardDeleteSession(selected.getSessionId())) {
                refreshSessions();
            } else {
                errorLabel.setText("Error In Deleting Session");
                errorLabel.setTextFill(Color.RED);
            }
        } else {
            errorLabel.setText("Please Select a Term To Delete");
            errorLabel.setTextFill(Color.RED);
        }
    }

}