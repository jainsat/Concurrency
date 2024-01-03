// three people can use the bathrrooms concurrently.
// male-female not allowed together.
// either all three are used by male, or female.
public class UnisexBathroom {

    int males, females;

    UnisexBathroom() {
        males = females = 0;
    }

    synchronized void acquireForMale() throws InterruptedException {
        while(females > 0 || males == 3) {
            wait();
        }
        males++;
    }

    synchronized void acquireForFemale() throws InterruptedException {
        while(males > 0 || females == 3) {
            wait();
        }
        females++;
    }

    synchronized void releaseForMale() {
        males--;
        notifyAll();
    }

    synchronized  void releaseForFemale() {
        females--;
        notifyAll();
    }

    void use() {
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {

        }
    }
    void useBathRoomForMale() throws InterruptedException {
        acquireForMale();
        use();
        releaseForMale();
    }

    void useBathRoomForFemale() throws InterruptedException {
        acquireForFemale();
        use();
        releaseForFemale();
    }
}

