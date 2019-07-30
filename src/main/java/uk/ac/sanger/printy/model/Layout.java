package uk.ac.sanger.printy.model;

import java.util.List;

public class Layout {
    private List<TextField> textFields;
    private List<BarcodeField> barcodeFields;

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
}
