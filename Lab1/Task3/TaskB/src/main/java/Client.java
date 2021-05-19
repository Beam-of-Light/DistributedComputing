import java.util.concurrent.Semaphore;

public class Client implements Runnable {
    private final int id;
    private Barber barber;
    private Semaphore chairSemaphore;

    Client(int id, Barber barber, Semaphore chairSemaphore) {
        this.id = id;
        this.barber = barber;
        this.chairSemaphore = chairSemaphore;
    }

    @Override
    public void run() {
        if (barber.isSleeping()) {
            barber.wakeUp();
        }
        try {
            chairSemaphore.acquire();
            System.out.println("Client " + id + ": sat in the chair");
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("Client " + id + ": went home");
            chairSemaphore.release();
            return;
        }
    }
}
