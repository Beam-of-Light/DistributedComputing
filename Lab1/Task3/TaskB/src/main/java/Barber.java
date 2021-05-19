import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Barber implements Runnable {
    private Semaphore chairSemaphore;
    private AtomicBoolean isSleeping = new AtomicBoolean();
    private boolean isDayOver = false;

    Barber(Semaphore chairSemaphore) {
        this.chairSemaphore = chairSemaphore;
    }

    @Override
    public void run() {
        while (!isDayOver) {
            if (isSleeping.get()) {
                try {
                    System.out.println("Zzz");
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } else {
                if (/*chairSemaphore.availablePermits() == 1 && */chairSemaphore.tryAcquire()) {
                    isSleeping.set(true);
                } else {
                    //System.out.println("Barber: ...working...");
                    //
                    try {
                        System.out.println("Barber: ...working...");
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public void wakeUp() {
        if (isSleeping.get()) {
            isSleeping.set(false);
            chairSemaphore.release();
            System.out.println("Barber: waked up");
        }
    }

    public boolean isSleeping() {
        return isSleeping.get();
    }

    public void setDayOver() {
        isDayOver = true;
    }
}
