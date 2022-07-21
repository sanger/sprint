package uk.ac.sanger.sprint.model;

import com.google.common.base.MoreObjects;

import java.util.Collections;
import java.util.List;

/**
 * The contents of one label in a print request.
 */
public class Layout {
    private List<TextField> textFields = Collections.emptyList();
    private List<BarcodeField> barcodeFields = Collections.emptyList();
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
        this.textFields = textFields;
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
        this.barcodeFields = barcodeFields;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("labelSize", labelSize)
                .add("textFields", textFields)
                .add("barcodeFields", barcodeFields)
                .toString();
    }
}
