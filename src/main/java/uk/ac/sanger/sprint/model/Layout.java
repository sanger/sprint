package uk.ac.sanger.sprint.model;

import java.util.Collections;
import java.util.List;

public class Layout {
    private List<TextField> textFields = Collections.emptyList();
    private List<BarcodeField> barcodeFields = Collections.emptyList();
    private LabelSize labelSize;

    public List<TextField> getTextFields() {
        return textFields;
    }

    public void setTextFields(List<TextField> textFields) {
        this.textFields = textFields;
    }

    public List<BarcodeField> getBarcodeFields() {
        return barcodeFields;
    }

    public void setBarcodeFields(List<BarcodeField> barcodeFields) {
        this.barcodeFields = barcodeFields;
    }

    public LabelSize getLabelSize() {
        return this.labelSize;
    }

    public void setLabelSize(LabelSize labelSize) {
        this.labelSize = labelSize;
    }
}
