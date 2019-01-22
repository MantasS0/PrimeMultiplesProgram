package com.mantassasnauskas.program;

import com.sun.javafx.binding.StringFormatter;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.StageStyle;


public class Controller {

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
    @FXML
    private Label informationLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressBarLabel;

    private Service<ObservableList<String>> service;


    @FXML
    public void initialize() {
        startCalcButton.setDisable(true);


        service = new CalculationService();
        progressBar.progressProperty().bind(service.progressProperty());
        progressBarLabel.textProperty().bind(StringFormatter.format("%.0f%%",progressBar.progressProperty().multiply(100)));
        informationLabel.textProperty().bind(service.messageProperty());
        progressBar.visibleProperty().bind(service.runningProperty());
        progressBarLabel.visibleProperty().bind(service.runningProperty());
        informationLabel.visibleProperty().bind(service.runningProperty());

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Alert alert = new Alert(Alert.AlertType.NONE);

                if (service.getValue().isEmpty()) {
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Failed!");
                    alert.setContentText("Įvesti skaičiai neatitinka reikalavimų.");
                    alert.getButtonTypes().add(ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        service.cancel();
                        service.reset();
                    }
                } else {
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Success!");
                    alert.setContentText("Skaidymas baigtas. Rezultatai faile rezultatai.txt");
                    alert.getButtonTypes().add(ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        service.cancel();
                        service.reset();
                    }
                }
            }
        });
    }

    @FXML
    public void onButtonClicked(ActionEvent e){
        if (e.getSource().equals(startCalcButton)) {
            System.out.println("Start");
            ((CalculationService) service).setFirstNumber(dumbCheck(firstNumberTextField.getText()));
            ((CalculationService) service).setLastNumber(dumbCheck(lastNumberTextField.getText()));
            ((CalculationService) service).setIntevalNumber(dumbCheck(intervalNumberTextField.getText()));


            if (service.getState() == Service.State.SUCCEEDED || service.getState() == Service.State.CANCELLED) {
                service.reset();
                service.start();
            } else if (service.getState() == Service.State.RUNNING || service.getState() == Service.State.FAILED) {
                service.cancel();
                service.reset();
                service.start();
            } else if (service.getState() == Service.State.READY) {
                service.start();
            }


        } else if (e.getSource().equals(exitButton)) {
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

    private Integer dumbCheck(String string) {
        if (string.matches("^[0-9]+$") && string.charAt(0) != '0' && Integer.parseInt(string) >= 1) {
            return Integer.parseInt(string);
        }
        return -1;
    }

}
