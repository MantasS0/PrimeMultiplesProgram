package com.mantassasnauskas.program;

import com.mantassasnauskas.program.entryModel.EntryData;
import com.mantassasnauskas.program.entryModel.FieldEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;


public class Controller {
    @FXML
    public static Label informationLabel;
    @FXML
    private TextField firstNumberTextField;
    @FXML
    private TextField lastNumberTextField;
    @FXML
    private TextField intervalNumberTextField;
    @FXML
    private CheckBox clearFieldsCheckBox;
    @FXML
    private Button startCalcButton;
    @FXML
    private Button exitButton;

//    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @FXML
    public void initialize() {
        startCalcButton.setDisable(true);
    }

    @FXML
    public void onButtonClicked(ActionEvent e) throws InterruptedException {
        if (e.getSource().equals(startCalcButton)) {
            System.out.println("Start");
            processFieldEntry();
                EntryData.getInstance().calculatePrimeIntegers(true);
        } else if (e.getSource().equals(exitButton)) {
                EntryData.getInstance().calculatePrimeIntegers(false);
            System.out.println("Exit");
            System.exit(0);
        }

        if (clearFieldsCheckBox.isSelected() && e.getSource().equals(startCalcButton)) {
            firstNumberTextField.clear();
            lastNumberTextField.clear();
            intervalNumberTextField.clear();
            initialize();
        }
    }

    @FXML
    public void handleKeyReleased() {
        String firstNumber = firstNumberTextField.getText();
        String lastNumber = lastNumberTextField.getText();
        String intervalNumber = intervalNumberTextField.getText();
        boolean disableStartButton = firstNumber.isEmpty() || firstNumber.trim().isEmpty() ||
                lastNumber.isEmpty() || lastNumber.trim().isEmpty() ||
                intervalNumber.isEmpty() || intervalNumber.trim().isEmpty();
        startCalcButton.setDisable(disableStartButton);
    }


    public FieldEntry processFieldEntry(){
        String firstNumber = dumbCheck(firstNumberTextField.getText().trim());
        String lastNumber = dumbCheck(lastNumberTextField.getText().trim());
        String intervalNumber = dumbCheck(intervalNumberTextField.getText().trim());
        LocalDateTime currentDate = LocalDateTime.now();
        if (firstNumber.equals("-1") || lastNumber.equals("-1") || intervalNumber.equals("-1")){
            System.out.println("-1 gautas processFieldEntry metode");
            FieldEntry newFieldEntry = new FieldEntry(100,200,26,currentDate);
            EntryData.getInstance().addFieldEntry(newFieldEntry);
            return newFieldEntry;

        }else {
            FieldEntry newFieldEntry = new FieldEntry(Integer.parseInt(firstNumber),Integer.parseInt(lastNumber),
                    Integer.parseInt(intervalNumber),currentDate);
            EntryData.getInstance().addFieldEntry(newFieldEntry);
            return newFieldEntry;
        }
    }

    private String dumbCheck (String string){
        if (string.matches("^[0-9]+$") && string.charAt(0) !='0' && Integer.parseInt(string) >= 2){
            return string;
        }
        return "-1";
    }

}
