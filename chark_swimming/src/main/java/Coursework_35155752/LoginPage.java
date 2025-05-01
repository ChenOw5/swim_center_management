package Coursework_35155752;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage extends Application {
    private BorderPane main_borderPane;
    private SplitPane main_splitPane;
    private BorderPane leftpane;
    private BorderPane rightpane;
    private String pattern = "(.+)(@)(.+)(.com)$";
    private Stage loginStage;

    private final String labelStyle = "-fx-text-fill: darkblue;" +
            "-fx-font-size: 15px;" +
            "-fx-text-alignment: center;";
    private final String buttonStyle = "-fx-background-color: darkblue;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px;" +
            "-fx-text-alignment: center;";

    @Override
    public void start(Stage loginStage) throws Exception {
        this.loginStage = loginStage;
        createMainStage();
    }

    public LoginPage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    public void createSignupPage() {
        VBox vBox1 = new VBox();
        vBox1.setSpacing(10);
        vBox1.setAlignment(Pos.CENTER);

        HBox hbox0 = createHbox();
        HBox hbox1 = createHbox();
        HBox hbox2 = createHbox();
        HBox hbox3 = createHbox();
        HBox hbox4 = createHbox();

        TextField signupusername = new TextField();
        TextField signupemail = new TextField();
        PasswordField signuppass = new PasswordField();
        PasswordField signupconfirmpass = new PasswordField();
        Button signup = new Button("Sign Up");

        signupusername.setPromptText("Username");
        signupemail.setPromptText("Email");
        signuppass.setPromptText("Password");
        signupconfirmpass.setPromptText("Confirm Password");

        Label textLabel = new Label("Not a User? Sign Up!");
        textLabel.setStyle(labelStyle);

        Label l1 = new Label("Enter Username:");
        Label l2 = new Label("Enter Email:    ");
        Label l3 = new Label("Enter Password: ");
        Label l4 = new Label("Confirm Password: ");

        for (Label lbl : new Label[]{l1, l2, l3, l4}) {
            lbl.setStyle(labelStyle);
        }

        signupusername.setPrefWidth(200);
        signupemail.setPrefWidth(200);
        signuppass.setPrefWidth(200);
        signupconfirmpass.setPrefWidth(200);
        signup.setStyle(buttonStyle);

        hbox0.getChildren().addAll(textLabel);
        hbox1.getChildren().addAll(l1, signupusername);
        hbox2.getChildren().addAll(l2, signupemail);
        hbox3.getChildren().addAll(l3, signuppass);
        hbox4.getChildren().addAll(l4, signupconfirmpass);

        Button switchToLogin = new Button("Already have an account? Login");
        switchToLogin.setStyle(buttonStyle);
        switchToLogin.setOnAction(e -> ClickedLoginPage(textLabel));

        signup.setOnAction(event -> SignUp(signupusername, signupemail, signuppass, signupconfirmpass, textLabel));
        leftpane.setStyle("-fx-background-color: #FFFFFF;");

        vBox1.getChildren().addAll(hbox0, hbox1, hbox2, hbox3, hbox4, signup,switchToLogin);
        vBox1.setMinWidth(300);
        leftpane.setCenter(vBox1);
        vBox1.setVisible(true);
    }

    private void ClickedSignUpPage(Label label) {
        VBox leftVbox = (VBox) leftpane.getCenter();
        VBox rightVbox = (VBox) rightpane.getCenter();
        label.setText("Already a User? Log In!");
        label.setTextFill(Color.BLACK);

        leftpane.setStyle("-fx-background-color: #FFFFFF;");
        leftVbox.setVisible(true);

        rightVbox.setVisible(false);
        rightpane.setStyle("-fx-background-color: darkblue;");
    }

    private void SignUp(TextField username, TextField email, PasswordField pass, PasswordField confirmPass, Label statusLabel) {
        String user_name = username.getText().trim();
        String user_email = email.getText().trim();
        String password1 = pass.getText().trim();
        String password2 = confirmPass.getText().trim();
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(user_email);
        String errors = "";
        int errorcheck = 0;

        if (!SQLConnection.isConnected()) {
            errors += "Database Not Connected \n";
            errorcheck = 1;
        }
        if (user_name.isEmpty()) {
            errors += "Missing Username \n";
            errorcheck = 1;
        } else if (errorcheck == 0 && SQLConnection.UsernameExist(user_name)) {
            errors += "Username Already In Use\n";
            errorcheck = 1;
        }
        if (user_email.isEmpty()) {
            errors += "Missing Email Address\n";
            errorcheck = 1;
        } else if (!matcher.matches()) {
            errors += "Invalid Email Format\n";
            errorcheck = 1;
        } else if (errorcheck == 0 && SQLConnection.EmailExist(user_email)) {
            errors += "Email Already In Use\n";
            errorcheck = 1;
        }
        if (password1.isEmpty()) {
            errors += "Missing Password\n";
            errorcheck = 1;
        }
        if (errorcheck == 0 && !password1.equals(password2)) {
            errors += "Password Doesn't Match\n";
            errorcheck = 1;
        }
        if (errorcheck == 0) {
            SQLConnection.signup(user_email, user_name, password1);
            statusLabel.setText("Sign Up Successful!");
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            openMainMenu(user_email);
            return;
        }
        statusLabel.setText(errors);
        statusLabel.setTextFill(Color.RED);
        statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
    }

    public void createLoginPage() {
        VBox vBox2 = new VBox();
        vBox2.setSpacing(10);
        vBox2.setAlignment(Pos.CENTER);

        HBox hbox0 = createHbox();
        HBox hbox1 = createHbox();
        HBox hbox2 = createHbox();

        TextField loginemail = new TextField();
        PasswordField loginpass = new PasswordField();
        Button login = new Button("Login");

        loginemail.setPromptText("Email");
        loginpass.setPromptText("Password");

        loginemail.setPrefWidth(200);
        loginpass.setPrefWidth(200);
        login.setStyle(buttonStyle);

        Label textLabel = new Label("Already A User? Login!");
        textLabel.setStyle(labelStyle);

        Label l1 = new Label("Enter Email:    ");
        Label l2 = new Label("Enter Password: ");
        l1.setStyle(labelStyle);
        l2.setStyle(labelStyle);

        hbox0.getChildren().addAll(textLabel);
        hbox1.getChildren().addAll(l1, loginemail);
        hbox2.getChildren().addAll(l2, loginpass);

        Button switchToSignup = new Button("Don't have an account? Sign Up");
        switchToSignup.setStyle(buttonStyle);
        switchToSignup.setOnAction(e -> ClickedSignUpPage(textLabel));

        vBox2.getChildren().addAll(hbox0, hbox1, hbox2, login,switchToSignup);
        vBox2.setMinWidth(300);

        login.setOnAction(event -> Login(loginemail, loginpass, textLabel));
        rightpane.setStyle("-fx-background-color: darkblue;");
        rightpane.setCenter(vBox2);
        vBox2.setVisible(false);
    }

    private void ClickedLoginPage(Label label) {
        VBox leftVbox = (VBox) leftpane.getCenter();
        VBox rightVbox = (VBox) rightpane.getCenter();
        label.setText("Not a User? Sign Up!");
        label.setTextFill(Color.BLACK);

        rightpane.setStyle("-fx-background-color: #FFFFFF;");
        rightVbox.setVisible(true);

        leftVbox.setVisible(false);
        leftpane.setStyle("-fx-background-color: darkblue;");
    }

    private void Login(TextField email, PasswordField pass, Label statusLabel) {
        String user_email = email.getText().trim();
        String password = pass.getText().trim();
        String errors = "";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(user_email);
        int errorcheck = 0;

        if (!SQLConnection.isConnected()) {
            errors += "Database Not Connected \n";
            errorcheck = 1;
        }

        if (user_email.isEmpty()) {
            errors += "Missing Email\n";
            errorcheck = 1;
        } else if (!matcher.matches()) {
            errors += "Invalid Email Format\n";
            errorcheck = 1;
        } else if (errorcheck == 0 && !SQLConnection.EmailExist(user_email)) {
            errors += "Email Not Found\n";
            errorcheck = 1;
        }

        if (password.isEmpty()) {
            errors += "Missing Password\n";
            errorcheck = 1;
        } else if (errorcheck == 0 && !SQLConnection.matchPassword(user_email, password)) {
            errors += "Password is Incorrect\n";
            errorcheck = 1;
        }

        if (errorcheck == 0) {
            statusLabel.setText("Log In Successful!");
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            if (SQLConnection.getPrivilege(user_email).equals("user")) {
                openMainMenu(user_email);
            } else if (SQLConnection.getPrivilege(user_email).equals("admin")) {
                openAdminMenu(user_email);
            }
        } else {
            statusLabel.setText(errors);
            statusLabel.setTextFill(Color.RED);
            statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        }
    }

    public void createMainStage() {
        main_borderPane = new BorderPane();
        main_splitPane = new SplitPane();
        leftpane = new BorderPane();
        rightpane = new BorderPane();

        createSignupPage();
        createLoginPage();

        Header header = new Header();
        main_splitPane.getItems().addAll(leftpane, rightpane);
        main_borderPane.setCenter(main_splitPane);
        main_borderPane.setTop(header.createHeaderBar0());
        Scene scene = new Scene(main_borderPane, 800, 600);
        loginStage.setScene(scene);
        loginStage.setTitle("Chark Swimming Center - Login/SignUp");
        loginStage.show();
    }

    private void openMainMenu(String user_email) {
        loginStage.close();
        Stage MenuStage = new Stage();
        UserMenu mainMenu_class = new UserMenu(user_email, MenuStage);
        mainMenu_class.createMenuPage();
    }

    private void openAdminMenu(String user_email) {
        loginStage.close();
        Stage AdminStage = new Stage();
        AdminMenu adminMenu_class = new AdminMenu(user_email, AdminStage);
        adminMenu_class.createMenuPage();
    }

    private HBox createHbox() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(5);
        return hbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}