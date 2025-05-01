package chark_swimming_center;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Iterator;

public class viewBooked extends Application {
    private String user_email;
    private VBox main_vbox;
    private Stage stage;
    private TableView<Session> tableView;
    private HBox buttonBox;
    private Button futureSessions;
    private Button pastSessions;
    private Button delSessions;

    public viewBooked(String user_email) {
        this.user_email = user_email;
    }

    public VBox createViewBooked() {
        main_vbox = new VBox();
        main_vbox.setSpacing(10);

        futureSessions = new Button("View Future Sessions");
        pastSessions = new Button("View Past Sessions");
        delSessions = new Button("Delete Past Sessions");

        String buttonStyle = "-fx-background-color: darkblue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;";

        futureSessions.setStyle(buttonStyle);
        pastSessions.setStyle(buttonStyle);
        delSessions.setStyle(buttonStyle);

        buttonBox = new HBox(10, pastSessions);
        buttonBox.setAlignment(Pos.CENTER);

        tableView = new TableView<>();
        tableView.setStyle("-fx-font-size: 14px; " +
                "-fx-text-fill: darkblue;");

        TableColumn<Session, Integer> idCol = new TableColumn<>("ID");
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

        showFutureSessions();

        futureSessions.setOnAction(e -> showFutureSessions());
        pastSessions.setOnAction(e -> showPastSessions());
        delSessions.setOnAction(e -> deletePastSessions());

        VBox.setVgrow(tableView, Priority.ALWAYS);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().forEach(col -> col.setReorderable(false));

        main_vbox.getChildren().addAll(tableView, buttonBox);
        return main_vbox;
    }

    private void showFutureSessions() {
        ObservableList<Session> sessionList = SQLConnection.getBookedSessions(user_email);
        LocalDate today = LocalDate.now();
        int now = LocalTime.now().getHour();

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
        buttonBox.getChildren().setAll(pastSessions);
    }

    private void showPastSessions() {
        ObservableList<Session> sessionList = SQLConnection.getBookedSessions(user_email);
        LocalDate today = LocalDate.now();
        int now = LocalTime.now().getHour();

        Iterator<Session> it = sessionList.iterator();
        while (it.hasNext()) {
            Session session = it.next();
            LocalDate sessionDate = session.getSessionDate().toLocalDate();
            LocalTime time = LocalTime.parse(session.getStartTime());
            int hour = time.getHour();

            if (sessionDate.isAfter(today) || (sessionDate.isEqual(today) && now < hour)) {
                it.remove();
            }
        }
        tableView.setItems(sessionList);
        buttonBox.getChildren().setAll(futureSessions, delSessions);
    }

    private void deletePastSessions() {
        tableView = (TableView<Session>) main_vbox.getChildren().get(0);
        Session selectedSession = tableView.getSelectionModel().getSelectedItem();

        if (selectedSession != null) {
            int session_id = selectedSession.getSessionId();
            SQLConnection.softDeleteSession(session_id);
            showPastSessions();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
