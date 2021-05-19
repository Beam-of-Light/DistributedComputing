import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Mediator {
    public static Random random = new Random();

    private LinkedList<Integer> smokingItems;
    public AtomicInteger smokingItemsID;


    Mediator() {
        smokingItems = new LinkedList<>(Arrays.asList(0, 1, 2));
        smokingItemsID = new AtomicInteger(-1);
    }

    public void simulate() {
        Semaphore semaphore = new Semaphore(1, true);

        Smoker smoker1 = new Smoker(SmokingItem.TOBACCO, semaphore, this, "tobacco");
        Smoker smoker2 = new Smoker(SmokingItem.PAPER, semaphore, this, "paper");
        Smoker smoker3 = new Smoker(SmokingItem.MATCHES, semaphore, this, "matches");

        new Thread(smoker1).start();
        new Thread(smoker2).start();
        new Thread(smoker3).start();

        while (true) {
            if (smokingItemsID.get() == -1) {
                int nextItems = random.nextInt(3);
                switch (nextItems) {
                    case 0 -> System.out.println("Mediator:\tpaper + matches");
                    case 1 -> System.out.println("Mediator:\ttobacco + matches");
                    case 2 -> System.out.println("Mediator:\ttobacco + paper");
                }
                smokingItemsID.set(nextItems);
            }
        }
    }

    public static void main(String[] args) {
        Mediator mediator = new Mediator();
        mediator.simulate();
    }
}
