package chark_swimming_center;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class manageSession_stage {
    private final Session session;
    private VBox main_vbox;
    private Stage manageSessionStage;
    private double totalPrice;
    private DatePicker selectDate;
    private ComboBox<Integer> selectPax;
    private Spinner<Integer> selectStartHour;
    private Spinner<Integer> selectEndHour;
    private Label priceLabel;
    private Label error_label;
    private ComboBox<String> selectPayMethod;
    private ComboBox<String> is_deleted;
    private manageSession root;
    public manageSession_stage(Session session,manageSession root) {
        this.root =root;
        this.session = session;
    }

    private Scene createManageSessionScene() {
        main_vbox = new VBox();
        main_vbox.setSpacing(10);
        main_vbox.setAlignment(Pos.CENTER);

        String labelstyle = "-fx-text-fill: darkblue;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;";

        HBox hbox0 = new HBox();
        hbox0.setAlignment(Pos.CENTER);
        hbox0.setSpacing(5);
        HBox hbox1 = new HBox();
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setSpacing(5);
        HBox hbox2 = new HBox();
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setSpacing(5);
        HBox hbox3 = new HBox();
        hbox3.setAlignment(Pos.CENTER);
        hbox3.setSpacing(5);
        HBox hbox4 = new HBox();
        hbox4.setAlignment(Pos.CENTER);
        hbox4.setSpacing(5);
        HBox hbox5 = new HBox();
        hbox5.setAlignment(Pos.CENTER);
        hbox5.setSpacing(5);
        HBox hbox6 = new HBox();
        hbox6.setAlignment(Pos.CENTER);
        hbox6.setSpacing(5);
        HBox hbox7 = new HBox();
        hbox7.setAlignment(Pos.CENTER);
        hbox7.setSpacing(5);
        HBox hbox8 = new HBox();
        hbox8.setAlignment(Pos.CENTER);
        hbox8.setSpacing(5);
        HBox hbox9 = new HBox();
        hbox9.setAlignment(Pos.CENTER);
        hbox9.setSpacing(5);

        error_label = new Label("Update Booking Details");
        selectDate = new DatePicker();

        selectStartHour = new Spinner<>(8, 21, Integer.parseInt(session.getStartTime().substring(0, 2)));
        selectEndHour = new Spinner<>(9, 22, Integer.parseInt(session.getEndTime().substring(0, 2)));
        selectPax = new ComboBox<>();
        selectPayMethod = new ComboBox<>();
        is_deleted = new ComboBox<>();
        selectPayMethod.getItems().addAll("Credit Card", "Debit Card", "Bank Transfer", "E-Wallet QR Code", "Cash");
        is_deleted.getItems().addAll("0","1");

        totalPrice = session.getAmount();
        selectPax.setValue(session.getSessionPax());
        selectDate.setValue(session.getSessionDate().toLocalDate());
        selectPayMethod.setValue(session.getPaymentMethod());
        is_deleted.setValue( ""+session.getIs_deleted());

        Button cancel = new Button("Cancel Changes");
        Button confirm = new Button("Confirm Changes");
        cancel.setStyle("-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;");
        confirm.setStyle("-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;");

        selectPax.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        selectDate.setPrefWidth(200);
        selectPax.setPrefWidth(200);
        selectStartHour.setPrefWidth(200);
        selectEndHour.setPrefWidth(200);
        selectPayMethod.setPrefWidth(200);

        selectStartHour.setOnMouseClicked(mouseEvent -> adjustStartHour());
        selectEndHour.setOnMouseClicked(mouseEvent -> adjustEndHour());
        confirm.setOnAction(event -> clickedConfirm());
        cancel.setOnAction(event -> clickedCancel());

        error_label.setStyle("-fx-text-fill: black;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;");

        priceLabel = new Label("Total Amount: " + totalPrice);
        priceLabel.setStyle("-fx-font-weight: bold;" + labelstyle);

        Label label0 = new Label(" User's Email: ");
        Label emailLabel = new Label(session.getUser_email());
        Label label1 = new Label("Select Date: ");
        Label label2 = new Label("Select Start Hour: ");
        Label label3 = new Label("Select End Hour: ");
        Label label4 = new Label("Select Pax: ");
        Label label5 = new Label("Select Payment Method: ");
        Label label6 = new Label("Is Deleted: ");


        Label[] labels = {label0,emailLabel ,label1, label2, label3, label4,label5,label6};
        for (Label lbl : labels) {
            lbl.setStyle(labelstyle);
        }

        hbox0.getChildren().addAll(error_label);
        hbox1.getChildren().addAll(label0, emailLabel);
        hbox2.getChildren().addAll(label1, selectDate);
        hbox3.getChildren().addAll(label2, selectStartHour);
        hbox4.getChildren().addAll(label3, selectEndHour);
        hbox5.getChildren().addAll(label4, selectPax);
        hbox6.getChildren().addAll(priceLabel);
        hbox7.getChildren().addAll(label5, selectPayMethod);
        hbox8.getChildren().addAll(label6,is_deleted);
        hbox9.getChildren().addAll(cancel,confirm);

        selectPax.valueProperty().addListener((obs, oldVal, newVal) -> {
            resetErrorLabel();
            if (newVal != null && selectDate.getValue() != null) {
                calculatePrice();
            }
        });
        selectDate.valueProperty().addListener((obs, oldVal, newVal) -> {
            resetErrorLabel();
            if (newVal != null && selectPax.getValue() != null) {
                calculatePrice();
            }
        });
        selectStartHour.valueProperty().addListener((obs, oldVal, newVal) -> {
            resetErrorLabel();
            if (selectDate.getValue() != null && selectPax.getValue() != null) {
                calculatePrice();
            }
        });
        selectEndHour.valueProperty().addListener((obs, oldVal, newVal) -> {
            resetErrorLabel();
            if (selectDate.getValue() != null && selectPax.getValue() != null) {
                calculatePrice();
            }
        });

        main_vbox.getChildren().addAll(error_label, hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7,hbox8,hbox9);
        main_vbox.setAlignment(Pos.CENTER);
        main_vbox.setStyle("-fx-background-color:#FFFFFF ;");

        return new Scene(main_vbox, 600, 500);
    }
    private void calculatePrice() {
        totalPrice = (selectEndHour.getValue() - selectStartHour.getValue()) * 15 * selectPax.getValue();
        priceLabel.setText(String.format("Total Amount: %.2f", totalPrice));
    }
    private void resetErrorLabel() {
        error_label.setText("Enter Booking Details");
        error_label.setTextFill(Color.BLACK);
    }
    private void adjustStartHour(){
        if (selectStartHour.getValue() >= selectEndHour.getValue()) {
            selectEndHour.getValueFactory().setValue(selectStartHour.getValue() + 1);
        }
    }
    private void adjustEndHour() {
        if (selectStartHour.getValue() >= selectEndHour.getValue()) {
            selectStartHour.getValueFactory().setValue(selectEndHour.getValue() - 1);
        }
    }
    private void clickedCancel(){
        manageSessionStage.close();
    }
    private void clickedConfirm(){
        SQLConnection.updateSession_admin(session.getSessionId(),selectDate.getValue(),
                selectStartHour.getValue()*10000,selectEndHour.getValue()*10000,
                selectPax.getValue(),totalPrice,selectPayMethod.getValue(), Integer.parseInt(is_deleted.getValue()));
        root.refreshSessions();
        manageSessionStage.close();
    }
    public void createManageSessionStage(Stage ownerStage) {
        manageSessionStage = new Stage();
        manageSessionStage.initModality(Modality.WINDOW_MODAL);
        manageSessionStage.initOwner(ownerStage);
        manageSessionStage.setScene(createManageSessionScene());
        manageSessionStage.setWidth(600);
        manageSessionStage.setHeight(500);
        manageSessionStage.setMinWidth(600);
        manageSessionStage.setMinHeight(500);
        manageSessionStage.setTitle("Chark Swimming Center - Update Session Details");
        manageSessionStage.show();
    }
}
