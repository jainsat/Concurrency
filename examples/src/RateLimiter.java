// implement a RateLimiter using token bucket filter algo, in which tokens are added every second to the
// bucket. Bucket can hold only a MAX amount of tokens.
public class RateLimiter {
    int numToken, max;

    Thread addTokenThread;

    RateLimiter(int max) {
        this.max = max;
        numToken = 0;
        // it is usually not a good idea to start a thread in constructor because child thread could
        // try accessing out class's "this" object, which is not fully initialized yet.
        addTokenThread = new Thread(new AddTokenTask());
        addTokenThread.setDaemon(true);
    }

    public void start() {
        addTokenThread.start();
    }

    synchronized void getToken() throws InterruptedException {
        while(numToken == 0) {
            wait();
        }
        numToken--;
    }

    class AddTokenTask implements Runnable {
        @Override
        public void run() {
            while(true) {
                // since this task uses same object as getToken() for synchronization, it is possible that it will miss
                // to increment tokens due to race with another thread. We can use timestamp based approach
                // (diff of timestamp), just to be safe.
                synchronized (RateLimiter.this) {
                    numToken = Math.min(max, numToken+1);
                    notify();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }

        }
    }
}
