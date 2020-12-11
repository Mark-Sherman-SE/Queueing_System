package app.controllers;

import app.Modulator;
import app.Main;
import app.analytics.DeviceAnalytics;
import app.analytics.SourceAnalytics;
import app.dataRepresentation.TableRowDataForDeviceAnalytics;
import app.dataRepresentation.TableRowDataForSourceAnalytics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AutoModeFormController {

    private Main main;
    private Modulator modulator;
    private ObservableList<TableRowDataForSourceAnalytics> sourcesData = FXCollections.observableArrayList();
    private ObservableList<TableRowDataForDeviceAnalytics> devicesData = FXCollections.observableArrayList();

    @FXML
    private TableView sourceTable;

    @FXML
    private TableColumn<TableRowDataForSourceAnalytics, String> sourceNumber;

    @FXML
    private TableColumn<TableRowDataForSourceAnalytics, String> sourceRequestsNumber;

    @FXML
    private TableColumn<TableRowDataForSourceAnalytics, String> sourceDoneRequestsNumber;

    @FXML
    private TableColumn<TableRowDataForSourceAnalytics, String> sourceRejectedRequestsNumber;

    @FXML
    private TableColumn<TableRowDataForSourceAnalytics, String> sourceFailureProbability;

    @FXML
    private TableColumn<TableRowDataForSourceAnalytics, String> sourceAverageTimeInSystem;

    @FXML
    private TableColumn<TableRowDataForSourceAnalytics, String> sourceAverageTimeOfWaiting;

    @FXML
    private TableColumn<TableRowDataForSourceAnalytics, String> sourceAverageProcessTime;

    @FXML
    private TableColumn<TableRowDataForSourceAnalytics, String> sourceDispersionWaitingTime;

    @FXML
    private TableColumn<TableRowDataForSourceAnalytics, String> sourceDispersionProcessTime;

    @FXML
    private TableView deviceTable;

    @FXML
    private TableColumn<TableRowDataForDeviceAnalytics, String> deviceNumber;

    @FXML
    private TableColumn<TableRowDataForDeviceAnalytics, String> deviceTimeOfWork;

    @FXML
    private TableColumn<TableRowDataForDeviceAnalytics, String> deviceUtilizationRate;

    @FXML
    private Button runButton;

    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        modulator = Modulator.getInstance();
        sourceNumber.setCellValueFactory(new PropertyValueFactory<>("sourceIndex"));
        sourceRequestsNumber.setCellValueFactory(new PropertyValueFactory<>("numberOfRequests"));
        sourceDoneRequestsNumber.setCellValueFactory(new PropertyValueFactory<>("numberOfDoneRequests"));
        sourceRejectedRequestsNumber.setCellValueFactory(new PropertyValueFactory<>("numberOfRejectedRequests"));
        sourceFailureProbability.setCellValueFactory(new PropertyValueFactory<>("probabilityOfFailure"));
        sourceAverageTimeInSystem.setCellValueFactory(new PropertyValueFactory<>("averageTimeSpentInSystem"));
        sourceAverageTimeOfWaiting.setCellValueFactory(new PropertyValueFactory<>("averageWaitingTime"));
        sourceAverageProcessTime.setCellValueFactory(new PropertyValueFactory<>("averageProcessTime"));
        sourceDispersionWaitingTime.setCellValueFactory(new PropertyValueFactory<>("dispersionWaitingTime"));
        sourceDispersionProcessTime.setCellValueFactory(new PropertyValueFactory<>("dispersionProcessTime"));

        deviceNumber.setCellValueFactory(new PropertyValueFactory<>("deviceIndex"));
        deviceTimeOfWork.setCellValueFactory(new PropertyValueFactory<>("timeOfWork"));
        deviceUtilizationRate.setCellValueFactory(new PropertyValueFactory<>("deviceUtilizationRate"));
    }

    @FXML
    public void onClickRun(ActionEvent actionEvent) {
        modulator.modulateWork();
        sourcesData.clear();
        devicesData.clear();
        List<SourceAnalytics> sourceAnalyticsList = modulator.getCollectedData().getAllSourcesAnalytics();
        List<DeviceAnalytics> deviceAnalyticsList = modulator.getCollectedData().getAllDevicesAnalytics();
        for (SourceAnalytics source: sourceAnalyticsList) {
            sourcesData.add(new TableRowDataForSourceAnalytics(source.getSource_index().toString(),
                    source.getNumberOfRequests().toString(), source.getNumberOfDoneRequests().toString(),
                    source.getNumberOfRejectedRequests().toString(),
                    String.format("%.6f", source.getProbabilityOfFailure()),
                    String.format("%.6f", source.getAverageTimeSpentInSystem()),
                    String.format("%.6f", source.getAverageWaitingTime()),
                    String.format("%.6f", source.getAverageProcessTime()),
                    String.format("%.6f", source.getDispersionWaitingTime()),
                    String.format("%.6f", source.getDispersionProcessTime())));
        }
        for (DeviceAnalytics device: deviceAnalyticsList) {
            devicesData.add(new TableRowDataForDeviceAnalytics(String.valueOf(device.getDeviceIndex()),
                    String.format("%.6f", device.getTimeOfWork()),
                    String.format("%.6f", device.getDeviceUtilizationRate())));
        }
        sourceTable.setItems(sourcesData);
        deviceTable.setItems(devicesData);
    }

    @FXML
    public void onClickExit(ActionEvent actionEvent) {
        main.loadMenu();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
