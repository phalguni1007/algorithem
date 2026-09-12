/**
 * RateLimiter
 */
public interface RateLimiter {

    public boolean allowRequest(int tokens);
}