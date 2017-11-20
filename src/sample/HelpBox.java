package sample;

import javafx.scene.control.Alert;

public class HelpBox {

    public static void display() {
        String message = "How to use this program:\n\n" +
                "    1. Enter 2 out of 3 input fields satisfying the indicated rules\n" +
                "    2. Choose whether or not to include the end date\n" +
                "    3. Click CALCULATE button to compute\n" +
                "    4. Click RESET button to clear out all inputs\n" +
                "    5. Click TODAY/CLEAR button to automatically fill/clear out inputs\n" +
                "    6. Switch the time variance label will automatically reset the input\n\n" +
                "NOTE: any violations will result in a warning dialog.";
        AlertBox.display(Alert.AlertType.INFORMATION, "Information", message);
    }
}
