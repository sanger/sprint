package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.*;

import java.util.ArrayList;
import java.util.List;

public class JScriptAdapter implements PrinterLanguageAdapter {
    private Printer printer;

    public JScriptAdapter(Printer printer) {
        this.printer = printer;
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
        LabelType labelType = printer.getLabelType();
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

    private void addFields(List<String> lines, Layout layout) {
        for (BarcodeField bf : layout.getBarcodeFields()) {
            switch (bf.getBarcodeType()) {
                case ean13:
                    lines.add(ean13(bf));
                    break;
                case code128:
                    lines.add(code128(bf));
                    break;
                case datamatrix:
                    lines.add(dataMatrix(bf));
                    break;
            }
        }
        for (TextField tf : layout.getTextFields()) {
            lines.add(text(tf));
        }
    }

    private String code128(BarcodeField bf) {
        return String.format("B %s,%s,%s,CODE128,%s,%.2f;%s",
                bf.getX(), bf.getY(), rotationAngle(bf.getRotation()), bf.getHeight(), bf.getCellWidth(),
                bf.getValue());
    }

    private String ean13(BarcodeField bf) {
        return String.format("B %s,%s,%s,EAN13,%s,%.2f;%s",
                bf.getX(), bf.getY(), rotationAngle(bf.getRotation()),
                bf.getHeight(), bf.getCellWidth(), bf.getValue());
    }

    private String rotationAngle(Rotation rotation) {
        switch (rotation) {
            case north: return "0";
            case west: return "90";
            case south: return "180";
            case east: return "270";
        }
        throw new UnsupportedOperationException();
    }

    private String dataMatrix(BarcodeField bf) {
        return String.format("B %s,%s,%s,DATAMATRIX,%.2f;%s",
                bf.getX(), bf.getY(), rotationAngle(bf.getRotation()),
                bf.getCellWidth(), bf.getValue());
    }

    private String fontCode(Font font) {
        return (font==Font.mono ? "596" : "3");
    }

    private String fontSize(int size) {
        return "pt"+size;
    }

    private String text(TextField tf) {
        return String.format("T %s,%s,%s,%s,%s;%s",
                tf.getX(), tf.getY(), rotationAngle(tf.getRotation()), fontCode(tf.getFont()), fontSize(tf.getFontSize()),
                tf.getValue());
    }


}
