public class ReadWriteLock {
    int numWriter, numReader;

    ReadWriteLock() {
        numWriter = 0;
        numReader = 0;
    }

    synchronized void acquireReadLock() throws InterruptedException {
        while(numWriter == 1) {
            wait();
        }
        numReader++;
    }

    synchronized void releaseReadLock() {
        numReader--;
        if (numReader == 0) {
            notifyAll();
        }
    }

    synchronized void acquireWriteLock() throws InterruptedException {
        while(numWriter == 1 || numReader > 0) {
            wait();
        }
        numWriter++;
    }

    synchronized void releaseWriteLock() {
        numWriter--;
        notifyAll();
    }
}
