package chark_swimming_center;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class bookSession extends Application {
    private String user_email;
    private VBox main_vbox;
    private double totalPrice;
    private DatePicker selectDate;
    private ComboBox<Integer> selectPax;
    private Spinner<Integer> selectStartHour;
    private Spinner<Integer> selectEndHour;
    private Label priceLabel;
    private Label error_label;
    private CheckBox tnc_check;

    public bookSession(String user_email) {
        this.user_email = user_email;
    }

    @Override
    public void start(Stage stage) throws Exception {
        main_vbox = new VBox();
        Scene scene = new Scene(createBookSession(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public VBox createBookSession() {
        main_vbox = new VBox();
        totalPrice = 0.00;
        main_vbox.setSpacing(10);

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

        selectDate = new DatePicker();
        selectPax = new ComboBox<>();
        selectStartHour = new Spinner<>(8, 21, 8);
        selectEndHour = new Spinner<>(9, 22, 9);
        error_label = new Label("Enter Booking Details");
        Button confirmPayment = new Button("Proceed to Payment");
        confirmPayment.setStyle("-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;");

        tnc_check = new CheckBox();

        selectPax.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        selectDate.setPrefWidth(200);
        selectPax.setPrefWidth(200);
        selectStartHour.setPrefWidth(200);
        selectEndHour.setPrefWidth(200);

        selectStartHour.setOnMouseClicked(mouseEvent -> adjustStartHour());
        selectEndHour.setOnMouseClicked(mouseEvent -> adjustEndHour());
        confirmPayment.setOnAction(event -> clickedConfirm());

        error_label.setStyle("-fx-text-fill: black;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;");

        priceLabel = new Label("Total Amount: " + totalPrice);
        priceLabel.setStyle("-fx-font-weight: bold;" + labelstyle);

        Label linkLabel = new Label("Terms and Conditions");
        linkLabel.setStyle("-fx-text-fill: blue; " +
                "-fx-underline: true; " +
                "-fx-font-size: 14px;");
        linkLabel.setOnMouseClicked(event -> {
            Stage termsStage = new Stage();
            termsStage.setTitle("Chark Swimming Center - Terms and Conditions");

            session_pricing pricing = new session_pricing();
            VBox pricingVbox = pricing.createPricingVbox();
            pricingVbox.setStyle("-fx-background-color: #FFFFFF;");

            Scene scene = new Scene(pricingVbox, 600, 400);
            termsStage.setScene(scene);
            termsStage.show();
        });

        Label label0 = new Label("Select Date: ");
        Label label1 = new Label("Select Start Hour: ");
        Label label2 = new Label("Select End Hour: ");
        Label label3 = new Label("Select Pax: ");
        Label label4 = new Label("I agree to the");

        Label[] labels = {label0, label1, label2, label3, label4};
        for (Label lbl : labels) {
            lbl.setStyle(labelstyle);
        }

        hbox0.getChildren().addAll(error_label);
        hbox1.getChildren().addAll(label0, selectDate);
        hbox2.getChildren().addAll(label1, selectStartHour);
        hbox3.getChildren().addAll(label2, selectEndHour);
        hbox4.getChildren().addAll(label3, selectPax);
        hbox5.getChildren().addAll(priceLabel);
        hbox6.getChildren().addAll(tnc_check, label4, linkLabel);

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

        main_vbox.getChildren().addAll(error_label, hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, confirmPayment);
        main_vbox.setMinWidth(800);
        main_vbox.setAlignment(Pos.CENTER);

        return main_vbox;
    }

    private void resetErrorLabel() {
        error_label.setText("Enter Booking Details");
        error_label.setTextFill(Color.BLACK);
    }

    private void adjustStartHour() {
        if (selectStartHour.getValue() >= selectEndHour.getValue()) {
            selectEndHour.getValueFactory().setValue(selectStartHour.getValue() + 1);
        }
    }

    private void adjustEndHour() {
        if (selectStartHour.getValue() >= selectEndHour.getValue()) {
            selectStartHour.getValueFactory().setValue(selectEndHour.getValue() - 1);
        }
    }

    private void calculatePrice() {
        totalPrice = (selectEndHour.getValue() - selectStartHour.getValue()) * 15 * selectPax.getValue();
        priceLabel.setText(String.format("Total Amount: %.2f", totalPrice));
    }

    private void clickedConfirm() {
        LocalDate currentDate = LocalDate.now();
        int localHour = LocalTime.now().getHour();
        int startHourValue = selectStartHour.getValue();
        int endHourValue = selectEndHour.getValue();
        if (selectDate.getValue() == null) {
            error_label.setText("Please Select A Date");
            error_label.setTextFill(Color.RED);
        } else if (currentDate.isAfter(selectDate.getValue())) {
            error_label.setText("Selected Date Cannot Be In The Past");
            error_label.setTextFill(Color.RED);
        } else if (currentDate.isEqual(selectDate.getValue()) && startHourValue <= localHour) {
            error_label.setText("Selected Hour Must Be A Future Hour");
            error_label.setTextFill(Color.RED);
        } else if (selectPax.getValue() == null) {
            error_label.setText("Please Select Pax Amount");
            error_label.setTextFill(Color.RED);
        } else if (!tnc_check.isSelected()) {
            error_label.setText("Please Agree to The Terms and Conditions");
            error_label.setTextFill(Color.RED);
        } else {
            Payment paymentClass = new Payment(totalPrice, user_email, this);
            Stage currentStage = (Stage) main_vbox.getScene().getWindow();
            paymentClass.createPaymentStage(currentStage);
        }
    }

    public void SQLbookSession(int payment_check) {
        SQLConnection.BookSession(user_email, selectDate.getValue(), selectStartHour.getValue() * 10000, selectEndHour.getValue() * 10000, selectPax.getValue(), payment_check);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
