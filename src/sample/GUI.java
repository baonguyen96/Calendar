/***
 * Project:             Date Processing
 * Created by:          Bao Nguyen (C)
 * Last date modified:  06/18/2017
 *
 * This program utilize JavaFX framework as the GUI. It calculates dates, featuring:
 *      1. Given 2 dates, calculate the time elapsed (in days)
 *      2. Given 1 date and duration, calculate the other date
 *      3. ErrorBox checking on:
 *          3a. Date format
 *          3b. Date validation
 *
 * Open for any suggestions. You can feel free to use this program with proper citation. Enjoy.
 */

package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.text.ParseException;
import java.util.Date;


public class GUI extends Application {
    private Label date1Label;
    private TextField date1Input;
    private Button todayButton1;
    private Label date2Label;
    private TextField date2Input;
    private Button todayButton2;
    private ChoiceBox timeUnitChoiceBox;
    private Label timeVarianceLabel;
    private TextField timeVarianceInput;
    private CheckBox includeEndDateCheckBox;
    private Button calculateButton;
    private Button clearButton;
    private Button helpButton;
    private final int BUTTON_WIDTH = 100;
    private final String TODAY = "TODAY";
    private final String CLEAR = "CLEAR";

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Date Calculator 2.0");
        createElements();

        VBox vBox = new VBox();

        // date 1 row
        HBox date1Hbox = new HBox();
        date1Hbox.getChildren().addAll(todayButton1, date1Label, date1Input);
        date1Hbox.setSpacing(50);
        date1Hbox.setAlignment(Pos.CENTER);

        // date 2 row
        HBox date2Hbox = new HBox();
        date2Hbox.getChildren().addAll(todayButton2, date2Label, date2Input);
        date2Hbox.setSpacing(50);
        date2Hbox.setAlignment(Pos.CENTER);

        // time variance row
        HBox timeVarianceHbox = new HBox();
        timeVarianceHbox.getChildren().addAll(timeUnitChoiceBox, timeVarianceLabel, timeVarianceInput);
        timeVarianceHbox.setSpacing(40);
        timeVarianceHbox.setAlignment(Pos.CENTER);

        // buttons row
        HBox buttonsHbox = new HBox();
        buttonsHbox.getChildren().addAll(calculateButton, clearButton, helpButton);
        buttonsHbox.setSpacing(40);
        buttonsHbox.setAlignment(Pos.CENTER);

