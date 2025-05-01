package Coursework_35155752;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UserMenu extends Application {
    private Stage menuStage;
    private BorderPane main_borderPane;
    private StackPane main_stackPane;
    private StackPane UI_stackPane;
    private VBox main_vbox;
    private Region background;
    private GridPane navbar;
    private String user_email;
    private int page = -1;

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = createScene(stage);
        stage.setScene(scene);
        stage.setTitle("Chark Swimming Center - Main Menu");
        stage.show();
    }

    public UserMenu(String user_email, Stage menuStage) {
        this.user_email = user_email;
        this.menuStage = menuStage;
    }

    private void createNavBar() {
        String ButtonStyle = "-fx-text-fill: black;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;";

        Button bookSwimmingSession = new Button("Book Session");
        Button viewBookedSessions = new Button("View Booked");
        Button rescheduleSessions = new Button("Reschedule");
        Button viewPricingAndTerms = new Button("View Pricing");

        bookSwimmingSession.setOnAction(event -> clickedBookSession());
        viewBookedSessions.setOnAction(event -> clickedViewBooked());
        rescheduleSessions.setOnAction(event -> clickedRescheduleSession());
        viewPricingAndTerms.setOnAction(event -> clickedViewPricingSession());

        Button[] buttons = {bookSwimmingSession, viewBookedSessions, rescheduleSessions, viewPricingAndTerms};
        for (Button btn : buttons) {
            btn.setStyle("-fx-background-color: #FFBF00;" + ButtonStyle);
            btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: #c8be00;" + ButtonStyle));
            btn.setOnMouseExited(event -> btn.setStyle("-fx-background-color: #FFBF00;" + ButtonStyle));
        }

        navbar.add(bookSwimmingSession, 0, 0);
        navbar.add(viewBookedSessions, 1, 0);
        navbar.add(rescheduleSessions, 2, 0);
        navbar.add(viewPricingAndTerms, 3, 0);
    }
    private void clickedBookSession() {
        if (page != 0) {
            bookSession bookSessionClass = new bookSession(user_email);
            UI_stackPane.getChildren().clear();

            background.setPrefHeight(300);
            background.minHeight(300);
            UI_stackPane.getChildren().add(background);

            UI_stackPane.getChildren().add(bookSessionClass.createBookSession());
            page = 0;
        }
    }

    private void clickedViewBooked() {
        if (page != 1) {
            viewBooked viewBookedClass = new viewBooked(user_email);
            UI_stackPane.getChildren().clear();

            UI_stackPane.getChildren().add(viewBookedClass.createViewBooked());
            page = 1;
        }
    }

    private void clickedRescheduleSession() {
        if (page != 2) {
            rescheduleSession rescheduleSessionClass = new rescheduleSession(user_email);
            UI_stackPane.getChildren().clear();

            UI_stackPane.getChildren().add(rescheduleSessionClass.createViewBooked());
            page = 2;
        }
    }

    private void clickedViewPricingSession() {
        if (page != 3) {
            session_pricing pricingTermsClass = new session_pricing();
            UI_stackPane.getChildren().clear();
            background.setPrefHeight(300);
            background.minHeight(300);
            UI_stackPane.getChildren().add(background);

            UI_stackPane.getChildren().add(pricingTermsClass.createPricingVbox());
            page = 3;
        }
    }

    public void clickedUser() {
        if (page != 10) {
            editProfile editProfileClass = new editProfile(SQLConnection.getUsername(user_email), user_email, this);
            UI_stackPane.getChildren().clear();

            background.setPrefHeight(300);
            background.minHeight(300);
            UI_stackPane.getChildren().add(background);

            UI_stackPane.getChildren().add(editProfileClass.createEditProfile());
            page = 10;
        }
    }

    public void clickedUser(String user_email) {
        editProfile editProfileClass = new editProfile(SQLConnection.getUsername(user_email), user_email, this);
        UI_stackPane.getChildren().clear();

        background.setPrefHeight(300);
        background.minHeight(300);
        UI_stackPane.getChildren().add(background);

        UI_stackPane.getChildren().add(editProfileClass.createEditProfile());
        page = 10;
    }

    private void createBackground() {
        Image poolImage = new Image(getClass().getResource("/chark/competition-swimming-pool-background.jpg").toExternalForm());

        BackgroundImage bgImage = new BackgroundImage(
                poolImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, false
                )
        );

        main_vbox.setBackground(Background.EMPTY);
        main_stackPane.setBackground(new Background(bgImage));
        main_stackPane.getChildren().clear();
        main_stackPane.getChildren().add(main_vbox);
        main_borderPane.setCenter(main_stackPane);
    }


    private void createUIBackground() {
        background = new Region();
        background.setStyle("-fx-background-color: white;");
        background.prefWidthProperty().bind(UI_stackPane.widthProperty());
    }

    private void setUpVBox() {
        main_vbox.getChildren().addAll(navbar, UI_stackPane);
        main_vbox.heightProperty().addListener((obs, oldVal, newVal) -> {
            double paddingval = (newVal.doubleValue() - 350) / 2;
            UI_stackPane.setPadding(new Insets(paddingval, 0, paddingval, 0));
        });
    }

    public void createHeader() {
        Header header = new Header();
        main_borderPane.setTop(header.createHeaderBar1(menuStage, SQLConnection.getUsername(user_email), this));
    }

    private Scene createScene(Stage stage) {
        Header header = new Header();
        main_borderPane = new BorderPane();
        main_stackPane = new StackPane();
        UI_stackPane = new StackPane();
        main_vbox = new VBox();
        navbar = new GridPane();

        createNavBar();
        createBackground();
        createUIBackground();
        setUpVBox();
        createHeader();

        UI_stackPane.setAlignment(Pos.CENTER);
        main_borderPane.setMinWidth(800);
        main_borderPane.setMinHeight(600);
        Scene scene = new Scene(main_borderPane, 800, 600);
        return scene;
    }

    public void createMenuPage() {
        menuStage.setTitle("Chark Swimming Center - Main Menu");
        menuStage.setWidth(800);
        menuStage.setHeight(600);
        menuStage.setMinWidth(800);
        menuStage.setMinHeight(600);
        menuStage.setScene(createScene(menuStage));
        menuStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
