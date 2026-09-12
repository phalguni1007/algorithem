package algorithem;

import java.util.ArrayDeque;
import java.util.Deque;

public class LeakyBucket implements RateLimiter {
    private final int capacity;
    private final int leakRatePerSecond;
    private final Deque<Long> requestTimestamps = new ArrayDeque<>();
    private long lastLeakTimestamp;

    public LeakyBucket(int capacity, int leakRatePerSecond) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be greater than zero");
        }
        if (leakRatePerSecond <= 0) {
            throw new IllegalArgumentException("leakRatePerSecond must be greater than zero");
        }

        this.capacity = capacity;
        this.leakRatePerSecond = leakRatePerSecond;
        this.lastLeakTimestamp = System.currentTimeMillis();
    }

    @Override
    public synchronizedboolean allowRequest(int tokens) {
        if (tokens <= 0) {
            return true;
        }

        long now = System.currentTimeMillis();
        leak(now);

        if (requestTimestamps.size() + tokens > capacity) {
            return false;
        }

        for (int i = 0; i < tokens; i++) {
            requestTimestamps.addLast(now);
        }

        return true;
    }

    private void leak(long now) {
        long elapsedMillis = now - lastLeakTimestamp;
        if (elapsedMillis <= 0) {
            return;
        }

        long leakedRequests = (elapsedMillis * leakRatePerSecond) / 1000L;
        for (long i = 0; i < leakedRequests && !requestTimestamps.isEmpty(); i++) {
            requestTimestamps.pollFirst();
        }

        lastLeakTimestamp = now;
    }
}
