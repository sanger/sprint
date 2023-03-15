package uk.ac.sanger.sprint.service.protocol;

import org.junit.jupiter.api.*;
import org.springframework.util.FileSystemUtils;
import uk.ac.sanger.sprint.model.Printer;

import java.io.IOException;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** Test {@link VolumeProtocolAdapter} */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VolumeProtocolAdapterTest {
    String expectedFilename;
    Path printerPath;
    VolumeProtocolAdapter adapter;

    @BeforeAll
    void setup() throws IOException {
        Printer printer = mock(Printer.class);
        printerPath = Paths.get("volumeProtocolAdapterTestDir");
        when(printer.getPath()).thenReturn(printerPath);
        DateTimeFormatter timeFormat = PrintProtocolAdapterFactoryImplementation.makeDateTimeFormatter();
        Instant instant = LocalDateTime.of(2022, 1, 5, 12, 0, 0, 987654321).toInstant(ZoneOffset.UTC);
        expectedFilename = "2022-01-05T12:00:00.987654321.csv";
        adapter = new VolumeProtocolAdapter(printer, timeFormat, () -> instant);
        Files.createDirectory(printerPath);
    }

    @AfterAll
    public void tearDown() throws IOException {
        FileSystemUtils.deleteRecursively(printerPath);
    }

    @Test
    public void testNewFilename() {
        assertEquals(expectedFilename, adapter.newFilename());
    }

    @Test
    public void testPrint() throws IOException {
        adapter.print("A,B,C,,D\nA,B,,C,D");
        Path path = printerPath.resolve(expectedFilename);
        assertTrue(Files.isRegularFile(path));
        List<String> lines = Files.readAllLines(path);
        assertEquals(lines.size(), 2);
        assertEquals(lines.get(0), "A,B,C,,D");
        assertEquals(lines.get(1), "A,B,,C,D");
    }
}