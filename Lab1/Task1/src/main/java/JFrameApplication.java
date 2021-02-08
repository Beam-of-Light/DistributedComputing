import javax.swing.*;
import java.awt.*;

public class JFrameApplication {
    private JFrame frame;
    private JPanel panelFlow;
    private JPanel panelGrid;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JButton buttonStart;
    private JProgressBar progressBar;

    private static final int THREAD_VALUE_1 = 10;
    private static final int THREAD_VALUE_2 = 90;
    private static final int THREAD_SPEED = 100;

    private Thread thread1;
    private Thread thread2;
    private boolean started = false;


    JFrameApplication() {
        frame = new JFrame("Task1_A");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.setSize(500, 500);
        frame.setLocation(400, 200);

        initializeThreads();
        initializePanel();

        frame.getContentPane().add(panelFlow);
        frame.pack();
        frame.setVisible(true);
    }

    private Thread createThread(int setValue) {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (progressBar) {
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
            }
        });
        thread.setPriority(1);
        thread.setDaemon(true);
        return thread;
    }

    private void initializeThreads() {
        thread1 = createThread(THREAD_VALUE_1);
        thread2 = createThread(THREAD_VALUE_2);
    }

    private void initializePanel() {
        panelFlow = new JPanel((new FlowLayout(FlowLayout.CENTER, 15, 15)));
        panelGrid = new JPanel(new GridLayout(3, 2, 25, 25));

        JLabel label1 = new JLabel("Пріорітет першого потоку:");
        spinner1 = createSpinner(thread1);

        JLabel label2 = new JLabel("Пріорітет другого потоку:");
        spinner2 = createSpinner(thread2);

        buttonStart = createButton();
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setValue(50);

        panelGrid.add(label1);
        panelGrid.add(spinner1);
        panelGrid.add(label2);
        panelGrid.add(spinner2);
        panelGrid.add(progressBar);
        panelGrid.add(buttonStart);
        panelFlow.add(panelGrid);
    }

    private JSpinner createSpinner(Thread thread) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        spinner.addChangeListener(e -> {
            JSpinner s = (JSpinner) e.getSource();
            thread.setPriority((Integer) s.getValue());
        });
        return spinner;
    }

    private JButton createButton() {
        buttonStart = new JButton("Пуск");
        buttonStart.addActionListener(e -> {
            if (!started) {
                thread1.start();
                thread2.start();
                started = true;
                ((JButton) e.getSource()).setEnabled(false);
            }
        });
        return buttonStart;
    }

    public static void main(String[] args) {
        JFrameApplication app = new JFrameApplication();
    }
}
