package app;

public class Device {
    private int priority;
    private double lambda;
    private boolean is_free;
    private double completion_time;
    private double work_time;
    private Request request;

    public Device(int priority, double lambda) {
        this.completion_time = 0;
        this.priority = priority;
        this.lambda = lambda;
        this.is_free = true;
        this.request = null;
        this.work_time = 0;
    }

    public int getPriority() {
        return priority;
    }

    public double getCompletion_time() {
        return completion_time;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRequest(double current_time, Request request) {
        this.is_free = false;
        this.request = request;
        this.request.setDevice_arrival_time(current_time);
        this.completion_time = current_time + CalculateTime();
        this.request.setDevice_departure_time(this.completion_time);
    }

    public Request freeDevice(double current_time) {
        is_free = true;
        Request tmpReq = request;
        request = null;
        tmpReq.setDevice_departure_time(current_time);
        return tmpReq;
    }

    public boolean isFree() {
        return is_free;
    }

    public double getWork_time() {
        return work_time;
    }

    @Override
    public String toString() {
        return "is_free: " + is_free + ", completion_time " + completion_time + ", work_time " + work_time + "\n";
    }

    private double CalculateTime() {
        work_time = lambda * Math.exp(Math.random());
        return work_time;
    }

}
