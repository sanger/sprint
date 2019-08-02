package uk.ac.sanger.printy.service.language;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import uk.ac.sanger.printy.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;

/**
 * Tests for the {@link JScriptAdapter}.
 * @author dr6
 */
@Test
public class JScriptAdapterTest {
    private JScriptAdapter adapter;

    @BeforeClass
    private void setup() {
        LabelType labelType = new LabelType(30, 12, 16, "tiny");
        adapter = new JScriptAdapter(labelType);
    }

    private BarcodeField barcodeField(BarcodeType barcodeType) {
        BarcodeField bf = new BarcodeField();
        bf.setBarcodeType(barcodeType);
        bf.setX(10);
        bf.setY(20);
        bf.setCellWidth(0.3f);
        bf.setHeight(6);
        bf.setValue("VALUE");
        return bf;
    }

    private TextField textField() {
        TextField tf = new TextField();
        tf.setFont(Font.mono);
        tf.setFontSize(7);
        tf.setValue("my text");
        tf.setX(10);
        tf.setY(20);
        return tf;
    }

    @Test
    public void testText() {
        TextField tf = textField();

        assertEquals(adapter.text(tf), "T 10,20,0,596,7.0;my text");

        tf.setFont(Font.proportional);
        tf.setRotation(Rotation.west);
        assertEquals(adapter.text(tf), "T 10,20,90,3,7.0;my text");
    }

    @Test
    public void testCode128Barcode() {
        BarcodeField bf = barcodeField(BarcodeType.code128);
        assertEquals(adapter.barcode(bf), "B 10,20,0,CODE128,6,0.30;VALUE");
        bf.setRotation(Rotation.south);
        assertEquals(adapter.barcode(bf), "B 10,20,180,CODE128,6,0.30;VALUE");
    }

    @Test
    public void testCode39Barcode() {
        BarcodeField bf = barcodeField(BarcodeType.code39);
        assertEquals(adapter.barcode(bf), "B 10,20,0,CODE39,6,0.30,3;VALUE");
        bf.setRotation(Rotation.south);
        assertEquals(adapter.barcode(bf), "B 10,20,180,CODE39,6,0.30,3;VALUE");
    }

    @Test
    public void testEAN13Barcode() {
        BarcodeField bf = barcodeField(BarcodeType.ean13);
        bf.setValue("2000000000015");
        assertEquals(adapter.barcode(bf), "B 10,20,0,EAN13,6,0.30;2000000000015");
        bf.setRotation(Rotation.west);
        assertEquals(adapter.barcode(bf), "B 10,20,90,EAN13,6,0.30;2000000000015");
    }

    @Test
    public void testDataMatrixBarcode() {
        BarcodeField bf = barcodeField(BarcodeType.datamatrix);
        assertEquals(adapter.barcode(bf), "B 10,20,0,DATAMATRIX,0.30;VALUE");
        bf.setRotation(Rotation.east);
        assertEquals(adapter.barcode(bf), "B 10,20,270,DATAMATRIX,0.30;VALUE");
    }

    @Test
    public void testTranscribe() {
        String[] strings = { "ALPHA", "BETA", "GAMMA" };
        List<TextField> textFields = IntStream.range(0, strings.length)
                .mapToObj(i -> {
                    TextField tf = textField();
                    tf.setX(10*i);
                    tf.setY(10*i+10);
                    tf.setValue(strings[i]);
                    return tf;
                })
                .collect(Collectors.toList());
        textFields.get(1).setFont(Font.proportional);

        List<BarcodeField> barcodeFields = Arrays.asList(
                barcodeField(BarcodeType.ean13),
                barcodeField(BarcodeType.datamatrix)
        );

        List<Layout> layouts = Arrays.asList(new Layout(), new Layout());
        layouts.get(0).setTextFields(textFields.subList(0,2));
        layouts.get(1).setBarcodeFields(barcodeFields);
        layouts.get(1).setTextFields(textFields.subList(2,3));

        PrintRequest request = new PrintRequest(layouts);
        String jobId = "MyJob";
        String code = adapter.transcribe(request, jobId);
        assertEquals(code, "m m\nJ\nS l1;0,0,12,16,30\nO R\nH 100\nj MyJob\n" +
                "T 0,10,0,596,7.0;ALPHA\nT 10,20,0,3,7.0;BETA\nA 1\n" +
                "J\nS l1;0,0,12,16,30\nO R\nH 100\nj MyJob\n" +
                "B 10,20,0,EAN13,6,0.30;VALUE\nB 10,20,0,DATAMATRIX,0.30;VALUE\n" +
                "T 20,30,0,596,7.0;GAMMA\nA 1\n");
    }
}
