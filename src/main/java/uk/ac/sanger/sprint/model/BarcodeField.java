package uk.ac.sanger.sprint.model;

/**
 * A barcode on a label
 */
public class BarcodeField extends Field {
    private BarcodeType barcodeType;
    private int height;
    private float cellWidth;

    /**
     * The type of barcode
     * @return the type of barcode
     */
    public BarcodeType getBarcodeType() {
        return barcodeType;
    }

    /**
     * Sets the barcode type
     * @param barcodeType the type of barcode
     */
    public void setBarcodeType(BarcodeType barcodeType) {
        this.barcodeType = barcodeType;
    }

    /**
     * The height of the barcode in mm. Only applies to certain types of barcode.
     * @return the height of the barcode in mm. Only applies to certain types of barcode.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the barcode in mm. This only affects certain types of barcode.
     * @param height the height in mm
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * The cell width or module width of the barcode in mm.
     * This means different things to different barcode types,
     * for example the width of a bar in a 1D barcode, or the width of a cell in
     * a 2D barcode.
     * @return the cell width of this barcode in mm
     */
    public float getCellWidth() {
        return cellWidth;
    }

    /**
     * Sets the cell width of this barcode in mm.
     * This means different things to different barcode types,
     * for example the width of a bar in a 1D barcode, or the width of a cell in
     * a 2D barcode.
     * @param cellWidth the cell width in mm
     */
    public void setCellWidth(float cellWidth) {
        this.cellWidth = cellWidth;
    }
}
