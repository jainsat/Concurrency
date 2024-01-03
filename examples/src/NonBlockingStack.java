import java.util.concurrent.atomic.AtomicReference;

public class NonBlockingStack<T> {

    AtomicReference<Node<T>> top;
    void push(T data) {
        Node<T> newTop = new Node<>(data);
        Node<T> oldTop = top.get();
        while(!top.compareAndSet(oldTop, newTop)) {
            oldTop = top.get();
            newTop.next =oldTop;
        }
    }

    T pop() {
        Node<T> newTop = top.get() != null ? top.get().next : null;
        Node<T> oldTop= top.get();
        while(!top.compareAndSet(oldTop, newTop)) {
            oldTop = top.get();
            newTop = oldTop != null ? oldTop.next : null;
        }
        return oldTop != null ? oldTop.val : null;
    }

    static class Node<T> {
        T val;
        Node<T> next;

        Node(T val) {
            this.val = val;
            next = null;
        }
    }


}
