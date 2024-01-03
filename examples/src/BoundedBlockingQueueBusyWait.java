import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBlockingQueueBusyWait<T> {
    int cap;
    int cursize;

    T arr[];

    int rear;

    int front;

    Lock lock;

    public BoundedBlockingQueueBusyWait(int cap) {
        this.cap = cap;
        rear = 0;
        front = 0;
        arr = (T[])(new Object[cap]);
        cursize = 0;
        lock = new ReentrantLock();
    }
    void enqueue(T item) throws InterruptedException {
        // first acquire the lock, so that value of cur size wouldn't change.
        // Then compare it with cap. If queue is full, then unlock, so that
        // consumer thread could acquire it and change its value. Then grab
        // the lock again to make sure nobody changes it value while reading
        lock.lock();
        while (cursize == cap) {
            lock.unlock();
            lock.lock();
        }
        cursize++;
        arr[rear] = item;
        rear = (rear+1)%cap;
        this.notifyAll();
    }

      T dequeue() throws InterruptedException {
        lock.lock();
        while(cursize == 0) {
            lock.unlock();
            lock.lock();
        }
        cursize--;
        T item = arr[front];
        front = (front+1)%cap;
        this.notifyAll();
        return item;
    }
}
