import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        Storage storage = new Storage(20);
        LinkedList<Item> truck = new LinkedList();
        Queue<Item> loaderBuffer = new ArrayDeque<>();

        Counter counter = new Counter(truck);
        Loader loader = new Loader(truck, loaderBuffer, counter);
        Thief thief = new Thief(storage, loaderBuffer, loader);

        Thread t1 = new Thread(counter);
        Thread t2 = new Thread(loader);
        Thread t3 = new Thread(thief);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nTotal: " + counter.getTotal());
    }
}
