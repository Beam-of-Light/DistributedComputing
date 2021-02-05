import javax.swing.*;
import java.awt.*;

public class JFrameApplication {
    private JFrame frame;
    private JPanel panelFlow;
    private JPanel panelGrid;
    private JButton buttonStart;
    private JProgressBar progressBar;

    JFrameApplication() {
        frame = new JFrame("Task1_A");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.setSize(500, 500);
        frame.setLocation(400, 200);

        initializePanel();


        frame.getContentPane().add(panelFlow);
        frame.pack();
        frame.setVisible(true);
    }

    void initializePanel() {
        panelFlow = new JPanel((new FlowLayout(FlowLayout.CENTER, 15, 15)));
        panelGrid = new JPanel(new GridLayout(3, 2, 25, 25));

        JLabel label1 = new JLabel("Пріорітет першого потоку:");
        JSpinner spinner1 = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        ((JSpinner.DefaultEditor) spinner1.getEditor()).getTextField().setEditable(false);

        JLabel label2 = new JLabel("Пріорітет другого потоку:");
        JSpinner spinner2 = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        ((JSpinner.DefaultEditor) spinner2.getEditor()).getTextField().setEditable(false);

        buttonStart = new JButton("Пуск");
        progressBar = new JProgressBar();

        panelGrid.add(label1);
        panelGrid.add(spinner1);
        panelGrid.add(label2);
        panelGrid.add(spinner2);
        panelGrid.add(progressBar);
        panelGrid.add(buttonStart);
        panelFlow.add(panelGrid);
    }

    public static void main(String[] args) {
        JFrameApplication app = new JFrameApplication();
    }
}
