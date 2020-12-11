package app;

public class Request {
    private int source_priority;
    private int request_number;
    private int device_index = -1;
    private double creation_time;
    private double buffer_arrival_time = -1;
    private double buffer_departure_time = -1;
    private double device_arrival_time = -1;
    private double device_departure_time = -1;

    public Request(int source_priority, double creation_time, int request_number) {
        this.source_priority = source_priority;
        this.creation_time = creation_time;
        this.request_number = request_number;
    }

    public void setBuffer_arrival_time(double buffer_arrival_time) {
        this.buffer_arrival_time = buffer_arrival_time;
    }

    public void setBuffer_departure_time(double buffer_departure_time) {
        this.buffer_departure_time = buffer_departure_time;
    }

    public void setDevice_arrival_time(double device_arrival_time) {
        this.device_arrival_time = device_arrival_time;
    }

    public void setDevice_departure_time(double device_departure_time) {
        this.device_departure_time = device_departure_time;
    }

    public void setDevice_index(int device_index) {
        this.device_index = device_index;
    }

    public int getSource_priority() {
        return source_priority;
    }

    public double getCreation_time() {
        return creation_time;
    }

    public double getBuffer_arrival_time() {
        return buffer_arrival_time;
    }

    public double getBuffer_departure_time() {
        return buffer_departure_time;
    }

    public double getBuffer_time() {
        return buffer_departure_time - buffer_arrival_time;
    }

    public double getDevice_arrival_time() {
        return device_arrival_time;
    }

    public double getDevice_departure_time() {
        return device_departure_time;
    }

    public double getDevice_time() {
        return device_departure_time - device_arrival_time;
    }

    public double getTimeInSystem() {
        if (device_arrival_time == -1) {
            return buffer_departure_time - creation_time;
        } else {
            return device_departure_time - creation_time;
        }
    }

    public int getDevice_index() {
        return device_index;
    }

    public int getRequest_number() {
        return request_number;
    }
}
