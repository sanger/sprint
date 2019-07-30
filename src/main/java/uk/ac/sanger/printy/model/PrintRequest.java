package uk.ac.sanger.printy.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class PrintRequest {
    private int labelWidth, labelHeight;
    private List<Layout> layouts;

    public PrintRequest() {}

    public PrintRequest(int labelWidth, int labelHeight, List<Layout> layouts) {
        this.labelWidth = labelWidth;
        this.labelHeight = labelHeight;
        this.layouts = layouts;
    }

    public int getLabelWidth() {
        return labelWidth;
    }

    public void setLabelWidth(int labelWidth) {
        this.labelWidth = labelWidth;
    }

    public int getLabelHeight() {
        return labelHeight;
    }

    public void setLabelHeight(int labelHeight) {
        this.labelHeight = labelHeight;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<Layout> layouts) {
        this.layouts = layouts;
    }
}
