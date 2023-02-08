package uk.ac.sanger.sprint.service.language;

import uk.ac.sanger.sprint.model.KeyField;
import uk.ac.sanger.sprint.model.PrintRequest;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * An adapter for a csv file.
 * Requests are expected to include keyfields, whose values are inserted in the order given.
 * @author dr6
 */
public class CsvAdapter implements PrinterLanguageAdapter {
    private final String valueSeparator;

    public CsvAdapter(String valueSeparator) {
        this.valueSeparator = valueSeparator;
    }

    @Override
    public String transcribe(PrintRequest request, String jobId) {
        return request.getLayouts().stream()
                .map(ly -> this.transcribeKeyFields(ly.getKeyFields()))
                .collect(joining("\n"));
    }

    String sanitiseValue(String value) {
        if (value==null) {
            return "";
        }
        return value.replace(valueSeparator, " ").replaceAll("\\s+", " ");
    }

    String transcribeKeyFields(List<KeyField> keyFields) {
        return keyFields.stream()
                .map(kf -> sanitiseValue(kf.getValue()))
                .collect(joining(valueSeparator));
    }
}
