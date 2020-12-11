package app.analytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RealTimeSnapshot {
    private Double current_time = 0.0;
    private List<List<String>> sources = new ArrayList<>();
    private List<List<String>> buffer = new ArrayList<>();
    private List<List<String>> devices = new ArrayList<>();
    private String global_action = "";
    private Action action;
    private final String arrow = "<---";
    private int sources_pointer;
    private int buffer_pointer;
    private int devices_pointer;
    private int prev_device_pointer;

    public RealTimeSnapshot(int sources_size, int buffer_size, int devices_size) {
        for (int i = 0; i < sources_size; i++) {
            this.sources.add(new ArrayList<>(Arrays.asList("Free", "")));
        }
        for (int i = 0; i < buffer_size; i++) {
            this.buffer.add(new ArrayList<>(Arrays.asList("Free", "None", "")));
        }
        for (int i = 0; i < devices_size; i++) {
            this.devices.add(new ArrayList<>(Arrays.asList("Free", "None", "")));
        }
        this.sources.get(0).set(1, arrow);
        this.buffer.get(buffer_size - 1).set(2, arrow);
        this.devices.get(0).set(2, arrow);
        this.sources_pointer = 0;
        this.buffer_pointer = buffer_size - 1;
        this.devices_pointer = 0;
        this.prev_device_pointer = 0;
    }

    public RealTimeSnapshot(RealTimeSnapshot snapshot) {
        this.current_time = snapshot.getCurrent_time();
        this.sources = new ArrayList<>(snapshot.getSources());
        this.buffer = new ArrayList<>(snapshot.getBuffer());
        this.devices = new ArrayList<>(snapshot.getDevices());
        this.global_action = snapshot.getGlobal_action();
        this.action = snapshot.getAction();
        this.sources_pointer = snapshot.getSources_pointer();
        this.buffer_pointer = snapshot.getBuffer_pointer();
        this.devices_pointer = snapshot.getDevices_pointer();
        this.prev_device_pointer = snapshot.getPrev_device_pointer();
    }

    public void setCurrent_time(double current_time) {
        this.current_time = current_time;
    }

    public void setSourceAction(int source_index, String request_number) {
        sources.set(sources_pointer, new ArrayList<>(Arrays.asList(sources.get(sources_pointer).get(0), "")));
        sources.set(source_index, new ArrayList<>(Arrays.asList(request_number, arrow)));
        sources_pointer = source_index;
    }

    public void setBufferAction(int buffer_index, String request_number, String request_priority) {
        if (buffer.size() == 1) {
            buffer.set(buffer_pointer, new ArrayList<>(Arrays.asList(request_number, request_priority, arrow)));
        } else {
            if (buffer_pointer == 0 && buffer_index == 0) {
                buffer.set(buffer_pointer, new ArrayList<>(Arrays.asList(request_number, request_priority, arrow)));
            } else {
                buffer.set(buffer_pointer, new ArrayList<>(Arrays.asList(buffer.get(buffer_pointer).get(0),
                        buffer.get(buffer_pointer).get(1), "")));
                if (buffer_index == 0) {
                    buffer_pointer = 0;
                    buffer.set(buffer_index, new ArrayList<>(Arrays.asList(request_number, request_priority, arrow)));
                    buffer.set(buffer_index + 1, new ArrayList<>(Arrays.asList(buffer.get(buffer_index + 1).get(0),
                            buffer.get(buffer_index + 1).get(1), "")));
                } else {
                    buffer_pointer = buffer_index - 1;
                    buffer.set(buffer_pointer, new ArrayList<>(Arrays.asList(buffer.get(buffer_pointer).get(0),
                            buffer.get(buffer_pointer).get(1), arrow)));
                    buffer.set(buffer_index, new ArrayList<>(Arrays.asList(request_number, request_priority, "")));
                }
            }
        }
    }

    public void setDeviceAction(int device_index, Action action, String request_number, String request_priority) {
        if (action == Action.DEVICE_ACCEPTED_REQUEST) {

            devices_pointer = device_index;
            devices.set(devices_pointer, new ArrayList<>(Arrays.asList(request_number, request_priority, "")));
            prev_device_pointer = devices_pointer;
            if (devices.stream().anyMatch(x -> x.get(0).equals("Free"))) {
                while (true) {
                    devices.set(devices_pointer, new ArrayList<>(Arrays.asList(devices.get(devices_pointer).get(0),
                            devices.get(devices_pointer).get(1), "")));
                    if (devices_pointer == devices.size() - 1) {
                        devices_pointer = 0;
                    } else {
                        devices_pointer++;
                    }
                    if (devices.get(devices_pointer).get(0).equals("Free")) {
                        devices.set(devices_pointer, new ArrayList<>(Arrays.asList("Free", "None", arrow)));
                        return;
                    }
                }
            }
        } else {
            devices.set(device_index, new ArrayList<>(Arrays.asList(request_number, request_priority, "")));
            if (devices.get(devices_pointer).get(0).equals("Free")) {
                if (prev_device_pointer < device_index && devices_pointer > device_index) {
                    devices.set(devices_pointer, new ArrayList<>(Arrays.asList(devices.get(devices_pointer).get(0),
                            devices.get(devices_pointer).get(1), "")));
                    devices_pointer = device_index;
                    devices.set(devices_pointer, new ArrayList<>(Arrays.asList(devices.get(devices_pointer).get(0),
                            devices.get(devices_pointer).get(1), arrow)));
                }
            } else {
                while (true) {
                    devices.set(devices_pointer, new ArrayList<>(Arrays.asList(devices.get(devices_pointer).get(0),
                            devices.get(devices_pointer).get(1), "")));
                    if (devices_pointer == devices.size() - 1) {
                        devices_pointer = 0;
                    } else {
                        devices_pointer++;
                    }
                    if (devices.get(devices_pointer).get(0).equals("Free")) {
                        devices.set(devices_pointer, new ArrayList<>(Arrays.asList("Free", "None", arrow)));
                        return;
                    }
                }
            }
        }
    }

    public void setGlobal_action(String global_action) {
        this.global_action = global_action;
    }

    public void deleteRequestFromBuffer(int index) {
        buffer.remove(index);
        buffer.add(0, new ArrayList<>(Arrays.asList("Free", "None", "")));
        if (buffer_pointer == buffer.size() - 1) {
            buffer.set(buffer_pointer, new ArrayList<>(Arrays.asList(buffer.get(buffer_pointer).get(0),
                    buffer.get(buffer_pointer).get(1), arrow)));
        } else if (buffer_pointer == 0 && !buffer.get(1).get(0).equals("Free")){
            buffer.set(buffer_pointer + 1, new ArrayList<>(Arrays.asList(buffer.get(buffer_pointer + 1).get(0),
                    buffer.get(buffer_pointer + 1).get(1), "")));
            buffer.set(buffer_pointer, new ArrayList<>(Arrays.asList(buffer.get(buffer_pointer).get(0),
                    buffer.get(buffer_pointer).get(1), arrow)));
        } else {
            buffer.set(buffer_pointer + 1, new ArrayList<>(Arrays.asList(buffer.get(buffer_pointer + 1).get(0),
                    buffer.get(buffer_pointer + 1).get(1), "")));
            buffer_pointer++;
            buffer.set(buffer_pointer, new ArrayList<>(Arrays.asList(buffer.get(buffer_pointer).get(0),
                    buffer.get(buffer_pointer).get(1), arrow)));
        }
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public double getCurrent_time() {
        return current_time;
    }

    public List<List<String>> getSources() {
        return sources;
    }

    public List<List<String>> getBuffer() {
        return buffer;
    }

    public List<List<String>> getDevices() {
        return devices;
    }

    public String getGlobal_action() {
        return global_action;
    }

    public Action getAction() {
        return action;
    }

    public int getSources_pointer() {
        return sources_pointer;
    }

    public int getBuffer_pointer() {
        return buffer_pointer;
    }

    public int getDevices_pointer() {
        return devices_pointer;
    }

    public int getPrev_device_pointer() {
        return prev_device_pointer;
    }
}
