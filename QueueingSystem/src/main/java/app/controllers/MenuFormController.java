package app.controllers;

import app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuFormController {
    private Main main;

    @FXML
    private Button autoModeButton;

    @FXML
    private Button stepModeButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button exitButton;

    @FXML
    public void onClickAutoMode(ActionEvent event) {
        main.loadAutoMode();
    }

    @FXML
    public void onClickStepMode(ActionEvent event) {
        main.loadStepMode();
    }

    @FXML
    public void onClickSettingsButton(ActionEvent event) {
        main.loadSettings();
    }

    @FXML
    public void onClickExitButton(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void setMain(Main main) {
        this.main = main;
    }


}
