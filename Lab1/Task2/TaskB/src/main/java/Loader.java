import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Loader implements Runnable {
    private Queue<Item> loaderBuffer;
    private LinkedList<Item> truck;
    private AtomicBoolean isStorageEmpty;
    private Counter counter;

    Loader(LinkedList<Item> truck, Queue<Item> loaderBuffer, Counter counter) {
        this.truck = truck;
        this.counter = counter;
        this.loaderBuffer = loaderBuffer;
        this.isStorageEmpty = new AtomicBoolean(false);
    }

    public void setStorageEmpty() {
        isStorageEmpty.set(true);
    }

    @Override
    public void run() {
        while (!isStorageEmpty.get()) {
            synchronized(loaderBuffer) {
                if (!loaderBuffer.isEmpty()) {
                    truck.add(loaderBuffer.remove());
                }
            }
        }
        // We don't need to sync on buffer
        while (!loaderBuffer.isEmpty()) {
            truck.add(loaderBuffer.remove());
        }
        counter.setLoaderFinished();
    }
}
