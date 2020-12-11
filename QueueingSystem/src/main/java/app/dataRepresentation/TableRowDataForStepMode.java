package app.dataRepresentation;

import javafx.beans.property.SimpleStringProperty;

public class TableRowDataForStepMode {
    private SimpleStringProperty index;
    private SimpleStringProperty request_number;
    private SimpleStringProperty request_priority;
    private SimpleStringProperty pointer;

    public TableRowDataForStepMode(String index, String request_number, String pointer) {
        this.index = new SimpleStringProperty(index);
        this.request_number = new SimpleStringProperty(request_number);
        this.pointer = new SimpleStringProperty(pointer);
    }

    public TableRowDataForStepMode(String index, String request_number, String request_priority, String pointer) {
        this.index = new SimpleStringProperty(index);
        this.request_number = new SimpleStringProperty(request_number);
        this.request_priority = new SimpleStringProperty(request_priority);
        this.pointer = new SimpleStringProperty(pointer);
    }

    public void setIndex(String index) {
        this.index.set(index);
    }

    public void setRequest_number(String request_number) {
        this.request_number.set(request_number);
    }

    public void setRequest_priority(String request_priority) {
        this.request_priority.set(request_priority);
    }

    public void setPointer(String pointer) {
        this.pointer.set(pointer);
    }

    public String getIndex() {
        return index.get();
    }

    public String getRequest_number() {
        return request_number.get();
    }

    public String getRequest_priority() {
        return request_priority.get();
    }

    public String getPointer() {
        return pointer.get();
    }
}
