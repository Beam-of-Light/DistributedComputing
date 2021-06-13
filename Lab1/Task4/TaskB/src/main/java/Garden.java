import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Garden {
    List<List<Boolean>> gardenArray;
    int MAX_GARDEN_SIZE = 10;
    Random random;
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Garden() {
        random = new Random();
        random.setSeed(System.nanoTime());

        gardenArray = new ArrayList<>();
        int gardenSize = random.nextInt(MAX_GARDEN_SIZE - 1) + 1;
        for (int i = 0; i < gardenSize; ++i) {
            gardenArray.add(new ArrayList<>());
            for (int j = 0; j < gardenSize; ++j) {
                gardenArray.get(i).add(random.nextBoolean());
            }
        }
    }

    public void printGarden() {
        for (int i = 0, gardenSize = gardenArray.size(); i < gardenSize; ++i) {
            for (int j = 0; j < gardenSize; ++j) {
                System.out.print(((gardenArray.get(i).get(j)) ? "1" : "0") + " ");
            }
            System.out.println();
        }
    }

    public Thread getThreadGardener() {
        return new Thread(() -> {
            while (true) {
                lock.readLock().lock();
                for (int i = 0, gardenSize = gardenArray.size(); i < gardenSize; ++i) {
                    for (int j = 0; j < gardenSize; ++j) {
                        if (!gardenArray.get(i).get(j)) {
                            lock.readLock().unlock();
                            lock.writeLock().lock();

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            System.out.println("Watered plant");
                            gardenArray.get(i).set(j, true);
                            lock.writeLock().unlock();
                            lock.readLock().lock();
                        }

                    }
                }
                lock.readLock().unlock();
            }
        });
    }

    public Thread getThreadNature() {
        return new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    lock.writeLock().lock();

                    int gardenSize = gardenArray.size();
                    gardenArray.get(random.nextInt(gardenSize)).set(random.nextInt(gardenSize), random.nextBoolean());
                    System.out.println("Nature changed");

                    lock.writeLock().unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Thread getThreadMonitorToFile() {
        return new Thread(() -> {
            try {
                FileWriter fileWriter = new FileWriter("out.txt", false);
                fileWriter.close();
                while (true) {
                    Thread.sleep(3000);
                    fileWriter = new FileWriter("out.txt", true);
                    System.out.println("Writing to file");
                    lock.readLock().lock();
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    for (int i = 0, gardenSize = gardenArray.size(); i < gardenSize; ++i) {
                        for (int j = 0; j < gardenSize; ++j) {
                            bufferedWriter.write(((gardenArray.get(i).get(j)) ? "1" : "0") + " ");
                        }
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    lock.readLock().unlock();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public Thread getThreadMonitorToConsole() {
        return new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(4000);
                    lock.readLock().lock();
                    printGarden();
                    lock.readLock().unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        Garden g = new Garden();
        g.printGarden();

        Thread gardener = g.getThreadGardener();
        Thread nature = g.getThreadNature();
        Thread fileMonitor = g.getThreadMonitorToFile();
        Thread consoleMonitor = g.getThreadMonitorToConsole();

        gardener.start();
        nature.start();
        fileMonitor.start();
        consoleMonitor.start();
    }
}
