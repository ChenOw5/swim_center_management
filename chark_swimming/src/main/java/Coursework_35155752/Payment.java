package Coursework_35155752;

import com.almasb.fxgl.cutscene.dialogue.SerializableChoiceNode;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Payment extends Application {
    private double amount;
    private String user_email;
    private Stage paymentStage;
    private Label errorLabel;
    private ComboBox<String> selectPayMethod;
    private bookSession session_user;
    private bookSession_admin session_admin;

    @Override
    public void start(Stage stage) throws Exception {

    }

    public Payment(double amount, String user_email, bookSession session) {
        this.amount = amount;
        this.user_email = user_email;
        this.session_user = session;
    }

    public Payment(double amount, String user_email, bookSession_admin session) {
        this.amount = amount;
        this.user_email = user_email;
        this.session_admin = session;
    }

    public void createPaymentStage(Stage ownerStage) {
        paymentStage = new Stage();
        paymentStage.initModality(Modality.WINDOW_MODAL);
        paymentStage.initOwner(ownerStage);
        paymentStage.setScene(createPaymentScene());
        paymentStage.setWidth(400);
        paymentStage.setHeight(300);
        paymentStage.setMinWidth(400);
        paymentStage.setMinHeight(300);
        paymentStage.setTitle("Chark Swimming Center - Confirm Payment");
        paymentStage.show();
    }

    private Scene createConfirmedScene() {
        VBox vBox = new VBox();
        Label text = new Label("Payment Successful");
        Label text1 = new Label("Enjoy your swim");
        Button exit = new Button("Return to Main Menu");

        String labelstyle = "-fx-text-fill: darkblue;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;";

        exit.setStyle("-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;");
        text.setStyle(labelstyle);
        text1.setStyle(labelstyle);

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        exit.setOnAction(event -> clickedCancel());

        vBox.getChildren().addAll(text, text1, exit);
        vBox.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(vBox, 400, 300);
        return scene;
    }

    private Scene createPaymentScene() {
        VBox vBox = new VBox();
        errorLabel = new Label("");
        selectPayMethod = new ComboBox<>();
        Label label0 = new Label("Select Payment Method: ");
        Label amountLabel = new Label(String.format("Total Amount: %.2f", amount));
        Button confirm = new Button("Confirm Payment");
        Button cancel = new Button("Cancel Payment");
        HBox hbox0 = new HBox();
        HBox hbox1 = new HBox();

        label0.setStyle("-fx-font-size: 15px;" +
                "-fx-text-alignment: center;" +
                "-fx-text-fill: darkblue;");

        errorLabel.setStyle("-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;");

        String buttonStyle = "-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;";

        amountLabel.setStyle("-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;" +
                "-fx-text-fill: darkblue;");

        confirm.setStyle(buttonStyle);
        cancel.setStyle(buttonStyle);

        hbox0.setAlignment(Pos.CENTER);
        hbox0.setSpacing(5);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        selectPayMethod = new ComboBox<>();
        selectPayMethod.getItems().addAll("Credit Card", "Debit Card", "Bank Transfer", "E-Wallet QR Code", "Cash");
        selectPayMethod.setPrefWidth(150);
        confirm.setOnAction(event -> clickedConfirm());
        cancel.setOnAction(event -> clickedCancel());

        hbox0.getChildren().addAll(label0, selectPayMethod);
        hbox1.getChildren().addAll(cancel, confirm);

        vBox.getChildren().addAll(errorLabel, hbox0, amountLabel, hbox1);
        vBox.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(vBox, 400, 300);
        return scene;
    }

    private void clickedConfirm() {
        if (!SQLConnection.isConnected()) {
            errorLabel.setText("Database Is Not Connected");
            errorLabel.setTextFill(Color.RED);
            return;
        }
        if (selectPayMethod.getValue() == null) {
            errorLabel.setText("Payment Method Not Selected");
            errorLabel.setTextFill(Color.RED);
            return;
        }
        int payment_check = SQLConnection.createPayment(amount, selectPayMethod.getValue());

        if (payment_check != 0) {
            if (session_admin != null) {
                session_admin.SQLbookSession(payment_check);
            } else {
                session_user.SQLbookSession(payment_check);
            }
            paymentStage.setScene(createConfirmedScene());
            paymentStage.centerOnScreen();
            paymentStage.sizeToScene();
        } else {
            errorLabel.setText("Error Occurred During Payment Process");
            errorLabel.setTextFill(Color.RED);
        }
    }

    private void clickedCancel() {
        paymentStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
