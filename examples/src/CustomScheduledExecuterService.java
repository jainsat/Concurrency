import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

public class CustomScheduledExecuterService {

    Thread thread;
    PriorityQueue<Command> pq;

    CustomScheduledExecuterService() {
        pq = new PriorityQueue<>((a,b) -> Long.compare(a.execTime, b.execTime));
        thread = new Thread(new SchedulerTask());
        thread.setDaemon(true);
    }

    public void start() {
        thread.start();
    }
    synchronized void schedule(Runnable cmd, long delay) {
        pq.add(new Command(cmd, delay+System.currentTimeMillis()));
        notify();
    }

    // if priority queue is empty, it should wait for signal from schedule
    // get item from pq. If item is in future wrt cur time, then it should go to sleep for diff time
    // it is possible that a task that is to be scheduled before the first task in pq. In this case, it is necessary
    // to aware scheduler task.
    class SchedulerTask implements Runnable {
        @Override
        public void run() {
            while(true) {
                synchronized (CustomScheduledExecuterService.this) {
                    try {
                        while(pq.size() == 0) {
                            wait();
                        }
                        long cur = System.currentTimeMillis();
                        long diff = cur - pq.peek().execTime;
                        while(diff < 0) {
                            wait(-diff);
                            // diff is re-calculated in case of signal from schedule or spurious wake-ups
                            diff = System.currentTimeMillis() - pq.peek().execTime;
                        }
                        pq.peek().runnable.run();
                        pq.remove();
                    } catch(InterruptedException e) {

                    }

                }
            }

        }
    }

    class Command {
        Runnable runnable;
        long execTime;
        Command(Runnable runnable, long execTime) {
            this.runnable = runnable;
            this.execTime = execTime;
        }

    }


}
