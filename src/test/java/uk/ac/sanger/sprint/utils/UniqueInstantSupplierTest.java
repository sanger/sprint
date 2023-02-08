package uk.ac.sanger.sprint.utils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.*;
import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Tests {@link UniqueInstantSupplier}
 */
public class UniqueInstantSupplierTest {
    @Test(dataProvider = "clocks")
    public void testNewInstant(Clock clock) {
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
            fail(a+" should be before "+b);
        }
    }

    private static void assertNotBefore(Instant a, Instant b) {
        if (a.isBefore(b)) {
            fail(a+" should be after or equal to "+b);
        }
    }

    @DataProvider(name="clocks")
    public Object[][] clocks() {
        return new Object[][]{ {Clock.systemUTC()}, {Clock.fixed(Instant.now(), ZoneOffset.UTC)} };
    }

}