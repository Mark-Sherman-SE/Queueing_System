package app;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ProductionManager {
    private List<Source> source_list;

    public ProductionManager(int source_amount, double tau_1_list, double tau_2_list) {
        this.source_list = new ArrayList<>();
        for (int i = 0; i < source_amount; i++) {
            source_list.add(new Source(i, tau_1_list, tau_2_list));
        }
    }

    public Pair<Request, Double> getRequest(double current_time, int request_number) {
        fillSourceWorkTime();
        Pair<Source, Double> free_source = findFreeSource();
        recalculateWorkTime(free_source.getValue());
        return new Pair<>(free_source.getKey().generateRequest(current_time, request_number), free_source.getValue());
    }

    private Pair<Source, Double> findFreeSource() {
        Source free_source = source_list.get(0);
        double min_time = free_source.getWork_time();
        for (Source source: source_list) {
            if (source.getWork_time() < min_time) {
                free_source = source;
                min_time = source.getWork_time();
            }
        }
        return new Pair<>(free_source, min_time);
    }

    private void fillSourceWorkTime() {
        source_list.forEach(x -> {
            if (x.getWork_time() <= 0) {
                x.calculateTime();
            }
        });
    }

    private void recalculateWorkTime(double min_time) {
        source_list.forEach(x -> x.reduceWork_time(min_time));
    }
}
