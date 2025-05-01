package chark_swimming_center;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SalesReport {

    private TableView<Session> salesTable;
    private DatePicker minDatePicker;
    private DatePicker maxDatePicker;
    private Label totalAmount;
    private Label totalSalesLabel;
    private Label avgSalesLabel;
    private Label maxSalesLabel;
    private Label minSalesLabel;
    private Label errorLabel;

    public VBox createSalesReport() {
        VBox mainVBox = new VBox(15);
        mainVBox.setPadding(new Insets(20));
        mainVBox.setAlignment(Pos.CENTER);

        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER);

        minDatePicker = new DatePicker();
        maxDatePicker = new DatePicker();

        Button applyButton = createStyledButton("Apply Filters");
        applyButton.setOnAction(e -> applyFilter());

        Label dateLabel = new Label("Apply Date Filers: ");
        Label to = new Label("To ");
        totalAmount = new Label("Total Session Amount: 0");
        totalSalesLabel = new Label("Total Sales: 0.00");
        avgSalesLabel = new Label("Average Sale: 0.00");
        maxSalesLabel = new Label("Maximum Sale: 0.00");
        minSalesLabel = new Label("Minimum Sale: 0.00");

        filterBox.getChildren().addAll(dateLabel, minDatePicker,to, maxDatePicker, applyButton);


        String labelstyle = "-fx-text-fill: darkblue;" +
                "-fx-font-size: 14px;" +
                "-fx-text-alignment: center;" +
                "-fx-font-weight: bold;";

        Label[] labels = {dateLabel,to, totalAmount,totalSalesLabel, avgSalesLabel, maxSalesLabel, minSalesLabel};
        for (Label lbl : labels) {
            lbl.setStyle(labelstyle);
        }

        HBox summaryBox = new HBox(20, totalAmount,totalSalesLabel, avgSalesLabel, maxSalesLabel, minSalesLabel);
        summaryBox.setAlignment(Pos.CENTER);

        salesTable = new TableView<>();
        salesTable.setStyle("-fx-font-size: 14px; -fx-text-fill: darkblue;");
        createSalesTable();

        errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-alignment: center;");

        refreshSales();

        mainVBox.getChildren().addAll(salesTable, errorLabel, filterBox, summaryBox);

        return mainVBox;
    }

    private void createSalesTable() {
        salesTable.getColumns().clear();

        TableColumn<Session, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("sessionId"));

        TableColumn<Session, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));

        TableColumn<Session, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));

        TableColumn<Session, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Session, String> methodCol = new TableColumn<>("Payment Method");
        methodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        salesTable.getColumns().addAll(idCol, emailCol, dateCol, amountCol, methodCol);
        salesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        salesTable.getColumns().forEach(col -> col.setReorderable(false));
    }

    private void refreshSales() {
        List<Session> sessions = SQLConnection.getBookedSessions_admin();
        ObservableList<Session> observableList = FXCollections.observableArrayList(sessions);
        salesTable.setItems(observableList);
        updateSalesSummary(observableList);
    }

    private void applyFilter() {
        LocalDate minDate = minDatePicker.getValue();
        LocalDate maxDate = maxDatePicker.getValue();

        if (minDate != null || maxDate != null) {
            List<Session> sessions = SQLConnection.getBookedSessions_admin();
            List<Session> filtered;
            if (minDate != null && maxDate == null) {
                filtered = sessions.stream()
                        .filter(s -> s.getSessionDate().toLocalDate().isAfter(minDate))
                        .collect(Collectors.toList());
            } else if (minDate == null) {
                filtered = sessions.stream()
                        .filter(s -> s.getSessionDate().toLocalDate().isBefore(maxDate))
                        .collect(Collectors.toList());
            } else {
                filtered = sessions.stream()
                        .filter(s -> s.getSessionDate().toLocalDate().isAfter(minDate) && s.getSessionDate().toLocalDate().isBefore(maxDate))
                        .collect(Collectors.toList());
            }

            ObservableList<Session> observableList = FXCollections.observableArrayList(filtered);
            salesTable.setItems(observableList);
            updateSalesSummary(observableList);
            errorLabel.setText("");
        }
    }

    private void updateSalesSummary(ObservableList<Session> sessions) {
        int amount = sessions.size();
        double total = sessions.stream().mapToDouble(Session::getAmount).sum();
        double avg = sessions.isEmpty() ? 0 : total / amount;
        double max = sessions.stream().mapToDouble(Session::getAmount).max().orElse(0);
        double min = sessions.stream().mapToDouble(Session::getAmount).min().orElse(0);

        totalAmount.setText(String.format("Total Sessions: %d", amount));
        totalSalesLabel.setText(String.format("Total Sales: %.2f", total));
        avgSalesLabel.setText(String.format("Average Sale: %.2f", avg));
        maxSalesLabel.setText(String.format("Maximum Sale: %.2f", max));
        minSalesLabel.setText(String.format("Minimum Sale: %.2f", min));
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: darkblue; -fx-text-fill: white; -fx-font-size: 15px; -fx-text-alignment: center;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #0092c7; -fx-text-fill: white; -fx-font-size: 15px; -fx-text-alignment: center;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: darkblue; -fx-text-fill: white; -fx-font-size: 15px; -fx-text-alignment: center;"));
        return button;
    }
}
