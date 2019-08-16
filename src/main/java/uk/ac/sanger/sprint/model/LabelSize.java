package uk.ac.sanger.sprint.model;

import com.google.common.base.MoreObjects;

/**
 * A description of a label size.
 * Dimensions are in mm.
 */
public class LabelSize {
    private int width, height, displacement;

    /**
     * Constructs a new label size instance with zeroes for the fields
     */
    public LabelSize() {}

    /**
     * Constructs a new label size instance with the given values for its fields, in mm.
     * @param width the width of the label
     * @param height the height of the label
     * @param displacement the distance between the top of one label and the top of the next label
     */
    public LabelSize(int width, int height, int displacement) {
        this.width = width;
        this.height = height;
        this.displacement = displacement;
    }

    /**
     * Gets the width of the label
     * @return the width in mm
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Sets the width of the label
     * @param width the new width in mm
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height of the label
     * @return the height in mm
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Sets the height of the label
     * @param height the new height in mm
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the displacement of the label. That means
     * the distance between the top of one label and the top of the next label.
     * @return the displacement in mm
     */
    public int getDisplacement() {
        return this.displacement;
    }

    /**
     * Sets the displacement of the label. That means
     * the distance between the top of one label and the top of the next label.
     * @param displacement the new displacement in mm
     */
    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("width", width)
                .add("height", height)
                .add("displacement", displacement)
                .toString();
    }
}
