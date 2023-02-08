package uk.ac.sanger.sprint.service.protocol;

import uk.ac.sanger.sprint.model.Printer;

import java.io.IOException;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;

/**
 * Adapter for copying request data to a file mounted locally
 * @author dr6
 */
public class VolumeProtocolAdapter implements PrintProtocolAdapter {
    public static final String TMP_EXT = "_TEMP";
    private final Printer printer;
    private final Supplier<Instant> instantSupplier;
    private final DateTimeFormatter timeFormat;

    public VolumeProtocolAdapter(Printer printer, DateTimeFormatter timeFormat, Supplier<Instant> instantSupplier) {
        this.printer = printer;
        this.timeFormat = timeFormat;
        this.instantSupplier = instantSupplier;
    }

    String newFilename() {
        return LocalDateTime.ofInstant(instantSupplier.get(), ZoneOffset.UTC).format(timeFormat);

    }

    @Override
    public void print(String printCode) throws IOException {
        String filename = newFilename();
        Path savePath = printer.getPath().resolve(filename + TMP_EXT);
        Files.write(savePath, singletonList(printCode));
        Path finalPath = printer.getPath().resolve(filename);
        Files.move(savePath, finalPath);
    }
}
