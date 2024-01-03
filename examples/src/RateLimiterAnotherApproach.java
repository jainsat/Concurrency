public class RateLimiterAnotherApproach {
    int numToken;
    long lastWritten;

    int maxTokens;

    RateLimiterAnotherApproach(int maxTokens) {
        this.maxTokens = maxTokens;
        numToken = 0;
        lastWritten = System.currentTimeMillis();
    }

    synchronized void getToken() throws InterruptedException {
        long cur = System.currentTimeMillis();
        int tokens =(int)(cur - lastWritten)/1000;
        numToken = Math.min(maxTokens, numToken+tokens);
        if (numToken == 0) {
            Thread.sleep(1000);
        } else {
            numToken--;
        }
        lastWritten = cur;
    }
}
