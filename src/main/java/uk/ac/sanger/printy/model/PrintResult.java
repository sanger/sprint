package uk.ac.sanger.printy.model;

import com.google.common.base.MoreObjects;

public class PrintResult {
    private String id;

    public PrintResult() {}

    public PrintResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
