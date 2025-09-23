import Configs.Config;
import Game.Logic;
import Gui.MyPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        JFrame frame = new JFrame();
        Logic logic = new Logic();
        Config config = new Config();
        MyPanel panel = new MyPanel(logic);
        int screenWidth, screenHeight, windowWidth, windowHeight;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        windowWidth = config.getIntConfig("windowWidth");
        windowHeight = config.getIntConfig("windowHeight");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(windowWidth, windowHeight));
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
        frame.setVisible(true);
        frame.setContentPane(panel);
        logic.startGame(frame);
    }
}
