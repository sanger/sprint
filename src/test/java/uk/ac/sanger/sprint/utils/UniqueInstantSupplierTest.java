package uk.ac.sanger.sprint.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests {@link UniqueInstantSupplier}
 */
public class UniqueInstantSupplierTest {
    @ParameterizedTest
    @ValueSource(booleans={false,true})
    public void testNewInstant(boolean fixedClock) {
        final Clock clock = (fixedClock ? Clock.fixed(Instant.now(), ZoneOffset.UTC) : Clock.systemUTC());
        final Instant start = clock.instant();
        UniqueInstantSupplier uts = new UniqueInstantSupplier(clock);
        Instant[] instants = IntStream.range(0,3)
                .mapToObj(x -> uts.get())
                .toArray(Instant[]::new);
        Instant end = clock.instant();
        if (start.equals(end)) {
            assertEquals(instants[0], start);
        } else {
            assertNotBefore(instants[0], start);
            assertNotBefore(end.plusNanos(instants.length), instants[instants.length-1]);
        }
        for (int i = 1; i < instants.length; ++i) {
            assertBefore(instants[i-1], instants[i]);
            assertBefore(instants[i], instants[i-1].plusSeconds(1));
        }
    }

    private static void assertBefore(Instant a, Instant b) {
        if (!a.isBefore(b)) {
            throw new AssertionError(a+" should be before "+b);
        }
    }

    private static void assertNotBefore(Instant a, Instant b) {
        if (a.isBefore(b)) {
            throw new AssertionError(a+" should be after or equal to "+b);
        }
    }
}
