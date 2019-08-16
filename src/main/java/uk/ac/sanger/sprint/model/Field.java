package uk.ac.sanger.sprint.model;

/**
 * A base class for fields on the label.
 * Fields have a value, a rotation, and a position.
 * @author dr6
 */
abstract class Field {
    private int x,y;
    private Rotation rotation = Rotation.north;
    private String value;

    /**
     * Gets the x-coordinate of this field
     * @return the x-coordinate of this field in mm
     */
    public int getX() {
        return this.x;
    }

    /**
     * Sets the x-coordinate of this field
     * @param x the new x-coordinate of this field in mm
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of this field
     * @return the y-coordinate of this field in mm
     */
    public int getY() {
        return this.y;
    }

    /**
     * Sets the y-coordinate of this field
     * @param y the new y-coordinate of this field in mm
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * The rotation of this field. The default is {@code north} (unrotated)
     * @return the rotation of this field
     */
    public Rotation getRotation() {
        return this.rotation;
    }

    /**
     * Sets the rotation of this field
     * @param rotation the new rotation of this field
     */
    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    /**
     * Gets the value of this field.
     * That means the text for a text field, or the barcode contents for a barcode field.
     * @return the value of this field
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the value of this field
     * @param value the new value of this field
     */
    public void setValue(String value) {
        this.value = value;
    }
}
