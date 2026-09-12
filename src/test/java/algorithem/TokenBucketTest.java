package algorithem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenBucketTest {

    @Test
    void allowsRequestsWhenTokensAreAvailable() {
        TokenBucket bucket = new TokenBucket(5, 2);

        assertTrue(bucket.allowRequest(1));
        assertTrue(bucket.allowRequest(1));
        assertTrue(bucket.allowRequest(1));
        assertTrue(bucket.allowRequest(1));
        assertTrue(bucket.allowRequest(1));
        assertFalse(bucket.allowRequest(1));
    }
}
