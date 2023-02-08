package uk.ac.sanger.sprint.model;

import java.util.Objects;

/**
 * A field associated with a key (string) rather than a position
 * @author dr6
 */
public class KeyField {
    private String key;
    private String value;

    public KeyField() {}

    public KeyField(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("(%s=%s)", key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyField that = (KeyField) o;
        return (Objects.equals(this.key, that.key)
                && Objects.equals(this.value, that.value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
