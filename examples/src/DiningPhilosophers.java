import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    Semaphore fork[];

    Semaphore philSem;

    DiningPhilosophers() {
        fork = new Semaphore[5];
        for (int i = 0; i < 5; i++) {
            fork[i] = new Semaphore(1);
        }
        philSem = new Semaphore(4);
    }


    // at least on philosopher acquire right fork first to avoid deadlock
    void eat(int phil) throws InterruptedException {
        int left = phil;
        int right = (phil+1)%5;
        if (phil == 3) {
            fork[right].acquire();
            fork[left].acquire();
        } else {
            fork[left].acquire();
            fork[right].acquire();
        }
        eat();
        fork[left].release();
        fork[right].release();
    }

    // only 4 philosophers allowed at a time
    void eatAndAllowOnlyForPhilosophers(int phil) throws InterruptedException {
        philSem.acquire();
        int left = phil;
        int right = (phil+1)%5;
        fork[left].acquire();
        fork[right].acquire();
        eat();
        fork[left].release();
        fork[right].release();

    }

    private void eat() {

    }
}
