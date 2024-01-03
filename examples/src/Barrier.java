public class Barrier {

    // No of threads to wait for before barrier is broken
    int waitFor;
    int arrived;

    int escaped;

    Barrier(int num) {
        waitFor = num;
        arrived = 0;
        escaped = 0;
    }
    synchronized void await() throws InterruptedException {
        while(escaped > 0) {
            wait();
        }
        arrived++;
        if (arrived == waitFor) {
            escaped++;
            notifyAll();
            return;
        }
        while(arrived < waitFor) {
            wait();
        }
        if (escaped == waitFor) {
            escaped = 0;
            arrived = 0;
            notifyAll();
        }
    }
}
