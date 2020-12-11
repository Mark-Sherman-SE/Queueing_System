package app.controllers;

import app.Modulator;
import app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class SettingsFormController {
    private Main main;
    private Modulator modulator;

    @FXML
    private VBox vBox;

    @FXML
    private TextField sourcesAmountField;

    @FXML
    private TextField bufferSizeField;

    @FXML
    private TextField devicesAmountField;

    @FXML
    private TextField requestsNumberField;

    @FXML
    private TextField tau1Field;

    @FXML
    private TextField tau2Field;

    @FXML
    private TextField lambdaField;

    @FXML
    private Button saveButton;

    @FXML
    public void initialize() {
        modulator = Modulator.getInstance();
        sourcesAmountField.setText(String.valueOf(modulator.getSources_amount()));
        bufferSizeField.setText(String.valueOf(modulator.getBuffer_size()));
        devicesAmountField.setText(String.valueOf(modulator.getDevices_amount()));
        requestsNumberField.setText(String.valueOf(modulator.getRequests_number()));
        tau1Field.setText(String.valueOf(modulator.getTau_1()));
        tau2Field.setText(String.valueOf(modulator.getTau_2()));
        lambdaField.setText(String.valueOf(modulator.getLambda()));
    }

    @FXML
    void onClickSaveButton(ActionEvent event) {
        try {
            if (sourcesAmountField.getText().equals("") || bufferSizeField.getText().equals("")
                    || devicesAmountField.getText().equals("") || requestsNumberField.getText().equals("")
                    || tau1Field.getText().equals("") || tau2Field.getText().equals("") || lambdaField.getText().equals("")
            ) {
                throw new NumberFormatException();
            }

            int sources_amount = Integer.parseInt(sourcesAmountField.getText());
            int buffer_size = Integer.parseInt(bufferSizeField.getText());
            int devices_amount = Integer.parseInt(devicesAmountField.getText());
            int requests_number = Integer.parseInt(requestsNumberField.getText());
            double tau_1 = Double.parseDouble(tau1Field.getText());
            double tau_2 = Double.parseDouble(tau2Field.getText());
            double lambda = Double.parseDouble(lambdaField.getText());

            if ((sources_amount < 1) || (buffer_size < 1) || (devices_amount < 1) || (requests_number < 1)
                    || (tau_1 < 0) || (tau_2 < 0) || (tau_1 > tau_2) || (lambda < 0)
            ) {
                throw new NumberFormatException();
            }

            modulator.setSources_amount(sources_amount);
            modulator.setBuffer_size(buffer_size);
            modulator.setDevices_amount(devices_amount);
            modulator.setRequests_number(requests_number);
            modulator.setTau_1(tau_1);
            modulator.setTau_2(tau_2);
            modulator.setLambda(lambda);

            main.loadMenu();

        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение");
            alert.setHeaderText(null);
            alert.setContentText("Вводимые данные не соответствуют типу полей. Заполните все поля численными данными.\n"
                    + "Все значения должны быть больше 0, причём Tau1 < Tau2.");
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

}
