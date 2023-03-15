package uk.ac.sanger.sprint.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Utility for getting a unique time at or closely after the actual current time.
 * @author dr6
 */
@Component
public class UniqueInstantSupplier implements Supplier<Instant> {
    private final AtomicReference<Instant> lastInstant = new AtomicReference<>();
    private final Clock clock;

    /**
     * Creates a new unique timestamp supplier
     * @param clock the clock to use for the time
     */
    @Autowired
    public UniqueInstantSupplier(Clock clock) {
        this.clock = requireNonNull(clock, "clock is null");
    }

    /**
     * Gets the unique time
     * @return A new instant approximately now but definitely after previous calls
     */
    @Override
    public Instant get() {
        Instant now = clock.instant();
        while (true) {
            Instant last = lastInstant.get();
            if (last!=null && !now.isAfter(last)) {
                now = last.plusNanos(1);
            }
            if (lastInstant.compareAndSet(last, now)) {
                return now;
            }
        }
    }
}
