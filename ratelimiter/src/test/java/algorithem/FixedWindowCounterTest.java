package algorithem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FixedWindowCounterTest {

    @Test
    void blocksRequestsPastTheWindowLimit() {
        FixedWindowCounter limiter = new FixedWindowCounter(3, 1_000);

        assertTrue(limiter.allowRequest(1));
        assertTrue(limiter.allowRequest(1));
        assertTrue(limiter.allowRequest(1));
        assertFalse(limiter.allowRequest(1));
    }
}
