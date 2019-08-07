package uk.ac.sanger.sprint.model;

import com.google.common.base.MoreObjects;

public class LabelType extends LabelSize {
    private String name;

    public LabelType() {
        super();
    }

    public LabelType(int width, int height, int displacement, String name) {
        super(width, height, displacement);
        this.name = name;
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
                .add("name", name)
                .add("width", getWidth())
                .add("height", getHeight())
                .add("displacement", getDisplacement())
                .toString();
    }
}
