import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Counter implements Runnable {
    private LinkedList<Item> truck;
    private AtomicBoolean isLoaderFinished;
    private int currentItem;
    private int total;

    Counter(LinkedList<Item> truck) {
        this.currentItem = 0;
        this.total = 0;
        this.truck = truck;
        this.isLoaderFinished = new AtomicBoolean(false);
    }

    public void setLoaderFinished() {
        isLoaderFinished.set(true);
    }

    public int getTotal() {
        return total;
    }

    @Override
    public void run() {
        while (!isLoaderFinished.get()) {
            if (currentItem < truck.size()) {
                total += truck.get(currentItem).getPrice();
                ++currentItem;
            }
        }
        for (; currentItem < truck.size(); ++currentItem) {
            total += truck.get(currentItem).getPrice();
        }
    }
}
