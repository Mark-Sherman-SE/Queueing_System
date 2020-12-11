package app.controllers;

import app.Modulator;
import app.Main;
import app.dataRepresentation.TableRowDataForStepMode;
import app.analytics.RealTimeSnapshot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StepModeFormController {

    private Main main;
    private Modulator modulator;
    private List<RealTimeSnapshot> snapshotList;
    private ObservableList<TableRowDataForStepMode> sourcesData = FXCollections.observableArrayList();
    private ObservableList<TableRowDataForStepMode> bufferData = FXCollections.observableArrayList();
    private ObservableList<TableRowDataForStepMode> devicesData = FXCollections.observableArrayList();
    private int current_step = 0;
    private int number_of_steps = 0;
    private double current_time = 0.0;

    @FXML
    private TableView<TableRowDataForStepMode> sourcesActionsTable;

    @FXML
    private TableColumn<TableRowDataForStepMode, String> sourcesNumberColumn;

    @FXML
    private TableColumn<TableRowDataForStepMode, String> sourcesRequestNumberColumn;

    @FXML
    private TableColumn<TableRowDataForStepMode, String> sourcesPointerColumn;

    @FXML
    private TableView<TableRowDataForStepMode> bufferActionsTable;

    @FXML
    private TableColumn<TableRowDataForStepMode, String> bufferCellsNumberColumn;

    @FXML
    private  TableColumn<TableRowDataForStepMode, String> bufferRequestNumberColumn;

    @FXML
    private  TableColumn<TableRowDataForStepMode, String> bufferRequestPriorityColumn;

    @FXML
    private  TableColumn<TableRowDataForStepMode, String> bufferPointerColumn;

    @FXML
    private TableView<TableRowDataForStepMode> devicesActionsTable;

    @FXML
    private  TableColumn<TableRowDataForStepMode, String> devicesNumberColumn;

    @FXML
    private  TableColumn<TableRowDataForStepMode, String> deviceRequestNumberColumn;

    @FXML
    private  TableColumn<TableRowDataForStepMode, String> deviceRequestPriorityColumn;

    @FXML
    private  TableColumn<TableRowDataForStepMode, String> devicesPointerColumn;

    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label stepLabel;

    @FXML
    private Label numberOfStepsLabel;

    @FXML
    private TextArea globalActionTextArea;

    @FXML
    private TextField stepSnapshotTextField;

    @FXML
    private Button changeStepButton;

    @FXML
    private Button stepForwardButton;

    @FXML
    private Button stepBackwardButton;

    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        modulator = Modulator.getInstance();

        changeStepButton.setDisable(true);
        stepForwardButton.setDisable(true);
        stepBackwardButton.setDisable(true);

        sourcesNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Index"));
        sourcesRequestNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Request_number"));
        sourcesPointerColumn.setCellValueFactory(new PropertyValueFactory<>("Pointer"));

        bufferCellsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Index"));
        bufferRequestNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Request_number"));
        bufferRequestPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("Request_priority"));
        bufferPointerColumn.setCellValueFactory(new PropertyValueFactory<>("Pointer"));

        devicesNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Index"));
        deviceRequestNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Request_number"));
        deviceRequestPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("Request_priority"));
        devicesPointerColumn.setCellValueFactory(new PropertyValueFactory<>("Pointer"));
    }

    @FXML
    public void onClickStepForward() {
        if (current_step == number_of_steps - 1) {
            return;
        }
        ++current_step;
        stepLabel.setText(String.valueOf(current_step));
        changeSnapshot();
    }

    @FXML
    public void onClickStepBackward() {
        if (current_step == 0) {
            return;
        }
        --current_step;
        stepLabel.setText(String.valueOf(current_step));
        changeSnapshot();
    }

    @FXML
    public void onClickChangeStep() {
        try {
            int step = Integer.parseInt(stepSnapshotTextField.getText());
            if (step < 0 || step >= number_of_steps) {
                throw  new NumberFormatException();
            }
            current_step = step;
            stepLabel.setText(String.valueOf(current_step));
            changeSnapshot();
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение");
            alert.setHeaderText(null);
            alert.setContentText("Шаг должен находиться в пределах от 0 до " + number_of_steps);
        }
    }


    @FXML
    public void onClickRun(ActionEvent event) {
        changeStepButton.setDisable(true);
        stepForwardButton.setDisable(true);
        stepBackwardButton.setDisable(true);
        modulator.modulateWork();
        snapshotList = modulator.getCollectedData().getSnapshotList();
        resetFields();
        changeSnapshot();
        changeStepButton.setDisable(false);
        stepForwardButton.setDisable(false);
        stepBackwardButton.setDisable(false);
    }

    @FXML
    public void onClickExitButton(ActionEvent actionEvent) {
        main.loadMenu();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private void changeSnapshot() {
        RealTimeSnapshot current_snapshot = snapshotList.get(current_step);

        for (int i = 0; i < sourcesData.size(); i++) {
            sourcesData.set(i, new TableRowDataForStepMode(String.valueOf(i),
                    current_snapshot.getSources().get(i).get(0), current_snapshot.getSources().get(i).get(1)));
        }

        for (int i = 0; i < bufferData.size(); i++) {
            bufferData.set(i, new TableRowDataForStepMode(String.valueOf(i), current_snapshot.getBuffer().get(i).get(0),
                    current_snapshot.getBuffer().get(i).get(1), current_snapshot.getBuffer().get(i).get(2)));
        }

        for (int i = 0; i < devicesData.size(); i++) {
            devicesData.set(i, new TableRowDataForStepMode(String.valueOf(i), current_snapshot.getDevices().get(i).get(0),
                    current_snapshot.getDevices().get(i).get(1), current_snapshot.getDevices().get(i).get(2)));
        }

        current_time = current_snapshot.getCurrent_time();
        currentTimeLabel.setText(String.format("%.6f", current_time));
        globalActionTextArea.setText(current_snapshot.getGlobal_action());
    }

    private void resetFields() {
        current_step = 0;
        number_of_steps = snapshotList.size();
        current_time = 0.0;
        numberOfStepsLabel.setText(String.valueOf(number_of_steps));

        sourcesData.clear();
        bufferData.clear();
        devicesData.clear();

        for (int i = 0; i < modulator.getSources_amount(); i++) {
            sourcesData.add(new TableRowDataForStepMode(String.valueOf(i), "Free", ""));
        }
        for (int i = 0; i < modulator.getBuffer_size(); i++) {
            bufferData.add(new TableRowDataForStepMode(String.valueOf(i), "Free", "None", ""));
        }
        for (int i = 0; i < modulator.getDevices_amount(); i++) {
            devicesData.add(new TableRowDataForStepMode(String.valueOf(i), "Free", "None", ""));
        }
        sourcesActionsTable.setItems(sourcesData);
        bufferActionsTable.setItems(bufferData);
        devicesActionsTable.setItems(devicesData);
    }

}
