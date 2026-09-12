/**
 * TokenBucket
 */
public class TokenBucket implements RateLimiter{

    private long capacity;
    private long tokens;
    private long refillRatePerSecond;
    private long lastRefillTimestamp;

    public TokenBucket(long capacity, long refillRatePerSecond) {
     this.capacity = capacity;
     this.tokens = capacity;
     this.refillRatePerSecond = refillRatePerSecond;
     this.lastRefillTimestamp = Instance.now();
    }

    @Override 
    public synchronized boolean allowRequest(int tokens){
        refill();

        if(this.tokens >= tokens){
            this.tokens -= tokens;
            return true;
        }
        return false;
    }   

    private void refill(){
        long now= Instance.now();
        long elapsedTime=now-this.lastRefillTimestamp;
        long tokensToAdd= (elapsedTime * refillRatePerSecond) / 1000;
        if(tokensToAdd>0){
            this.tokens = Math.min(capacity, this.tokens + tokensToAdd);
        }
        this.lastRefillTimestamp = now;
    }
}