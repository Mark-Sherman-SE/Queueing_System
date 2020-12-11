package app;

public class Source {
    private int priority;
    private double tau_1;
    private double tau_2;
    private double work_time = 0;

    public Source(int priority, double tau_1, double tau_2) {
        this.priority = priority;
        this.tau_1 = tau_1;
        this.tau_2 = tau_2;
    }

    public Request generateRequest(double current_time, int request_number) {
        return new Request(priority, current_time,  request_number);
    }

    public double calculateTime() {
        work_time = tau_1 + (tau_2 - tau_1) * Math.random();
        return work_time;
    }

    public double getWork_time() {
        return work_time;
    }

    public void reduceWork_time(double time) {
        work_time -= time;
    }
}
