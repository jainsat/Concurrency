import java.util.concurrent.Semaphore;

public class CustomExecutor {

    public static void main(String args[]) throws Exception {
        CustomExecutor customExecutor = new CustomExecutor();
        Callback cb = new Callback() {

            @Override
            public void done() {
                System.out.println("done");
            }
        };
        //customExecutor.asynchronousExecution(cb);
        System.out.println("waiting");
        CustomExecutor customExecutor1 = new CustomExecutorExtended();
        customExecutor1.asynchronousExecution(cb);
        System.out.println("waiting");

    }
    public interface Callback {

        public void done();
    }
    public void asynchronousExecution(Callback callback) throws Exception {

        Thread t = new Thread(() -> {
            // Do some useful work
            try {
                // Simulate useful work by sleeping for 5 seconds
                //System.out.println("here");
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
            }
            callback.done();
        });
        t.start();
    }
    static class CustomExecutorExtended extends CustomExecutor {
        @Override
        public void asynchronousExecution(Callback callback) throws Exception {
            Semaphore sem = new Semaphore(0);
            //boolean done = false;
            Callback newC = new Callback() {

                @Override
                public void done() {
                    callback.done();
                    sem.release();
                }
            };
            super.asynchronousExecution(newC);
            sem.acquire();
        }

    }
}
