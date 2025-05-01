package chark_swimming_center;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Iterator;

public class rescheduleSession extends Application {
    private String user_email;
    private VBox main_vbox;
    private TableView<Session> tableView;

    public rescheduleSession(String user_email) {
        this.user_email = user_email;
    }

    public VBox createViewBooked() {
        main_vbox = new VBox();
        main_vbox.setAlignment(Pos.CENTER);
        main_vbox.setSpacing(10);

        Button selectSession = new Button("Reschedule Session");
        selectSession.setStyle("-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;");
        selectSession.setOnAction(event -> clickedSelectSession());

        tableView = new TableView<>();
        tableView.setStyle("-fx-font-size: 14px; " +
                "-fx-text-fill: darkblue;");

        TableColumn<Session, Date> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        idCol.setMinWidth(65);

        TableColumn<Session, Date> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        dateCol.setMinWidth(110);

        TableColumn<Session, String> startCol = new TableColumn<>("Start Time");
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startCol.setMinWidth(100);

        TableColumn<Session, String> endCol = new TableColumn<>("End Time");
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endCol.setMinWidth(100);

        TableColumn<Session, Integer> paxCol = new TableColumn<>("Pax");
        paxCol.setCellValueFactory(new PropertyValueFactory<>("sessionPax"));
        paxCol.setMinWidth(65);

        TableColumn<Session, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.setMinWidth(90);

        TableColumn<Session, String> methodCol = new TableColumn<>("Payment Method");
        methodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        methodCol.setMinWidth(150);

        tableView.getColumns().addAll(idCol, dateCol, startCol, endCol, paxCol, amountCol, methodCol);

        ObservableList<Session> sessionList = SQLConnection.getBookedSessions(user_email);

        LocalDate today = LocalDate.now();
        int now = LocalTime.now().getHour() + 6;

        Iterator<Session> it = sessionList.iterator();

        while (it.hasNext()) {
            Session session = it.next();

            LocalDate sessionDate = session.getSessionDate().toLocalDate();
            LocalTime time = LocalTime.parse(session.getStartTime());
            int hour = time.getHour();

            if (sessionDate.isBefore(today) || (sessionDate.isEqual(today) && now >= hour)) {
                it.remove();
            }
        }

        tableView.setItems(sessionList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().forEach(col -> col.setReorderable(false));

        main_vbox.getChildren().addAll(tableView, selectSession);
        return main_vbox;
    }

    private void clickedSelectSession() {
        tableView = (TableView<Session>) main_vbox.getChildren().get(0);
        Session selectedSession = tableView.getSelectionModel().getSelectedItem();

        if (selectedSession != null) {
            int session_id = selectedSession.getSessionId();
            LocalDate date = selectedSession.getSessionDate().toLocalDate();
            String startTime = selectedSession.getStartTime();
            String endTime = selectedSession.getEndTime();

            int startHour = Integer.parseInt(startTime.split(":")[0]);
            int endHour = Integer.parseInt(endTime.split(":")[0]);
            int duration = endHour - startHour;

            rescheduleSession_stage reschedule_stage = new rescheduleSession_stage(this, session_id, date, startHour, endHour, duration);
            Stage currentStage = (Stage) main_vbox.getScene().getWindow();
            reschedule_stage.createRescheduleStage(currentStage);
        }
    }

    public void refreshTable() {
        ObservableList<Session> sessionList = SQLConnection.getBookedSessions(user_email);

        LocalDate today = LocalDate.now();
        int now = LocalTime.now().getHour() + 6;

        sessionList.removeIf(session -> {
            LocalDate sessionDate = session.getSessionDate().toLocalDate();
            LocalTime time = LocalTime.parse(session.getStartTime());
            int hour = time.getHour();

            return sessionDate.isBefore(today) || (sessionDate.isEqual(today) && now >= hour);
        });
        tableView.setItems(sessionList);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
