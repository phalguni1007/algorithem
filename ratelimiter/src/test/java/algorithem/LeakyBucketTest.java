package algorithem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LeakyBucketTest {

    @Test
    void rejectsWhenCapacityIsExceeded() {
        LeakyBucket bucket = new LeakyBucket(2, 1);

        assertTrue(bucket.allowRequest(1));
        assertTrue(bucket.allowRequest(1));
        assertFalse(bucket.allowRequest(1));
    }

    @Test
    void allowsRequestAfterLeakOccurs() throws InterruptedException {
        LeakyBucket bucket = new LeakyBucket(1, 1);

        assertTrue(bucket.allowRequest(1));
        assertFalse(bucket.allowRequest(1));

        Thread.sleep(1100);
        assertTrue(bucket.allowRequest(1));
    }
}
