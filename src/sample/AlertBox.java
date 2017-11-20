/***
 * Project:             Date Processing
 * Created by:          Bao Nguyen (C)
 * Last date modified:  06/18/2017
 *
 * Class: AlertBox
 *
 * Popup error/warning messages.
 * Has to be closed before continuing the previous window.
 */
package sample;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class AlertBox {

    public static void display(Alert.AlertType alertType, String title, String message) {
        Alert dialog = new Alert(alertType);
        dialog.setHeaderText(title);
        dialog.setContentText(message);
        if(alertType == Alert.AlertType.ERROR) {
            dialog.getDialogPane().setPrefSize(300, 200);
        }
        else {
            dialog.getDialogPane().setPrefSize(400, 320);
        }
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("sample/clock_icon.png"));
        stage.setResizable(false);
        stage.show();
    }

}
