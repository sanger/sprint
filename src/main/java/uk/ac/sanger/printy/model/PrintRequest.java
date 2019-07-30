package uk.ac.sanger.printy.model;

import java.util.Collections;
import java.util.List;

public class PrintRequest {
    private List<Layout> layouts = Collections.emptyList();

    public PrintRequest() {}

    public PrintRequest(List<Layout> layouts) {
        this.layouts = layouts;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<Layout> layouts) {
        this.layouts = layouts;
    }
}
