import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBlockingQueueUsingSemaphore<T> {
    int cap;
    AtomicInteger cursize;

    T arr[];

    int rear;

    int front;

    Semaphore producer;

    Semaphore consumer;

    Semaphore enQueuelock;

    Semaphore dequeueLock;


    public BoundedBlockingQueueUsingSemaphore(int cap) {
        this.cap = cap;
        rear = 0;
        front = 0;
        arr = (T[])(new Object[cap]);
        cursize = new AtomicInteger(0);
        producer = new Semaphore(cap);
        consumer = new Semaphore(0);
        enQueuelock = new Semaphore(1);
        dequeueLock = new Semaphore(1);
    }

    void enqueue(T item) throws InterruptedException {
        producer.acquire();
        enQueuelock.acquire();
        arr[rear] = item;
        rear = (rear+1)%cap;
        enQueuelock.release();
        cursize.incrementAndGet();
        consumer.release();
    }

    T dequeue() throws InterruptedException {
        consumer.acquire();
        T item = null;
        dequeueLock.acquire();
        item = arr[front];
        front = (front+1)%cap;
        dequeueLock.release();
        cursize.decrementAndGet();
        producer.release();
        return item;
    }
}
