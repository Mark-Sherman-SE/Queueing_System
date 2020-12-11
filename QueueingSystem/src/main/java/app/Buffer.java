package app;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Buffer {
    private LinkedList<Request> buffer_list;
    private int deque_size;
    private int paste_index;

    public Buffer(int deque_size) {
        this.deque_size = deque_size;
        this.buffer_list = new LinkedList<>();
        for (int i = 0; i < deque_size; i++) {
            this.buffer_list.add(null);
        }
        this.paste_index = deque_size - 1;
    }

    public Pair<Request, Integer> addRequest(double current_time, Request request) {
        request.setBuffer_arrival_time(current_time);
        Request rejected_request = null;
        if (isFull()) {
            rejected_request = buffer_list.remove(0);
            rejected_request.setBuffer_departure_time(current_time);
            buffer_list.addFirst(null);
            buffer_list.set(0, request);
        } else {
            buffer_list.set(paste_index, request);
            --paste_index;
        }
        return new Pair<>(rejected_request, isFull() ? 0 : paste_index + 1);
    }

    public Pair<Request, Integer> issueRequest(double current_time) {
        try {
            Request tmpRequest = buffer_list.stream()
                    .filter(Objects::nonNull)
                    .findFirst()
                    .get();
            List<Request> tmp_list = buffer_list.stream()
                                              .filter(Objects::nonNull)
                                              .collect(Collectors.toList());
            for (Request request: tmp_list) {
                if (tmpRequest.getSource_priority() > request.getSource_priority()) {
                    tmpRequest = request;
                } else if (tmpRequest.getSource_priority() == request.getSource_priority()) {
                    if (tmpRequest.getBuffer_arrival_time() > request.getBuffer_arrival_time()) {
                        tmpRequest = request;
                    }
                }
            }
            int remove_index = buffer_list.indexOf(tmpRequest);
            Request issuance_request = buffer_list.remove(remove_index);
            issuance_request.setBuffer_departure_time(current_time);
            buffer_list.addFirst(null);
            ++paste_index;
            return new Pair<>(issuance_request, remove_index);
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    public boolean isFull() {
        return buffer_list.stream()
                .allMatch(Objects::nonNull);
    }

    public boolean isEmpty() {
        return buffer_list.stream()
                .allMatch(Objects::isNull);
    }

}
