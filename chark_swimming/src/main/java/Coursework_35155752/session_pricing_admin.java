package Coursework_35155752;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

public class session_pricing_admin {
    private TableView<Term> termsTable;
    private TextField termInput;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Label errorLabel;

    public VBox createTermsManager() {
        VBox main_vbox = new VBox(15);
        main_vbox.setPadding(new Insets(20));
        main_vbox.setAlignment(Pos.CENTER);
        errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: black;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;");

        termsTable = new TableView<>();
        termsTable.setStyle("-fx-font-size: 14px; -fx-text-fill: darkblue;");
        createTermsTable();

        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);

        termInput = new TextField();
        termInput.setPromptText("Enter new term");
        termInput.setMinWidth(200);
        termInput.setStyle("-fx-font-size: 14px;");

        addButton = createStyledButton("Add");
        updateButton = createStyledButton("Update");
        deleteButton = createStyledButton("Delete");

        inputBox.getChildren().addAll(termInput, addButton, updateButton, deleteButton);

        addButton.setOnAction(e -> addTerm());
        updateButton.setOnAction(e -> updateTerm());
        deleteButton.setOnAction(e -> deleteTerm());

        refreshTerms();

        main_vbox.getChildren().addAll(termsTable, errorLabel, inputBox);
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

    private void createTermsTable() {
        termsTable.getColumns().clear();

        TableColumn<Term, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("term_id"));
        idCol.setMinWidth(65);

        TableColumn<Term, String> termCol = new TableColumn<>("Term");
        termCol.setCellValueFactory(new PropertyValueFactory<>("terms"));
        termCol.setMinWidth(400);

        termsTable.getColumns().addAll(idCol, termCol);
        termsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        termsTable.getColumns().forEach(col -> col.setReorderable(false));
    }

    private void refreshTerms() {
        List<Term> terms = SQLConnection.getTerms();
        ObservableList<Term> observableList = FXCollections.observableArrayList(terms);
        termsTable.setItems(observableList);
    }

    private void addTerm() {
        String termText = termInput.getText().trim();
        if (!termText.isEmpty()) {
            if (SQLConnection.addTerm(termText)) {
                refreshTerms();
                termInput.clear();
            } else {
                errorLabel.setText("Error In Adding Term");
                errorLabel.setTextFill(Color.RED);
            }
        } else {
            errorLabel.setText("Please Enter New Text To Add");
            errorLabel.setTextFill(Color.RED);
        }
    }

    private void updateTerm() {
        Term selected = termsTable.getSelectionModel().getSelectedItem();
        String newText = termInput.getText().trim();

        if (selected != null && !newText.isEmpty()) {
            if (SQLConnection.updateTerm(selected.getTerm_id(), newText)) {
                refreshTerms();
                termInput.clear();
            } else {
                errorLabel.setText("Error In Updating Term");
                errorLabel.setTextFill(Color.RED);
            }
        } else {
            errorLabel.setText("Please Select a Term and Enter New Text");
            errorLabel.setTextFill(Color.RED);
        }
    }

    private void deleteTerm() {
        Term selected = termsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (SQLConnection.deleteTerm(selected.getTerm_id())) {
                refreshTerms();
            } else {
                errorLabel.setText("Error In Deleting Term");
                errorLabel.setTextFill(Color.RED);
            }
        } else {
            errorLabel.setText("Please Select a Term To Delete");
            errorLabel.setTextFill(Color.RED);
        }
    }

}