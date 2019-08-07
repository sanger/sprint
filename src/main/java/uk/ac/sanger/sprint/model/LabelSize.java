package uk.ac.sanger.sprint.model;

import com.google.common.base.MoreObjects;

public class LabelSize {
    private int width, height, displacement;

    public LabelSize() {}

    public LabelSize(int width, int height, int displacement) {
        this.width = width;
        this.height = height;
        this.displacement = displacement;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDisplacement() {
        return this.displacement;
    }

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
