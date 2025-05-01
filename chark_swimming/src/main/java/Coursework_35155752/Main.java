package Coursework_35155752;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage mainstage) throws Exception {
        LoginPage loginPage = new LoginPage(mainstage);
        loginPage.createMainStage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
