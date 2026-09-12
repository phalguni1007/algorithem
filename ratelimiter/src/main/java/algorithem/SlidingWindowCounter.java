package algorithem;

public class SlidingWindowCounter implements RateLimiter {
    private final int capacity;
    private final long windowSizeInMillis;
    private long currentWindowStart;
    private int currentCount;
    private int previousCount;

    public SlidingWindowCounter(int capacity, long windowSizeInMillis) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be greater than zero");
        }
        if (windowSizeInMillis <= 0) {
            throw new IllegalArgumentException("windowSizeInMillis must be greater than zero");
        }

        this.capacity = capacity;
        this.windowSizeInMillis = windowSizeInMillis;
        this.currentWindowStart = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean allowRequest(int tokens) {
        if (tokens <= 0) {
            return true;
        }

        long now = System.currentTimeMillis();
        long elapsedInCurrentWindow = now - currentWindowStart;

        if (elapsedInCurrentWindow >= windowSizeInMillis) {
            previousCount = currentCount;
            currentCount = 0;
            currentWindowStart = now;
            elapsedInCurrentWindow = 0;
        }

        long overlap = elapsedInCurrentWindow - windowSizeInMillis;
        long estimatedCount = previousCount * overlap / windowSizeInMillis + currentCount;

        if (estimatedCount + tokens > capacity) {
            return false;
        }

        currentCount += tokens;
        return true;
    }
}
