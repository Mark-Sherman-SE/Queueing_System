package app.analytics;

public class DeviceAnalytics {
    private double timeOfWork;
    private double deviceUtilizationRate;
    private int deviceIndex;

    public DeviceAnalytics(double timeOfWork, double allTimeOfWork, int deviceIndex) {
        this.timeOfWork = timeOfWork;
        this.deviceUtilizationRate = timeOfWork / allTimeOfWork;
        this.deviceIndex = deviceIndex;
    }

    public double getTimeOfWork() {
        return timeOfWork;
    }

    public double getDeviceUtilizationRate() {
        return deviceUtilizationRate;
    }

    public int getDeviceIndex() {
        return deviceIndex;
    }
}
