package uk.ac.sanger.printy.model;

import com.google.common.base.MoreObjects;

public class LabelType {
    private int width, height, displacement;
    private String name;

    public LabelType() {}

    public LabelType(int width, int height, int displacement, String name) {
        this.width = width;
        this.height = height;
        this.displacement = displacement;
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("width", width)
                .add("height", height)
                .add("displacement", displacement)
                .add("name", name)
                .toString();
    }
}
