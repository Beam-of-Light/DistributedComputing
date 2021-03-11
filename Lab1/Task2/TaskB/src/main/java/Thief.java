import java.util.Queue;

public class Thief implements Runnable {
    private Storage storage;
    private Queue<Item> loaderBuffer;
    private Loader loader;

    Thief(Storage storage, Queue<Item> loaderBuffer, Loader loader) {
        this.storage = storage;
        this.loaderBuffer = loaderBuffer;
        this.loader = loader;
    }

    @Override
    public void run() {
        while (!storage.isEmpty()) {
            synchronized (loaderBuffer) {
                loaderBuffer.add(storage.removeItem());
            }
        }
        loader.setStorageEmpty();
    }

}
