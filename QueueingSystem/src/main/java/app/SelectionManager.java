package app;

import java.util.*;

public class SelectionManager {
    private List<Device> device_list;
    private ListIterator<Device> list_iterator;
    private Double[] working_time;

    public SelectionManager(int device_amount, double lambda) {
        this.device_list = new ArrayList<>();
        for (int i = 0; i < device_amount; i++) {
            device_list.add(new Device(i, lambda));
        }
        this.list_iterator = device_list.listIterator();

        this.working_time = new Double[device_amount];
        Arrays.fill(this.working_time, 0.0);
    }

    public int addRequest(double current_time, Request request) {
        while (true) {
            if (!list_iterator.hasNext()) {
                list_iterator = device_list.listIterator();
            }
            Device device = list_iterator.next();
            if (device.isFree()) {
                request.setDevice_index(device_list.indexOf(device));
                device.setRequest(current_time, request);

                working_time[device.getPriority()] += device.getWork_time();

                return device_list.indexOf(device);
            }
        }
    }

    public List<Request> freeDevices(double current_time) {
        List<Request> completed_requests = new ArrayList<>();
        ListIterator<Device> tmp_it = device_list.listIterator(list_iterator.nextIndex());
        for (int i = 0; i < device_list.size(); ++i) {
            if (!tmp_it.hasNext()) {
                tmp_it = device_list.listIterator();
            }
            Device device = tmp_it.next();
            if (!device.isFree()) {
                if (device.getCompletion_time() < current_time) {
                    completed_requests.add(device.freeDevice(current_time));
                }
            }
        }

        return completed_requests;
    }

    public double getMinWorkTime() {
        return device_list.stream()
                           //.filter(x -> !x.isFree())
                           .min(Comparator.comparing(Device::getWork_time))
                           .get().getWork_time();
    }

    public int numberOfFreeDevices() {
        return (int) device_list.stream()
                        .filter(Device::isFree)
                        .count();
    }

    public int numberOfBusyDevices() {
        return (int) device_list.stream()
                .filter(x -> !x.isFree())
                .count();
    }

    public List<Device> getDevice_list() {
        return device_list;
    }

    public Double[] getWorking_time() {
        return working_time;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Device device: device_list) {
            string.append(device);
        }
        return string.toString();
    }

}
