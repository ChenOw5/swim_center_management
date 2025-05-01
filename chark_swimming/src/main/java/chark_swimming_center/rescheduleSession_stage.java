package chark_swimming_center;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

public class rescheduleSession_stage extends Application {
    private Stage rescheduleStage;
    private LocalDate sessionDate;
    private rescheduleSession root_scene;
    private int session_ID;
    private int startTime;
    private int endTime;
    private int duration;
    private Label error_label;
    private DatePicker selectDate;
    private Spinner<Integer> selectStartHour;
    private Label selectEndHour;

    public rescheduleSession_stage(rescheduleSession root_scene, int sessionID, LocalDate sessionDate, int startTime, int endTime, int duration) {
        this.root_scene = root_scene;
        this.session_ID = sessionID;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public void createRescheduleStage(Stage ownerStage) {
        rescheduleStage = new Stage();
        rescheduleStage.initModality(Modality.WINDOW_MODAL);
        rescheduleStage.initOwner(ownerStage);
        rescheduleStage.setScene(createRescheduleScene());
        rescheduleStage.setWidth(800);
        rescheduleStage.setHeight(300);
        rescheduleStage.setMinWidth(800);
        rescheduleStage.setMinHeight(300);
        rescheduleStage.setTitle("Chark Swimming Center - Confirm Reschedule");
        rescheduleStage.show();
    }

    private Scene createRescheduleScene() {
        VBox main_vbox = new VBox();
        GridPane main_grid = new GridPane();
        HBox main_hbox1 = new HBox();
        HBox hbox0 = new HBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        error_label = new Label("Enter New Session Details");
        Button confirm = new Button("Confirm Changes");
        Button cancel = new Button("Cancel Changes");
        selectDate = new DatePicker();
        selectStartHour = new Spinner<Integer>(8, 22 - duration, 8);
        selectEndHour = new Label("");
        selectEndHour.setText(String.format("%02d:00:00", 8 + duration));

        error_label.setStyle("-fx-text-fill: black;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;");

        String buttonStyle = "-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;";

        cancel.setStyle(buttonStyle);
        confirm.setStyle(buttonStyle);

        Label label0 = new Label("Previous Date: ");
        Label label1 = new Label("Previous Start Time: ");
        Label label2 = new Label("Previous End Time: ");
        Label label3 = new Label("New Date: ");
        Label label4 = new Label("New Start Time: ");
        Label label5 = new Label("New End Time: ");
        Label label6 = new Label("Session Duration: ");
        Label label7 = new Label(" Hour(s)");
        Label durationLabel = new Label("" + duration);

        Label olddate = new Label("" + sessionDate.toString());
        Label oldstartime = new Label(String.format("%02d:00:00", startTime));
        Label oldendtime = new Label(String.format("%02d:00:00", endTime));

        String labelstyle = "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;";

        Label[] labels0 = {label0, label1, label2, label3, label4, label5, label6, label7};
        for (Label lbl : labels0) {
            lbl.setStyle(labelstyle + "-fx-text-fill: darkblue;");
        }

        Label[] labels1 = {olddate, oldendtime, oldstartime, selectEndHour, durationLabel};
        for (Label lbl : labels1) {
            lbl.setStyle(labelstyle + "-fx-text-fill: black;");
        }

        main_vbox.setAlignment(Pos.CENTER);
        main_vbox.setSpacing(10);

        main_grid.setVgap(15);
        main_grid.setHgap(30);
        main_grid.setAlignment(Pos.CENTER);

        main_hbox1.setAlignment(Pos.CENTER);
        main_hbox1.setSpacing(10);

        hbox0.setAlignment(Pos.CENTER);
        hbox0.setSpacing(5);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setSpacing(5);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setSpacing(5);
        hbox3.setAlignment(Pos.CENTER);
        hbox3.setSpacing(5);

        hbox0.getChildren().addAll(label3, selectDate);
        hbox1.getChildren().addAll(label4, selectStartHour);
        hbox2.getChildren().addAll(label5, selectEndHour);
        hbox3.getChildren().addAll(label6, durationLabel, label7);

        main_hbox1.getChildren().addAll(cancel, confirm);

        confirm.setOnAction(event -> clickedConfirm());
        cancel.setOnAction(event -> clickedCancel());


        selectDate.valueProperty().addListener((obs, oldVal, newVal) -> {
            resetErrorLabel();
        });
        selectStartHour.valueProperty().addListener((obs, oldVal, newVal) -> {
            resetErrorLabel();
            selectEndHour.setText(String.format("%02d:00:00", newVal + duration));
        });

        main_grid.add(new HBox(label0, olddate), 0, 0);
        main_grid.add(new HBox(label1, oldstartime), 0, 1);
        main_grid.add(new HBox(label2, oldendtime), 0, 2);
        main_grid.add(hbox0, 1, 0);
        main_grid.add(hbox1, 1, 1);
        main_grid.add(hbox2, 1, 2);

        main_vbox.getChildren().addAll(error_label, main_grid, hbox3, main_hbox1);

        main_vbox.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(main_vbox, 800, 300);
        return scene;
    }

    private void resetErrorLabel() {
        error_label.setText("Enter New Session Detail");
        error_label.setTextFill(Color.BLACK);
    }

    private Scene createConfirmedScene() {
        VBox vBox = new VBox();
        Label text = new Label("Reschedule Successful");
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

    private void clickedCancel() {
        rescheduleStage.close();
    }

    private void clickedConfirm() {
        LocalDate new_date = selectDate.getValue();
        int new_startHour = selectStartHour.getValue();
        if (new_date == null) {
            error_label.setText("Please Select A Date");
            error_label.setTextFill(Color.RED);
        } else if (sessionDate.isAfter(new_date)) {
            error_label.setText("Selected Date Cannot Be Before The Session");
            error_label.setTextFill(Color.RED);
        } else if (sessionDate.isEqual(new_date) && new_startHour < startTime) {
            error_label.setText("Selected Time Cannot Be Before The Session");
            error_label.setTextFill(Color.RED);
        } else {
            int update_check = SQLConnection.updateSession(session_ID, new_date, new_startHour * 10000, (new_startHour + duration) * 10000);

            if (update_check != 0) {
                root_scene.refreshTable();
                rescheduleStage.setScene(createConfirmedScene());
                rescheduleStage.centerOnScreen();
                rescheduleStage.sizeToScene();
            } else {
                error_label.setText("Error Occurred During Rescheduling Process");
                error_label.setTextFill(Color.RED);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
