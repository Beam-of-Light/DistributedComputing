package TaskB;

import javax.swing.*;
import java.awt.*;

public class JFrameApplicationB {
    private JFrame frame;
    private JPanel panelFlow;
    private JPanel panelBorder;
    private JPanel panelGrid;
    private JButton buttonStart1;
    private JButton buttonStart2;
    private JButton buttonStop1;
    private JButton buttonStop2;
    private JProgressBar progressBar;
    private JLabel status;

    private static final int THREAD_VALUE_1 = 10;
    private static final int THREAD_VALUE_2 = 90;
    private static final int THREAD_SPEED = 100;

    private Thread thread1;
    private Thread thread2;
    private Semaphore semaphore = new Semaphore();

    private static final String STATUS_FREE = "Статус: вільно";
    private static final String STATUS_BUSY = "Статус: зайнято потоком";


    JFrameApplicationB() {
        frame = new JFrame("Task1_B");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.setSize(500, 500);
        frame.setLocation(400, 200);

        initializePanel();

        frame.getContentPane().add(panelFlow);
        frame.pack();
        frame.setVisible(true);
    }

    private void initializePanel() {
        panelFlow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBorder = new JPanel(new BorderLayout(25, 25));
        panelGrid = new JPanel(new GridLayout(2, 3, 25, 25));

        JLabel label1 = new JLabel("Керування першим потоком:");
        JLabel label2 = new JLabel("Керування другим потоком:");

        buttonStart1 = new JButton("Пуск 1");
        buttonStart2 = new JButton("Пуск 2");
        buttonStop1 = new JButton("Стоп 1");
        buttonStop2 = new JButton("Стоп 2");

        // buttonStop1.setEnabled(false);
        // buttonStop2.setEnabled(false);

        buttonStart1.addActionListener(e -> onButtonStart1());
        buttonStart2.addActionListener(e -> onButtonStart2());
        buttonStop1.addActionListener(e -> onButtonStop1());
        buttonStop2.addActionListener(e -> onButtonStop2());

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setValue(50);

        status = new JLabel(STATUS_FREE);

        panelGrid.add(label1);
        panelGrid.add(buttonStart1);
        panelGrid.add(buttonStop1);
        panelGrid.add(label2);
        panelGrid.add(buttonStart2);
        panelGrid.add(buttonStop2);

        panelBorder.add(panelGrid, BorderLayout.NORTH);
        panelBorder.add(progressBar, BorderLayout.CENTER);
        panelBorder.add(status, BorderLayout.SOUTH);

        panelFlow.add(panelBorder);
    }

    private void onButtonStart1() {
        if (semaphore.acquire()) {
            thread1 = createThread(THREAD_VALUE_1);
            thread1.setPriority(1);
            thread1.setDaemon(true);
            thread1.start();

            buttonStop2.setEnabled(false);
            status.setText(STATUS_BUSY);
        }
    }

    private void onButtonStart2() {
        if (semaphore.acquire()) {
            thread2 = createThread(THREAD_VALUE_2);
            thread2.setPriority(10);
            thread2.setDaemon(true);
            thread2.start();

            buttonStop1.setEnabled(false);
            status.setText(STATUS_BUSY);
        }
    }

    private void onButtonStop1() {
        if (thread1 != null && thread1.isAlive()) {
            thread1.interrupt();
            semaphore.release();

            buttonStop2.setEnabled(true);
            status.setText(STATUS_FREE);
        }
    }

    private void onButtonStop2() {
        if (thread2 != null && thread2.isAlive()) {
            thread2.interrupt();
            semaphore.release();

            buttonStop1.setEnabled(true);
            status.setText(STATUS_FREE);
        }
    }

    private Thread createThread(int setValue) {
        return new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                int currentValue = progressBar.getValue();
                if (setValue == THREAD_VALUE_1 && currentValue > THREAD_VALUE_1) {
                    progressBar.setValue(currentValue - 1);
                } else if (setValue == THREAD_VALUE_2 && currentValue < THREAD_VALUE_2) {
                    progressBar.setValue(currentValue + 1);
                }
                try {
                    Thread.sleep(THREAD_SPEED);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                Thread.yield();
            }
        });
    }

    public static void main(String[] args) {
        JFrameApplicationB app = new JFrameApplicationB();
    }
}
