package uk.ac.sanger.sprint.model;

import com.google.common.base.MoreObjects;

import java.util.*;

import static uk.ac.sanger.sprint.utils.BasicUtils.nullToEmpty;

/**
 * The contents of one label in a print request.
 */
public class Layout {
    private List<TextField> textFields = Collections.emptyList();
    private List<BarcodeField> barcodeFields = Collections.emptyList();
    private List<KeyField> keyFields = Collections.emptyList();
    private LabelSize labelSize;

    /**
     * Gets the text fields in this layout
     * @return the text fields in this layout
     */
    public List<TextField> getTextFields() {
        return textFields;
    }

    /**
     * Sets the list of text fields in this layout
     * @param textFields the new text fields in this layout
     */
    public void setTextFields(List<TextField> textFields) {
        this.textFields = nullToEmpty(textFields);
    }

    /**
     * Gets the barcode fields in this layout
     * @return the barcode fields in this layout
     */
    public List<BarcodeField> getBarcodeFields() {
        return barcodeFields;
    }

    /**
     * Sets the barcode fields in this layout
     * @param barcodeFields the new barcode fields in this layout
     */
    public void setBarcodeFields(List<BarcodeField> barcodeFields) {
        this.barcodeFields = nullToEmpty(barcodeFields);
    }

    /**
     * Gets the key fields in this layout
     * @return the key fields in this layout
     */
    public List<KeyField> getKeyFields() {
        return this.keyFields;
    }

    /**
     * Sets the key fields in this layout
     * @param keyFields the key fields in this layout
     */
    public void setKeyFields(List<KeyField> keyFields) {
        this.keyFields = nullToEmpty(keyFields);
    }

    /**
     * Gets the label size for this layout.
     * This is optional in a print request
     * @return the label size for this layout, or null
     */
    public LabelSize getLabelSize() {
        return this.labelSize;
    }

    /**
     * Sets the label size for this layout.
     * @param labelSize the new label size for this layout, or null
     */
    public void setLabelSize(LabelSize labelSize) {
        this.labelSize = labelSize;
    }

    /**
     * Creates a new layout with the given keyfields
     * @param keyFields the keyfields for the layout
     * @return a new layout with the given keyfields
     */
    public static Layout ofKeyFields(List<KeyField> keyFields) {
        Layout ly = new Layout();
        ly.setKeyFields(keyFields);
        return ly;
    }

    /**
     * Creates a new layout with the given strings as keyfields
     * @param keysValues the keys and values for the fields, alternating key then value
     * @return a new layout with the given keyfields
     * @exception IllegalArgumentException if an odd number of <tt>keysValues</tt> is given
     */
    public static Layout ofKeyFields(String... keysValues) {
        if (keysValues.length%2!=0) {
            throw new IllegalArgumentException("Expected an even number for keysValues");
        }
        List<KeyField> keyFields = new ArrayList<>(keysValues.length/2);
        for (int i = 0; i < keysValues.length; i += 2) {
            keyFields.add(new KeyField(keysValues[i], keysValues[i+1]));
        }
        return ofKeyFields(keyFields);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("labelSize", labelSize)
                .add("textFields", textFields)
                .add("barcodeFields", barcodeFields)
                .add("keyFields", keyFields)
                .toString();
    }
}
