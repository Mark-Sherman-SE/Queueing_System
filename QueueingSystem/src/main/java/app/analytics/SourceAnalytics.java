package app.analytics;

import app.Request;

import java.util.ArrayList;
import java.util.List;

public class SourceAnalytics {
    private Long numberOfRequests;
    private Long numberOfDoneRequests;
    private Long numberOfRejectedRequests;
    private Double requestsTimeInSystem;
    private Double requestsWaitingTime;
    private Double requestsProcessTime;

    private Double probabilityOfFailure;
    private Double averageTimeSpentInSystem;
    private Double averageWaitingTime;
    private Double averageProcessTime;

    private Double dispersionWaitingTime;
    private Double dispersionProcessTime;
    private Integer source_index;

    public SourceAnalytics(int source_index, List<Request> completed_requests, List<Request> rejected_requests) {
        this.source_index = source_index;
        List<Request> all_requests = new ArrayList<>(completed_requests);
        all_requests.addAll(rejected_requests);
        this.numberOfRequests = calculateNumberOfRequests(all_requests);
        this.numberOfDoneRequests = calculateNumberOfRequests(completed_requests);
        this.numberOfRejectedRequests = calculateNumberOfRequests(rejected_requests);
        this.requestsTimeInSystem = calculateRequestsTimeInSystem(all_requests);
        this.requestsWaitingTime = calculateRequestsWaitingTime(all_requests); //check
        this.requestsProcessTime = calculateRequestsProcessTime(completed_requests);

        this.probabilityOfFailure = calculateProbabilityOfFailure();
        this.averageTimeSpentInSystem = calculateAverageTimeSpentInSystem();
        this.averageWaitingTime = calculateAverageWaitingTime();
        this.averageProcessTime = calculateAverageProcessTime();

        this.dispersionWaitingTime = calculateDispersionWaitingTime(all_requests);
        this.dispersionProcessTime = calculateDispersionProcessTime(completed_requests);
    }

    private Long calculateNumberOfRequests(List<Request> requests) {
        Long result = 0L;
        for (Request ignored : requests) {
            result += 1;
        }
        return result;
    }

    private Double calculateRequestsTimeInSystem(List<Request> requests) {
        Double result = 0.0;
        for (Request request : requests) {
            result += request.getTimeInSystem();
        }
        return result;
    }

    private Double calculateRequestsWaitingTime(List<Request> requests) {
        Double result = 0.0;
        for (Request request : requests) {
            result += request.getBuffer_time();
        }
        return result;
    }

    private Double calculateRequestsProcessTime(List<Request> requests) {
        Double result = 0.0;
        for (Request request : requests) {
            result += request.getDevice_time();
        }
        return result;
    }

    private Double calculateProbabilityOfFailure() {
        return ((double) numberOfRejectedRequests / numberOfRequests);
    }

    private Double calculateAverageTimeSpentInSystem() {
        return requestsTimeInSystem / numberOfRequests;
    }

    private Double calculateAverageWaitingTime() {
        return requestsWaitingTime / numberOfRequests;
    }

    private Double calculateAverageProcessTime() {
        return requestsProcessTime / numberOfRequests;
    }

    private Double calculateDispersionWaitingTime(List<Request> requests) {
        Double result = 0.0;
        for (Request request: requests) {
            result += Math.pow(request.getBuffer_time() - averageWaitingTime, 2);
        }
        return result / requests.size();
    }

    private Double calculateDispersionProcessTime(List<Request> requests) {
        Double result = 0.0;
        for (Request request: requests) {
            result += Math.pow(request.getDevice_time() - averageProcessTime, 2);
        }

        return result / requests.size();
    }

    public Integer getSource_index() {
        return source_index;
    }

    public Long getNumberOfRequests() {
        return numberOfRequests;
    }

    public Long getNumberOfDoneRequests() {
        return numberOfDoneRequests;
    }

    public Long getNumberOfRejectedRequests() {
        return numberOfRejectedRequests;
    }

    public Double getProbabilityOfFailure() {
        return probabilityOfFailure;
    }

    public Double getAverageTimeSpentInSystem() {
        return averageTimeSpentInSystem;
    }

    public Double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public Double getAverageProcessTime() {
        return averageProcessTime;
    }

    public Double getDispersionWaitingTime() {
        return dispersionWaitingTime;
    }

    public Double getDispersionProcessTime() {
        return dispersionProcessTime;
    }
}
