package uk.ac.sanger.printy.service.language;

import uk.ac.sanger.printy.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for creating JScript for a print request.
 */
public class JScriptAdapter implements PrinterLanguageAdapter {
    private LabelType labelType;

    public JScriptAdapter(Printer printer) {
        this(printer.getLabelType());
    }

    public JScriptAdapter(LabelType labelType) {
        this.labelType = labelType;
    }

    @Override
    public String transcribe(PrintRequest request, String jobId) {
        List<String> lines = new ArrayList<>();
        String JOB_START = "J";
        String SET_MEASUREMENT = "m m";
        String SET_HEAT = "H 100";
        String PRINT = "A 1";
        String SET_OPTIONS = "O R";
        String setId = "j "+jobId;
        String setSize = String.format("S l1;0,0,%s,%s,%s",
                labelType.getHeight(), labelType.getDisplacement(), labelType.getWidth());

        lines.add(SET_MEASUREMENT);
        for (Layout layout : request.getLayouts()) {
            lines.add(JOB_START);
            lines.add(setSize);
            lines.add(SET_OPTIONS);
            lines.add(SET_HEAT);
            lines.add(setId);
            addFields(lines, layout);
            lines.add(PRINT);
        }
        lines.add("");
        return String.join("\n", lines);
    }

    void addFields(List<String> lines, Layout layout) {
        for (BarcodeField bf : layout.getBarcodeFields()) {
            lines.add(barcode(bf));
        }
        for (TextField tf : layout.getTextFields()) {
            lines.add(text(tf));
        }
    }

    String barcode(BarcodeField bf) {
        switch (bf.getBarcodeType()) {
            case ean13: return ean13(bf);
            case code128: return code128(bf);
            case datamatrix: return dataMatrix(bf);
            case code39: return code39(bf);
        }
        throw new UnsupportedOperationException("Cannot translate barcode type "+bf.getBarcodeType());
    }

    String code128(BarcodeField bf) {
        return String.format("B %s,%s,%s,CODE128,%s,%.2f;%s",
                bf.getX(), bf.getY(), rotationAngle(bf.getRotation()), bf.getHeight(), bf.getCellWidth(),
                bf.getValue());
    }

    String ean13(BarcodeField bf) {
        return String.format("B %s,%s,%s,EAN13,%s,%.2f;%s",
                bf.getX(), bf.getY(), rotationAngle(bf.getRotation()),
                bf.getHeight(), bf.getCellWidth(), bf.getValue());
    }

    String dataMatrix(BarcodeField bf) {
        return String.format("B %s,%s,%s,DATAMATRIX,%.2f;%s",
                bf.getX(), bf.getY(), rotationAngle(bf.getRotation()),
                bf.getCellWidth(), bf.getValue());
    }

    String code39(BarcodeField bf) {
        final int RATIO = 3;
        return String.format("B %s,%s,%s,CODE39,%s,%.2f,%s;%s",
                bf.getX(), bf.getY(), rotationAngle(bf.getRotation()),
                bf.getHeight(), bf.getCellWidth(), RATIO, bf.getValue());
    }

    String text(TextField tf) {
        return String.format("T %s,%s,%s,%s,%.1f;%s",
                tf.getX(), tf.getY(), rotationAngle(tf.getRotation()), fontCode(tf.getFont()), tf.getFontSize(),
                tf.getValue());
    }

    String rotationAngle(Rotation rotation) {
        switch (rotation) {
            case north: return "0";
            case west: return "90";
            case south: return "180";
            case east: return "270";
        }
        throw new UnsupportedOperationException();
    }

    String fontCode(Font font) {
        return (font==Font.mono ? "596" : "3");
    }
}
