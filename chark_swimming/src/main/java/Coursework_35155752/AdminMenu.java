package Coursework_35155752;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminMenu extends Application {
    private Stage adminStage;
    private BorderPane main_borderpane;
    private StackPane main_stackpane;
    private VBox sidebar;
    private String user_email;
    private int page = -1;

    public AdminMenu(String user_email, Stage adminStage) {
        this.user_email = user_email;
        this.adminStage = adminStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = createScene(stage);
        stage.setScene(scene);
        stage.setTitle("Chark Swimming Center - Admin Menu");
        stage.show();
    }

    private void createSidebar() {
        sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setPrefWidth(200);

        Label adminLabel = new Label("Admin Menu");
        Label sessionLabel = new Label("Session Management");
        Label termLabel = new Label("Terms Management");
        Label reportLabel = new Label("Sales Report");
        Label userLabel = new Label("User Management");

        String labelstyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";
        Label[] labels = {sessionLabel, termLabel, reportLabel, userLabel};
        for (Label lbl : labels) {
            lbl.setStyle(labelstyle);
        }
        adminLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button addSession = createMenuButton("Add Session");
        Button viewSessions = createMenuButton("Manage Sessions");
        Button termsAndPricing = createMenuButton("Manage Terms");
        Button manageUsers = createMenuButton("Manage Users");
        Button salesReport = createMenuButton("Sales Report");


        addSession.setOnAction(e -> clickedAddSessionPage());
        viewSessions.setOnAction(e -> clickedViewSessionsPage());
        manageUsers.setOnAction(e -> clickedManageUsersPage());
        termsAndPricing.setOnAction(e -> clickedTermsPage());
        salesReport.setOnAction(e -> clickedSalesReportPage());

        sidebar.getChildren().addAll(adminLabel, new Separator(), sessionLabel,
                addSession, viewSessions, new Separator(), termLabel,
                termsAndPricing, new Separator(), userLabel, manageUsers,
                new Separator(), reportLabel, salesReport);
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle("-fx-background-color: #FFBF00;  -fx-font-size: 15px; -fx-font-weight: bold;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #c8be00; -fx-font-size: 15px; -fx-font-weight: bold;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #FFBF00; -fx-font-size: 15px; -fx-font-weight: bold;"));
        return button;
    }

    private void clickedAddSessionPage() {
        if (page != 0) {
            main_stackpane.getChildren().clear();

            bookSession_admin bookSessionAdmin = new bookSession_admin(user_email);
            main_stackpane.getChildren().add(bookSessionAdmin.createBookSession());

            page = 0;
        }
    }

    private void clickedViewSessionsPage() {
        if (page != 1) {
            main_stackpane.getChildren().clear();

            manageSession manageSession = new manageSession();
            main_stackpane.getChildren().add(manageSession.createSessionManager());

            page = 1;
        }
    }

    private void clickedTermsPage() {
        if (page != 2) {
            main_stackpane.getChildren().clear();
            session_pricing_admin termsManager = new session_pricing_admin();
            main_stackpane.getChildren().add(termsManager.createTermsManager());
            page = 2;
        }
    }

    private void clickedManageUsersPage() {
        if (page != 3) {
            main_stackpane.getChildren().clear();

            manageUsers manageUsers = new manageUsers();
            main_stackpane.getChildren().add(manageUsers.createUserManager());

            page = 3;
        }
    }

    private void clickedSalesReportPage() {
        if (page != 4) {
            main_stackpane.getChildren().clear();

            SalesReport salesReportPage = new SalesReport();
            main_stackpane.getChildren().add(salesReportPage.createSalesReport());

            page = 4;
        }
    }

    public void clickedUser() {
        if (page != 10) {
            editProfile editProfileClass = new editProfile(SQLConnection.getUsername(user_email), user_email, this);
            main_stackpane.getChildren().clear();

            main_stackpane.getChildren().add(editProfileClass.createEditProfile());
            page = 10;
        }
    }

    public void createHeader() {
        Header header = new Header();
        main_borderpane.setTop(header.createHeaderBar2(adminStage, SQLConnection.getUsername(user_email), this));
    }

    private Scene createScene(Stage stage) {
        main_borderpane = new BorderPane();
        main_stackpane = new StackPane();
        main_stackpane.setStyle("-fx-background-color: #FFFFFF;");
        main_stackpane.setPadding(new Insets(20));

        createSidebar();
        createHeader();
        main_borderpane.setLeft(sidebar);
        main_borderpane.setCenter(main_stackpane);
        Scene scene = new Scene(main_borderpane, 800, 600);
        return scene;
    }

    public void createMenuPage() {
        adminStage.setTitle("Chark Swimming Center - Admin Menu");
        adminStage.setWidth(1200);
        adminStage.setHeight(600);
        adminStage.setMinWidth(1200);
        adminStage.setMinHeight(600);
        adminStage.setScene(createScene(adminStage));
        adminStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}