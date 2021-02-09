package TaskB;

public class Semaphore {
    private int semaphore = 1;

    public synchronized boolean acquire() {
        if (semaphore == 0) {
            return false;
        } else {
            semaphore = 0;
        }
        return true;
    }

    public synchronized void release() {
        semaphore = 1;
    }
}
