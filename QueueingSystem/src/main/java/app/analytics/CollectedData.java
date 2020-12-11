package app.analytics;

import app.Request;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollectedData {
    private List<RealTimeSnapshot> snapshotList = new ArrayList<>();
    private RealTimeSnapshot realTimeSnapshot;

    private List<SourceAnalytics> allSourcesAnalytics = new ArrayList<>();
    private List<DeviceAnalytics> allDevicesAnalytics = new ArrayList<>();

    private int sources_amount;
    private int buffer_size;
    private int devices_amount;

    public CollectedData(int sources_amount, int buffer_size, int devices_amount) {
        this.sources_amount = sources_amount;
        this.buffer_size = buffer_size;
        this.devices_amount = devices_amount;
        realTimeSnapshot = new RealTimeSnapshot(sources_amount, buffer_size, devices_amount);
    }

    public void processData(List<Request> completed_requests, List<Request> rejected_requests,
                            List<Double> devices_work_time, double all_work_time) {
        for (int i = 0; i < sources_amount; i++) {
            int finalI = i;
            List<Request> source_completed_requests = completed_requests.stream()
                                                        .filter(x -> x.getSource_priority() == finalI)
                                                        .collect(Collectors.toList());
            List<Request> source_rejected_requests = rejected_requests.stream()
                                                        .filter(x -> x.getSource_priority() == finalI)
                                                        .collect(Collectors.toList());
            allSourcesAnalytics.add(new SourceAnalytics(i, source_completed_requests, source_rejected_requests));
        }
        for (int i = 0; i < devices_amount; i++) {
            allDevicesAnalytics.add(new DeviceAnalytics(devices_work_time.get(i), all_work_time, i));
        }
    }

    public void setSourceGeneratedRequest(int source_priority, int request_number, double current_time) {
        RealTimeSnapshot newSnapshot = new RealTimeSnapshot(realTimeSnapshot);
        newSnapshot.setCurrent_time(current_time);
        newSnapshot.setSourceAction(source_priority, String.valueOf(request_number));
        newSnapshot.setAction(Action.SOURCE_GENERATED_REQUEST);
        newSnapshot.setGlobal_action("НОВАЯ ЗАЯВКА:\nПриоритет " + source_priority + "\nНомер " + request_number
                + "\nСоздана в момент " + current_time);
        snapshotList.add(newSnapshot);
        realTimeSnapshot = newSnapshot;
    }

    public void setBufferAcceptedRequest(int buffer_index, int request_priority, int request_number, double current_time) {
        RealTimeSnapshot newSnapshot = new RealTimeSnapshot(realTimeSnapshot);
        newSnapshot.setCurrent_time(current_time);
        newSnapshot.setBufferAction(buffer_index, String.valueOf(request_number), String.valueOf(request_priority));
        newSnapshot.setAction(Action.BUFFER_ACCEPTED_REQUEST);
        newSnapshot.setGlobal_action("ПОСТУПЛЕНИЕ ЗАЯВКИ В БУФЕР:\nПриоритет " + request_priority + "\nНомер "
                + request_number + "\nНомер очереди " + buffer_index + "\n Загружена в момент " + current_time);
        snapshotList.add(newSnapshot);
        realTimeSnapshot = newSnapshot;
    }

    public void setDeviceAcceptedRequest(int device_index, int request_priority, int request_number, int buffer_index, double current_time) {
        RealTimeSnapshot newSnapshot = new RealTimeSnapshot(realTimeSnapshot);
        newSnapshot.setCurrent_time(current_time);
        newSnapshot.setDeviceAction(device_index, Action.DEVICE_ACCEPTED_REQUEST, String.valueOf(request_number), String.valueOf(request_priority));
        newSnapshot.setAction(Action.DEVICE_ACCEPTED_REQUEST);
        newSnapshot.setGlobal_action("ОТДАЧА ЗАЯВКИ НА ПРИБОР:\nПриоритет " + request_priority + "\nНомер "
                + request_number + "\nПрибор № " + device_index + "\nМомент " + current_time);

        newSnapshot.deleteRequestFromBuffer(buffer_index);
        snapshotList.add(newSnapshot);
        realTimeSnapshot = newSnapshot;
    }

    public void setDeviceReleasedRequest(int device_index, int request_number, double current_time) {
        RealTimeSnapshot newSnapshot = new RealTimeSnapshot(realTimeSnapshot);
        newSnapshot.setCurrent_time(current_time);
        newSnapshot.setDeviceAction(device_index, Action.DEVICE_RELEASED_REQUEST, "Free", "None");
        newSnapshot.setAction(Action.DEVICE_RELEASED_REQUEST);
        newSnapshot.setGlobal_action("ОСВОБОЖДЕНИЕ ПРИБОРА:\nПрибор № " + device_index + "\nОбработал заявку " +
                + request_number + "\nМомент " + current_time);
        snapshotList.add(newSnapshot);
        realTimeSnapshot = newSnapshot;
    }

    public void setRequestRejectedFromBuffer(int request_priority, int request_number, double current_time) {
        RealTimeSnapshot newSnapshot = new RealTimeSnapshot(realTimeSnapshot);
        newSnapshot.setCurrent_time(current_time);
        newSnapshot.setBufferAction(0, "Free", "None");
        newSnapshot.setAction(Action.BUFFER_REJECTED_REQUEST);
        newSnapshot.setGlobal_action("ОТКАЗ ЗАЯВКИ:\nПриоритет " + request_priority + "\nНомер заявки "
                + request_number + "\nМомент " + current_time);
        snapshotList.add(newSnapshot);
        realTimeSnapshot = newSnapshot;
    }

    public List<RealTimeSnapshot> getSnapshotList() {
        return snapshotList;
    }

    public List<SourceAnalytics> getAllSourcesAnalytics() {
        return allSourcesAnalytics;
    }

    public List<DeviceAnalytics> getAllDevicesAnalytics() {
        return allDevicesAnalytics;
    }
}
