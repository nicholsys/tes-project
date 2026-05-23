import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        
        JFrame frame = new JFrame("Game 01");
        frame.setSize(1920, 1090);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);

        Game panel = new Game();

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        
        frame.setVisible(true);
    }
}
