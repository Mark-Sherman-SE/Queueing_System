package app;

import app.analytics.CollectedData;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Modulator {
    private static Modulator instance;
    private int sources_amount;
    private int buffer_size;
    private int devices_amount;
    private double tau_1;
    private double tau_2;
    private double lambda;
    private int requests_number;
    private CollectedData collectedData;

    private Modulator() {
        this.sources_amount = 9;
        this.buffer_size = 5;
        this.devices_amount = 6;
        this.requests_number = 5000;
        this.tau_1 = 3.0;
        this.tau_2 = 4.5;
        this.lambda = 1.4;
    }

    public void modulateWork() {

        collectedData = new CollectedData(sources_amount, buffer_size, devices_amount);

        List<Request> rejected_requests = new ArrayList<>();
        List<Request> completed_requests = new ArrayList<>();
        ProductionManager productionManager = new ProductionManager(sources_amount, tau_1, tau_2);
        Buffer buffer = new Buffer(buffer_size);
        SelectionManager selectionManager = new SelectionManager(devices_amount, lambda);

        double current_time = 0;

        for (int i = 0; i < requests_number; i++) {
            Pair<Request, Double> request_and_remaining_time = productionManager.getRequest(current_time, i + 1);
            Request new_request = request_and_remaining_time.getKey();
            current_time += request_and_remaining_time.getValue();

            List<Request> done_requests = selectionManager.freeDevices(current_time);
            for (Request request: done_requests) {

                collectedData.setDeviceReleasedRequest(request.getDevice_index(), request.getRequest_number(), current_time);

//                collectedData.transmitIncrementalAction("Освобождение прибора: прибор №" + request.getDevice_index() + ", момент "
//                        + current_time);
                completed_requests.add(request);
            }

            collectedData.setSourceGeneratedRequest(new_request.getSource_priority(), new_request.getRequest_number(),
                    current_time);

            Pair<Request, Integer> rej_request_with_list_index = buffer.addRequest(current_time, new_request);
            Request rej_request = rej_request_with_list_index.getKey();
            int list_index = rej_request_with_list_index.getValue();

            if (rej_request != null) {
                rejected_requests.add(rej_request);

                collectedData.setRequestRejectedFromBuffer(rej_request.getSource_priority(),
                        rej_request.getRequest_number(), current_time);

//                collectedData.transmitIncrementalAction("Отказ заявки: приоритет " + rej_request.getSource_priority()
//                        + ", номер заявки " + rej_request.getRequest_number() + ", момент " + current_time);
            }

            collectedData.setBufferAcceptedRequest(list_index, new_request.getSource_priority(),
                    new_request.getRequest_number(), current_time);

//            collectedData.transmitIncrementalAction("Новая заявка: приоритет " + new_request.getSource_priority()
//                    + ", номер " + new_request.getRequest_number() + " создана и загружена в буфер в момент " + current_time);


            for (int j = 0; j < selectionManager.numberOfFreeDevices(); j++) {
                if (!buffer.isEmpty()) {
                    Pair<Request, Integer> request_with_list_index = buffer.issueRequest(current_time);
                    Request request = request_with_list_index.getKey();
                    list_index = request_with_list_index.getValue();
                    int device_index = selectionManager.addRequest(current_time, request_with_list_index.getKey());

                    collectedData.setDeviceAcceptedRequest(device_index, request.getSource_priority(),
                            request.getRequest_number(), list_index, current_time);

//                    collectedData.transmitIncrementalAction("Отдача заявки на прибор: приоритет " + request_with_list_index.getKey().getSource_priority()
//                            + ", номер заявки " + request_with_list_index.getKey().getRequest_number() + ", прибор №" + device_index + ", момент " + current_time);
                } else {
                    break;
                }
            }
        }

        while (!buffer.isEmpty() || selectionManager.numberOfBusyDevices() != 0) {
            current_time += selectionManager.getMinWorkTime();
            List<Request> done_requests = selectionManager.freeDevices(current_time);
            for (Request request: done_requests) {
                collectedData.setDeviceReleasedRequest(request.getDevice_index(), request.getRequest_number(), current_time);
//                collectedData.transmitIncrementalAction("Освобождение прибора: прибор №" + request.getDevice_index() + ", момент "
//                        + current_time);
                completed_requests.add(request);
            }

            for (int j = 0; j < selectionManager.numberOfFreeDevices(); j++) {
                if (!buffer.isEmpty()) {
                    Pair<Request, Integer> request_with_list_index = buffer.issueRequest(current_time);
                    Request request = request_with_list_index.getKey();
                    int list_index = request_with_list_index.getValue();
                    int device_index = selectionManager.addRequest(current_time, request_with_list_index.getKey());

                    collectedData.setDeviceAcceptedRequest(device_index, request.getSource_priority(),
                            request.getRequest_number(), list_index, current_time);

//                    collectedData.transmitIncrementalAction("Отдача заявки на прибор: приоритет " + request_with_list_index.getKey().getSource_priority()
//                            + ", номер заявки " + request_with_list_index.getKey().getRequest_number() + ", прибор №" + device_index + ", момент " + current_time);
                }
            }
        }
        collectedData.processData(completed_requests, rejected_requests,
                new ArrayList<>(Arrays.asList(selectionManager.getWorking_time())), current_time);
    }

    public void setSources_amount(int sources_amount) {
        this.sources_amount = sources_amount;
    }

    public void setBuffer_size(int buffer_size) {
        this.buffer_size = buffer_size;
    }

    public void setDevices_amount(int devices_amount) {
        this.devices_amount = devices_amount;
    }

    public void setTau_1(double tau_1) {
        this.tau_1 = tau_1;
    }

    public void setTau_2(double tau_2) {
        this.tau_2 = tau_2;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public void setRequests_number(int requests_number) {
        this.requests_number = requests_number;
    }

    public int getSources_amount() {
        return sources_amount;
    }

    public int getBuffer_size() {
        return buffer_size;
    }

    public int getDevices_amount() {
        return devices_amount;
    }

    public double getTau_1() {
        return tau_1;
    }

    public double getTau_2() {
        return tau_2;
    }

    public double getLambda() {
        return lambda;
    }

    public int getRequests_number() {
        return requests_number;
    }

    public CollectedData getCollectedData() {
        return collectedData;
    }

    public static Modulator getInstance() {
        if (instance == null) {
            instance = new Modulator();
        }
        return instance;
    }
}
