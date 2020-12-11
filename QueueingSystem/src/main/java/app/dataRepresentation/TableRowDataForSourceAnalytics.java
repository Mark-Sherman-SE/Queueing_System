package app.dataRepresentation;

import javafx.beans.property.SimpleStringProperty;

public class TableRowDataForSourceAnalytics {

    private SimpleStringProperty sourceIndex;

    private SimpleStringProperty numberOfRequests;
    private SimpleStringProperty numberOfDoneRequests;
    private SimpleStringProperty numberOfRejectedRequests;

    private SimpleStringProperty probabilityOfFailure;
    private SimpleStringProperty averageTimeSpentInSystem;
    private SimpleStringProperty averageWaitingTime;
    private SimpleStringProperty averageProcessTime;

    private SimpleStringProperty dispersionWaitingTime;
    private SimpleStringProperty dispersionProcessTime;

    public TableRowDataForSourceAnalytics(String sourceIndex, String numberOfRequests, String numberOfDoneRequests,
                                          String numberOfRejectedRequests, String probabilityOfFailure,
                                          String averageTimeSpentInSystem, String averageWaitingTime,
                                          String averageProcessTime, String dispersionWaitingTime,
                                          String dispersionProcessTime) {
        this.numberOfRequests = new SimpleStringProperty(numberOfRequests);
        this.numberOfDoneRequests = new SimpleStringProperty(numberOfDoneRequests);
        this.numberOfRejectedRequests = new SimpleStringProperty(numberOfRejectedRequests);
        this.probabilityOfFailure = new SimpleStringProperty(probabilityOfFailure);
        this.averageTimeSpentInSystem = new SimpleStringProperty(averageTimeSpentInSystem);
        this.averageWaitingTime = new SimpleStringProperty(averageWaitingTime);
        this.averageProcessTime = new SimpleStringProperty(averageProcessTime);
        this.dispersionWaitingTime = new SimpleStringProperty(dispersionWaitingTime);
        this.dispersionProcessTime = new SimpleStringProperty(dispersionProcessTime);
        this.sourceIndex = new SimpleStringProperty(sourceIndex);
    }

    public void setNumberOfRequests(String numberOfRequests) {
        this.numberOfRequests.set(numberOfRequests);
    }

    public void setNumberOfDoneRequests(String numberOfDoneRequests) {
        this.numberOfDoneRequests.set(numberOfDoneRequests);
    }

    public void setNumberOfRejectedRequests(String numberOfRejectedRequests) {
        this.numberOfRejectedRequests.set(numberOfRejectedRequests);
    }

    public void setProbabilityOfFailure(String probabilityOfFailure) {
        this.probabilityOfFailure.set(probabilityOfFailure);
    }

    public void setAverageTimeSpentInSystem(String averageTimeSpentInSystem) {
        this.averageTimeSpentInSystem.set(averageTimeSpentInSystem);
    }

    public void setAverageWaitingTime(String averageWaitingTime) {
        this.averageWaitingTime.set(averageWaitingTime);
    }

    public void setAverageProcessTime(String averageProcessTime) {
        this.averageProcessTime.set(averageProcessTime);
    }

    public void setDispersionWaitingTime(String dispersionWaitingTime) {
        this.dispersionWaitingTime.set(dispersionWaitingTime);
    }

    public void setDispersionProcessTime(String dispersionProcessTime) {
        this.dispersionProcessTime.set(dispersionProcessTime);
    }

    public void setSourceIndex(String sourceIndex) {
        this.sourceIndex.set(sourceIndex);
    }

    public String getNumberOfRequests() {
        return numberOfRequests.get();
    }

    public String getNumberOfDoneRequests() {
        return numberOfDoneRequests.get();
    }

    public String getNumberOfRejectedRequests() {
        return numberOfRejectedRequests.get();
    }

    public String getProbabilityOfFailure() {
        return probabilityOfFailure.get();
    }

    public String getAverageTimeSpentInSystem() {
        return averageTimeSpentInSystem.get();
    }

    public String getAverageWaitingTime() {
        return averageWaitingTime.get();
    }

    public String getAverageProcessTime() {
        return averageProcessTime.get();
    }

    public String getDispersionWaitingTime() {
        return dispersionWaitingTime.get();
    }

    public String getDispersionProcessTime() {
        return dispersionProcessTime.get();
    }

    public String getSourceIndex() {
        return sourceIndex.get();
    }
}
