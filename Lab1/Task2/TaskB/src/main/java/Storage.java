import java.util.LinkedList;
import java.util.Random;

public class Storage {
    private LinkedList<Item> items;
    private static final int MIN_PRICE = 10;
    private static final int MAX_PRICE = 100;

    Storage(int amount) {
        items = new LinkedList<>();
        Random random = new Random();
        int randomPrice, total = 0;

        System.out.println("Prices: ");
        for (int i = 0; i < amount; ++i) {
            randomPrice = random.nextInt(MAX_PRICE - MIN_PRICE) + MIN_PRICE;
            total += randomPrice;
            System.out.print(randomPrice + " ");
            items.add(new Item(randomPrice));
        }
        System.out.print(" - " + total + "\n");
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Item removeItem() {
        return items.remove();
    }
}
