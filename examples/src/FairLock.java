import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

// Design a lock which has FIFO preference.
//lock
//we add the locking task in a queue and block on the task future
//
//unlock
//remove task from the queue, if invoked by same thread as task, or else throw exception
//
//background thread
//peeks first task from queue and signal lock method, and then wait for unlock signal.
public class FairLock {

    Queue<LockTask> queue;

    Semaphore  unlockSignal, mutex, newLockRequest;

    ExecutorService executorService;

    FairLock() {
        queue = new LinkedList<>();
        unlockSignal = new Semaphore(0);
        newLockRequest = new Semaphore(0);
        mutex = new Semaphore(1);
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new QueueTaskExecutor());

    }
    void lock() throws InterruptedException {
        LockTask task = new LockTask(Thread.currentThread());
        mutex.acquire();
        queue.add(task);
        mutex.release();
        newLockRequest.release();
        // wait for task to acquire the lock
        task.get();
    }

    void unlock() {
        if (queue.isEmpty()) {
            throw new IllegalMonitorStateException();
        }
        if (!Thread.currentThread().equals(queue.peek().invokingThread) || !queue.peek().lockGranted) {
            throw new IllegalMonitorStateException();
        }
        queue.remove();
        unlockSignal.release();
    }

    class QueueTaskExecutor implements Runnable {

        @Override
        public void run() {
            while(true) {
                try {
                    newLockRequest.acquire();
                    LockTask task = queue.peek();
                    task.complete();
                    unlockSignal.acquire();

                } catch(Exception e) {

                }
            }

        }
    }

    class LockTask {
        Thread invokingThread;
        Semaphore acquireLock;

        volatile boolean lockGranted;

        LockTask(Thread invokingThread) {
            this.invokingThread = invokingThread;
            acquireLock = new Semaphore(0);
            lockGranted = false;
        }

        void get() throws InterruptedException {
            acquireLock.acquire();
        }
        void complete() {
            acquireLock.release();
            lockGranted = true;
        }

    }
}

