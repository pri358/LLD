import java.util.*;
/*
 Requirements: 

 Rate Limiting Algorithms: 
 1. Fixed Window, Fixed Bucket, Sliding Window 
 1. Rate Limiting -> boolean allowRequest, updateConfiguration
 */

public class ratelimiterrunner {

    public static void main(String[] args)
    {

    }
    
}

enum RateLimiterType
{
    TOKEN_BUCKET,
    FIXED_WINDOW
}

interface IRateLimiter
{
    boolean allowRequest(String customerId);
    boolean updateConfig(int maxRequests); 
}

class FixedWindowRateLimiter implements IRateLimiter
{
    int maxRequests; 
    int windowSizeInMilis;
    int globalStartTime;
    int globalRequests;
    Map<String, Integer> requestCounts;
    Map<String, Integer> windowStartTimes; 

    public boolean allowRequest(String customerId)
    {
        return true;
    }

    public boolean updateConfig(int maxRequests)
    {
        this.maxRequests = maxRequests;
        return true;
    }

}

class SlidingWindowRateLimiter implements IRateLimiter
{
    int maxRequests;
    int windowSizeInMilis;
    int globalStartTime;
    int globalRequests;
    Map<String, Queue<Long>> requestTimestamps;

    public boolean allowRequest(String customerId)
    {
        return true;
    }

    public boolean updateConfig(int maxRequests)
    {
        this.maxRequests = maxRequests;
        return true;
    }
}