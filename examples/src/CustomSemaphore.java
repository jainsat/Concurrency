// Java does provide its own implementation of Semaphore, however, Java's semaphore is initialized with an initial number
// of permits, rather than the maximum possible permits and the developer is expected to take care of always releasing the
// intended number of maximum permits.
//
//Briefly, a semaphore is a construct that allows some threads to access a fixed set of resources in parallel.
// Always think of a semaphore as having a fixed number of permits to give out. Once all the permits are given out,
// requesting threads, need to wait for a permit to be returned before proceeding forward.
//
//Your task is to implement a semaphore which takes in its constructor the maximum number of permits allowed and is also
// initialized with the same number of permits.
public class CustomSemaphore {
    // both acquire and release should wait if their respective conditiosn are not satisfied.

    int maxPermits, usedPermits;

    CustomSemaphore(int maxPermits) {
        this.maxPermits = maxPermits;
        usedPermits = 0;
    }

    synchronized public void acquire() throws InterruptedException {
        while(usedPermits == maxPermits) {
            wait();
        }
        usedPermits++;
        notifyAll();
    }

    synchronized  public void release() throws InterruptedException {
        while(usedPermits == 0) {
            wait();
        }
        usedPermits--;
        notifyAll();
    }
}
