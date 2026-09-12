package algorithem;

public class FixedWindowCounter implements RateLimiter {
    private final long capacity;
    private final long windowSizeInMillis;
    private long currentCount;
    private long windowStartTimestamp;

    public FixedWindowCounter(long capacity, long windowSizeInMillis) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be greater than zero");
        }
        if (windowSizeInMillis <= 0) {
            throw new IllegalArgumentException("windowSizeInMillis must be greater than zero");
        }

        this.capacity = capacity;
        this.windowSizeInMillis = windowSizeInMillis;
        this.windowStartTimestamp = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean allowRequest(int tokens) {
        if (tokens <= 0) {
            return true;
        }

        long now = System.currentTimeMillis();
        if (now - this.windowStartTimestamp >= this.windowSizeInMillis) {
            this.currentCount = 0;
            this.windowStartTimestamp = now;
        }

        if (this.currentCount + tokens <= this.capacity) {
            this.currentCount += tokens;
            return true;
        }

        return false;
    }
}