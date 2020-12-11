package app.dataRepresentation;

import javafx.beans.property.SimpleStringProperty;

public class TableRowDataForDeviceAnalytics {
    private SimpleStringProperty deviceIndex;
    private SimpleStringProperty timeOfWork;
    private SimpleStringProperty deviceUtilizationRate;


    public TableRowDataForDeviceAnalytics(String deviceIndex, String timeOfWork, String deviceUtilizationRate) {
        this.deviceIndex = new SimpleStringProperty(deviceIndex);
        this.timeOfWork = new SimpleStringProperty(timeOfWork);
        this.deviceUtilizationRate = new SimpleStringProperty(deviceUtilizationRate);
    }

    public void setTimeOfWork(String timeOfWork) {
        this.timeOfWork.set(timeOfWork);
    }

    public void setDeviceUtilizationRate(String deviceUtilizationRate) {
        this.deviceUtilizationRate.set(deviceUtilizationRate);
    }

    public void setDeviceIndex(String deviceIndex) {
        this.deviceIndex.set(deviceIndex);
    }

    public String getTimeOfWork() {
        return timeOfWork.get();
    }

    public String getDeviceUtilizationRate() {
        return deviceUtilizationRate.get();
    }

    public String getDeviceIndex() {
        return deviceIndex.get();
    }
}
