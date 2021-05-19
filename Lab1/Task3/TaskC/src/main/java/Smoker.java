import java.util.concurrent.Semaphore;

public class Smoker implements Runnable {
    private SmokingItem smokingItem;
    private Semaphore semaphore;
    private Mediator mediator;
    private String smokerItemName;

    Smoker(SmokingItem smokingItem, Semaphore semaphore, Mediator mediator, String smokerItemName) {
        this.smokingItem = smokingItem;
        this.semaphore = semaphore;
        this.mediator = mediator;
        this.smokerItemName = smokerItemName;
    }

    private boolean checkSmokingItems(int smokingItemsID) {
        return smokingItem.ordinal() == smokingItemsID;
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaphore.acquire();
                if (checkSmokingItems(mediator.smokingItemsID.get())) {
                    System.out.println("Smoker(" + smokerItemName + "):\t...smoking...");
                    Thread.sleep(300);
                    mediator.smokingItemsID.set(-1);
                }
                semaphore.release();

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
