import entity.Record;
import model.Reader;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Main {
    public static void main(String[] args) throws IOException {
        run();
    }


    private static final int BUFFER_SIZE = 64;

    public static void run() throws IOException {
        Path path = Paths.get("out.txt");
        RandomAccessFile file = new RandomAccessFile(path.toFile(), "rw");
        FileChannel fileChannel = file.getChannel();
        fileChannel.truncate(0);

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        Record record1 = new Record(
                "Prokopchuk",
                "Vladyslav",
                "Andriyovych",
                "+380996542669");
        Record record2 = new Record(
                "Shevchenko",
                "Taras",
                "Hryhorovych",
                "+546548987546");
        Record record3 = new Record(
                "Ivanov",
                "Ivan",
                "Ivanovych",
                "+5465487981545");

        model.Writer writer = new model.Writer(lock, fileChannel, BUFFER_SIZE);
        model.Reader reader = new Reader(lock, fileChannel, BUFFER_SIZE);

        List<Thread> threadList = new LinkedList<>(Arrays.asList(
                writer.getThreadToAddRecord(record1),
                writer.getThreadToAddRecord(record2),
                writer.getThreadToAddRecord(record3),
                reader.getThreadToFindByPhone("+5465487981545"),
                reader.getThreadToFindByFamilyName("Shevchenko"),
                writer.getThreadToDeleteRecord(record2)
        ));

        for (Thread t : threadList) {
            t.start();
        }
        try {
            for (Thread t : threadList) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
