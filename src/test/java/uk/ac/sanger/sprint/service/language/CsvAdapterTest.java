package uk.ac.sanger.sprint.service.language;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import uk.ac.sanger.sprint.model.*;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

/** Test {@link CsvAdapter} */
public class CsvAdapterTest {

    CsvAdapter adapter = new CsvAdapter(",");

    @Test
    public void testTranscribe() {
        PrintRequest request = new PrintRequest(Arrays.asList(
                Layout.ofKeyFields("A", "Alpha", "B", "Beta", "C", "Gamma", "", null),
                Layout.ofKeyFields("", "Alabama", "", null, "", "Calif,\nornia")
        ));
        String output = adapter.transcribe(request, null);
        assertEquals(output, "Alpha,Beta,Gamma,\nAlabama,,Calif ornia");
    }

    @Test
    public void testTranscribeKeyFields() {
        List<KeyField> kfs = Arrays.asList(
                new KeyField("", "Alpha"), new KeyField("", null), new KeyField("", ""),
                new KeyField("", "Alpha  \n Beta"), new KeyField("", "Gamma,\tDelta")
        );
        String line = adapter.transcribeKeyFields(kfs);
        assertEquals(line, "Alpha,,,Alpha Beta,Gamma Delta");
    }

    @Test(dataProvider="sanitiseValueArgs")
    public void testSanitiseValue(String input, String expected) {
        assertEquals(adapter.sanitiseValue(input), expected);
    }

    @DataProvider(name="sanitiseValueArgs")
    Object[][] sanitiseValueArgs() {
        return new Object[][] {
                { "", "" },
                { null, "" },
                { "Alpha, Beta", "Alpha Beta"},
                { "Alpha\nBeta", "Alpha Beta"},
                { "Alpha\t,\n   Beta", "Alpha Beta"},
        };
    }
}