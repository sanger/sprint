package uk.ac.sanger.sprint.model;

import java.util.Collections;
import java.util.List;

/**
 * A specification of what to print in a batch of labels
 */
public class PrintRequest {
    private List<Layout> layouts = Collections.emptyList();

    /**
     * Constructs an empty request
     */
    public PrintRequest() {}

    /**
     * Constructs a request to print the given layouts
     * @param layouts the label layouts to print
     */
    public PrintRequest(List<Layout> layouts) {
        this.layouts = layouts;
    }

    /**
     * Gets the layouts for the labels in this request
     * @return the list of layouts in this request
     */
    public List<Layout> getLayouts() {
        return layouts;
    }

    /**
     * Sets the layouts for this request
     * @param layouts the new layouts for this request
     */
    public void setLayouts(List<Layout> layouts) {
        this.layouts = layouts;
    }
}
