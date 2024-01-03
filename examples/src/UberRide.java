import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

// Imagine at the end of a political conference, republicans and democrats are trying to leave the venue and ordering
// Uber rides at the same time. However, to make sure no fight breaks out in an Uber ride, the software developers at
// Uber come up with an algorithm whereby either an Uber ride can have all democrats or republicans or two Democrats and
// two Republicans. All other combinations can result in a fist-fight.
public class UberRide {
    int dc, rc;
    CyclicBarrier barrier;

    Semaphore dcSem, rcSem, mutex;

    UberRide() {
        dc = rc = 0;
        barrier = new CyclicBarrier(4);
        dcSem = new Semaphore(0);
        rcSem = new Semaphore(0);
        mutex = new Semaphore(1);
    }
    void rideForDemocrat() throws InterruptedException, BrokenBarrierException {
        boolean leader = false;
        mutex.acquire();
        dc++;
        if (dc == 4) {
            dcSem.release(3);
            dc = 0;
            leader = true;
            //mutex.release();
        } else if (dc == 2 && rc >= 2) {
            dcSem.release(1);
            rcSem.release(2);
            dc = 0;
            rc -= 2;
            leader = true;
            //mutex.release();
        } else {
            // wait
            mutex.release();
            dcSem.acquire();
        }
        seated();
        // leader keeps the mutex with itself and wait for other threads to wake up and drive.
        barrier.await();
        if (leader) {
            drive();
            mutex.release();
        }
    }

    void rideForRepublic() throws InterruptedException, BrokenBarrierException {
        boolean leader = false;
        mutex.acquire();
        rc++;
        if (rc == 4) {
            rcSem.release(3);
            rc = 0;
            leader = true;
            //mutex.release();
        } else if (rc == 2 && dc >= 2) {
            rcSem.release(1);
            dcSem.release(2);
            rc = 0;
            dc -= 2;
            leader = true;
            //mutex.release();
        } else {
            // wait
            mutex.release();
            rcSem.acquire();
        }
        seated();
        barrier.await();
        if (leader) {
            drive();
            mutex.release();
        }

    }

    void drive() {

    }

    void seated() {

    }
}
