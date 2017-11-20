package sample;

import javafx.scene.control.Alert;

public class ErrorBox {

    private static String title;
    private static String message;

    private static void display() {
        AlertBox.display(Alert.AlertType.ERROR, title, message);
    }


    public static class InvalidDateErrorBox {

        public static void display() {
            title = "Invalid date";
            message = "You entered a non-existed date on the calendar.";
            ErrorBox.display();
        }
    }


    public static class InvalidDateFormatErrorBox {
        public static void display() {
            title = "Invalid date format";
            message = "You entered a date that is not in the format mm/dd/yyyy.";
            ErrorBox.display();
        }
    }


    public static class InvalidParseErrorBox {
        public static void display() {
            title = "Invalid time variance";
            message = "Time variance must be an integer.";
            ErrorBox.display();
        }
    }

    public static class InvalidFieldsErrorBox {
        public static void display() {
            title = "Invalid fields required";
            message = "You did not fill out 2 out of 3 required fields.";
            ErrorBox.display();
        }
    }

}
