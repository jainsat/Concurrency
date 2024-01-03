import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// You are designing a library of superheroes for a video game that your fellow developers will consume. Your library
// should always create a single instance of any of the superheroes and return the same instance to all the requesting consumers.
// Say, you start with the class Superman. Your task is to make sure that other developers using your class can never
// instantiate multiple copies of superman. After all, there is only one superman!
public class CustomSingleTon {

    private static volatile CustomSingleTon instance;
    private CustomSingleTon() {

    }

    public static CustomSingleTon getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (CustomSingleTon.class) {
            if (instance == null) {
                instance = new CustomSingleTon();
            }
        }

        return instance;
    }


}
