package uk.ac.sanger.sprint.utils;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author dr6
 */
public class BasicUtils {
    /**
     * Returns a string representation of the given object.
     * If it is a string it will be in quote marks and unprintable
     * characters will be shown as unicode insertions.
     * @param o object to represent
     * @return a string
     */
    public static String repr(Object o) {
        if (o==null) {
            return "null";
        }
        if (o instanceof CharSequence) {
            return StringRepr.repr((CharSequence) o);
        }
        if (o instanceof Character) {
            return StringRepr.repr((char) o);
        }
        return o.toString();
    }

    public static <E> List<E> nullToEmpty(List<E> list) {
        return (list==null ? Collections.emptyList() : list);
    }

    public static String nullToEmpty(String string) {
        return (string==null ? "" : string);
    }

    /**
     * Collector to a map where the values are the input objects
     * @param keyMapper a mapping function to produce keys
     * @param mapFactory a supplier providing a new empty {@code Map}
     *                   into which the results will be inserted
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <M> the type of the resulting {@code Map}
     * @return a {@code Collector} which collects elements into a {@code Map}
     *             whose keys are the result of applying a key mapping function to the input
     *             elements, and whose values are input elements
     */
    public static <T, K, M extends Map<K, T>> Collector<T, ?, M> inMap(Function<? super T, ? extends K> keyMapper,
                                                                       Supplier<M> mapFactory) {
        return Collectors.toMap(keyMapper, Function.identity(), illegalStateMerge(), mapFactory);
    }

    /**
     * A binary operator that throws an illegal state exception. Used as the merge function for collecting
     * to a map whose incoming keys are expected to be unique.
     * @param <U> the type of value
     * @return a binary operator that throws an {@link IllegalStateException}
     */
    public static <U> BinaryOperator<U> illegalStateMerge() {
        return (a, b) -> {throw new IllegalStateException("Duplicate keys found in map.");};
    }
}
