package uk.ac.sanger.sprint.model;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * A type of label described in config.
 */
public class LabelType extends LabelSize {
    private String name;
    private List<Printer> printers = new ArrayList<>();

    /**
     * Constructs a new label type with default values for all fields
     */
    public LabelType() {
        super();
    }

    /**
     * Constructs a new label type with the given field values
     * @param width the width of the label in mm
     * @param height the height of the label in mm
     * @param displacement the displacement of the label in mm
     * @param name the name of the label type
     */
    public LabelType(int width, int height, int displacement, String name) {
        super(width, height, displacement);
        this.name = name;
    }

    /**
     * Gets the name of the label type
     * @return the name of the label type
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the label type
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of printers associated with this label type.
     * By default this is an {@link ArrayList}, so you can add stuff directly to it
     * @return the list of printers associated with this label type
     */
    public List<Printer> getPrinters() {
        return printers;
    }

    /**
     * Sets the printers associated with this label type
     * @param printers the printers associated with this label type
     */
    public void setPrinters(List<Printer> printers) {
        this.printers = printers;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("width", getWidth())
                .add("height", getHeight())
                .add("displacement", getDisplacement())
                .toString();
    }
}
