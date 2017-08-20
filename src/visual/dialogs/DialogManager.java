package visual.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogManager {

    static ResourceBundle res = ResourceBundle.getBundle("bundles.ResBundle");

    public static void showCloseDialog(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(res.getString("key.confirm"));
        alert.setHeaderText(res.getString("key.on_close"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(("file:resources/icons/question.png")));
        ButtonType yesButton = new ButtonType(res.getString("key.yes"));
        ButtonType noButton = new ButtonType(res.getString("key.no"));
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == noButton)
            event.consume();
    }

    public static void showErrorDialog(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(res.getString("key.error"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(("file:resources/icons/error.png")));
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.showAndWait();
    }

    public static boolean showConfirmDialog(String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(res.getString("key.confirm"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:resources/icons/question.png"));
        ButtonType yesButton = new ButtonType(res.getString("key.yes"));
        ButtonType noButton = new ButtonType(res.getString("key.no"));
        alert.setHeaderText(text);
        alert.setContentText("");
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == noButton) {
            return false;
        }
        return true;
    }

    public static Integer showWarningDialog(String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(res.getString("key.warning"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:resources/icons/warning.png"));
        ButtonType yesButton = new ButtonType(res.getString("key.yes"));
        ButtonType noButton = new ButtonType(res.getString("key.no"));
        ButtonType dontAskButton = new ButtonType(res.getString("key.dont_ask"));
        alert.setHeaderText(text);
        alert.setContentText("");
        alert.getButtonTypes().setAll(yesButton, noButton, dontAskButton);
        Optional<ButtonType> result = alert.showAndWait();
        Integer falsee = 0, truee = 2, dontAsk = 1;
        if (result.get() == noButton)
            return falsee;
        if (result.get() == dontAskButton)
            return dontAsk;
        return truee;
    }

}