        // setup vbox
        vBox.getChildren().addAll(date1Hbox, date2Hbox, timeVarianceHbox,
                includeEndDateCheckBox, buttonsHbox);
        vBox.setSpacing(35);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);

        // set scene and display
        Scene scene = new Scene(vBox, 500, 350);
        primaryStage.getIcons().add(new Image("sample/clock_icon.png"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    /***
     * method: createElements
     * setup the individual elements on the scene and the eventListeners
     */
    private void createElements() {
        // date1 label
        date1Label = new Label("DATE 1");

        // date1 input
        date1Input = new TextField();
        date1Input.setPromptText("mm/dd/yyyy");
        date1Input.textProperty().addListener(((observable, oldValue, newValue) -> changeTodayButton1()));

        // date 1 today button
        todayButton1 = new Button("TODAY");
        todayButton1.setMinWidth(BUTTON_WIDTH);
        todayButton1.setOnAction(e -> setTodayDate1());

        // date2 label
        date2Label = new Label("DATE 2");

        // date2 input
        date2Input = new TextField();
        date2Input.setPromptText("mm/dd/yyyy");
        date2Input.textProperty().addListener(((observable, oldValue, newValue) -> changeTodayButton2()));

        // date 2 today button
        todayButton2 = new Button("TODAY");
        todayButton2.setMinWidth(BUTTON_WIDTH);
        todayButton2.setOnAction(e -> setTodayDate2());

        // choicebox
        timeUnitChoiceBox = new ChoiceBox<>();
        timeUnitChoiceBox.setMinWidth(BUTTON_WIDTH);
        timeUnitChoiceBox.getItems().addAll(DateProcessing.DAY, DateProcessing.MONTH, DateProcessing.YEAR);
        timeUnitChoiceBox.setValue(DateProcessing.DAY);
        timeUnitChoiceBox.setOnMouseClicked(e -> timeVarianceInput.clear());

        // time variance label
        timeVarianceLabel = new Label("VARIANCE");

        // time variance input
        timeVarianceInput = new TextField();
        timeVarianceInput.setPromptText("Integer");

        // checkbox
        includeEndDateCheckBox = new CheckBox("INCLUDE END DATE (Add 1 day to the day duration)");

        // calculate button
        calculateButton = new Button("CALCULATE");
        calculateButton.setMinWidth(BUTTON_WIDTH);
        calculateButton.setOnAction(e -> calculate());

        // reset button
        clearButton = new Button("RESET");
        clearButton.setMinWidth(BUTTON_WIDTH);
        clearButton.setOnAction(e -> reset());

        // help button
        helpButton = new Button("HELP");
        helpButton.setMinWidth(BUTTON_WIDTH);
        helpButton.setOnAction(e -> HelpBox.display());
    }

    private void changeTodayButton2() {
        if(!date2Input.getText().equalsIgnoreCase("")) {
            todayButton2.setText(CLEAR);
        }
        else {
            todayButton2.setText(TODAY);
        }
    }

    private void changeTodayButton1() {
        if(!date1Input.getText().equalsIgnoreCase("")) {
            todayButton1.setText(CLEAR);
        }
        else {
            todayButton1.setText(TODAY);
        }
    }

    private void setTodayDate1() {
        if(todayButton1.getText().equalsIgnoreCase(TODAY)) {
            date1Input.setText(DateProcessing.getDate());
            todayButton1.setText(CLEAR);
        }
        else {
            date1Input.clear();
            todayButton1.setText(TODAY);
        }
    }


    private void setTodayDate2() {
        if(todayButton2.getText().equalsIgnoreCase(TODAY)) {
            date2Input.setText(DateProcessing.getDate());
            todayButton2.setText(CLEAR);
        }
        else {
            date2Input.clear();
            todayButton2.setText(TODAY);
        }
    }


    /***
     * method: reset
     * reset out all inputs
     */
    private void reset() {
        date1Input.setText("");
        date2Input.setText("");
        timeVarianceInput.setText("");
        includeEndDateCheckBox.setSelected(false);
    }


    /***
     * method: calculate
     * process the inputs to get the desired result
     * given 2 fields, calculate the other
     * warning popups when violate rules
     */
    private void calculate() {
        String date1Text = "", date2Text = "", daysVarianceText = "";
        Date date1 = null, date2 = null;
        int daysVariance = 0, filledItem = 0;

        // bind
        if(!(date1Input.getText().equalsIgnoreCase(""))) {
            date1Text = date1Input.getText();
            filledItem++;
        }
        if(!(date2Input.getText().equalsIgnoreCase(""))) {
            date2Text = date2Input.getText();
            filledItem++;
        }
        if(!(timeVarianceInput.getText().equalsIgnoreCase(""))) {
            daysVarianceText = timeVarianceInput.getText();
            filledItem++;
        }

        // only valid if 2/3 items are filled
        if(filledItem != 2) {
            ErrorBox.InvalidFieldsErrorBox.display();
            return;
        }

        // given 2 dates, calculate the duration
        if(!date1Text.equalsIgnoreCase("") && !date2Text.equalsIgnoreCase("")) {
            try {
                if(DateProcessing.isValidDate(date1Text) && DateProcessing.isValidDate(date2Text)) {
                    date1 = DateProcessing.parseDate(date1Text);
                    date2 = DateProcessing.parseDate(date2Text);
                    daysVariance = DateProcessing.getDuration(date1, date2,
                            includeEndDateCheckBox.isSelected(), (String)timeUnitChoiceBox.getValue());
                    timeVarianceInput.setText(Integer.toString(daysVariance));
                }
                else {
                    ErrorBox.InvalidDateErrorBox.display();
                }
            }
            catch (ParseException e) {
                ErrorBox.InvalidDateFormatErrorBox.display();
            }

        }
        // given 1 date and duration, calculate the other date
        else if(!daysVarianceText.equalsIgnoreCase("")) {
            try {
                daysVariance = Integer.parseInt(daysVarianceText);
                try {
                    // given date2
                    if (date1Text.equalsIgnoreCase("") && DateProcessing.isValidDate(date2Text)) {
                        date2 = DateProcessing.parseDate(date2Text);
                        date1 = DateProcessing.modifyDate(date2, daysVariance,
                                includeEndDateCheckBox.isSelected(), (String)timeUnitChoiceBox.getValue());
                        date1Input.setText(DateProcessing.getDate(date1));
                    }
                    // given date1
                    else if (date2Text.equalsIgnoreCase("") && DateProcessing.isValidDate(date1Text)) {
                        date1 = DateProcessing.parseDate(date1Text);
                        date2 = DateProcessing.modifyDate(date1, daysVariance,
                                includeEndDateCheckBox.isSelected(), (String)timeUnitChoiceBox.getValue());
                        date2Input.setText(DateProcessing.getDate(date2));
                    }
                    else {
                        ErrorBox.InvalidDateErrorBox.display();
                    }
                }
                catch (ParseException e) {
                    ErrorBox.InvalidDateFormatErrorBox.display();
                }
            }
            catch (NumberFormatException e) {
                ErrorBox.InvalidParseErrorBox.display();
            }
        }
    }
}
