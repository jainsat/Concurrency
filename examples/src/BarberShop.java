import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// A barbershop consists of a waiting room with n chairs, and a barber chair for giving haircuts. If there are no
// customers to be served, the barber goes to sleep. If a customer enters the barbershop and all chairs are occupied,
// then the customer leaves the shop. If the barber is busy, but chairs are available, then the customer sits in one
// of the free chairs. If the barber is asleep, the customer wakes up the barber. Write a program to coordinate the
// interaction between the barber and the customers.
public class BarberShop {

    int chairsInWaitingRoom;
    int usedChairs;

    Lock lock;

    Semaphore customerArrived, callCustomer, waitForCustomerToLeave, haircutDone;

    BarberShop(int n) {
        chairsInWaitingRoom = n;
        usedChairs = 0;
        customerArrived = new Semaphore(0);
        callCustomer = new Semaphore(0);
        waitForCustomerToLeave = new Semaphore(0);
        haircutDone = new Semaphore(0);
        lock  = new ReentrantLock();
    }

    void start() throws InterruptedException {
        while(true) {
            customerArrived.acquire();
            callCustomer.release();
            doHaircut();
            haircutDone.release();
            waitForCustomerToLeave.acquire();
        }
    }

     void getHairCut() throws InterruptedException {
        lock.lock();
        if (usedChairs == chairsInWaitingRoom) {
            return;
        }
        usedChairs++;
        lock.unlock();
        customerArrived.release();
        callCustomer.acquire();
        usedChairs--;
        haircutDone.acquire();
        waitForCustomerToLeave.release();
    }

    void doHaircut() {
        Random random = new Random();
        try {
            int time = 2000 + random.nextInt()*(5000);
            Thread.sleep(time);
        } catch(InterruptedException e) {

        }
    }
}
