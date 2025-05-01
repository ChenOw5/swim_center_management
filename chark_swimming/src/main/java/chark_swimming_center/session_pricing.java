package chark_swimming_center;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.List;

public class session_pricing extends Application {
    private VBox main_vbox;

    public VBox createPricingVbox() {
        main_vbox = new VBox();
        main_vbox.setAlignment(Pos.CENTER);
        main_vbox.setSpacing(10);
        List<Term> termList = SQLConnection.getTerms();
        Iterator<Term> it = termList.iterator();

        String textStyle = "-fx-text-fill: darkblue;" +
                "-fx-font-size: 15px;" +
                "-fx-text-alignment: center;";

        Label label0 = new Label("Swimming Sessions – Pricing and Terms");
        label0.setStyle("-fx-text-fill: black;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;");

        main_vbox.getChildren().add(label0);

        while (it.hasNext()) {
            Term term = it.next();
            Label label = new Label(term.getTerms());
            label.setStyle(textStyle);
            main_vbox.getChildren().add(label);
        }

        return main_vbox;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
