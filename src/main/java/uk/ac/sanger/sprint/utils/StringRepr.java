package uk.ac.sanger.sprint.utils;

/**
 * Class providing the functionality for {@link BasicUtils#repr}
 * @author dr6
 */
class StringRepr {
    private static final char SIMPLE_CHAR_FIRST = ' '; // 32
    private static final char SIMPLE_CHAR_LAST = '~'; // 126

    private StringRepr() {}

    private static String repr_char(char ch, char quote) {
        if (ch==quote) {
            return (quote == '"' ? "\\\"" : "\\'");
        }
        switch (ch) {
            case '\t': return "\\t";
            case '\n': return "\\n";
            case '\f': return "\\f";
            case '\r': return "\\r";
            case '\\': return "\\\\";
        }
        // Don't use \0 because it produces misleading output if followed by digits
        if (ch >= SIMPLE_CHAR_FIRST && ch <= SIMPLE_CHAR_LAST) {
            return null;
        }
        return String.format("\\u%04X", (int) ch);
    }

    public static String repr(CharSequence source) {
        if (source == null) {
            return "null";
        }
        int limit = source.length();
        int start = 0;
        String v = null;

        // Usual case: no weird characters.
        while (start < limit) {
            v = repr_char(source.charAt(start), '"');
            if (v!=null) {
                break;
            }
            ++start;
        }
        if (v==null) {
            return "\"" + source + "\"";
        }

        // Unusual case: switch to a stringbuilder
        StringBuilder sb = new StringBuilder(Math.max(16, limit+2+v.length()));
        sb.append('"');
        sb.append(source, 0, start);
        sb.append(v);
        int written = start+1;
        for (int i = written; i < limit; ++i) {
            v = repr_char(source.charAt(i), '"');
            if (v!=null) {
                if (i > written) {
                    sb.append(source, written, i);
                }
                sb.append(v);
                written = i+1;
            }
        }
        if (written < limit) {
            sb.append(source, written, limit);
        }
        sb.append('"');
        return sb.toString();
    }

    public static String repr(char ch) {
        String v = repr_char(ch, '\'');
        if (v==null) {
            return "'"+ch+"'";
        } else {
            return '\''+v+'\'';
        }
    }
}
