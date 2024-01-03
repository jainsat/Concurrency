/**
 * Classical synchronization problem involving a limited size buffer which can have items added to it or removed from it
 * by different producer and consumer threads. This problem is known by different names: consumer producer problem,
 * bounded buffer problem or blocking queue problem.
 */
public class BoundedBlockingQueueUsingMonitor<T> {
    int cap;
    int cursize;

    T arr[];

    int rear;

    int front;

    public BoundedBlockingQueueUsingMonitor(int cap) {
        this.cap = cap;
        rear = 0;
        front = 0;
        arr = (T[])(new Object[cap]);
        cursize = 0;
    }
    synchronized  void enqueue(T item) throws InterruptedException {
        while (cursize == cap) {
            this.wait();
        }
        cursize++;
        arr[rear] = item;
        rear = (rear+1)%cap;
        this.notifyAll();
    }

    synchronized  T dequeue() throws InterruptedException {
        while(cursize == 0) {
            this.wait();
        }
        cursize--;
        T item = arr[front];
        front = (front+1)%cap;
        this.notifyAll();
        return item;
    }
}
