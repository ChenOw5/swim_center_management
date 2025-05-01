package Coursework_35155752;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Header extends Application {
    private HBox createLeftBox() {
        Image logoImage = new Image(getClass().getResource("/chark/chark_logo_nobackground.png").toExternalForm());
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(100);
        logoImageView.setPreserveRatio(true);

        Image textImage = new Image(getClass().getResource("/chark/chark_logo_text_removebg.png").toExternalForm());
        ImageView textImageView = new ImageView(textImage);
        textImageView.setFitHeight(100);
        textImageView.setPreserveRatio(true);

        HBox leftBox = new HBox(10, logoImageView, textImageView);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(leftBox, Priority.ALWAYS);
        return leftBox;
    }

    public HBox createHeaderBar0() {
        HBox headerBar = new HBox();
        headerBar.setMinWidth(700);
        headerBar.setMinHeight(120);
        headerBar.setPadding(new Insets(10));
        headerBar.setStyle("-fx-background-color: deepskyblue;");
        headerBar.setAlignment(Pos.CENTER);
        headerBar.setSpacing(20);

        headerBar.getChildren().addAll(createLeftBox());

        return headerBar;
    }

    public HBox createHeaderBar1(Stage MenuStage, String user_name, UserMenu userMenu) {
        HBox headerBar = new HBox();
        headerBar.setMinWidth(700);
        headerBar.setMinHeight(100);
        headerBar.setPadding(new Insets(10));
        headerBar.setStyle("-fx-background-color: deepskyblue;");
        headerBar.setAlignment(Pos.CENTER);
        headerBar.setSpacing(20);

        Image logOutIcon = new Image(getClass().getResource("/chark/logout_icon_removebg.png").toExternalForm());
        ImageView logOutIconView = new ImageView(logOutIcon);
        logOutIconView.setFitHeight(40);
        logOutIconView.setPreserveRatio(true);

        Button logOut = new Button();
        logOut.setGraphic(logOutIconView);
        String logOut_style = "-fx-padding: 2;";
        logOut.setStyle("-fx-background-color: deepskyblue;" + logOut_style);

        logOut.setOnMouseEntered(e -> logOut.setStyle("-fx-background-color: #0092c7;" + logOut_style));
        logOut.setOnMouseExited(e -> logOut.setStyle("-fx-background-color: deepskyblue;" + logOut_style));

        Label usernameLabel = new Label(user_name);
        usernameLabel.setFont(Font.font("Fredoka", 30));
        usernameLabel.setTextFill(Color.WHITE);

        Button user_button = new Button(user_name);
        String user_button_style = "-fx-text-fill: white;" +
                "-fx-font-size: 30px;" +
                "-fx-text-alignment: center;" +
                "-fx-padding: 2;";
        user_button.setStyle("-fx-background-color: deepskyblue;" + user_button_style);

        user_button.setOnMouseEntered(e -> user_button.setStyle("-fx-background-color: #0092c7;" + user_button_style));
        user_button.setOnMouseExited(e -> user_button.setStyle("-fx-background-color: deepskyblue;" + user_button_style));

        VBox rightBox = new VBox(logOut, user_button);
        rightBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(rightBox, Priority.ALWAYS);
        rightBox.setPadding(new Insets(0, 20, 0, 0));

        logOut.setOnAction(event -> clickedLogOut(MenuStage));
        user_button.setOnAction(event -> clickedProfile(userMenu));

        headerBar.getChildren().addAll(createLeftBox(), rightBox);

        return headerBar;
    }

    public HBox createHeaderBar2(Stage MenuStage, String user_name, AdminMenu adminMenu) {
        HBox headerBar = new HBox();
        headerBar.setMinWidth(700);
        headerBar.setMinHeight(100);
        headerBar.setPadding(new Insets(10));
        headerBar.setStyle("-fx-background-color: deepskyblue;");
        headerBar.setAlignment(Pos.CENTER);
        headerBar.setSpacing(20);

        Image logOutIcon = new Image(getClass().getResource("/chark/logout_icon_removebg.png").toExternalForm());
        ImageView logOutIconView = new ImageView(logOutIcon);
        logOutIconView.setFitHeight(40);
        logOutIconView.setPreserveRatio(true);

        Button logOut = new Button();
        logOut.setGraphic(logOutIconView);
        String logOut_style = "-fx-padding: 2;";
        logOut.setStyle("-fx-background-color: deepskyblue;" + logOut_style);

        logOut.setOnMouseEntered(e -> logOut.setStyle("-fx-background-color: #0092c7;" + logOut_style));
        logOut.setOnMouseExited(e -> logOut.setStyle("-fx-background-color: deepskyblue;" + logOut_style));

        Label usernameLabel = new Label(user_name);
        usernameLabel.setFont(Font.font("Fredoka", 30));
        usernameLabel.setTextFill(Color.WHITE);

        Button user_button = new Button(user_name);
        String user_button_style = "-fx-text-fill: white;" +
                "-fx-font-size: 30px;" +
                "-fx-text-alignment: center;" +
                "-fx-padding: 2;";
        user_button.setStyle("-fx-background-color: deepskyblue;" + user_button_style);

        user_button.setOnMouseEntered(e -> user_button.setStyle("-fx-background-color: #0092c7;" + user_button_style));
        user_button.setOnMouseExited(e -> user_button.setStyle("-fx-background-color: deepskyblue;" + user_button_style));

        VBox rightBox = new VBox(logOut, user_button);
        rightBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(rightBox, Priority.ALWAYS);
        rightBox.setPadding(new Insets(0, 20, 0, 0));

        logOut.setOnAction(event -> clickedLogOut(MenuStage));
        user_button.setOnAction(event -> clickedProfile(adminMenu));

        headerBar.getChildren().addAll(createLeftBox(), rightBox);

        return headerBar;
    }

    @Override
    public void start(Stage stage) {

    }

    private void clickedLogOut(Stage menuStage) {
        menuStage.close();
        Stage login_stage = new Stage();
        LoginPage login_class = new LoginPage(login_stage);
        login_class.createMainStage();
    }

    private void clickedProfile(UserMenu userMenu) {
        userMenu.clickedUser();
    }

    private void clickedProfile(AdminMenu adminMenu) {
        adminMenu.clickedUser();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
