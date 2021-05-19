import java.util.Random;
import java.util.concurrent.Semaphore;

public class BarberShop {
    private static Random random = new Random();
    private Semaphore semaphore = new Semaphore(1, true);

    Semaphore getSemaphore() {
        return semaphore;
    }

    public static void simulateDay() {
        BarberShop barberShop = new BarberShop();
        Barber barber = new Barber(barberShop.getSemaphore());
        new Thread(barber).start();
        for (int i = 0; i < 10; ++i) {
            try {
                int sleepTime = (random.nextInt(5) + 1) * 1000;
                System.out.println("Sleep time: " + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            new Thread(new Client(i, barber, barberShop.getSemaphore())).start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        barber.setDayOver();
    }

    public static void main(String[] args) {
        simulateDay();
    }
}
