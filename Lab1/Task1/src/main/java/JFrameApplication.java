import javax.swing.*;

public class JFrameApplication {
    private JFrame frame;

    JFrameApplication() {
        frame = new JFrame("Task1_A");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrameApplication app = new JFrameApplication();
    }
}
