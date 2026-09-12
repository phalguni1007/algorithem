package src.main.java.algorithem;

import java.time.Instant;

/**
 * SlidingWindowLog
 */
public class SlidingWindowLog {
    private final int capacity;
    private Queue<Long> requestTimestamps;
    private long windowSizeInMillis;
    private long lastRequestTimestamp;

    public SlidingWindowLog(int capacity, long windowSizeInMillis) {

        if(capacity <= 0) {
            throw new IllegalArgumentException("capacity must be greater than zero");
        }

        if(windowSizeInMillis <= 0) {
            throw new IllegalArgumentException("windowSizeInMillis must be greater than zero");
        }

        this.capacity = capacity;
        this.requestTimestamps = new LinkedList<>();
        this.windowSizeInMillis = windowSizeInMillis;
        this.lastRequestTimestamp = 0;
    }

    @Override
    public synchronized boolean allowRequest(int tokens) {

        long now = Instant.now();
        long windowStart = now - windowSizeInMillis;
        // Implementation for allowing requests based on sliding window logic
        while(!requestTimestamps.isEmpty() && requestTimestamps.peek() < windowStart) {
            requestTimestamps.poll();
        }

        if(requestTimestamps.size() < capacity) {
            requestTimestamps.offer(now);
            return true;
        }

        return false;
    }
}